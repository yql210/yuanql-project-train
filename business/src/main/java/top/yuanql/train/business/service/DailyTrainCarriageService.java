package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.DailyTrainCarriageQueryReq;
import top.yuanql.train.business.req.DailyTrainCarriageSaveReq;
import top.yuanql.train.business.response.DailyTrainCarriageQueryResp;

import java.util.Date;


public interface DailyTrainCarriageService {

    public void save(DailyTrainCarriageSaveReq dailyTrainCarriageSaveReq);

    public PageResp<DailyTrainCarriageQueryResp> querList(DailyTrainCarriageQueryReq req);

    void delete(Long id);

    void genDaily(Date date, String trainCode);

}
