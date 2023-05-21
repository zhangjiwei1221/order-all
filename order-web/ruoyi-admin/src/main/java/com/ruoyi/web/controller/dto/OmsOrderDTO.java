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
public class OmsOrderDTO extends PageDTO {
    
    private String shopName;

    private String phone;

    private String token;
    
}
