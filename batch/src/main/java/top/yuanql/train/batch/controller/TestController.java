package top.yuanql.train.batch.controller;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuanql.train.batch.feign.BusinessFeign;
import top.yuanql.train.batch.job.DailyTrainJob;

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

    @Resource
    private BusinessFeign businessFeign;

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);

    @GetMapping("/hello")
    public String Hello() {
        String businessHelo = businessFeign.Hello();

        LOG.info(businessHelo);

        return "hello word!!!!---batch";

    }
}
