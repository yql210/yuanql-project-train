package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.TrainSeat;
import top.yuanql.train.business.req.TrainSeatQueryReq;
import top.yuanql.train.business.req.TrainSeatSaveReq;
import top.yuanql.train.business.response.TrainSeatQueryResp;
import top.yuanql.train.common.response.PageResp;

import java.util.List;


public interface TrainSeatService {

    public void save(TrainSeatSaveReq trainSeatSaveReq);

    public PageResp<TrainSeatQueryResp> querList(TrainSeatQueryReq req);

    void delete(Long id);

    public void genTrainSeat(String trainCode);

    public List<TrainSeat> selectByTrainCode(String trainCode);

}
