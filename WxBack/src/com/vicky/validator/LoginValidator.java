package com.vicky.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.vicky.model.User;
import com.vicky.utils.MyCaptchaRender;

public class LoginValidator extends Validator {
    
   protected void validate(Controller controller) {
		validateRequiredString("user_name", "nameMsg", "请输入登录用户名!");
		validateRequiredString("user_pwd", "pwdMsg", "请输入登录密码!");
		validateRequiredString("verifyCode", "verifyMsg", "请输入验证码!");
		String inputRandomCode=controller.getPara("verifyCode");
	    if(!MyCaptchaRender.validate(controller, inputRandomCode)){
	    	 addError("verifyMsg", "验证码输入不正确,请重新输入!");
	    }
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(User.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/login/login"))
			controller.render("/login.html");
	}
}
