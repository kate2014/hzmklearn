package com.mockuai.usercenter.common.action;

public enum ActionEnum {
	/***************************************************************************/

	/**
	 * 用户登陆
	 * */
	USER_LOGIN("loginUser"),

	/**
	 * 第三方用户登陆
	 * */
	API_USER_LOGIN("apiUserLogin"),

	/************************************ 用户操作接口 ********************************************/
	/**
	 * 添加用户
	 */
	ADD_USER("addUser"),

	/**
	 * 添加来自第三方开放平台的用户
	 */
	ADD_OPEN_USER("addOpenUser"),

	/**
	 * 修改邮箱
	 * */
	UPDATE_EMAIL("updateEmail"),

	/**
	 * 修改电话
	 * */
	UPDATE_MOBILE("updateMobile"),

	/**
	 * 修改密码
	 * */
	UPDATE_PWD("updatePwd"),

	/**
	 * 修改头像
	 */
	UPDATE_HEADIMG("updateHeadImg"),

	/**
	 * 修改邀请码
	 * */
	UPDATE_INVITATION_CODE("updateInvitationCode"),

	/**
	 * 更新邀请人id
	 */
	UPDATE_INVITER_ID("updateInviterId"),

	/**
	 * 生成不重复的邀请码
	 * */
	GENERATE_INVITATION_CODE("generateInvitationCode"),

	/**
	 * 修改用户的基础信息
	 * */
	UPDATE_USER("updateUser"),

	/**
	 * 激活用户
	 * */
	ACTIVE_USER("activeUser"),

	/**
	 * 冻结用户
	 * */
	FREEZE_USER("freezeUser"),

	/**
	 * 将用户移入回收站
	 * */
	MOVE_USER("moveUser"),

	/**
	 * 将用户删除
	 * */
	DELETE_USER("deleteUser"),

	/***
	 * 根据用户id获取用户
	 * */
	GET_USER_BY_ID("getUserById"),

	/**
	 * 根据用户名获取用户
	 * */
	GET_USER_BY_NAME("getUserByName"),

	/**
	 * 根据手机号码获取用户
	 * */
	GET_USER_BY_MOBILE("getUserByMobile"),

	/**
	 * 根据邮件获取用户
	 * */
	GET_USER_BY_EMAIL("getUserByEmail"),

	/**
	 * 符合查询用户列表
	 * */
	QUERY_USER("queryUser"),

	/**
	 * 设置用户角色
	 * */
	SET_ROLE("setRole"),

	/**
	 * 将回收站里的用户还原
	 * */
	RESTORE_USER("restoreUser"),

	/*************************************** 用户扩展信息接口 *********************************************/

	/**
	 * 添加普通用户扩展信息
	 * */
	ADD_USER_EXTRA_INFO("addUserExtraInfo"),

	/**
	 * 修改普通用户的扩展信息
	 * */
	UPDATE_USER_EXTRA_INFO("updateUsreExtraInfo"),

	/**
	 * 添加供应商的扩展信息
	 * */
	ADD_SUPPLIER_EXTRA_INFO("addSupplierExtraInfo"),

	/**
	 * 获取供应商的扩展信息
	 */
	GET_SUPPLIER_EXTRA_INFO("getSupplierExtraInfo"),

	/**
	 * 修改供应商的扩展信息
	 * */
	UPDATE_SUPPLIER_EXTRA_INFO("updateSupplierExtraInfo"),

	/**
	 * 供应商审核通过
	 * */
	SUPPLIER_IDENTIFIED_ACTION("supplierIdentifiedAction"),
	/**
	 * 供应商审核失败
	 * */
	SUPPLIER_FAIL_IDENTIFIED_ACTION("supplierFailIdentifiedAction"),

	/**
	 * 忽略供应商审核
	 * */
	SUPPLIER_IGNORE_IDENTIFIED_ACTION("supplierIgnoreIdentifiedAction"),

	/**
	 * 获得买家用户的扩展信息
	 */
	GET_USER_EXTRA_INFO("getUserExtraInfo"),

	/******************************** 用户宝贝信息接口 ***************************************/
	/**
	 * 添加用户的宝贝信息
	 * */
	ADD_USER_BABY_INFO("addUserBabyInfo"),

	/**
	 * 删除用户的宝贝信息
	 * */
	DELETE_USER_BABY_INFO("deleteBabyInfo"),

	/**
	 * 修改用户的宝贝信息
	 * */
	UPDATE_USER_BABY_INFO("updateBabyInfo"),

	/**
	 * 获得指定用户的宝贝信息
	 * */
	QUERY_USER_BABYS("getUserBabys"),

	/************************************** 用户实名认证信息 *********************************************/

	/**
	 * 添加用户实名认证信息
	 * */
	ADD_USER_AUTH_INFO("addUserAuthInfo"),

	/**
	 * 用户认证通过
	 * */
	PASS_USER_AUTH_INFO("passUserAuthInfo"),

	/**
	 * 用户认证不通过
	 * */
	REJECT_USER_AUTH_INFO("rejectUserAuthInfo"),

