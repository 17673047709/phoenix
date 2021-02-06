package com.boot.security.server.service.impl;

import com.boot.security.server.constants.Constants;
import com.boot.security.server.dao.PayBaseInfoDao;
import com.boot.security.server.dao.ProductBaseInfoDao;
import com.boot.security.server.dao.TenantBaseInfoDao;
import com.boot.security.server.dto.ProductDto;
import com.boot.security.server.model.PayBaseInfo;
import com.boot.security.server.model.ProductBaseInfo;
import com.boot.security.server.model.TenantBaseInfo;
import com.boot.security.server.service.IWeixinPayService;
import com.boot.security.server.utils.*;
import com.boot.security.server.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class WeixinPayServiceImpl implements IWeixinPayService {

	@Autowired(required = false)
	private TenantBaseInfoDao tenantBaseInfoDao;

	@Autowired(required = false)
	private ProductBaseInfoDao productBaseInfoDao;

	@Autowired(required = false)
	private PayBaseInfoDao payBaseInfoDao;


	/**
	 * 查询订单
	 */
	@Override
	public R orderQuery(ProductDto productDto) {

		//获取商户信息
		TenantBaseInfo tenant = getTenantBaseInfo(productDto);

		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid",tenant.getAppId());// 公众账号ID
		packageParams.put("mch_id",tenant.getMchId());// 商户号
		packageParams.put("nonce_str", PayCommonUtil.getNonceStr());// 随机字符串
		packageParams.put("out_trade_no", productDto.getOutTradeNo());// 商户订单号
		String trade_state = "";
		try {
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, tenant.getApiKey());//API密钥
			packageParams.put("sign", sign);

			// 查询订单 https://api.mch.weixin.qq.com/pay/orderquery
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(ConfigUtil.CHECK_ORDER_URL, requestXML);
			Map map = XMLUtil.doXMLParse(resXml);
			trade_state = (String) map.get("trade_state");
		}catch (Exception e){
			e.printStackTrace();
		}
		//SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭 REVOKED—已撤销（付款码支付）
		// USERPAYING--用户支付中（付款码支付） PAYERROR--支付失败(其他原因，如银行返回失败)
		if ("SUCCESS".equals(trade_state)){
			return R.failed("","支付成功");
		}
		if ("REFUND".equals(trade_state)){
			return R.failed("转入退款");
		}
		if ("NOTPAY".equals(trade_state)){
			return  R.failed("未支付");
		}
		if ("CLOSED".equals(trade_state)){
			return R.failed("已关闭");
		}
		if ("REVOKED".equals(trade_state)){
			return  R.failed("已撤销（付款码支付）");
		}
		if ("USERPAYING".equals(trade_state)){
			return  R.failed("用户支付中（付款码支付）");
		}
		if ("PAYERROR".equals(trade_state)){
			return  R.failed("支付失败");
		}
		return  R.ok(trade_state);

	}

	/**
	 * 微信支付要求商户订单号保持唯一性（建议根据当前系统时间加随机序列来生成订单号）。
	 * 重新发起一笔支付要使用原订单号，避免重复支付；已支付过或已调用关单、撤销的订单号不能重新发起支付。
	 * 注意：支付金额和商品描述必须一样，下单后金额或者描述如果有改变也会出现订单号重复。
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public R weixinPay(ProductDto productDto) {
		log.info("订单号：{} 生成微信支付码",productDto.getOutTradeNo());
		String  message = Constants.SUCCESS;
		try {
			//获取商户信息
			TenantBaseInfo tenant = getTenantBaseInfo(productDto);
			//获取商品信息
			ProductBaseInfo product=getProductBaseInfo(productDto);
			String totalFee = product.getTotalFee();
			totalFee =  CommonUtil.subZeroAndDot(totalFee);
			//--------------------------调用微信参数信息-----------------------------
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			packageParams.put("appid", tenant.getAppId());// 公众账号ID
			packageParams.put("mch_id",tenant.getMchId());// 商户号
			packageParams.put("nonce_str", PayCommonUtil.getNonceStr());// 随机字符串
			packageParams.put("product_id",product.getProductId());// 商品ID
			packageParams.put("body", product.getBody());// 商品描述
			packageParams.put("out_trade_no", productDto.getOutTradeNo());// 商户订单号
			packageParams.put("total_fee", totalFee);// 总金额
			packageParams.put("spbill_create_ip", productDto.getSpbillCreateIp());// 发起人IP地址
			packageParams.put("notify_url",  getUrl(product.getNotifyUrl(), productDto.getOutTradeNo()));// 回调地址
			packageParams.put("trade_type", "NATIVE");// 交易类型 原生扫码支付
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, tenant.getApiKey());//API密钥
			packageParams.put("sign", sign);// 签名
			//--------------------------end-----------------------------
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(ConfigUtil.UNIFIED_ORDER_URL, requestXML);
			//-------------------------保存调用接口信息---------------------------------------
			PayBaseInfo payBaseInfo = new PayBaseInfo();
			payBaseInfo.setOutTradeNo(productDto.getOutTradeNo());
			payBaseInfo.setSubject(productDto.getSubject());
			payBaseInfo.setRequestXml(requestXML);
			payBaseInfo.setResponseXml(resXml);
			payBaseInfoDao.save(payBaseInfo);
			//--------------------------end-----------------------------
			Map map = XMLUtil.doXMLParse(resXml);
			String returnCode = (String) map.get("return_code");
			if("SUCCESS".equals(returnCode)){
				String resultCode = (String) map.get("result_code");
				if("SUCCESS".equals(resultCode)){
					log.info("订单号：{} 生成微信支付码成功",productDto.getOutTradeNo());
					String urlCode = (String) map.get("code_url");
					ConfigUtil.shorturl(urlCode);//转换为短链接
					return R.ok(urlCode,message);
				}else{
					String errCodeDes = (String) map.get("err_code_des");
					log.info("订单号：{}生成微信支付码(系统)失败:{}",productDto.getOutTradeNo(),errCodeDes);
					message = Constants.FAIL;
				}
			}else{
				String returnMsg = (String) map.get("return_msg");
				log.info("(订单号：{}生成微信支付码(通信)失败:{}",productDto.getOutTradeNo(),returnMsg);
				message = Constants.FAIL;
			}
		} catch (Exception e) {
			log.error("订单号：{}生成微信支付码失败(系统异常))",productDto.getOutTradeNo(),e);
			message = Constants.FAIL;
		}
		return R.failed(message);
	}

	/**
	 * 根据商品id和商户id获取商品信息
	 * @param productDto
	 * @return
	 */
	private ProductBaseInfo getProductBaseInfo(ProductDto productDto){
		Map params01 =new HashMap();
		params01.put("productId",productDto.getProductId());
		params01.put("tenantUuid",productDto.getUuid());
		List<ProductBaseInfo> products = productBaseInfoDao.list(params01,0,1);
		if(products!=null){
			return products.get(0);
		}
		return null;
	}

	/**
	 * 根据商户id获取商户信息
	 * @param productDto
	 * @return
	 */
	private TenantBaseInfo getTenantBaseInfo(ProductDto productDto){
		Map params02 =new HashMap();
		params02.put("uuid",productDto.getUuid());
		List<TenantBaseInfo> tenants = tenantBaseInfoDao.list(params02,0,1);
		if(tenants!=null){
			return tenants.get(0);
		}
		return null;
	}

	/**
	 * 拼接商品回调地址和订单号
	 * @param url 回调地址
	 * @param outTradeNo 订单号
	 * @return
	 */
	private String getUrl(String url, String outTradeNo){

		//String notifyUrl=url+"/"+outTradeNo;
		String notifyUrl= url.replace("{uuid}",outTradeNo);
		log.info("回调地址：{}",notifyUrl);
		return notifyUrl;
	}

	/*public static void main(String[] args) {
		String outTradeNo="11111";
		String url="http://127.0.0.1:8080/allpay/{uuid}/ssdjsd/test.do";
		url = url.replace("{uuid}",outTradeNo);
		System.out.println(url);
	}*/
	//微信回调接参方法，@PathVariable可以用来映射URL中的占位符到目标方法的参数中
	//@RequestMapping("/testPathVariable/{id}")
	//public String testPathVariable(@PathVariable("id") Integer id)

}
