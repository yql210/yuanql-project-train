package top.yuanql.train.member.service;

import top.yuanql.train.common.request.MemberTicketReq;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.TicketQueryReq;
import top.yuanql.train.member.response.TicketQueryResp;


public interface TicketService {

    public void save(MemberTicketReq req);

    public PageResp<TicketQueryResp> querList(TicketQueryReq req);

    void delete(Long id);
}
