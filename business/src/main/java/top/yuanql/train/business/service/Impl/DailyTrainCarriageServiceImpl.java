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
import top.yuanql.train.business.domain.DailyTrainCarriage;
import top.yuanql.train.business.domain.DailyTrainCarriageExample;
import top.yuanql.train.business.mapper.DailyTrainCarriageMapper;
import top.yuanql.train.business.req.DailyTrainCarriageQueryReq;
import top.yuanql.train.business.req.DailyTrainCarriageSaveReq;
import top.yuanql.train.business.response.DailyTrainCarriageQueryResp;
import top.yuanql.train.business.service.DailyTrainCarriageService;

import java.util.List;


@Service
public class DailyTrainCarriageServiceImpl implements DailyTrainCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private DailyTrainCarriageMapper dailyTrainCarriageMapper;

    @Override
    public void save(DailyTrainCarriageSaveReq dailyTrainCarriageSaveReq) {
        DateTime now = DateTime.now();
        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(dailyTrainCarriageSaveReq, DailyTrainCarriage.class);
        if (ObjectUtil.isNull(dailyTrainCarriage.getId())) {
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        } else {
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.updateByPrimaryKey(dailyTrainCarriage);

        }
    }

    @Override
    public PageResp<DailyTrainCarriageQueryResp> querList(DailyTrainCarriageQueryReq req) {
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.setOrderByClause("id desc");
        DailyTrainCarriageExample.Criteria criteria = dailyTrainCarriageExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainCarriage> dailyTrainCarriageList = dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);

        PageInfo<DailyTrainCarriage> pageInfo = new PageInfo<>(dailyTrainCarriageList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainCarriageQueryResp> dailyTrainCarriageQueryResps = BeanUtil.copyToList(dailyTrainCarriageList, DailyTrainCarriageQueryResp.class);

        PageResp<DailyTrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(dailyTrainCarriageQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }
}
