package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 产品表
 *
 * @author zjw
 * @date 2023-05-21
 */
@Data
@TableName("oms_product")
public class OmsProduct {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    
    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 单位类别
     */
    private Integer unitKey;

    /**
     * 索引
     */
    private String productIndex;
    
}
