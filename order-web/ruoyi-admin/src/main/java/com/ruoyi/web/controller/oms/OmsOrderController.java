package com.ruoyi.web.controller.oms;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    @GetMapping("/export/{id}")
    public void export(@PathVariable Long id, HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            OmsOrder omsOrder = orderService.getById(id);
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                    .withTemplate(System.getProperty("user.dir") + File.separator + "template" + File.separator + "order.xlsx")
                    .build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            Map<String, Object> map = MapUtils.newHashMap();
            map.put("shopName", omsOrder.getShopName());
            map.put("phone", omsOrder.getPhone());
            map.put("remark", omsOrder.getRemark());
            excelWriter.fill(map, writeSheet);
            Map<String, Object> params = (Map<String, Object>) JSONUtils.parse(omsOrder.getProductList());
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            List<Map<String, Object>> productList = (List<Map<String, Object>>) params.getOrDefault("productList", Collections.emptyList());
            Map<String, Object> unitMap = (Map<String, Object>) JSONUtils.parse("{\"1\":{\"1\":\"大包\",\"2\":\"小包\",\"3\":\"件\",\"4\":\"斤\"},\"2\":{\"1\":\"市斤\",\"2\":\"公斤\",\"3\":\"件\"},\"3\":{\"1\":\"盒\",\"2\":\"市斤\",\"3\":\"公斤\",\"4\":\"件\"}}");
            for (Map<String, Object> product : productList) {
                String unitKey = String.valueOf(product.get("unitKey"));
                Map<String, Object> unitParams = (Map<String, Object>) unitMap.get(unitKey);
                if (unitParams != null) {
                    product.put("unit", unitParams.get(String.valueOf(product.get("unit"))));
                }
            }
            excelWriter.fill(productList, fillConfig, writeSheet);
            excelWriter.finish();
        } catch (Exception e) {
            throw new ApiException("导出失败");
        }
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
