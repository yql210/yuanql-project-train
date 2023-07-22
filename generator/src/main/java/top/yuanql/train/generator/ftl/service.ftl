package top.yuanql.train.${module}.service;

import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.${module}.req.${Domain}QueryReq;
import top.yuanql.train.${module}.req.${Domain}SaveReq;
import top.yuanql.train.${module}.response.${Domain}Resp;


public interface ${Domain}Service {

    public void save(${Domain}SaveReq ${domain}SaveReq);

    public PageResp<${Domain}Resp> querList(${Domain}QueryReq req);

    void delete(Long id);
}
