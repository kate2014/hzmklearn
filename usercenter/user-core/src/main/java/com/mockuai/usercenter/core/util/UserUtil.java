package com.mockuai.usercenter.core.util;

import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;

/**
 * 用户工具类
 * */
public class UserUtil {
	
	private static final String UNDERLINE = "_";
	
	/**
	 * 检验用户的注册信息是否合法
	 * */
	public static ResponseCode userInfoIsLegal(UserDTO userDto)
			throws UserException {
//		String name = userDto.getName();
		String pwd = userDto.getPassword();
		String email = userDto.getEmail();
		String mobile = userDto.getMobile();

		// 检查用户名
//		checkName(name);

		// 密码的长度必须为8-20,并且由字母，数字和下划线组成
		checkPwd(pwd);

		// 手机和邮箱可以有一个为空，但手机号或者是邮箱必须满足指定的格式
		if (null == email && null == mobile) {
			new UserException(ResponseCode.P_PARAM_NULL,
					"mobile or email can't  null");
		}

		if (email != null) {
			checkEamil(email);
		}
		if (mobile != null) {
			checkMobile(mobile);
		}

		return null;
	}

	/**
	 * 查看密码是否合法
	 * */
	public static ResponseCode checkPwd(String password) throws UserException {

		if (null == password) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"password is null");
		}

		if (null == password.trim()) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"password is null");
		}

		//TODO 密码校验逻辑重构
//		if (!password.matches("^[a-zA-Z0-9_]{8,20}$")) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"password format error");
//		}
		return null;
	}

	/**
	 * 查看手机号码是否合法
	 * */
	public static ResponseCode checkMobile(String Mobile) throws UserException {
		if (null != Mobile) {
			// 判断手机号码长度是否满足
			if (!Mobile.matches("1(\\d){10}")) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"mobile format error");
			}
		} else if (null == Mobile) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "mobile is null");
		}
		return null;
	}

	/**
	 * 座机号码验证
	 * */
	public static ResponseCode checkPhoneNo(String phoneNo)
			throws UserException {
		if (null != phoneNo) {
			// 判断手机号码长度是否满足
			if (!phoneNo.matches("0\\d{2,3}-?\\d{7,8}")) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"phoneNo format error");
			}
		}
		return null;
	}

	/**
	 * 检查qq号
	 * */
	public static ResponseCode checkQQ(String QQ) throws UserException {
		if (null != QQ) {
			// 判断手机号码长度是否满足
			if (!QQ.matches("(\\d){5,11}")) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"QQ format error");
			}
		} else if (null == QQ) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "QQ is null");
		}
		return null;
	}

	public static ResponseCode checkEamil(String email) throws UserException {
		if (null != email) {
			// 判断邮箱是否满足格式
			if (!email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"email format error");
			}
		} else if (null == email) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "email is null");
		}
		return null;
	}

	/**
	 * 获取允许包含中文字符的字符串的长度 中文字符为两个长度，英文字符为一个长度
	 * */
	public static UserResponse checkName(String name) throws UserException {
		if (null == name) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "name is null");
		}

		if (null == name.trim()) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "name is null");
		}

		int length = 0;
		char[] names = name.toCharArray();
		for (char ch : names) {
			if (ch >= 19968 && ch <= 171941) {
				length = length + 2;
			} else {
				length = length + 1;
			}
		}
		// 用户名用中英文、数字、下划线，长度为4-16位(包含中文时为2-8位)
		if (length > 16 || length < 4) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"username length error");
		}
		return null;
	}
	
	/**
	 * 生成uid 
	 * @param userId
	 * @param id
	 * @return
	 */
	public static String generateUid(Long userId,Long id){
		return String.valueOf(userId) +  UNDERLINE +String.valueOf(id);
	}
	

	public static void main(String[] args) {
		/*
		 * String str = "454_fd63@sinadsf.com"; System.out.println(str
		 * .matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"));
		 * String name = "叶正磊012"; char[] names = name.toCharArray();
		 * System.out.println(names[2]); char a = '叶'; System.out.println((int)
		 * a); System.out.println(getStringLength("叶正磊1fds"));
		 */

		try {
			checkEamil("dnjnfslkffkjkjkslioeo9edkdjfks");
			System.out.println(1);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
