package top.yuanql.train.business.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.business.req.ConfirmOrderQueryReq;
import top.yuanql.train.business.req.ConfirmOrderDoReq;
import top.yuanql.train.business.response.ConfirmOrderQueryResp;


public interface ConfirmOrderService {

    public void save(ConfirmOrderDoReq confirmOrderSaveReq);

    public PageResp<ConfirmOrderQueryResp> querList(ConfirmOrderQueryReq req);

    void delete(Long id);

    void doConfirm(ConfirmOrderDoReq confirmOrderSaveReq);
}
