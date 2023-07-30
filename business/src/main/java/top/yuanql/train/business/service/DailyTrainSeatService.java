package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.DailyTrainSeat;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.DailyTrainSeatQueryReq;
import top.yuanql.train.business.req.DailyTrainSeatSaveReq;
import top.yuanql.train.business.response.DailyTrainSeatQueryResp;

import java.util.Date;
import java.util.List;


public interface DailyTrainSeatService {

    public void save(DailyTrainSeatSaveReq dailyTrainSeatSaveReq);

    public PageResp<DailyTrainSeatQueryResp> querList(DailyTrainSeatQueryReq req);

    void delete(Long id);

    void genDaily(Date date, String trainCode);

    public int countSeat(Date date, String trainCode, String seatType);

    List<DailyTrainSeat> selectByCarriage(Date date, String trainCode, Integer carriageIndex);
}
