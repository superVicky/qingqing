package com.vicky.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class User extends Model<User> {
	/**
	 * ”√ªß±Ì
	 */
	private static final long serialVersionUID = 1L;
	public static final User dao = new User();
	
	/*public Page<User> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from crm_user order by createtime desc");
	}*/
}
