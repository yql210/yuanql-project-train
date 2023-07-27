package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.Train;
import top.yuanql.train.business.req.TrainQueryReq;
import top.yuanql.train.business.req.TrainSaveReq;
import top.yuanql.train.business.response.TrainQueryResp;
import top.yuanql.train.common.response.PageResp;

import java.util.List;


public interface TrainService {

    public void save(TrainSaveReq trainSaveReq);

    public PageResp<TrainQueryResp> querList(TrainQueryReq req);

    void delete(Long id);

    public List<TrainQueryResp> querAll();

    public List<Train> selectAll();
    }
