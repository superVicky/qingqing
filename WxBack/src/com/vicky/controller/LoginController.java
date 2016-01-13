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
	 * @Todo TODO ȥ����¼��ҳ
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016��1��7��
	 * @throws:
	 */
	public void index(){
	  render("/login.html");
    }
	/**
	 * 
	 * @Todo TODO ȥ���û���ҳ
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016��1��7��
	 * @throws:
	 */
	public void toindex(){
	  render("/index.html");
    }
	/**
	 * 
	 * @Todo TODO ��������
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016��1��7��
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
	    	//�������-������֪ͨ
		    user.set("user_pwd", MD5Util.MD5Encode(String.valueOf(password), "utf-8"));
		    user.update();
	    }
	    redirect("/login");
	}
	/**
	 * 
	 * @Todo TODO ȥ��ע��ҳ��
	 * @param:
	 * @return:void
	 * @author: vicky
	 * @CreateDate: 2016��1��7��
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
	    	renderText("�˻�����������");
	    }
	}
	
}
