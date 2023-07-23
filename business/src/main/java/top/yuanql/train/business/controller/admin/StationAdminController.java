package top.yuanql.train.business.controller.admin;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.yuanql.train.business.req.StationQueryReq;
import top.yuanql.train.business.req.StationSaveReq;
import top.yuanql.train.business.response.StationQueryResp;
import top.yuanql.train.business.service.StationService;
import top.yuanql.train.common.response.CommonResp;
import top.yuanql.train.common.response.PageResp;

import java.util.List;


@RestController
@RequestMapping("/admin/station")
public class StationAdminController {

    @Resource
    private StationService stationService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody StationSaveReq req) {
        stationService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<StationQueryResp>> queryList(@Valid StationQueryReq req) {
        PageResp<StationQueryResp> stationResps = stationService.querList(req);
        return new CommonResp<>(stationResps);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        stationService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryAll() {
        List<StationQueryResp> stationQueryResps = stationService.querAll();
        return new CommonResp<>(stationQueryResps);
    }
}
