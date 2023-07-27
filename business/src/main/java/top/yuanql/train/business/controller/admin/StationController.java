package top.yuanql.train.business.controller.admin;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuanql.train.business.response.StationQueryResp;
import top.yuanql.train.business.service.StationService;
import top.yuanql.train.common.response.CommonResp;

import java.util.List;


@RestController
@RequestMapping("/station")
public class StationController {

    @Resource
    private StationService stationService;

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryAll() {
        List<StationQueryResp> stationQueryResps = stationService.querAll();
        return new CommonResp<>(stationQueryResps);
    }
}
