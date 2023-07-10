package top.yuanql.train.member.service;

import top.yuanql.train.member.req.MemberLoginReq;
import top.yuanql.train.member.req.MemberRegisterReq;
import top.yuanql.train.member.req.MemberSendCodeReq;
import top.yuanql.train.member.response.MemberLoginResp;

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


    public long register(MemberRegisterReq req);

    public void sendCode(MemberSendCodeReq req);

    public MemberLoginResp login(MemberLoginReq req);
}
