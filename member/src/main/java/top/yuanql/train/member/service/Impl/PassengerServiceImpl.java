package top.yuanql.train.member.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.common.context.LoginMemberContext;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;
import top.yuanql.train.member.conf.MemberApplication;
import top.yuanql.train.member.domain.Passenger;
import top.yuanql.train.member.domain.PassengerExample;
import top.yuanql.train.member.mapper.PassengerMapper;
import top.yuanql.train.member.req.PassengerQueryReq;
import top.yuanql.train.member.req.PassengerSaveReq;
import top.yuanql.train.member.response.PassengerResp;
import top.yuanql.train.member.service.PassengerService;

import java.util.List;


@Service
public class PassengerServiceImpl implements PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

    @Resource
    private PassengerMapper passengerMapper;

    @Override
    public void save(PassengerSaveReq passengerSaveReq) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(passengerSaveReq, Passenger.class);
        if (ObjectUtil.isNull(passenger.getId())) {
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);

        }
    }

    @Override
    public PageResp<PassengerResp> querList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        passengerExample.setOrderByClause("id desc");
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);

        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<PassengerResp> passengerResps = BeanUtil.copyToList(passengerList, PassengerResp.class);

        PageResp<PassengerResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(passengerResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
    }
}
