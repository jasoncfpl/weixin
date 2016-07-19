package com.javen.common;

import java.io.File;

import com.javen.controller.AjaxController;
import com.javen.controller.AjaxFileContorlller;
import com.javen.controller.ConstellationController;
import com.javen.controller.FileController;
import com.javen.controller.IndexController;
import com.javen.controller.ShareController;
import com.javen.controller.TUserController;
import com.javen.model.Course;
import com.javen.model.Idea;
import com.javen.model.Order;
import com.javen.model.Stock;
import com.javen.model.TUser;
import com.javen.model.Users;
import com.javen.weixin.controller.WeiXinOauthController;
import com.javen.weixin.controller.WeixinApiController;
import com.javen.weixin.controller.WeixinMsgController;
import com.javen.weixin.controller.WeixinPayController;
import com.javen.weixin.user.UserController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.SchedulerPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

/**
 * @author Javen
 */
public class APPConfig extends JFinalConfig{
	static Log log = Log.getLog(WeixinMsgController.class);
	
	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		loadProp("javen_config_pro.txt", "javen_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setEncoding("utf-8");
		me.setViewType(ViewType.JSP);
		//设置上传文件保存的路径
		me.setBaseUploadPath(PathKit.getWebRootPath()+File.separator+"myupload");
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(me.getDevMode());
		
		
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		//微信
		me.add("/msg", WeixinMsgController.class);
		me.add("/api", WeixinApiController.class);
		me.add("/oauth", WeiXinOauthController.class);
		me.add("/share", ShareController.class,"/view");
		//可以去掉 /front
		me.add("/pay", WeixinPayController.class,"/front");
		me.add("/", IndexController.class,"/front");
		me.add("/tuser", TUserController.class,"/back");
		
		me.add("/ajax", AjaxController.class);
		me.add("/constellation", ConstellationController.class,"/front");
		me.add("/wxuser", UserController.class,"/front");
		me.add("/file", FileController.class,"/front");
		me.add("/ajaxfile", AjaxFileContorlller.class,"/front");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		 C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		 me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.addMapping("course", Course.class);
		arp.addMapping("orders", Order.class);
		arp.addMapping("users","id", Users.class);
		arp.addMapping("Tuser", TUser.class);
		arp.addMapping("stock", Stock.class);
		arp.addMapping("idea", Idea.class);
		arp.setShowSql(true);
		me.add(arp);
		
		// ehcahce插件配置
		me.add(new EhCachePlugin());
		
		
		SchedulerPlugin sp = new SchedulerPlugin("job.properties");
        me.add(sp);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 5);//启动配置项
	}

}
