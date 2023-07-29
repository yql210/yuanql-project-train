package top.yuanql.train.business.controller.admin;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.ConfirmOrderQueryReq;
import top.yuanql.train.business.req.ConfirmOrderDoReq;
import top.yuanql.train.business.response.ConfirmOrderQueryResp;
import top.yuanql.train.business.service.ConfirmOrderService;


@RestController
@RequestMapping("/admin/confirm-order")
public class ConfirmOrderAdminController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody ConfirmOrderDoReq req) {
        confirmOrderService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryList(@Valid ConfirmOrderQueryReq req) {
        PageResp<ConfirmOrderQueryResp> confirmOrderResps = confirmOrderService.querList(req);
        return new CommonResp<>(confirmOrderResps);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        confirmOrderService.delete(id);
        return new CommonResp<>();
    }
}
