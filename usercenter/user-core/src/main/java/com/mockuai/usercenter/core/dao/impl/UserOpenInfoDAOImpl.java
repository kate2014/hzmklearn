package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.core.dao.UserOpenInfoDAO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
@Service
public class UserOpenInfoDAOImpl extends SqlMapClientDaoSupport implements UserOpenInfoDAO {
    @Override
    public long addUserOpenInfo(UserOpenInfoDO userOpenInfoDO) {
        long userOpenInfoId = (Long) getSqlMapClientTemplate().insert(
                "user_open_info.addUserOpenInfo", userOpenInfoDO);
        return userOpenInfoId;
    }

    @Override
    public UserOpenInfoDO getUserOpenInfo(Integer openType, String openUid, String bizCode) {
        UserOpenInfoDO userOpenInfoDO = new UserOpenInfoDO();
        userOpenInfoDO.setOpenType(openType);
        userOpenInfoDO.setOpenUid(openUid);
        userOpenInfoDO.setBizCode(bizCode);
        userOpenInfoDO = (UserOpenInfoDO) getSqlMapClientTemplate().queryForObject(
                "user_open_info.getUserOpenInfo", userOpenInfoDO);
        return userOpenInfoDO;
    }

    @Override
    public UserOpenInfoDO getUserOpenInfoByUserId(Integer openType, Long userId, String bizCode) {
        UserOpenInfoDO userOpenInfoDO = new UserOpenInfoDO();
        userOpenInfoDO.setOpenType(openType);
        userOpenInfoDO.setUserId(userId);
        userOpenInfoDO.setBizCode(bizCode);
        userOpenInfoDO = (UserOpenInfoDO) getSqlMapClientTemplate().queryForObject(
                "user_open_info.getUserOpenInfoByUserId", userOpenInfoDO);
        return userOpenInfoDO;
    }

    public int updateUserId(Long id, Long oldUserId, Long newUserId, String bizCode){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", id);
        params.put("oldUserId", oldUserId);
        params.put("newUserId", newUserId);
        params.put("bizCode", bizCode);
        int opNum = getSqlMapClientTemplate().update("user_open_info.updateUserId", params);
        return opNum;
    }

    @Override
    public int deleteUserOpenInfo(Long id, Long userId) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", id);
        params.put("userId", userId);
        //FIXME 为了防止唯一性冲突，所以这里需要为deleteVersion字段填充时间戳
        params.put("deleteVersion", System.currentTimeMillis());
        int opNum = getSqlMapClientTemplate().update("user_open_info.deleteUserOpenInfo", params);
        return opNum;
    }
}
