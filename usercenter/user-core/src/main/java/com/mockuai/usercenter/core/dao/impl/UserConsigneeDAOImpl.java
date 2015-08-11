package com.mockuai.usercenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.mockuai.usercenter.core.dao.UserConsigneeDAO;
import com.mockuai.usercenter.core.domain.UserConsigneeDO;

@Component
public class UserConsigneeDAOImpl extends SqlMapClientDaoSupport implements
		UserConsigneeDAO {

	@Override
	public int getConsigneeCountByUserId(Long userId) {
		// TODO Auto-generated method stub
		int count = (Integer) getSqlMapClientTemplate().queryForObject(
				"user_consignee_info.selectCount", userId);
		return count;
	}

	@Override
	public Long addConsigee(UserConsigneeDO userConsigneeDo) {
		// TODO Auto-generated method stub

		Long id = (Long) getSqlMapClientTemplate().insert(
				"user_consignee_info.insert", userConsigneeDo);

		return id;
	}

	@Override
	public UserConsigneeDO getConsigneeById(Long userId, Long id) {

		UserConsigneeDO key = new UserConsigneeDO();
		key.setId(id);
		key.setUserId(userId);
		key = (UserConsigneeDO) getSqlMapClientTemplate().queryForObject(
				"user_consignee_info.selectById", key);

		return key;
	}

	@Override
	public int deleteConsignee(Long userId, Long id) {
		UserConsigneeDO key = new UserConsigneeDO();
		key.setId(id);
		key.setUserId(userId);
		int result = getSqlMapClientTemplate().update(
				"user_consignee_info.deleteById", key);
		return result;
	}

	@Override
	public int updateConsigee(UserConsigneeDO userConsigneeDo) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update(
				"user_consignee_info.updateById", userConsigneeDo);
		return result;
	}

	@Override
	public int updateUserDefaultConsignee(Long userId) {
		// TODO Auto-generated method stub

		int result = getSqlMapClientTemplate().update(
				"user_consignee_info.updateDefaultConsignee", userId);
		return result;
	}

	@Override
	public int setDefConsignee(Long userId, Long id) {
		UserConsigneeDO key = new UserConsigneeDO();
		key.setId(id);
		key.setUserId(userId);
		int result = getSqlMapClientTemplate().update(
				"user_consignee_info.setDefConsignee", key);
		return result;
	}

	@Override
	public List<UserConsigneeDO> queryConsignee(Long userId) {
		// TODO Auto-generated method stub

		List<UserConsigneeDO> userConsigneeDos = getSqlMapClientTemplate()
				.queryForList("user_consignee_info.queryConsignee", userId);

		return userConsigneeDos;
	}

	@Override
	public int deleteUserConsignee(Long userId) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update(
				"user_consignee_info.deleteByUserId", userId);
		return result;
	}

	@Override
	public int restoreUserConsignee(Long userId) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update(
				"user_consignee_info.restoreByUserId", userId);
		return result;
	}
}
