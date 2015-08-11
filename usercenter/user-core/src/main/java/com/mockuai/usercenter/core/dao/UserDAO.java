package com.mockuai.usercenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.domain.UserDO;

@Service
public interface UserDAO {
	Long addUser(UserDO userDo);

	int updatePwd(Long userId, String newPwd);

	int updateEmail(Long userId, String email);

	int updateMobile(Long userId, String mobile);

	int activeUser(Long userId);

	UserDO getUserByName(String name);

	UserDO getUserByEmail(String email);

	UserDO getUserByMobile(String mobile);

	UserDO getUserById(Long userId);

	int freezeUser(Long userId);

	int moveToRecycle(Long userId);

	int deleteUser(Long userId);

	/**
	 * 复合查询用户列表
	 * */
	List<UserDO> queryUser(UserQTO userQto);

	/**
	 * 设置用户角色
	 * */
	int setUserRoleMark(Long userId, Long roleMark);

	/**
	 * 获取表中记录的总数
	 * */
	Long getTotalCount(UserDO usreDo);

	/**
	 * 将用户从回收站还原
	 * */
	int restoreUser(Long userId);

	/**
	 * 获取指定回收站中的用户
	 * */
	UserDO getRecycleUser(Long userId);

	/**
	 * 用户登陆接口
	 * */
	UserDO userLogin(String loginName, String loginPwd);

	/**
	 * 修改用户名称
	 * */
	int updateName(Long userId, String name);

	/**
	 * 修改用户的基础信息
	 * */
	int updateUser(UserDO userDo);

	/**
	 * 修改用户头像
	 * 
	 * @param userId
	 * @param headImg
	 * @return
	 */
	int updateHeadImg(Long userId, String headImg);

	/**
	 * 根据登陆名查找用户
	 * 
	 * @param loginName
	 * @return
	 */
	UserDO getByLoginName(String loginName);

	/**
	 * 根据邀请码查用户
	 * */
	UserDO getByInvitationCode(String invitationCode);

	/**
	 * 修改用户邀请码
	 * */
	int updateInvitationCode(Long userId, String name);
}
