package com.vicky.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.vicky.config.Const;
import com.vicky.model.User;

public class AuthInterceptor implements Interceptor{
	
  private static final HashMap<String,String> filterMap = new HashMap<String,String>();
  
  
	public AuthInterceptor(){
		super();
		//不需要验证的就可以访问的连接
		filterMap.put("//*", "首页");
		filterMap.put("/login/*", "登录");
		filterMap.put("/index/*", "首页");
		filterMap.put("/captcha/*", "验证码");
		filterMap.put("/user/saveUser", "注册");
	}	
	
	@Override
	public void intercept(Invocation ai){
		Controller controller=ai.getController();
		String path=controller.getRequest().getContextPath();
		controller.setAttr("path", path);
		
		String action=ai.getControllerKey();
		String method=ai.getMethodName();
		boolean conmmon=checkFilter(action,method);
		
		User user=CacheKit.get(Const.USER_KEY,"user");
        if(conmmon || null!=user){
			controller.setAttr("user", user);
			ai.invoke();
	    }else{
	    	controller.render(Const.PLEASE_LOGIN);
	    }
	}
	 
    static boolean checkFilter(String action, String method) {
        boolean ischecked = false;
        System.out.println("action = [" + action + "], method = [" + method + "]");
        String filterKey = action + "/" + method;
        String filterAll = action + "/" + "*";
        ischecked = filterMap.containsKey(filterKey) || filterMap.containsKey(filterAll);
        return ischecked;
    }
}
