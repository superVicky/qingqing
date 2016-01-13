package com.vicky.utils;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.CaptchaRender;
import com.jfinal.kit.StrKit;
import com.jfinal.render.Render;
import com.vicky.config.Const;

public class CaptchaController extends Controller {
	
	
	/** 
	* @Title: index 
	* @Description: TODO(产生验证码) 
	* void
	*/ 
	public void index(){
		 /* CaptchaRender captchaRender= new CaptchaRender(kaptchaKit.getKaptcha(this.getSession()));
		  
		  System.out.println(captchaRender.toString());
		  System.out.println(captchaRender.hashCode());*/
		System.out.println(getPara(0));
		System.out.println("刷新验证码"+getPara(0));
        render(new MyCaptchaRender(74,20,5,true));
	}
	/** 
	* @Title: validSafcecode 
	* @Description: TODO(验证输入验证码是否正�?) 
	* void
	*/ 
	public void validSafcecode(){
		    String safecode=getPara("verifyCode").toUpperCase();
	        if(StrKit.notBlank(safecode)&&safecode.equals(getSessionAttr(Const.kaptchaKey))){
	            renderText("true");
	        }else {
	            renderText("false");
	        }
	}

}
