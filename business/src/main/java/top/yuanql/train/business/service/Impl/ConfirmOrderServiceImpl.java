package top.yuanql.train.business.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yuanql.train.business.config.BusinessApplication;
import top.yuanql.train.common.response.PageResp;
import top.yuanql.train.common.util.SnowUtil;
import top.yuanql.train.business.domain.ConfirmOrder;
import top.yuanql.train.business.domain.ConfirmOrderExample;
import top.yuanql.train.business.mapper.ConfirmOrderMapper;
import top.yuanql.train.business.req.ConfirmOrderQueryReq;
import top.yuanql.train.business.req.ConfirmOrderDoReq;
import top.yuanql.train.business.response.ConfirmOrderQueryResp;
import top.yuanql.train.business.service.ConfirmOrderService;

import java.util.List;


@Service
public class ConfirmOrderServiceImpl implements ConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Override
    public void save(ConfirmOrderDoReq confirmOrderSaveReq) {
        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(confirmOrderSaveReq, ConfirmOrder.class);
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.insert(confirmOrder);
        } else {
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);

        }
    }

    @Override
    public PageResp<ConfirmOrderQueryResp> querList(ConfirmOrderQueryReq req) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        confirmOrderExample.setOrderByClause("id desc");
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<ConfirmOrder> confirmOrderList = confirmOrderMapper.selectByExample(confirmOrderExample);

        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrderList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryResp> confirmOrderQueryResps = BeanUtil.copyToList(confirmOrderList, ConfirmOrderQueryResp.class);

        PageResp<ConfirmOrderQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(confirmOrderQueryResps);
        return pageResp;
    }

    @Override
    public void delete(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void doConfirm(ConfirmOrderDoReq confirmOrderSaveReq) {
        // 省略业务数据校验，如：车次是否存在，余票是否存在，车次是否再有效期内，tickets条数>0，同乘客同车次是否已买过票


        // 保存确认订单，状态初始


        // 查余票记录，需要得到真实的库存


        // 扣减余票数量，并判断余票是否足够


        // 选座

            // 一个车厢一个车厢获取座位数据


            // 挑选符合条件的座位，如果这个车厢不满足，则进入下一个车厢（多个选座应该在同一个车厢）



        // 选中座位后的事务处理

            // 座位表修改售卖情况sell；


            // 余票详情表修改余票；


            // 为会员增加购票记录；


            // 更新确认订单为成功。


        
    }
}
