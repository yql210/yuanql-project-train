package top.yuanql.train.member.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.PassengerQueryReq;
import top.yuanql.train.member.req.PassengerSaveReq;
import top.yuanql.train.member.response.PassengerResp;


public interface PassengerService {

    public void save(PassengerSaveReq passengerSaveReq);

    public PageResp<PassengerResp> querList(PassengerQueryReq req);

    void delete(Long id);
}
