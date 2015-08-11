package com.mockuai.usercenter.client;

import java.util.List;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserInfoDTO;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.qto.UserQTO;

public interface UserClient {

	/**
	 * 激活用户
	 */
	Response<Boolean> activeUser(Long userId, String appKey);

	/**
	 * 删除用户
	 */
	Response<Void> deletedUser(Long userId, String appKey);

	/**
	 * 冻结用户
	 */
	Response<Boolean> freezeUser(Long userId, String appKey);

	/**
	 * 根据邮箱获取用户
	 */
	Response<UserDTO> getUserByEmail(String email, String appKey);

	/**
	 * 根据id获取用户
	 */
	Response<UserDTO> getUserById(Long userId, String appKey);

	/**
	 * 根据电话获取用户
	 */
	Response<UserDTO> getUserByMobile(String mobile, String appKey);

	/**
	 * 根据用户名获取用户
	 */
	Response<UserDTO> getUserByName(String name, String appKey);

	/**
	 * 添加用户
	 */
	Response<UserDTO> addUser(UserDTO userDto, String appKey);

	/**
	 * 添加来自第三方开放平台的用户
	 * @param userDto
	 * @return
	 */
	Response<UserDTO> addOpenUser(UserDTO userDto, String appKey);

	/**
	 * 将用户移入回收站
	 */
	Response<Boolean> moveUserIntoRecycle(Long userId, String appKey);

	/**
	 * 将用户从回收站还原
	 */
	Response<Boolean> restoreUser(Long userId, String appKey);

	/**
	 * 设置用户角色
	 */
	Response<Boolean> setUserRole(Long userId, Byte role, String appKey);

	/**
	 * 修改邮箱
	 */
	Response<Boolean> updateEmail(Long userId, String email, String appKey);

	/**
	 * 修改手机
	 */
	Response<Boolean> updateMobile(Long userId, String mobile, String appKey);

	/**
	 * 修改密码
	 */
	Response<Void> updatePwd(Long userId, String newPwd, String appKey);

	/**
	 * 修改用户头像
	 */
	Response<Boolean> updateHeadImg(Long userId, String headImg, String appKey);

	/**
	 * 修改邀请码
	 */
	Response<Boolean> updateInvitationCode(Long userId, String invitationCode, String appKey);

	/**
	 * 生成不重复的邀请码
	 * */
	Response<String> generateInvitationCode(String appKey);

	/**
	 * 查询用户
	 */
	Response<List<UserDTO>> queryUser(UserQTO userQto, String appKey);

	/**
	 * 第三方用户登陆
	 */
	Response<UserDTO> apiUserLogin(UserInfoDTO userInfoDto, String appKey);

	/**
	 * 用户登陆
	 */
	Response<UserDTO> userLogin(String loginName, String loginPwd, String appKey);

	/**
	 *
	 * @param openType
	 * @param openId
	 * @return
	 */
	Response<UserOpenInfoDTO> getUserOpenInfo(Integer openType, String openUid, String appKey);

	/**
	 *
	 * @param userOpenInfoDTO
	 * @return
	 */
	Response<UserOpenInfoDTO> addUserOpenInfo(UserOpenInfoDTO userOpenInfoDTO, String appKey);

	Response<Void> bingUserOpenInfo(Integer openType, String openUid,
									String mobile, String password, String invitationCode, String appKey);
}
