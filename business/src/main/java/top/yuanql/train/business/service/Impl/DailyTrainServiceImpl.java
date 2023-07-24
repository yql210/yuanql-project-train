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
import top.yuanql.train.business.domain.DailyTrain;
import top.yuanql.train.business.domain.DailyTrainExample;
import top.yuanql.train.business.mapper.DailyTrainMapper;
import top.yuanql.train.business.req.DailyTrainQueryReq;
import top.yuanql.train.business.req.DailyTrainSaveReq;
import top.yuanql.train.business.response.DailyTrainQueryResp;
import top.yuanql.train.business.service.DailyTrainService;

import java.util.List;


@Service
public class DailyTrainServiceImpl implements DailyTrainService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private DailyTrainMapper dailyTrainMapper;

    @Override
    public void save(DailyTrainSaveReq dailyTrainSaveReq) {
        DateTime now = DateTime.now();
        DailyTrain dailyTrain = BeanUtil.copyProperties(dailyTrainSaveReq, DailyTrain.class);
        if (ObjectUtil.isNull(dailyTrain.getId())) {
            dailyTrain.setId(SnowUtil.getSnowflakeNextId());
            dailyTrain.setCreateTime(now);
            dailyTrain.setUpdateTime(now);
            dailyTrainMapper.insert(dailyTrain);
        } else {
            dailyTrain.setUpdateTime(now);
            dailyTrainMapper.updateByPrimaryKey(dailyTrain);

        }
    }

    @Override
    public PageResp<DailyTrainQueryResp> querList(DailyTrainQueryReq req) {
        DailyTrainExample dailyTrainExample = new DailyTrainExample();
        dailyTrainExample.setOrderByClause("date desc, code desc");
        DailyTrainExample.Criteria criteria = dailyTrainExample.createCriteria();
        if (ObjectUtil.isNotEmpty(req.getCode())) {
            criteria.andCodeEqualTo(req.getCode());
        }
        if (ObjectUtil.isNotNull(req.getDate())) {
            criteria.andDateEqualTo(req.getDate());
        }


        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrain> dailyTrainList = dailyTrainMapper.selectByExample(dailyTrainExample);

        PageInfo<DailyTrain> pageInfo = new PageInfo<>(dailyTrainList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainQueryResp> dailyTrainQueryResps = BeanUtil.copyToList(dailyTrainList, DailyTrainQueryResp.class);

        PageResp<DailyTrainQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(dailyTrainQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        dailyTrainMapper.deleteByPrimaryKey(id);
    }
}
