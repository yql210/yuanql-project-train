package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.TrainStation;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.TrainStationQueryReq;
import top.yuanql.train.business.req.TrainStationSaveReq;
import top.yuanql.train.business.response.TrainStationQueryResp;

import java.util.List;


public interface TrainStationService {

    public void save(TrainStationSaveReq trainStationSaveReq);

    public PageResp<TrainStationQueryResp> querList(TrainStationQueryReq req);

    void delete(Long id);

    public List<TrainStation> selectByTrainCode(String trainCode);
}
