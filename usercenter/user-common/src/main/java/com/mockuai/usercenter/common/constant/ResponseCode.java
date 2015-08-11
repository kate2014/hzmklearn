package com.mockuai.usercenter.common.constant;

public enum ResponseCode {
	/**
	 * 请求成功
	 **/
	REQUEST_SUCCESS(10000, "request success"),


	/*********** 参数错误 ************/
	// NAME_LENGTH_ERROR("P001","用户名长度错误"),
	// PWD_FORMAT_ERROR("P002","密码的长度必须为8-20,并且由字母，数字和下划线组成"),
	// EMAIL_FORMAT_ERROR("P003","邮箱格式错误"),
	// MOBILE_FORMAT_ERROR("P004","手机格式错误"),
	// NAME_AND_PWD_IS_NULL("P005","用户名或密码为空"),
	// NAME_ALREADY_REG("P006","用户名已经被注册"),
	// EMAIL_ALREADY_REG("P007","邮箱已经被注册"),
	// MOBILE_ALREADY_REG("P008","号码已经被注册"),
	// USER_NOT_EXIST("P009","用户不存在"),
	// PARAM_E_PARAM_INVALID("P010","参数错误"),
	// USRE_ID_IS_NULL("P011","用户id为空"),
	P_PARAM_ERROR(20001, "param error"),
	P_PARAM_NULL(20002, "param null"),

	/********* 业务错误 ***********/
	// B_OLDPWD_NEWPWD_IS_SAME("B001","新老密码相同"),
	// B_PWD_IS_ERROR("B002","密码错误"),
	// B_RECORD_IS_EXIST("B003","记录已存在,重复添加记录"),
	// B_RECORD_IS_NOT_EXIST("B004","不存在指定的记录，不能修改");
	B_ADD_ERROR(30001, "add error"),
	B_UPDATE_ERROR(30002, "update error"),
	B_DELETE_ERROR(30003, "delete error"),
	B_SELECT_ERROR(30004, "select error"),
	/**
	 * 访问的接口不存在
	 * */
	B_ACTION_NO_EXIST(30005, "action no exist"),
	/**
	 * 请求被禁用
	 * */
	B_REQUEST_FORBBIDEN(30006, "request forbbiden"),
	/**
	 * 邮箱已存在
	 */
	B_EMAIL_IS_EXIST(30007, "email is exist"),
	/**
	 * 手机号 已存在
	 */
	B_MOBILE_IS_EXIST(30008, "mobile is exist"),
	/**
	 * 原密码错误
	 */
	B_OLDPWD_ERROR(30009, "old password is error"),

	/**
	 * 账号不存在
	 */
	B_ACCOUNT_NOT_EXIST(30010, "account not exist"),

	/**
	 * 密码错误
	 */
	B_PASSWORD_ERROR(30011, "password error"),
	B_BIND_DUPLICATE(30012, "duplicate bind error"),
	B_INVITATION_CODE_INVALID(30013, "invitation code is invalid"),
	B_OPEN_ACCOUNT_NOT_EXIST(30014, "the specified open account is not exist"),
	B_MOBILE_HAS_BINDED_BY_OTHER_PERSON(30015, "the specified mobile has binded by other people"),
	B_ID_CARD_NO_HAS_BEEN_AUTHED(30016, "idCardNo has been authed"),
	B_USER_AUTH_INFO_NOT_EXIST(30017, "userAuthInfo not exist"),
	B_USER_AUTH_INFO_IS_EXIST(30018, "userAuthInfo is exist already"),
	B_APP_NOT_EXIST(30019, "the specified app is not exist"),
	B_BIND_CONFLICT(30020, "bind conflict error"),
	B_INVITER_NOT_EXIST(30021, "the specified inviter is not exist"),
	/**
	 * 系统异常
	 * */
	SYS_E_SERVICE_EXCEPTION(40001, "system exception"),
	;

	private int value;
	private String desc;

	private ResponseCode(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
