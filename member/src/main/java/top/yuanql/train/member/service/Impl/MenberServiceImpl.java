package top.yuanql.train.member.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
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
import top.yuanql.train.member.req.MemberLoginReq;
import top.yuanql.train.member.req.MemberRegisterReq;
import top.yuanql.train.member.req.MemberSendCodeReq;
import top.yuanql.train.member.response.MemberLoginResp;
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

        Member memberDB = selectByMobile(mobile);

        if (ObjectUtil.isNotNull(memberDB)) {
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

        Member members = selectByMobile(mobile);

        // 如果手机号没有在用户表中，则插入一条放入到用户表中
        if (ObjectUtil.isNull(members)) {
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

    @Override
    public MemberLoginResp login(MemberLoginReq req) {

        String code = req.getCode();
        String mobile = req.getMobile();
        Member members = selectByMobile(mobile);

        if (ObjectUtil.isNull(members)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        if (!"8888".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        return BeanUtil.copyProperties(members, MemberLoginResp.class);
    }

    /**
     * 抽取除的函数，其主要用于判断数据库中是否存在传入的 mobile 电话号码
     * @param mobile 传入的电话号码，11位
     * @return
     */
    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(members)) {
            return null;
        } else {
            return members.get(0);
        }
    }
}
