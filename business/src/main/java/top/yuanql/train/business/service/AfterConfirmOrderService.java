package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.ConfirmOrder;
import top.yuanql.train.business.domain.DailyTrainSeat;
import top.yuanql.train.business.domain.DailyTrainTicket;
import top.yuanql.train.business.req.ConfirmOrderTicketReq;

import java.util.List;

public interface AfterConfirmOrderService {


    void afterDoConfirm(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList, List<ConfirmOrderTicketReq> tickets, ConfirmOrder confirmOrder);
}
