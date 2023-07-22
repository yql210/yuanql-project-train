package top.yuanql.train.member.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.PassengerQueryReq;
import top.yuanql.train.member.req.PassengerSaveReq;
import top.yuanql.train.member.response.PassengerQueryResp;


public interface PassengerService {

    public void save(PassengerSaveReq passengerSaveReq);

    public PageResp<PassengerQueryResp> querList(PassengerQueryReq req);

    void delete(Long id);
}
