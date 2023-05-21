package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.OmsOrder;
import com.ruoyi.system.mapper.OmsOrderMapper;
import com.ruoyi.system.service.IOmsOrderService;
import org.springframework.stereotype.Service;

/**
 * 订单服务接口实现类
 *
 * @author zjw
 * @date 2023-05-21
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {
}
