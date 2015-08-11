package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.dao.UserDAO;
import com.mockuai.usercenter.core.domain.UserDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDAOImpl extends SqlMapClientDaoSupport implements UserDAO {
	@Override
	public Long addUser(UserDO userDo) {
		// TODO Auto-generated method stub
		Long userId = (Long) getSqlMapClientTemplate().insert("user.insert",
				userDo);
		return userId;
	}

	@Override
	public int updatePwd(Long userId, String newPwd) {
		// TODO Auto-generated method stub
		UserDO key = new UserDO();
		key.setId(userId);
		key.setPassword(newPwd);
		int result = getSqlMapClientTemplate().update("user.updatePwd", key);
		return result;
	}

	@Override
	public int updateEmail(Long userId, String email) {
		// TODO Auto-generated method stub
		UserDO key = new UserDO();
		key.setId(userId);
		key.setEmail(email);
		int result = getSqlMapClientTemplate().update("user.updateEmail", key);
		return result;
	}

	@Override
	public int updateMobile(Long userId, String mobile) {
		// TODO Auto-generated method stub
		UserDO userDO = new UserDO();
		userDO.setId(userId);
		userDO.setMobile(mobile);
		int result = getSqlMapClientTemplate().update("user.updateMobile", userDO);
		return result;
	}

	@Override
	public int activeUser(Long id) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("user.activeUser", id);
		return result;
	}

	@Override
	public UserDO getUserByName(String name) {
		// TODO Auto-generated method stub
		UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
				"user.selectByName", name);
		return userDo;
	}

	@Override
	public UserDO getUserByEmail(String email) {
		// TODO Auto-generated method stub
		UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
				"user.selectByEmail", email);
		return userDo;
	}

	@Override
	public UserDO getUserByMobile(String mPhoneNo) {
		// TODO Auto-generated method stub
		UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
				"user.selectByMobile", mPhoneNo);
		return userDo;
	}

	@Override
	public UserDO getUserById(Long id) {
		// TODO Auto-generated method stub
		UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
				"user.selectById", id);
		return userDo;
	}

	@Override
	public int freezeUser(Long id) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("user.freezeUser", id);
		return result;
	}

	@Override
	public int moveToRecycle(Long id) {
		int result = getSqlMapClientTemplate().update("user.moveToRecycle", id);
		return result;
	}

	@Override
	public int deleteUser(Long id) {

		int result = getSqlMapClientTemplate().update("user.delete", id);
		return result;
	}

	@Override
	public List<UserDO> queryUser(UserQTO userQto) {

		List<UserDO> users = getSqlMapClientTemplate().queryForList(
				"user.queryUser", userQto);

		return users;
	}

	@Override
	public int setUserRoleMark(Long userId, Long roleMark) {
		//TODO 这里的roleMark的使用方式需要重构，重构成二进制打标的方式
		UserDO userDO = new UserDO();
		userDO.setId(userId);
		userDO.setRoleMark(roleMark);
		int result = getSqlMapClientTemplate().update("user.setRole", userDO);
		return result;
	}

	@Override
	public Long getTotalCount(UserDO userDo) {

		Long totalCount = (Long) getSqlMapClientTemplate().queryForObject(
				"user.totalCount", userDo);
		return totalCount;
	}

	@Override
	public int restoreUser(Long id) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("user.restore", id);
		return result;
	}

	@Override
	public UserDO getRecycleUser(Long id) {
		// TODO Auto-generated method stub
		UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
				"user.recycle", id);
		return userDo;
	}

	@Override
	public UserDO userLogin(String loginName, String loginPwd) {
		// TODO Auto-generated method stub
		UserDO userDo = new UserDO();
		userDo.setName(loginName);
		userDo.setPassword(loginPwd);
		return (UserDO) getSqlMapClientTemplate().queryForObject("user.login",
				userDo);
	}

	@Override
	public int updateName(Long userId, String name) {
		// TODO Auto-generated method stub
		UserDO userDo = new UserDO();
		userDo.setId(userId);
		userDo.setName(name);
		int result = getSqlMapClientTemplate()
				.update("user.updateName", userDo);
		return result;
	}

	@Override
	public int updateUser(UserDO userDo) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("user.updateUser", userDo);
		return result;
	}

	@Override
	public int updateHeadImg(Long userId, String imgUrl) {

		UserDO key = new UserDO();
		key.setId(userId);
		key.setImgUrl(imgUrl);
		int result = getSqlMapClientTemplate()
				.update("user.updateHeadImg", key);
		return result;
	}

	@Override
	public UserDO getByLoginName(String loginName) {

		UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
				"user.selectByLoginName", loginName);
		return userDo;
	}

	@Override
	public UserDO getByInvitationCode(String invitationCode)
	{
		UserDO userDo = (UserDO) getSqlMapClientTemplate().queryForObject(
				"user.selectByInvitationCode", invitationCode);
		return userDo;
	}

	@Override
	public int updateInvitationCode(Long userId, String invitationCode)
	{
		UserDO key = new UserDO();
		key.setId(userId);
		key.setInvitationCode(invitationCode);
		try
		{
			return getSqlMapClientTemplate().update("user.updateInvitationCode", key);
		}
		catch (DataAccessException e)
		{
			return -1;
		}
	}
}
