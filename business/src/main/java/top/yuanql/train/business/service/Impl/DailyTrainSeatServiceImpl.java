package top.yuanql.train.business.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yuanql.train.business.config.BusinessApplication;
import top.yuanql.train.business.domain.DailyTrainSeat;
import top.yuanql.train.business.domain.DailyTrainSeatExample;
import top.yuanql.train.business.domain.TrainSeat;
import top.yuanql.train.business.domain.TrainStation;
import top.yuanql.train.business.mapper.DailyTrainSeatMapper;
import top.yuanql.train.business.req.DailyTrainSeatQueryReq;
import top.yuanql.train.business.req.DailyTrainSeatSaveReq;
import top.yuanql.train.business.response.DailyTrainSeatQueryResp;
import top.yuanql.train.business.service.DailyTrainSeatService;
import top.yuanql.train.business.service.TrainSeatService;
import top.yuanql.train.business.service.TrainStationService;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;

import java.util.Date;
import java.util.List;


@Service
public class DailyTrainSeatServiceImpl implements DailyTrainSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    @Resource
    private TrainSeatService trainSeatService;

    @Resource
    private TrainStationService trainStationService;

    @Override
    public void save(DailyTrainSeatSaveReq dailyTrainSeatSaveReq) {
        DateTime now = DateTime.now();
        DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(dailyTrainSeatSaveReq, DailyTrainSeat.class);
        if (ObjectUtil.isNull(dailyTrainSeat.getId())) {
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        } else {
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.updateByPrimaryKey(dailyTrainSeat);

        }
    }

    @Override
    public PageResp<DailyTrainSeatQueryResp> querList(DailyTrainSeatQueryReq req) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("date desc, train_code asc, carriage_index asc, carriage_seat_index asc");
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();

        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainSeat> dailyTrainSeatList = dailyTrainSeatMapper.selectByExample(dailyTrainSeatExample);

        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeatList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainSeatQueryResp> dailyTrainSeatQueryResps = BeanUtil.copyToList(dailyTrainSeatList, DailyTrainSeatQueryResp.class);

        PageResp<DailyTrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(dailyTrainSeatQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void genDaily(Date date, String trainCode) {
        LOG.info("开始生成日期【{}】车次【{}】的座位信息", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的车站信息
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode);

        dailyTrainSeatMapper.deleteByExample(dailyTrainSeatExample);

        List<TrainStation> trainStations = trainStationService.selectByTrainCode(trainCode);
        String sell = StrUtil.fillBefore("", '0', trainStations.size() - 1);

        // 生成该车站的数据
        List<TrainSeat> trainSeats = trainSeatService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(trainSeats)) {
            LOG.info("该车次没有座位基础数据，生成该车站座位信息结束");
            return;
        }
        for (TrainSeat trainSeat: trainSeats) {
            DateTime now = DateTime.now();

            DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(trainSeat, DailyTrainSeat.class);
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());

            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeat.setDate(date);
            dailyTrainSeat.setSell(sell);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
        LOG.info("结束生成日期【{}】车次【{}】的座位信息", DateUtil.formatDate(date), trainCode);
    }

    @Override
    public int countSeat(Date date, String trainCode, String seatType) {
        DailyTrainSeatExample example = new DailyTrainSeatExample();
        example.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andSeatTypeEqualTo(seatType);
        long l = dailyTrainSeatMapper.countByExample(example);
        if (l == 0L) {
            return -1;
        }
        return Math.toIntExact(l);
    }

    @Override
    public List<DailyTrainSeat> selectByCarriage (Date date, String trainCode, Integer carriageIndex) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("carriage_seat_index asc");
        dailyTrainSeatExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andCarriageIndexEqualTo(carriageIndex);
        return dailyTrainSeatMapper.selectByExample(dailyTrainSeatExample);
    }

}
