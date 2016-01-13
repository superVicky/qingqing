package com.vicky.validator;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.vicky.model.User;
import com.vicky.utils.MD5Util;
import com.vicky.utils.MyCaptchaRender;

public class RegValidator  extends Validator {
    
   protected void validate(Controller controller) {
		validateRequiredString("user.user_name", "nameMsg", "请填写用户名!");
		validateRequiredString("user.user_phone", "phoneMsg", "请填写手机号码!");
		String actionKey = getActionKey();
		Boolean checkPhone=true;
		if(actionKey.equals("/user/settingInfo")){
			if(controller.getPara("user.user_phone").equals(controller.getPara("user_phone_hid"))){
				checkPhone=false;
			}
		}else if (actionKey.equals("/user/saveUser")){
			validateRequiredString("user.user_pwd", "pwdMsg", "请输入密码!");
			validateRequiredString("pwd2", "pwd2Msg", "请再输入密码!");
			//validateRequiredString("user.user_invite_code", "codeMsg", "请输入推荐码!");
			validateRequiredString("verifyCode", "verifyMsg", "请输入验证码!");
			String inputRandomCode=controller.getPara("verifyCode");
		    if(!MyCaptchaRender.validate(controller, inputRandomCode)){ 
		    	 addError("verifyMsg", "验证码输入不正确,请重新输入!");
		    }
		}
		if(checkPhone){
			//手机号不能重复
		    List<User> user = User.dao.find("select userid from crm_user where user_phone=?", controller.getPara("user.user_phone"));
	        if(user.size()>0){
	        	addError("phoneMsg", "该手机号已注册!");
	        }
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(User.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/user/saveUser"))
			controller.render("/register.html");
		else if (actionKey.equals("/user/settingInfo"))
			controller.render("setting.html");
	}

}
