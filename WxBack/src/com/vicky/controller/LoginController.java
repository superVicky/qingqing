package com.vicky.controller;

import java.util.Random;
import java.util.Scanner;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.vicky.config.Const;
import com.vicky.model.*;
import com.vicky.utils.MD5Util;
import com.vicky.validator.*;

public class LoginController extends Controller{
	/**
	 * 
	 * @Todo TODO 去往登录首页
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016年1月7日
	 * @throws:
	 */
	public void index(){
	  render("/login.html");
    }
	/**
	 * 
	 * @Todo TODO 去往用户首页
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016年1月7日
	 * @throws:
	 */
	public void toindex(){
	  render("/index.html");
    }
	/**
	 * 
	 * @Todo TODO 忘记密码
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016年1月7日
	 * @throws:
	 */
	public void forgotPWD(){
	  render("/forgotPWD.html");
    }
	public void sendPWD(){
	    User user = User.dao.findFirst("select * from crm_user where user_phone=?",getPara("user_phone"));
	    if(user!=null){
	    	Random random=new Random();
	    	int password=random.nextInt(999999);
	    	System.out.println(password);
	    	//后期添加-发短信通知
		    user.set("user_pwd", MD5Util.MD5Encode(String.valueOf(password), "utf-8"));
		    user.update();
	    }
	    redirect("/login");
	}
	/**
	 * 
	 * @Todo TODO 去往注册页面
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016年1月7日
	 * @throws:
	 */
	public void register(){
	  render("/register.html");
    }
	
	@Before(LoginValidator.class)
	public void login(){
		String user_name = getPara("user_name");
	    String password = getPara("user_pwd");
	    User user = User.dao.findFirst("select userid, user_name, user_phone, user_privilege from crm_user where user_name=? and user_pwd=?", user_name, MD5Util.MD5Encode(password, "utf-8"));
	    if (user != null) {
	    	CacheKit.put(Const.USER_KEY,"user", user);
	    	redirect("/index");
	    }else{
		    user = User.dao.findFirst("select userid, user_name, user_phone, user_privilege from crm_user where user_phone=? and user_pwd=?", user_name, MD5Util.MD5Encode(password, "utf-8"));
		    if (user != null) {
		    	CacheKit.put(Const.USER_KEY,"user", user);
		    	redirect("/index");
		    }else
	    	renderText("账户或密码有误！");
	    }
	}
	
}
