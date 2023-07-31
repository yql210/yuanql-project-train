package top.yuanql.train.business.service;

import top.yuanql.train.business.domain.DailyTrainSeat;

import java.util.List;

public interface AfterConfirmOrderService {


    void afterDoConfirm(List<DailyTrainSeat> finalSeatList);
}
