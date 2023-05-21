package com.ruoyi.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.OmsOrder;
import com.ruoyi.system.service.IOmsOrderService;
import com.ruoyi.web.controller.dto.OmsOrderDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 订单控制器
 *
 * @author zjw
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/oms/order")
public class OmsOrderController extends BaseController {

    @Resource
    private IOmsOrderService orderService;
    
    @PostMapping("/list")
    public TableDataInfo list(OmsOrderDTO omsOrderDTO) {
        startPage();
        LambdaQueryWrapper<OmsOrder> wrapper = new LambdaQueryWrapper<>();
        String shopName = omsOrderDTO.getShopName();
        if (StringUtils.isNotBlank(shopName)) {
            wrapper = wrapper.like(OmsOrder::getShopName, shopName);
        }
        String phone = omsOrderDTO.getPhone();
        if (StringUtils.isNotBlank(phone)) {
            wrapper = wrapper.like(OmsOrder::getPhone, phone);
        }
        String token = omsOrderDTO.getToken();
        if (StringUtils.isNotBlank(token)) {
            wrapper = wrapper.like(OmsOrder::getCreateId, String.valueOf(SecurityUtils.getUserId()));
        }
        List<OmsOrder> list = orderService.list(wrapper);
        return getDataTable(list);
    }
    
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable Long id) {
        return AjaxResult.success(orderService.getById(id));
    }
    
    @PostMapping("/save")
    public AjaxResult add(@RequestBody OmsOrder omsOrder) {
        omsOrder.setCreateId(String.valueOf(SecurityUtils.getUserId()));
        return toAjax(orderService.saveOrUpdate(omsOrder));
    }
    
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        orderService.removeByIds(Arrays.asList(ids));
        return success();
    }
    
}
