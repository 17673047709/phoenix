package com.boot.security.server.model;

import lombok.Data;

@Data
public class PayBaseInfo extends BaseEntity<Long> {

	private String note;
	private String outTradeNo;
	private String requestXml;
	private String responseXml;
	private Integer status;
	private String subject;


}
