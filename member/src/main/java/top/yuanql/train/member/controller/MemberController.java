package top.yuanql.train.member.controller;

import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public String count() {
        return String.valueOf(memberService.count());
    }

    @PostMapping("/register")
    public Long trgister(@PathParam("mobile") String mobile) {
        return memberService.register(mobile);
    }
}
