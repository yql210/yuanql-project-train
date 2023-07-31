package top.yuanql.train.member.controller.admin;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.TicketQueryReq;
import top.yuanql.train.member.response.TicketQueryResp;
import top.yuanql.train.member.service.TicketService;


@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {
        PageResp<TicketQueryResp> ticketResps = ticketService.querList(req);
        return new CommonResp<>(ticketResps);
    }

}
