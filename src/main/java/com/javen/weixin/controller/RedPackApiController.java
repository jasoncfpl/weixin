package com.javen.weixin.controller;

import com.javen.utils.ReadPackUtils;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

/**
 * 微信红包demo
 * @author Javen
 * 2016年5月28日
 */
public class RedPackApiController extends Controller {
	private static String sendName = "";
	//微信证书路径
	private static String certPath = "";
	//商户相关资料
	String wxappid = PropKit.get("appId");
	// 微信支付分配的商户号
	String partner = PropKit.get("mch_id");
	//API密钥
	String paternerKey = PropKit.get("paternerKey");

	public void send() {
		
		boolean isSend = ReadPackUtils.send(getRequest(), "10", "100", "感谢您参加猜灯谜活动，祝您元宵节快乐！",
				"猜灯谜抢红包活动", "猜越多得越多，快来抢！", "oxTWIuGaIt6gTKsQRLau2M0yL16E",
				partner, wxappid, sendName, paternerKey, certPath);
		
		renderJson(isSend);
	}

	public void query() {
		String query = ReadPackUtils.query("10000098201411111234567890", partner, wxappid, paternerKey, certPath);
		renderJson(query);
	}

}