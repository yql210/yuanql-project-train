package top.yuanql.train.member.service.Impl;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.common.exception.BusinessException;
import top.yuanql.train.common.exception.BusinessExceptionEnum;
import top.yuanql.train.common.util.SnowUtil;
import top.yuanql.train.member.conf.MemberApplication;
import top.yuanql.train.member.domain.Member;
import top.yuanql.train.member.domain.MemberExample;
import top.yuanql.train.member.mapper.MemberMapper;
import top.yuanql.train.member.req.MemberRegisterReq;
import top.yuanql.train.member.req.MemberSendCodeReq;
import top.yuanql.train.member.service.MemberService;

import java.util.List;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.member.service.Impl
 * @BelongsClassName: MenberServiceImpl
 * @Author: yuanql
 * @CreateTime: 2023-07-09  18:50
 * @Description: menber 的 service 层的实现
 * @Version: 1.0
 */

@Service
public class MenberServiceImpl implements MemberService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

    @Resource
    private MemberMapper memberMapper;

    @Override
    public int count() {

        return Math.toIntExact(memberMapper.countByExample(null));
    }

    @Override
    public long register(MemberRegisterReq req) {

        String mobile = req.getMobile();

        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);

        if (CollUtil.isNotEmpty(members)) {
//            return members.get(0).getId();
//            throw new RuntimeException("此手机号已注册，请勿重复注册");
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);

        int insert = memberMapper.insert(member);

        return member.getId();
    }

    @Override
    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();

        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);

        // 如果手机号没有在用户表中，则插入一条放入到用户表中
        if (CollUtil.isEmpty(members)) {
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            int insert = memberMapper.insert(member);
        }

        // 生成随机的验证码
//        String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成的验证码为： {}", code);

        // 保存短信记录表：手机号、短信验证码、有效期、是否已使用、业务类型、发送时间、使用时间
        LOG.info("保存短信记录表");

        // 对接短信通道，发送短信
        LOG.info("对接短信通道");

    }
}
