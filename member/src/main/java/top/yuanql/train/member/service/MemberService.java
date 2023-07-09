package top.yuanql.train.member.service;

import org.apache.ibatis.annotations.Param;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.member.service
 * @BelongsClassName: MemberService
 * @Author: yuanql
 * @CreateTime: 2023-07-09  18:48
 * @Description: member 的 service层接口层实现
 * @Version: 1.0
 */


public interface MemberService {

    public int count();


    public long register(@Param("mobile") String mobile);
}
