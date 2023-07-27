package top.yuanql.train.business.controller.admin;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import top.yuanql.train.business.req.DailyTrainQueryReq;
import top.yuanql.train.business.req.DailyTrainSaveReq;
import top.yuanql.train.business.response.DailyTrainQueryResp;
import top.yuanql.train.business.service.DailyTrainService;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;

import java.util.Date;


@RestController
@RequestMapping("/admin/daily-train")
public class DailyTrainAdminController {

    @Resource
    private DailyTrainService dailyTrainService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainSaveReq req) {
        dailyTrainService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainQueryResp>> queryList(@Valid DailyTrainQueryReq req) {
        PageResp<DailyTrainQueryResp> dailyTrainResps = dailyTrainService.querList(req);
        return new CommonResp<>(dailyTrainResps);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-daily/{date}")
    public CommonResp<Object> genDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        dailyTrainService.genDaily(date);
        return new CommonResp<>();
    }


}
