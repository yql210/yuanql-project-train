package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.TrainCarriage;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.TrainCarriageQueryReq;
import top.yuanql.train.business.req.TrainCarriageSaveReq;
import top.yuanql.train.business.response.TrainCarriageQueryResp;

import java.util.List;


public interface TrainCarriageService {

    public void save(TrainCarriageSaveReq trainCarriageSaveReq);

    public PageResp<TrainCarriageQueryResp> querList(TrainCarriageQueryReq req);

    void delete(Long id);

    public List<TrainCarriage> selectByTrainCode(String trainCode);
}
