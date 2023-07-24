package top.yuanql.train.batch.controller;

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
public class TestController {

    @GetMapping("/hello")
    public String Hello() {
        return "hello word!!!!---batch";
    }
}
