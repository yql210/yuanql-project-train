package top.yuanql.train.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.yuanqlprojecttrain.controller
 * @BelongsClassName: TestController
 * @Author: yuanql
 * @CreateTime: 2023-07-07  19:41
 * @Description: 测试热部署是否正常执行
 * @Version: 1.0
 */

@RestController
@RefreshScope
public class TestController {

    @Value("${test.nacos}")
    private String testNacos;

    @GetMapping("/hello")
    public String Hello() {
        return String.format("Hello %s!", testNacos);
    }
}
