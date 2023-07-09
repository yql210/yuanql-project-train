package top.yuanql.train.member.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yuanql.train.member.mapper.MemberMapper;
import top.yuanql.train.member.service.MemberService;

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

        return memberMapper.count();
    }
}
