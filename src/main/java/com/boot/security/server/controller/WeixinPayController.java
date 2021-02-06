package com.boot.security.server.controller;

import com.boot.security.server.dto.ProductDto;
import com.boot.security.server.service.IWeixinPayService;
import com.boot.security.server.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 微信支付
 *
 * @author zhangmenglai
 *
 */
@Api(tags ="微信支付")
@RestController
@RequestMapping(value = "weixinpay")
public class WeixinPayController {

	@Autowired
	private IWeixinPayService weixinPayService;

	@PreAuthorize("hasAuthority('weixin:pay:post')")
	@ApiOperation(value="二维码支付,下单并生成二维码")
	@RequestMapping(value="qcPay",method= RequestMethod.POST)
    public R qcPay(@RequestBody @Valid ProductDto productDto) {
		R data  =  weixinPayService.weixinPay(productDto);
		return data;
    }

}
