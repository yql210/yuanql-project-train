package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.DailyTrainSeat;
import top.yuanql.train.business.domain.DailyTrainTicket;

import java.util.List;

public interface AfterConfirmOrderService {



    void afterDoConfirm(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList);
}
