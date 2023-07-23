package top.yuanql.train.business.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.business.config.BusinessApplication;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;
import top.yuanql.train.business.domain.TrainSeat;
import top.yuanql.train.business.domain.TrainSeatExample;
import top.yuanql.train.business.mapper.TrainSeatMapper;
import top.yuanql.train.business.req.TrainSeatQueryReq;
import top.yuanql.train.business.req.TrainSeatSaveReq;
import top.yuanql.train.business.response.TrainSeatQueryResp;
import top.yuanql.train.business.service.TrainSeatService;

import java.util.List;


@Service
public class TrainSeatServiceImpl implements TrainSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private TrainSeatMapper trainSeatMapper;

    @Override
    public void save(TrainSeatSaveReq trainSeatSaveReq) {
        DateTime now = DateTime.now();
        TrainSeat trainSeat = BeanUtil.copyProperties(trainSeatSaveReq, TrainSeat.class);
        if (ObjectUtil.isNull(trainSeat.getId())) {
            trainSeat.setId(SnowUtil.getSnowflakeNextId());
            trainSeat.setCreateTime(now);
            trainSeat.setUpdateTime(now);
            trainSeatMapper.insert(trainSeat);
        } else {
            trainSeat.setUpdateTime(now);
            trainSeatMapper.updateByPrimaryKey(trainSeat);

        }
    }

    @Override
    public PageResp<TrainSeatQueryResp> querList(TrainSeatQueryReq req) {
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        trainSeatExample.setOrderByClause("train_code asc, carriage_index asc, carriage_seat_index asc");
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();

        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainSeat> trainSeatList = trainSeatMapper.selectByExample(trainSeatExample);

        PageInfo<TrainSeat> pageInfo = new PageInfo<>(trainSeatList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainSeatQueryResp> trainSeatQueryResps = BeanUtil.copyToList(trainSeatList, TrainSeatQueryResp.class);

        PageResp<TrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(trainSeatQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        trainSeatMapper.deleteByPrimaryKey(id);
    }
}
