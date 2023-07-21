package top.yuanql.train.member.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.PassengerQueryReq;
import top.yuanql.train.member.req.PassengerSaveReq;
import top.yuanql.train.member.response.PassengerResp;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.member.service
 * @BelongsClassName: PassengerService
 * @Author: yuanql
 * @CreateTime: 2023-07-20  18:36
 * @Description: 乘客
 * @Version: 1.0
 */


public interface PassengerService {

    public void save(PassengerSaveReq passengerSaveReq);

    public PageResp<PassengerResp> querList(PassengerQueryReq req);

    void delete(Long id);
}