	/**
	 * 更新用户实名认证信息
	 * */
	UPDATE_USER_AUTH_INFO("updateUserAuthInfo"),

	/**
	 * 获取指定用户的实名认证信息
	 * */
	GET_USER_AUTH_INFO("getUserAuthInfo"),

	/**
	 * 查询用户实名认证信息
	 */
	QUERY_USER_AUTH_INFO("queryUserAuthInfo"),

	/******************************** 用户收货地址 管理 *****************************/
	ADD_CONSIGNEE("addConsignee"),
	/**
	 * 删除指定的收货地址
	 * */
	DELETE_CONSIGNEE("deleteConsignee"),

	/**
	 * 修改指定的收货地址
	 * */
	UPDATE_CONSIGNEE("updateConsignee"),

	/**
	 * 将指定的地址设为默认
	 * */
	SET_DEFAULT_CONSIGNEE("setDefConsignee"),

	/**
	 * 查询用户的所有收货地址
	 * */
	QUERY_CONSIGNEE("queryConsignee"),

	/**
	 * 根据收货地址id查询指定的收货地址
	 */
	GET_CONSIGNEE_BY_ID("getConsigneeById"),

	/***************************** 供应商所属公司管理 ****************************/
	/**
	 * 添加公司
	 * */
	ADD_SUPPLIER_COMPANY("addSupplierCompany"),

	/**
	 * 删除公司
	 * */
	DELETE_SUPPLIER_COMPANY("delSupplierCompany"),
	/**
	 * 修改公司
	 * */
	UPDATE_SUPPLIER_COMPANY("updateSupplierCompany"),

	/**
	 * 查询公司列表
	 * */
	QUERY_SUPPLIER_COMPANY("querySupplierCompany"),

	/******************************* 意见反馈管理 **************************************/

	/**
	 * 添加意见反馈
	 */
	ADD_FEEDBACK("addFeedback"),

	/**
	 * 根据id查询意见反馈
	 */
	GET_FEEDBACK("getFeedback"),

	/**
	 * 查询意见反馈消息
	 */
	QUERY_USER_FEEDBACK("queryFeedback"),

	/******************************* 用户组管理 **************************************/

	/**
	 * 添加用户组信息
	 * */
	ADD_USER_GROUP("addUserGroup"),

	/**
	 * 删除用户组信息
	 * */
	DEL_USER_GROUP("delUserGroup"),

	/***
	 * 删除用户组信息
	 * */
	UPDATE_USER_GROUP("deleteUserGroup"),

	/**
	 * 查询用户组列表
	 * */
	QUERY_USER_GROUP("queryUserGroup"),

	/**
	 * 根据用户积分获取相应用户组信息
	 * */
	GET_USER_GROUP("getUserGroup"),



	/**
	 * 商家入驻
	 * */
	SUPPLIER_RESIDENT("supplierResident"),
	
	SELLER_USER_RELATE("sellerUserRelate"),
	
	QUERY_SELLER_USER_RELATE("querySellerUserRelate"),


	/******************************* 用户消息管理 qqyu**************************************/

	/**
	 * 发送消息
	 */
	ADD_USER_MESSAGE("addUserMessage"),

	/**
	 * 删除消息
	 */
	DEL_USER_MESSAGE("deleteUserMessage"),

	/**
	 * 获取消息列表
	 */
	QUERY_USER_MESSAGE("queryUserMessage"),

	/**
	 * 修改消息状态
	 */
	UPDATE_USER_MESSAGE_STATUS("updateUserMessageStatus"),

	/**
	 * 新增全局属性
	 */
	ADD_GLOBAL_MESSAGE("addGlobalMessage"),
	
	/**
	 * 查询全局消息
	 */
	QUERY_GLOBAL_MESSAGE("queryGlobalMessage"),
	
	/**
	 * 发送短信接口
	 */
	SEND_SMS("sendSms"),

	/******************************* 用户第三方开放平台信息管理**************************************/
	ADD_USER_OPEN_INFO("addUserOpenInfo"),
	GET_USER_OPEN_INFO("getUserOpenInfo"),
	BIND_USER_OPEN_INFO("bindUserOpenInfo"),
	RELEASE_USER_OPEN_INFO("releaseUserOpenInfo"),
	/*******************************数据迁移接口*****************************/
	INSERT_USER("insertUser"),
	INSERT_USER_OPEN_INFO("insertUserOpenInfo"),
	GET_MIGRATE_USER_OPEN_INFO("getMigrateUserOpenInfo"),
	GET_USER_BY_ORIGIN("getUserByOrigin")
	;


	private String actionName;

	private ActionEnum(String actionName) {
		this.actionName = actionName;
	}

	public static ActionEnum getActionEnum(String actionName) {
		for (ActionEnum ae : ActionEnum.values()) {
			if (ae.actionName.equals(actionName)) {
				return ae;
			}
		}
		return null;
	}

	public String getActionName() {
		return actionName;
	}
}
