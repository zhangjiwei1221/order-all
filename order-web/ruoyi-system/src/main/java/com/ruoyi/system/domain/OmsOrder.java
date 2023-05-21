package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单表
 *
 * @author zjw
 * @date 2023-05-21
 */
@Data
@TableName("oms_order")
public class OmsOrder {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    
    /**
     * 店名
     */
    private String shopName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 产品
     */
    private String productList;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 创建时间
     */
    private String createTime;
    
}
