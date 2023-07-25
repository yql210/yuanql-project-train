package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.DailyTrainSeatQueryReq;
import top.yuanql.train.business.req.DailyTrainSeatSaveReq;
import top.yuanql.train.business.response.DailyTrainSeatQueryResp;


public interface DailyTrainSeatService {

    public void save(DailyTrainSeatSaveReq dailyTrainSeatSaveReq);

    public PageResp<DailyTrainSeatQueryResp> querList(DailyTrainSeatQueryReq req);

    void delete(Long id);
}
