package top.yuanql.train.member.service;

import top.yuanql.train.member.req.PassengerSaveReq;

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

}
