package top.yuanql.train.business.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuanql.train.business.req.DailyTrainTicketQueryReq;
import top.yuanql.train.business.response.DailyTrainTicketQueryResp;
import top.yuanql.train.business.service.DailyTrainTicketService;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;


@RestController
@RequestMapping("/daily-train-ticket")
public class DailyTrainTicketController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        PageResp<DailyTrainTicketQueryResp> dailyTrainTicketResps = dailyTrainTicketService.querList(req);
        return new CommonResp<>(dailyTrainTicketResps);
    }

}
