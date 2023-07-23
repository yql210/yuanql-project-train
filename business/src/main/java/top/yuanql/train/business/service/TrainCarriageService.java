package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.TrainCarriageQueryReq;
import top.yuanql.train.business.req.TrainCarriageSaveReq;
import top.yuanql.train.business.response.TrainCarriageQueryResp;


public interface TrainCarriageService {

    public void save(TrainCarriageSaveReq trainCarriageSaveReq);

    public PageResp<TrainCarriageQueryResp> querList(TrainCarriageQueryReq req);

    void delete(Long id);
}
