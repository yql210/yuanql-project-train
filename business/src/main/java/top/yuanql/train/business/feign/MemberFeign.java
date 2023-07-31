package top.yuanql.train.business.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.yuanql.train.common.request.MemberTicketReq;
import top.yuanql.train.common.response.CommonResp;

/**
 * @BelongsProject: yuanql-project-train
 * @BelongsPackage: top.yuanql.train.business.feign
 * @BelongsClassName: MemberFeign
 * @Author: yuanql
 * @CreateTime: 2023-07-31  19:41
 * @Description:
 * @Version: 1.0
 */

@FeignClient(name = "yuanql-rain-membner", url = "http://localhost:8081/member/")
public interface MemberFeign {

    @GetMapping("/feign/ticket/save")
    CommonResp<Object> save(@Valid @RequestBody MemberTicketReq req);

}
