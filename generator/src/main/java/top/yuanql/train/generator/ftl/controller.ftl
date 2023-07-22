package top.yuanql.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.yuanql.train.common.context.LoginMemberContext;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.member.req.${Domain}QueryReq;
import top.yuanql.train.member.req.${Domain}SaveReq;
import top.yuanql.train.member.response.${Domain}Resp;
import top.yuanql.train.member.service.${Domain}Service;


@RestController
@RequestMapping("/${do_main}")
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody ${Domain}SaveReq req) {
        ${domain}Service.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<${Domain}Resp>> queryList(@Valid ${Domain}QueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<${Domain}Resp> ${domain}Resps = ${domain}Service.querList(req);
        return new CommonResp<>(${domain}Resps);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        ${domain}Service.delete(id);
        return new CommonResp<>();
    }
}
