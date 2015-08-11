package com.mockuai.sellercenter.web.common.constant;

public interface MessageConstants {

	public static final String ERROR_MSG = "errorMsg";
	
	/**
	 * 登入相关的提示信息
	 */
	public static class UserLogin{
		public static final String USERNAME_PASSWORD_NULL = "请输入用户名和密码";
		public static final String USERNAME_PASSWORD_ERROR = "用户名或是密码错误，请重新输入";
		public static final String VERIFY_CODE_ERROR = "验证码错误";
	}
	
	
}
