package top.yuanql.train.member.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.PassengerQueryReq;
import top.yuanql.train.member.req.PassengerSaveReq;
import top.yuanql.train.member.response.PassengerQueryResp;

import java.util.List;


public interface PassengerService {

    public void save(PassengerSaveReq passengerSaveReq);

    public PageResp<PassengerQueryResp> querList(PassengerQueryReq req);

    void delete(Long id);

    /**
     * 查询我的所有乘客
     */
    public List<PassengerQueryResp> queryMine();
}
