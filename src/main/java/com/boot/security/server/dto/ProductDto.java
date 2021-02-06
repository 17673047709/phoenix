package com.boot.security.server.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductDto {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "商品ID不能为空")
    private String productId;   // 商品ID

    @NotBlank(message = "订单名称不能为空")
    private String subject; //订单名称

    private String body;    // 商品描述

    private String totalFee;    // 总金额(单位是分)

    @NotBlank(message = "订单号不能为空")
    private String outTradeNo;  // 订单号(唯一)

    @NotBlank(message = "发起人IP地址不能为空")
    private String spbillCreateIp;  // 发起人IP地址

    private Short payType;  // 支付类型(1:支付宝 2:微信 3:银联)

    private Short payWay;   // 支付方式 (1：PC,平板 2：手机)

    private String notifyUrl;   // 前台回调地址  非扫码支付使用

    @NotBlank(message = "商户uuid不能为空")
    private String uuid;   //商户uuid

}
