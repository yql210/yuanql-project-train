package top.yuanql.train.member.service.Impl;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yuanql.train.member.domain.Member;
import top.yuanql.train.member.domain.MemberExample;
import top.yuanql.train.member.mapper.MemberMapper;
import top.yuanql.train.member.req.MemberRegisterReq;
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
            throw new RuntimeException("此手机号已注册，请勿重复注册");
        }

        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);

        int insert = memberMapper.insert(member);

        return member.getId();
    }
}
