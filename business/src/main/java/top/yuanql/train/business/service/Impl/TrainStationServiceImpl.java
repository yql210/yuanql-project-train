package top.yuanql.train.business.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.business.config.BusinessApplication;
import top.yuanql.train.business.domain.TrainStation;
import top.yuanql.train.business.domain.TrainStationExample;
import top.yuanql.train.business.mapper.TrainStationMapper;
import top.yuanql.train.business.req.TrainStationQueryReq;
import top.yuanql.train.business.req.TrainStationSaveReq;
import top.yuanql.train.business.response.TrainStationQueryResp;
import top.yuanql.train.business.service.TrainStationService;
import top.yuanql.train.common.exception.BusinessException;
import top.yuanql.train.common.exception.BusinessExceptionEnum;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;

import java.util.List;


@Service
public class TrainStationServiceImpl implements TrainStationService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private TrainStationMapper trainStationMapper;

    @Override
    public void save(TrainStationSaveReq trainStationSaveReq) {
        DateTime now = DateTime.now();
        TrainStation trainStation = BeanUtil.copyProperties(trainStationSaveReq, TrainStation.class);
        if (ObjectUtil.isNull(trainStation.getId())) {

            // 保存之前，先校验唯一键是否存在
            TrainStation trainStationDB = selectBuUnique(trainStationSaveReq.getTrainCode(), trainStationSaveReq.getIndex());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR);
            }
            trainStationDB = selectBuUnique(trainStationSaveReq.getTrainCode(), trainStationSaveReq.getName());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR);
            }

            trainStation.setId(SnowUtil.getSnowflakeNextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            trainStationMapper.insert(trainStation);
        } else {
            trainStation.setUpdateTime(now);
            trainStationMapper.updateByPrimaryKey(trainStation);

        }
    }

    private TrainStation selectBuUnique(String trainCode, Integer index) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria()
                .andTrainCodeEqualTo(trainCode)
                .andIndexEqualTo(index);
        List<TrainStation> list = trainStationMapper.selectByExample(trainStationExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    private TrainStation selectBuUnique(String trainCode, String name) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria()
                .andTrainCodeEqualTo(trainCode)
                .andNameEqualTo(name);
        List<TrainStation> list = trainStationMapper.selectByExample(trainStationExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public PageResp<TrainStationQueryResp> querList(TrainStationQueryReq req) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.setOrderByClause("train_code asc, `index` asc");
        TrainStationExample.Criteria criteria = trainStationExample.createCriteria();

        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainStation> trainStationList = trainStationMapper.selectByExample(trainStationExample);

        PageInfo<TrainStation> pageInfo = new PageInfo<>(trainStationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainStationQueryResp> trainStationQueryResps = BeanUtil.copyToList(trainStationList, TrainStationQueryResp.class);

        PageResp<TrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(trainStationQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        trainStationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TrainStation> selectByTrainCode(String trainCode) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.setOrderByClause("`index` asc");
        trainStationExample.createCriteria()
                .andTrainCodeEqualTo(trainCode);
        return trainStationMapper.selectByExample(trainStationExample);
    }
}
