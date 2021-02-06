package com.boot.security.server.service;

import com.boot.security.server.dto.ProductDto;
import com.boot.security.server.vo.R;

public interface IWeixinPayService {

	/**
	 * 微信支付下单
	 */
	R weixinPay(ProductDto product);

	R orderQuery(ProductDto product);

}
