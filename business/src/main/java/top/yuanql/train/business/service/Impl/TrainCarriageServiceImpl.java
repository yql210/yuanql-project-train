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
import top.yuanql.train.business.domain.TrainCarriage;
import top.yuanql.train.business.domain.TrainCarriageExample;
import top.yuanql.train.business.enums.SeatColEnum;
import top.yuanql.train.business.mapper.TrainCarriageMapper;
import top.yuanql.train.business.req.TrainCarriageQueryReq;
import top.yuanql.train.business.req.TrainCarriageSaveReq;
import top.yuanql.train.business.response.TrainCarriageQueryResp;
import top.yuanql.train.business.service.TrainCarriageService;
import top.yuanql.train.common.exception.BusinessException;
import top.yuanql.train.common.exception.BusinessExceptionEnum;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;

import java.util.List;


@Service
public class TrainCarriageServiceImpl implements TrainCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    @Override
    public void save(TrainCarriageSaveReq trainCarriageSaveReq) {
        DateTime now = DateTime.now();

        // 自动计算出列数和总座位数
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(trainCarriageSaveReq.getSeatType());
        trainCarriageSaveReq.setColCount(seatColEnums.size());
        trainCarriageSaveReq.setSeatCount(trainCarriageSaveReq.getColCount() * trainCarriageSaveReq.getRowCount());

        TrainCarriage trainCarriage = BeanUtil.copyProperties(trainCarriageSaveReq, TrainCarriage.class);
        if (ObjectUtil.isNull(trainCarriage.getId())) {

            // 保存之前，先校验唯一键是否存在
            TrainCarriage trainCarriageDB = selectBuUnique(trainCarriageSaveReq.getTrainCode(), trainCarriageSaveReq.getIndex());

            if (ObjectUtil.isNotEmpty(trainCarriageDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CARRIAGE_INDEX_UNIQUE_ERROR);
            }

            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        } else {
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.updateByPrimaryKey(trainCarriage);

        }
    }

    private TrainCarriage selectBuUnique(String trainCode, Integer index) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.createCriteria()
                .andTrainCodeEqualTo(trainCode)
                .andIndexEqualTo(index);
        List<TrainCarriage> list = trainCarriageMapper.selectByExample(trainCarriageExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public PageResp<TrainCarriageQueryResp> querList(TrainCarriageQueryReq req) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("train_code asc, `index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();

        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainCarriage> trainCarriageList = trainCarriageMapper.selectByExample(trainCarriageExample);

        PageInfo<TrainCarriage> pageInfo = new PageInfo<>(trainCarriageList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainCarriageQueryResp> trainCarriageQueryResps = BeanUtil.copyToList(trainCarriageList, TrainCarriageQueryResp.class);

        PageResp<TrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(trainCarriageQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        trainCarriageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TrainCarriage> selectByTrainCode(String trainCode) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("`index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode);

        return trainCarriageMapper.selectByExample(trainCarriageExample);
    }
}
