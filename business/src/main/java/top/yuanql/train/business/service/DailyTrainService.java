package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.Train;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.DailyTrainQueryReq;
import top.yuanql.train.business.req.DailyTrainSaveReq;
import top.yuanql.train.business.response.DailyTrainQueryResp;

import java.util.Date;


public interface DailyTrainService {

    public void save(DailyTrainSaveReq dailyTrainSaveReq);

    public PageResp<DailyTrainQueryResp> querList(DailyTrainQueryReq req);

    void delete(Long id);

    public void genDaily(Date date);

    public void genDailyTrain(Date date, Train train);

}
