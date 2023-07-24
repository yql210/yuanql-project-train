package top.yuanql.train.business.controller.admin;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.yuanql.train.common.context.LoginMemberContext;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.DailyTrainStationQueryReq;
import top.yuanql.train.business.req.DailyTrainStationSaveReq;
import top.yuanql.train.business.response.DailyTrainStationQueryResp;
import top.yuanql.train.business.service.DailyTrainStationService;


@RestController
@RequestMapping("/admin/daily-train-station")
public class DailyTrainStationAdminController {

    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainStationSaveReq req) {
        dailyTrainStationService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainStationQueryResp>> queryList(@Valid DailyTrainStationQueryReq req) {
        PageResp<DailyTrainStationQueryResp> dailyTrainStationResps = dailyTrainStationService.querList(req);
        return new CommonResp<>(dailyTrainStationResps);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainStationService.delete(id);
        return new CommonResp<>();
    }
}
