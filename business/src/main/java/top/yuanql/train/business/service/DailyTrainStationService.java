package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.DailyTrainStationQueryReq;
import top.yuanql.train.business.req.DailyTrainStationSaveReq;
import top.yuanql.train.business.response.DailyTrainStationQueryResp;

import java.util.Date;


public interface DailyTrainStationService {

    public void save(DailyTrainStationSaveReq dailyTrainStationSaveReq);

    public PageResp<DailyTrainStationQueryResp> querList(DailyTrainStationQueryReq req);

    void delete(Long id);

    void genDaily(Date date, String trainCode);
}
