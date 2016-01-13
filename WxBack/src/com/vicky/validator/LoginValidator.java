package com.vicky.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.vicky.model.User;
import com.vicky.utils.MyCaptchaRender;

public class LoginValidator extends Validator {
    
   protected void validate(Controller controller) {
		validateRequiredString("user_name", "nameMsg", "�������¼�û���!");
		validateRequiredString("user_pwd", "pwdMsg", "�������¼����!");
		validateRequiredString("verifyCode", "verifyMsg", "��������֤��!");
		String inputRandomCode=controller.getPara("verifyCode");
	    if(!MyCaptchaRender.validate(controller, inputRandomCode)){
	    	 addError("verifyMsg", "��֤�����벻��ȷ,����������!");
	    }
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(User.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/login/login"))
			controller.render("/login.html");
	}
}
