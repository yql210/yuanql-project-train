package top.yuanql.train.business.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.business.config.BusinessApplication;
import top.yuanql.train.business.domain.DailyTrainStation;
import top.yuanql.train.business.domain.DailyTrainStationExample;
import top.yuanql.train.business.domain.TrainStation;
import top.yuanql.train.business.mapper.DailyTrainStationMapper;
import top.yuanql.train.business.req.DailyTrainStationQueryReq;
import top.yuanql.train.business.req.DailyTrainStationSaveReq;
import top.yuanql.train.business.response.DailyTrainStationQueryResp;
import top.yuanql.train.business.service.DailyTrainStationService;
import top.yuanql.train.business.service.TrainStationService;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;

import java.util.Date;
import java.util.List;


@Service
public class DailyTrainStationServiceImpl implements DailyTrainStationService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private DailyTrainStationMapper dailyTrainStationMapper;

    @Resource
    private TrainStationService trainStationService;

    @Override
    public void save(DailyTrainStationSaveReq dailyTrainStationSaveReq) {
        DateTime now = DateTime.now();
        DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(dailyTrainStationSaveReq, DailyTrainStation.class);
        if (ObjectUtil.isNull(dailyTrainStation.getId())) {
            dailyTrainStation.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.insert(dailyTrainStation);
        } else {
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.updateByPrimaryKey(dailyTrainStation);

        }
    }

    @Override
    public PageResp<DailyTrainStationQueryResp> querList(DailyTrainStationQueryReq req) {
        DailyTrainStationExample dailyTrainStationExample = new DailyTrainStationExample();
        dailyTrainStationExample.setOrderByClause("date desc, train_code asc, `index` asc");
        DailyTrainStationExample.Criteria criteria = dailyTrainStationExample.createCriteria();

        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }
        if (ObjectUtil.isNotNull(req.getDate())) {
            criteria.andDateEqualTo(req.getDate());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainStation> dailyTrainStationList = dailyTrainStationMapper.selectByExample(dailyTrainStationExample);

        PageInfo<DailyTrainStation> pageInfo = new PageInfo<>(dailyTrainStationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainStationQueryResp> dailyTrainStationQueryResps = BeanUtil.copyToList(dailyTrainStationList, DailyTrainStationQueryResp.class);

        PageResp<DailyTrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(dailyTrainStationQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        dailyTrainStationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void genDaily(Date date, String trainCode) {
        LOG.info("开始生成日期【{}】车次【{}】的车站信息", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的车站信息
        DailyTrainStationExample dailyTrainStationExample = new DailyTrainStationExample();
        dailyTrainStationExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode);

        dailyTrainStationMapper.deleteByExample(dailyTrainStationExample);

        // 生成该车站的数据
        List<TrainStation> trainStations = trainStationService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(trainStations)) {
            LOG.info("该车次没有车站基础数据，生成该车次车站信息结束");
            return;
        }
        for (TrainStation trainStation: trainStations) {
            DateTime now = DateTime.now();

            DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(trainStation, DailyTrainStation.class);
            dailyTrainStation.setId(SnowUtil.getSnowflakeNextId());

            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStation.setDate(date);
            dailyTrainStationMapper.insert(dailyTrainStation);
        }
        LOG.info("结束生成日期【{}】车次【{}】的车站信息", DateUtil.formatDate(date), trainCode);
    }
}
