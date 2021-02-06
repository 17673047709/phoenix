package com.boot.security.server.model;

import lombok.Data;

@Data
public class ProductBaseInfo extends BaseEntity<Long> {

	private String body; // 商品描述

	private String note; // 备注

	private String notifyUrl; // 商品回调地址

	private String payType; // 支付类型(1:支付宝 2:微信 3:银联)

	private String payWay; // 支付方式 (1：PC,平板 2：手机)

	private String productId; // 商品ID

	private Integer status; //状态

	private String tenantUuid; //商户id

	private String totalFee; // 总金额(单位是分)

}
