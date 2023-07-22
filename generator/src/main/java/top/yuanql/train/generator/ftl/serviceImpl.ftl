package top.yuanql.train.${module}.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;
import top.yuanql.train.${module}.conf.MemberApplication;
import top.yuanql.train.${module}.domain.${Domain};
import top.yuanql.train.${module}.domain.${Domain}Example;
import top.yuanql.train.${module}.mapper.${Domain}Mapper;
import top.yuanql.train.${module}.req.${Domain}QueryReq;
import top.yuanql.train.${module}.req.${Domain}SaveReq;
import top.yuanql.train.${module}.response.${Domain}Resp;
import top.yuanql.train.${module}.service.${Domain}Service;

import java.util.List;


@Service
public class ${Domain}ServiceImpl implements ${Domain}Service {

    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    @Override
    public void save(${Domain}SaveReq ${domain}SaveReq) {
        DateTime now = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(${domain}SaveReq, ${Domain}.class);
        if (ObjectUtil.isNull(${domain}.getId())) {
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        } else {
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.updateByPrimaryKey(${domain});

        }
    }

    @Override
    public PageResp<${Domain}Resp> querList(${Domain}QueryReq req) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);

        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<${Domain}Resp> ${domain}Resps = BeanUtil.copyToList(${domain}List, ${Domain}Resp.class);

        PageResp<${Domain}Resp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(${domain}Resps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
