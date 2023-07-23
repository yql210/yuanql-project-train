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
import top.yuanql.train.business.domain.Train;
import top.yuanql.train.business.domain.TrainExample;
import top.yuanql.train.business.mapper.TrainMapper;
import top.yuanql.train.business.req.TrainQueryReq;
import top.yuanql.train.business.req.TrainSaveReq;
import top.yuanql.train.business.response.TrainQueryResp;
import top.yuanql.train.business.service.TrainService;

import java.util.List;


@Service
public class TrainServiceImpl implements TrainService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private TrainMapper trainMapper;

    @Override
    public void save(TrainSaveReq trainSaveReq) {
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(trainSaveReq, Train.class);
        if (ObjectUtil.isNull(train.getId())) {
            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        } else {
            train.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(train);

        }
    }

    @Override
    public PageResp<TrainQueryResp> querList(TrainQueryReq req) {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("id desc");
        TrainExample.Criteria criteria = trainExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Train> trainList = trainMapper.selectByExample(trainExample);

        PageInfo<Train> pageInfo = new PageInfo<>(trainList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainQueryResp> trainQueryResps = BeanUtil.copyToList(trainList, TrainQueryResp.class);

        PageResp<TrainQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(trainQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        trainMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<TrainQueryResp> querAll() {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("code asc");

        List<Train> trainList = trainMapper.selectByExample(trainExample);

        List<TrainQueryResp> trainQueryResps = BeanUtil.copyToList(trainList, TrainQueryResp.class);
        return trainQueryResps;
    }

}
