package com.ruoyi.web.controller.dto;

import com.ruoyi.common.core.domain.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OmsProductDTO
 *
 * @author zjw
 * @date 2023-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OmsProductDTO extends PageDTO {
    
    private String name;

    private Integer type;
    
}
