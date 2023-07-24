package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.TrainSeatQueryReq;
import top.yuanql.train.business.req.TrainSeatSaveReq;
import top.yuanql.train.business.response.TrainSeatQueryResp;


public interface TrainSeatService {

    public void save(TrainSeatSaveReq trainSeatSaveReq);

    public PageResp<TrainSeatQueryResp> querList(TrainSeatQueryReq req);

    void delete(Long id);

    public void genTrainSeat(String trainCode);
}
