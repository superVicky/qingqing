package com.vicky.config;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.vicky.controller.*;
import com.vicky.interceptor.AuthInterceptor;
import com.vicky.model.*;
import com.vicky.utils.*;

public class InitConfig extends JFinalConfig{
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
	@Override
	public void configConstant(Constants me){
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
	    me.setMainRenderFactory(new BeetlRenderFactory());
	    /*GroupTemplate groupTemplate = BeetlRenderFactory.groupTemplate ;
        WebAppResourceLoader loader = (WebAppResourceLoader)groupTemplate.getResourceLoader();
		loader.setRoot("/page");*/
        me.setError404View(Const.ERROR_404);
        me.setError500View(Const.ERROR_500);
        me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setEncoding("UTF-8"); 
	}

	@Override
	public void configHandler(Handlers me){
		
	}

	@Override
	public void configInterceptor(Interceptors interceptors){
		//过滤访问地址
		interceptors.add(new AuthInterceptor());
	}

	@Override
	public void configPlugin(Plugins me){
		// 配置C3p0数据库连接池插件   
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	    me.add(c3p0Plugin);
	    
	    //配置缓存机制
	    EhCachePlugin ecp = new EhCachePlugin("E://workspace//WxBack//src//ehcache.xml");
	    me.add(ecp);
	    
	    // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        
        arp.addMapping("CRM_USER","userid",User.class);
        arp.addMapping("INVITE_CODE","codeid",InviteCode.class);
        arp.addMapping("WX_INFO","infoid",WxInfo.class);
	}
    /**
     * 配置路由地址
     */
	@Override
	public void configRoute(Routes routes){
		
		routes.add("/",IndexController.class);
		routes.add("/index",IndexController.class);
		routes.add("/login",LoginController.class);
		routes.add("/captcha",CaptchaController.class);
		routes.add("/user",UserController.class);
	}
}
