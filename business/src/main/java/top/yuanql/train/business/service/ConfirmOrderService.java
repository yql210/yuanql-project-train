package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.ConfirmOrderQueryReq;
import top.yuanql.train.business.req.ConfirmOrderSaveReq;
import top.yuanql.train.business.response.ConfirmOrderQueryResp;


public interface ConfirmOrderService {

    public void save(ConfirmOrderSaveReq confirmOrderSaveReq);

    public PageResp<ConfirmOrderQueryResp> querList(ConfirmOrderQueryReq req);

    void delete(Long id);
}
