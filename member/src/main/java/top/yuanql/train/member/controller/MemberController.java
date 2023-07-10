package top.yuanql.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.member.req.MemberLoginReq;
import top.yuanql.train.member.req.MemberRegisterReq;
import top.yuanql.train.member.req.MemberSendCodeReq;
import top.yuanql.train.member.response.MemberLoginResp;
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

    /**
     * 测试向数据库中添加数据
     * @param req
     * @return
     */
    @PostMapping("/register")
    public CommonResp<Long> trgister(@Valid MemberRegisterReq req) {  // @Valid：相当于 校验框架 的开关注解，加入此注解才能生效
        long register = memberService.register(req);
//        CommonResp<Long> longCommonResp = new CommonResp<>();
//        longCommonResp.setContent(register);
//        return longCommonResp;
        return new CommonResp<>(register);
    }

    /**
     * 生成验证码，验证码的生成
     * @param req POST请求传输的数据
     * @return 返回请求的数值
     */
    @PostMapping("/send_code")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq req) {  // @Valid：相当于 校验框架 的开关注解，加入此注解才能生效
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    /**
     * 用户输入验证码之后，进行登录相关的操作
     * @param req
     * @return
     */
    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid MemberLoginReq req) {  // @Valid：相当于 校验框架 的开关注解，加入此注解才能生效
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
    }
}