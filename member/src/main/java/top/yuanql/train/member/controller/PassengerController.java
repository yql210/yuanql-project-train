package top.yuanql.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.yuanql.train.common.context.LoginMemberContext;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.PassengerQueryReq;
import top.yuanql.train.member.req.PassengerSaveReq;
import top.yuanql.train.member.response.PassengerResp;
import top.yuanql.train.member.service.PassengerService;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.member.controller
 * @BelongsClassName: PassengerController
 * @Author: yuanql
 * @CreateTime: 2023-07-20  18:44
 * @Description: 乘客信息控制层
 * @Version: 1.0
 */


@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req) {
        passengerService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerResp> passengerResps = passengerService.querList(req);
        return new CommonResp<>(passengerResps);
    }

}
