package top.yuanql.train.business.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.business.config.BusinessApplication;
import top.yuanql.train.business.domain.*;
import top.yuanql.train.business.enums.ConfirmOrderStatusEnum;
import top.yuanql.train.business.enums.SeatColEnum;
import top.yuanql.train.business.enums.SeatTypeEnum;
import top.yuanql.train.business.mapper.ConfirmOrderMapper;
import top.yuanql.train.business.req.ConfirmOrderDoReq;
import top.yuanql.train.business.req.ConfirmOrderQueryReq;
import top.yuanql.train.business.req.ConfirmOrderTicketReq;
import top.yuanql.train.business.response.ConfirmOrderQueryResp;
import top.yuanql.train.business.service.ConfirmOrderService;
import top.yuanql.train.business.service.DailyTrainCarriageService;
import top.yuanql.train.business.service.DailyTrainSeatService;
import top.yuanql.train.business.service.DailyTrainTicketService;
import top.yuanql.train.common.context.LoginMemberContext;
import top.yuanql.train.common.exception.BusinessException;
import top.yuanql.train.common.exception.BusinessExceptionEnum;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ConfirmOrderServiceImpl implements ConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @Override
    public void save(ConfirmOrderDoReq confirmOrderSaveReq) {
        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(confirmOrderSaveReq, ConfirmOrder.class);
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.insert(confirmOrder);
        } else {
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);

        }
    }

    @Override
    public PageResp<ConfirmOrderQueryResp> querList(ConfirmOrderQueryReq req) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        confirmOrderExample.setOrderByClause("id desc");
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<ConfirmOrder> confirmOrderList = confirmOrderMapper.selectByExample(confirmOrderExample);

        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrderList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryResp> confirmOrderQueryResps = BeanUtil.copyToList(confirmOrderList, ConfirmOrderQueryResp.class);

        PageResp<ConfirmOrderQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(confirmOrderQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void doConfirm(ConfirmOrderDoReq confirmOrderSaveReq) {
        // 省略业务数据校验，如：车次是否存在，余票是否存在，车次是否再有效期内，tickets条数>0，同乘客同车次是否已买过票


        Date date = confirmOrderSaveReq.getDate();
        String trainCode = confirmOrderSaveReq.getTrainCode();
        String start = confirmOrderSaveReq.getStart();
        String end = confirmOrderSaveReq.getEnd();
        List<ConfirmOrderTicketReq> ticketReqs = confirmOrderSaveReq.getTickets();

        // 保存确认订单，状态初始
        DateTime now = DateTime.now();

        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setId(SnowUtil.getSnowflakeNextId());
        confirmOrder.setMemberId(LoginMemberContext.getId());
        confirmOrder.setDate(date);
        confirmOrder.setTrainCode(trainCode);
        confirmOrder.setStart(start);
        confirmOrder.setEnd(end);
        confirmOrder.setDailyTrainTicketId(confirmOrderSaveReq.getDailyTrainTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setCreateTime(now);
        confirmOrder.setUpdateTime(now);
        confirmOrder.setTickets(JSON.toJSONString(ticketReqs));

        confirmOrderMapper.insert(confirmOrder);


        // 查余票记录，需要得到真实的库存
        DailyTrainTicket dailyTrainTicket = dailyTrainTicketService.selectByUnique(date, trainCode, start, end);
        LOG.info("查出余票记录：{}", dailyTrainTicket);


        // 扣减余票数量，并判断余票是否足够
        reduceTickets(confirmOrderSaveReq, dailyTrainTicket);

        // 计算相对第一个座位的偏离值
        // 比如选择的是c1、d2，则偏移量为：[0, 5]
        // 比如选择的是a1、b1、c1，则偏移量为：[0, 1, 2]
        ConfirmOrderTicketReq ticketReq0 = ticketReqs.get(0);
        if (StrUtil.isNotBlank(ticketReq0.getSeat())) {
            LOG.info("本次购票有选座");
            // 查出本次选座的座位类型都有那些列，用于计算所选座位与第一个座位的偏离值
            List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(ticketReq0.getSeatTypeCode());
            LOG.info("本次选座的座位类型包含的列：{}", colEnumList);

            // 组成和前端两拍选座一样的列表，用于作参照的座位列表。例如：referSeatList = {A1, C1, D1, F1, A2, C2, D2, F2}
            List<String > referSeatList = new ArrayList<>();

            for (int i = 1; i <= 2; i++) {
                for (SeatColEnum seatColEnum: colEnumList) {
                    referSeatList.add(seatColEnum.getCode() + i);
                }
            }
            LOG.info("用于做参照的两排座位");

            // 绝对偏移值，即：在操作座位列表中的位置
            List<Integer> aboluteOffsetList = new ArrayList<>();
            for (ConfirmOrderTicketReq ticket: ticketReqs) {
                int index = referSeatList.indexOf(ticket.getSeat());
                aboluteOffsetList.add(index);
            }
            LOG.info("计算得到所有座位的绝对偏移值：{}", aboluteOffsetList);

            List<Integer> offsetList = new ArrayList<>();
            for (Integer index : aboluteOffsetList) {
                int offeset = index - aboluteOffsetList.get(0);
                offsetList.add(offeset);
            }
            LOG.info("计算得到所有座位的相对第一个座位移值：{}", offsetList);


            getSeat(date,
                    trainCode,
                    ticketReq0.getSeatTypeCode(),
                    ticketReq0.getSeat().split("")[0],  // 从A1得到A
                    offsetList);


        } else {
            LOG.info("本次购票没有选座");

            for (ConfirmOrderTicketReq ticket: ticketReqs) {
                getSeat(date,
                        trainCode,
                        ticket.getSeatTypeCode(),
                        null,
                        null);
            }

        }

        // 选座

            // 一个车厢一个车厢获取座位数据


            // 挑选符合条件的座位，如果这个车厢不满足，则进入下一个车厢（多个选座应该在同一个车厢）



        // 选中座位后的事务处理

            // 座位表修改售卖情况sell；


            // 余票详情表修改余票；


            // 为会员增加购票记录；


            // 更新确认订单为成功。



    }

    private void getSeat(Date date, String trainCode, String seatType, String column, List<Integer> offsetList) {
        List<DailyTrainCarriage> carriageList = dailyTrainCarriageService.selectBySeatType(date, trainCode, seatType);
        LOG.info("共查出{}个符合条件的车厢",carriageList.size());

        // 一个车厢一个车厢获取座位数据

        for (DailyTrainCarriage dailyTrainCarriage: carriageList) {
            LOG.info("开始从车厢{}选座", dailyTrainCarriage.getIndex());

            List<DailyTrainSeat> seatList =
                    dailyTrainSeatService.selectByCarriage(date, trainCode, dailyTrainCarriage.getIndex());

            LOG.info("车厢{}的座位数:{}", dailyTrainCarriage.getIndex(), seatList.size());
        }

    }

    private static void reduceTickets(ConfirmOrderDoReq confirmOrderSaveReq, DailyTrainTicket dailyTrainTicket) {
        for (ConfirmOrderTicketReq ticketReq : confirmOrderSaveReq.getTickets()) {
            String typeCode = ticketReq.getSeatTypeCode();
            SeatTypeEnum seatTypeEnum = EnumUtil.getBy(SeatTypeEnum::getCode, typeCode);
            switch (seatTypeEnum) {
                case YDZ -> {
                    int countLeft = dailyTrainTicket.getYdz() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYdz(countLeft);
                }
                case EDZ -> {
                    int countLeft = dailyTrainTicket.getEdz() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setEdz(countLeft);
                }
                case RW -> {
                    int countLeft = dailyTrainTicket.getRw() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setRw(countLeft);
                }
                case YW -> {
                    int countLeft = dailyTrainTicket.getYw() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYw(countLeft);
                }
            }
        }
    }
}
