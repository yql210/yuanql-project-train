package top.yuanql.train.member.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.member.req.MemberRegisterReq;
import top.yuanql.train.member.service.MemberService;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.member.controller
 * @BelongsClassName: MemberController
 * @Author: yuanql
 * @CreateTime: 2023-07-09  19:11
 * @Description: member 的 controller层
 * @Version: 1.0
 */

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
//        CommonResp<Integer> integerCommonResp = new CommonResp<>();
//        integerCommonResp.setContent(count);
//        return integerCommonResp;
        return new CommonResp<>(count);
    }

    @PostMapping("/register")
    public CommonResp<Long> trgister(MemberRegisterReq req) {
        long register = memberService.register(req);
//        CommonResp<Long> longCommonResp = new CommonResp<>();
//        longCommonResp.setContent(register);
//        return longCommonResp;
        return new CommonResp<>(register);
    }
}