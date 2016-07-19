package com.javen.weixin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.javen.weixin.template.DataItem2;
import com.javen.weixin.template.TempItem;
import com.javen.weixin.template.TempToJson;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;

public class WeixinApiController extends ApiController {
	
	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
	 * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		
		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}

	/**
	 * 获取公众号菜单
	 */
	public void getMenu() {
		ApiResult apiResult = MenuApi.getMenu();
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}

	/**
	 * 创建菜单
	 */
	public void createMenu()
	{
		String str = "{\n" +
				"    \"button\": [\n" +
				"        {\n" +
				"            \"name\": \"进入理财\",\n" +
				"            \"url\": \"http://m.bajie8.com/bajie/enter\",\n" +
				"            \"type\": \"view\"\n" +
				"        },\n" +
				"        {\n" +
				"            \"name\": \"安全保障\",\n" +
				"            \"key\": \"112\",\n" +
				"\t    \"type\": \"click\"\n" +
				"        },\n" +
				"        {\n" +
				"\t    \"name\": \"使用帮助\",\n" +
				"\t    \"url\": \"http://m.bajie8.com/footer/cjwt\",\n" +
				"\t    \"type\": \"view\"\n" +
				"        }\n" +
				"    ]\n" +
				"}";
		ApiResult apiResult = MenuApi.createMenu(str);
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}

	/**
	 * 获取公众号关注用户
	 */
	public void getFollowers()
	{
		ApiResult apiResult = UserApi.getFollows();
		renderText(apiResult.getJson());
	}

	/**
	 * 获取用户信息
	 */
	public void getUserInfo()
	{
		ApiResult apiResult = UserApi.getUserInfo("ohbweuNYB_heu_buiBWZtwgi4xzU");
		renderText(apiResult.getJson());
	}

	/**
	 * 发送模板消息
	 */
	public void sendMsg()
	{
		String str = " {\n" +
				"           \"touser\":\"ohbweuNYB_heu_buiBWZtwgi4xzU\",\n" +
				"           \"template_id\":\"9SIa8ph1403NEM3qk3z9-go-p4kBMeh-HGepQZVdA7w\",\n" +
				"           \"url\":\"http://www.sina.com\",\n" +
				"           \"topcolor\":\"#FF0000\",\n" +
				"           \"data\":{\n" +
				"                   \"first\": {\n" +
				"                       \"value\":\"恭喜你购买成功！\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"keyword1\":{\n" +
				"                       \"value\":\"去哪儿网发的酒店红包（1个）\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"keyword2\":{\n" +
				"                       \"value\":\"1元\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"remark\":{\n" +
				"                       \"value\":\"欢迎再次购买！\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   }\n" +
				"           }\n" +
				"       }";
		ApiResult apiResult = TemplateMsgApi.send(str);
		renderText(apiResult.getJson());
	}

	/**
	 * 获取参数二维码
	 */
	public void getQrcode()
	{
		String str = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		ApiResult apiResult = QrcodeApi.create(str);
		renderText(apiResult.getJson());

//        String str = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"123\"}}}";
//        ApiResult apiResult = QrcodeApi.create(str);
//        renderText(apiResult.getJson());
	}
	/**
	 * 测试输出的结果
	 * create>>{"ticket":"gQFo8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0cwT21FZjNtM3RXbmd3REF6Ml82AAIEzyFQVwMEAAAAAA==","url":"http:\/\/weixin.qq.com\/q\/G0OmEf3m3tWngwDAz2_6"}
 qrcodeUrl:https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFo8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0cwT21FZjNtM3RXbmd3REF6Ml82AAIEzyFQVwMEAAAAAA==
	 * 
	 * 
	 */
	public void getQrcode2(){
		ApiResult apiResult = QrcodeApi.createPermanent(100);
		String qrcodeUrl = QrcodeApi.getShowQrcodeUrl(apiResult.getStr("ticket"));
		renderText("create>>"+apiResult.getJson()+" qrcodeUrl:"+qrcodeUrl);
	}

	/**
	 * 长链接转成短链接
	 */
	public void getShorturl()
	{
		String str = "{\"action\":\"long2short\"," +
				"\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}";
		ApiResult apiResult = ShorturlApi.getShorturl(str);
		renderText(apiResult.getJson());
	}

	/**
	 * 获取客服聊天记录
	 */
	public void getRecord()
	{
		String str = "{\n" +
				"    \"endtime\" : 987654321,\n" +
				"    \"pageindex\" : 1,\n" +
				"    \"pagesize\" : 10,\n" +
				"    \"starttime\" : 123456789\n" +
				" }";
		ApiResult apiResult = CustomServiceApi.getRecord(str);
		renderText(apiResult.getJson());
	}

	/**
	 * 获取微信服务器IP地址
	 */
	public void getCallbackIp()
	{
		ApiResult apiResult = CallbackIpApi.getCallbackIp();
		renderText(apiResult.getJson());
	}
	
	
	public void tem(){
		DataItem2 dataItem=new DataItem2();
		dataItem.setFirst(new TempItem("您好,你已成功购买课程", "#000000"));
		dataItem.setKeyword1(new TempItem("123", "##000000"));
		dataItem.setKeyword2(new TempItem("10.00元", "#000000"));
		dataItem.setKeyword3(new TempItem("课程名称", "#000000"));
		dataItem.setKeyword4(new TempItem("老师名称  联系方式", "#000000"));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		String time=sdf.format(new Date());
		dataItem.setKeyword5(new TempItem(time+"\n我们的专业客服人员会在24小时内与您联系，请注意接听我们的电话，再次感谢您的支持！", "#000000"));
		dataItem.setRemark(new TempItem("\n请点击详情直接看课程直播，祝生活愉快", "#008000"));
		
		String json=TempToJson.getTempJson("o_pncsidC-pRRfCP4zj98h6slREw", "7y1wUbeiYFsUONKH1IppVi47WwViICAjREZSdR3Zahc",
				"#743A3A", "http://mp.zhiqiangyy.com/ZQ/gensee/training?openId=o_pncsidC-pRRfCP4zj98h6slREw&id=5&time_end=20160420210032", dataItem);
		
		ApiResult result=TemplateMsgApi.send(json);
		
		System.out.println(JsonKit.toJson(result));
		renderText(JsonKit.toJson(result));
	}
	
}

