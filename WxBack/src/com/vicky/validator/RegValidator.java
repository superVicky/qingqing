package com.vicky.validator;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.vicky.model.User;
import com.vicky.utils.MD5Util;
import com.vicky.utils.MyCaptchaRender;

public class RegValidator  extends Validator {
    
   protected void validate(Controller controller) {
		validateRequiredString("user.user_name", "nameMsg", "����д�û���!");
		validateRequiredString("user.user_phone", "phoneMsg", "����д�ֻ�����!");
		String actionKey = getActionKey();
		Boolean checkPhone=true;
		if(actionKey.equals("/user/settingInfo")){
			if(controller.getPara("user.user_phone").equals(controller.getPara("user_phone_hid"))){
				checkPhone=false;
			}
		}else if (actionKey.equals("/user/saveUser")){
			validateRequiredString("user.user_pwd", "pwdMsg", "����������!");
			validateRequiredString("pwd2", "pwd2Msg", "������������!");
			//validateRequiredString("user.user_invite_code", "codeMsg", "�������Ƽ���!");
			validateRequiredString("verifyCode", "verifyMsg", "��������֤��!");
			String inputRandomCode=controller.getPara("verifyCode");
		    if(!MyCaptchaRender.validate(controller, inputRandomCode)){ 
		    	 addError("verifyMsg", "��֤�����벻��ȷ,����������!");
		    }
		}
		if(checkPhone){
			//�ֻ��Ų����ظ�
		    List<User> user = User.dao.find("select userid from crm_user where user_phone=?", controller.getPara("user.user_phone"));
	        if(user.size()>0){
	        	addError("phoneMsg", "���ֻ�����ע��!");
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
