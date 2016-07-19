package com.javen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.javen.weixin.template.DataItem2;
import com.javen.weixin.template.TempItem;
import com.javen.weixin.template.TempToJson;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;

public class WeiXinUtils {
	
	public static String filterWeixinEmoji(String source){
		if (containsEmoji(source)) {
			source = filterEmoji(source);
		}
		return source;
	}

	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		if (StrKit.isBlank(source)) {
			return false;
		}

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}

		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {

		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = null;

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}

				buf.append(codePoint);
			} else {
			}
		}

		if (buf == null) {
			return source;// 如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}
	}
	
	/** 
     * emoji表情转换(hex -> utf-16) 
     *  
     * @param hexEmoji 
     * @return 
     */  
    public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }
	
	/**
	 * 发送模板消息
	 * @param orderId
	 * @param price
	 * @param couresName
	 * @param teacherName
	 * @param openId
	 * @param url
	 * @return
	 */
	public static ApiResult sendTemplateMessage_2(String orderId,String price,String couresName,String teacherName,String openId,String url){
		DataItem2 dataItem=new DataItem2();
		dataItem.setFirst(new TempItem("您好,你已购买课程成功", "#743A3A"));
		dataItem.setKeyword1(new TempItem(orderId, "#FF0000"));
		dataItem.setKeyword2(new TempItem(price+"元", "#c4c400"));
		dataItem.setKeyword3(new TempItem(couresName, "#c4c400"));
		dataItem.setKeyword4(new TempItem(teacherName, "#c4c400"));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		String time=sdf.format(new Date());
		dataItem.setKeyword5(new TempItem(time, "#0000FF"));
		dataItem.setRemark(new TempItem("\n 请点击详情直接看课程直播，祝生活愉快", "#008000"));
		
		String json=TempToJson.getTempJson(openId, "7y1wUbeiYFsUONKH1IppVi47WwViICAjREZSdR3Zahc",
				"#743A3A", url, dataItem);
		
		ApiResult result=TemplateMsgApi.send(json);
		return result;
	}
	
	public static ApiResult sendTemplateMessageByOpen(String orderId,String price,String couresName,String teacherName,String openId,String url){
		DataItem2 dataItem=new DataItem2();
		dataItem.setFirst(new TempItem("您好,你已成功购买课程", "#000000"));
		dataItem.setKeyword1(new TempItem(orderId, "##000000"));
		dataItem.setKeyword2(new TempItem(price+"元", "#000000"));
		dataItem.setKeyword3(new TempItem(couresName, "#000000"));
		dataItem.setKeyword4(new TempItem(teacherName, "#000000"));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		String time=sdf.format(new Date());
		dataItem.setKeyword5(new TempItem(time+"\n我们的专业客服人员会在24小时内与您联系，请注意接听我们的电话，再次感谢您的支持！", "#000000"));
		dataItem.setRemark(new TempItem("\n请点击详情直接观看《大讲堂》课程直播，直播当天有效，祝生活愉快", "#008000"));
		
		String json=TempToJson.getTempJson(openId, "7y1wUbeiYFsUONKH1IppVi47WwViICAjREZSdR3Zahc",
				"#743A3A", url, dataItem);
		
		ApiResult result=TemplateMsgApi.send(json);
		return result;
	}
	
	public static ApiResult sendTemplateMessageByPrivate(String orderId,String price,String couresName,String teacherName,String openId,String url){
		DataItem2 dataItem=new DataItem2();
		dataItem.setFirst(new TempItem("您好,你已成功购买课程", "#000000"));
		dataItem.setKeyword1(new TempItem(orderId, "##000000"));
		dataItem.setKeyword2(new TempItem(price+"元", "#000000"));
		dataItem.setKeyword3(new TempItem(couresName, "#000000"));
		dataItem.setKeyword4(new TempItem(teacherName, "#000000"));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		String time=sdf.format(new Date());
		dataItem.setKeyword5(new TempItem(time, "#000000"));
		dataItem.setRemark(new TempItem("\n我们的专业客服人员会在24小时内与您联系，请注意接听我们的电话，再次感谢您的支持！", "#008000"));
		
		String json=TempToJson.getTempJson(openId, "7y1wUbeiYFsUONKH1IppVi47WwViICAjREZSdR3Zahc",
				"#743A3A", url, dataItem);
		
		ApiResult result=TemplateMsgApi.send(json);
		return result;
	}
	
}