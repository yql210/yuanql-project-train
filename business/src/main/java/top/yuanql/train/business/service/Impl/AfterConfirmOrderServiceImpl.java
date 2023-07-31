package top.yuanql.train.business.service.Impl;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.business.config.BusinessApplication;
import top.yuanql.train.business.domain.DailyTrainSeat;
import top.yuanql.train.business.mapper.DailyTrainSeatMapper;
import top.yuanql.train.business.service.AfterConfirmOrderService;

import java.util.Date;
import java.util.List;


@Service
public class AfterConfirmOrderServiceImpl implements AfterConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    /**
     * 选中座位后的事务处理
     *     座位表修改售卖情况sell；
     *     余票详情表修改余票；
     *     为会员增加购票记录；
     *     更新确认订单为成功。
     */
    @Override
    public void afterDoConfirm(List<DailyTrainSeat> finalSeatList) {

        for (DailyTrainSeat dailyTrainSeat : finalSeatList) {
            DailyTrainSeat seatForUpdate = new DailyTrainSeat();
            seatForUpdate.setId(dailyTrainSeat.getId());
            seatForUpdate.setSell(dailyTrainSeat.getSell());
            seatForUpdate.setUpdateTime(new Date());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(seatForUpdate);
        }

    }


}
