package com.mockuai.sellercenter.web.common.constant;

public enum ResponseEnum {
	/** 请求处理成功 */
	REQUEST_SUCESS(10000,"请求处理成功"),
	
	/** 参数类异常 */
	P_E_PARAM_ISNULL(20001,"参数不能为空"),
	P_E_PARAM_INVALID(20002,"参数非法"),
	P_E_USER_NOT_LOGIN(20003,"user_session_key is null,you need login."),
	
	/** 系统类异常 */
	S_E_SERVICE_ERROR(30001,"系统异常，请稍后重试"),

	S_E_OSS_FILE_ERROR(30002,"文件下载异常，请稍后重试"),

	S_E_OSS_FILE_NOT_EXISTS_ERROR(30003,"文件不存在"),
	
	/** 业务类异常 */
	B_E_SESSION_TIMEOUT(40000,"登入已过期，请重新登入"),
	B_E_PASSWORD_INVALID(40001,"用户名或是密码错误"),
	B_E_RECORD_NOT_EXIST(41000,"请求记录不存在"),
	B_E_VERYFY_CODE_TIMEOUT(40002,"验证码已失效"),
	B_E_VERYFY_CODE_INVALID(40003,"验证码错误"),
	B_E_API_LOGIN_NOT_SUPPORT(40005,"不支持该第三方登入方式");
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private int code;
	private String msg;
	private ResponseEnum(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
