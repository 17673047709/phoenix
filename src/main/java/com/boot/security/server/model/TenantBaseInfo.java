package com.boot.security.server.model;

import lombok.Data;

@Data
public class TenantBaseInfo extends BaseEntity<Long> {

	private String apiKey; // API密钥

	private String appId; // 服务号的应用ID

	private String mchId; // 商户号

	private String mchName; // 商户名称

	private String note; // 备注

	private Integer status; // 状态

	private String uuid; // 商户id

}
