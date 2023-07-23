package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.StationQueryReq;
import top.yuanql.train.business.req.StationSaveReq;
import top.yuanql.train.business.response.StationQueryResp;

import java.util.List;


public interface StationService {

    public void save(StationSaveReq stationSaveReq);

    public PageResp<StationQueryResp> querList(StationQueryReq req);

    void delete(Long id);

    public List<StationQueryResp> querAll();

}
