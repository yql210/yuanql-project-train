package top.yuanql.train.batch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.batch.fegin
 * @BelongsClassName: BusinessFeign
 * @Author: yuanql
 * @CreateTime: 2023-07-26  20:52
 * @Description: 业务类的fegin调用
 * @Version: 1.0
 */


@FeignClient(name = "yuanql-rain-business", url = "http://localhost:8082/business")
public interface BusinessFeign {

    @GetMapping("/hello")
    public String Hello();
}
