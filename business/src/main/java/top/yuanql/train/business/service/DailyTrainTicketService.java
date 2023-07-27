package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.DailyTrain;
import top.yuanql.train.business.req.DailyTrainTicketQueryReq;
import top.yuanql.train.business.req.DailyTrainTicketSaveReq;
import top.yuanql.train.business.response.DailyTrainTicketQueryResp;
import top.yuanql.train.common.response.PageResp;

import java.util.Date;


public interface DailyTrainTicketService {

    public void save(DailyTrainTicketSaveReq dailyTrainTicketSaveReq);

    public PageResp<DailyTrainTicketQueryResp> querList(DailyTrainTicketQueryReq req);

    void delete(Long id);

    public void genDaily(DailyTrain dailyTrain, Date date, String trainCode);
}
