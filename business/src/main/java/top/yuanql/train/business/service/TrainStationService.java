package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.TrainStationQueryReq;
import top.yuanql.train.business.req.TrainStationSaveReq;
import top.yuanql.train.business.response.TrainStationQueryResp;


public interface TrainStationService {

    public void save(TrainStationSaveReq trainStationSaveReq);

    public PageResp<TrainStationQueryResp> querList(TrainStationQueryReq req);

    void delete(Long id);
}
