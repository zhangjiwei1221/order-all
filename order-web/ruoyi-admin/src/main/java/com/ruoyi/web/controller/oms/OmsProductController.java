package com.ruoyi.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.OmsProduct;
import com.ruoyi.system.service.IOmsProductService;
import com.ruoyi.web.controller.dto.OmsProductDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 产品控制器
 *
 * @author zjw
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/oms/product")
public class OmsProductController extends BaseController {

    @Resource
    private IOmsProductService omsProductService;
    
    @PostMapping("/list")
    public TableDataInfo list(OmsProductDTO omsProductDTO) {
        startPage();
        LambdaQueryWrapper<OmsProduct> wrapper = new LambdaQueryWrapper<>();
        String name = omsProductDTO.getName();
        if (StringUtils.isNotBlank(name)) {
            wrapper = wrapper.like(OmsProduct::getName, name);
        }
        Integer type = omsProductDTO.getType();
        if (type != null) {
            wrapper = wrapper.eq(OmsProduct::getType, type);
        }
        List<OmsProduct> list = omsProductService.list(wrapper);
        return getDataTable(list);
    }
    
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable Long id) {
        return AjaxResult.success(omsProductService.getById(id));
    }
    
    @PostMapping("/save")
    public AjaxResult add(@RequestBody OmsProduct omsProduct) {
        return toAjax(omsProductService.saveOrUpdate(omsProduct));
    }
    
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        omsProductService.removeByIds(Arrays.asList(ids));
        return success();
    }
    
}
