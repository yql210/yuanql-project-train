package top.yuanql.train.business.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuanql.train.business.response.TrainQueryResp;
import top.yuanql.train.business.service.TrainService;
import top.yuanql.train.common.response.CommonResp;

import java.util.List;


@RestController
@RequestMapping("/train")
public class TrainController {

    @Resource
    private TrainService trainService;

    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryList() {
        List<TrainQueryResp> trainQueryResps = trainService.querAll();
        return new CommonResp<>(trainQueryResps);
    }

}
