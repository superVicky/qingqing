package com.vicky.controller;

import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.vicky.config.Const;
import com.vicky.model.User;
import com.vicky.utils.MD5Util;
import com.vicky.validator.RegValidator;

public class UserController extends Controller{	
	
	@Before(RegValidator.class)
	public void saveUser(){
		User user = getModel(User.class, "user");
		user.set("user_pwd", MD5Util.MD5Encode(user.get("user_pwd"), "utf-8"));
		user.set("createtime", new Date());
		user.save();
		CacheKit.put(Const.USER_KEY,"user", user);
		redirect("/login/toindex");
	}
	/**
	 * 
	 * @Todo TODO 退出系统
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016年1月7日
	 * @throws:
	 */
	public void logout(){
		CacheKit.remove(Const.USER_KEY,"user");
		redirect("/login/index");
	}
	/**
	 * 
	 * @Todo TODO 账户设置
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016年1月7日
	 * @throws:
	 */
	public void mySetting(){
		User user=CacheKit.get(Const.USER_KEY,"user");
		setAttr("user", User.dao.findById(user.getInt("userid")));
		render("/user/setting.html");
	}
	@Before(RegValidator.class)
	public void settingInfo(){
		try {
			User userSession=CacheKit.get(Const.USER_KEY,"user");
			
			User user=User.dao.findById(userSession.get("userid"));
			user.set("user_name", getPara("user.user_name"));
			user.set("user_phone", getPara("user.user_phone"));
			user.set("user_tele", getPara("user.user_tele"));
		    user.update();
		    setAttr("message", "您的信息已修改成功！");
		    setAttr("goBack", "user/mySetting");
		    render(Const.MESSAGE_PATH);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void settingPWD(){
		try {
			User userSession=CacheKit.get(Const.USER_KEY,"user");
			
			User user=User.dao.findById(userSession.get("userid"));
			user.set("user_pwd", MD5Util.MD5Encode(getPara("user_pwd"), "utf-8"));
		    user.update();
		    setAttr("message", "您的密码已修改成功！");
		    setAttr("goBack", "user/mySetting");
		    render(Const.MESSAGE_PATH);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 
	 * @Todo TODO 个人中心
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016年1月7日
	 * @throws:
	 */
	public void myCenter(){
		
		render("/user/usercenter.html");
	}
}
