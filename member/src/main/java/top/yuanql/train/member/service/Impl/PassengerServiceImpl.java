package top.yuanql.train.member.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.common.util.SnowUtil;
import top.yuanql.train.member.conf.MemberApplication;
import top.yuanql.train.member.domain.Passenger;
import top.yuanql.train.member.mapper.PassengerMapper;
import top.yuanql.train.member.req.PassengerSaveReq;
import top.yuanql.train.member.service.PassengerService;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.member.service.Impl
 * @BelongsClassName: PassengerServiceImpl
 * @Author: yuanql
 * @CreateTime: 2023-07-20  18:37
 * @Description: 乘客
 * @Version: 1.0
 */

@Service
public class PassengerServiceImpl implements PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

    @Resource
    private PassengerMapper passengerMapper;

    @Override
    public void save(PassengerSaveReq passengerSaveReq) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(passengerSaveReq, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
}
