package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.dao.UserOpenInfoDAO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserOpenInfoManager;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
@Service
public class UserOpenInfoManagerImpl implements UserOpenInfoManager{
    private static final Logger log = LoggerFactory.getLogger(UserOpenInfoManagerImpl.class);

    @Resource
    private UserOpenInfoDAO userOpenInfoDAO;

    @Override
    public long addUserOpenInfo(UserOpenInfoDO userOpenInfoDO) throws UserException {
        if(userOpenInfoDO == null){
            throw new UserException(ResponseCode.P_PARAM_NULL, "userOpenInfoDO is null");
        }

        try{
            long userOpenInfoId = userOpenInfoDAO.addUserOpenInfo(userOpenInfoDO);
            return userOpenInfoId;
        }catch(Exception e){
            log.error("userOpenInfoDO:{}", JsonUtil.toJson(userOpenInfoDO), e);
            throw new UserException(ResponseCode.B_ADD_ERROR);
        }
    }

    @Override
    public int updateUserId(Long id, Long oldUserId, Long newUserId, String bizCode) throws UserException {
        try{
            int opNum = userOpenInfoDAO.updateUserId(id, oldUserId, newUserId, bizCode);
            return opNum;
        }catch(Exception e){
            log.error("id:{}, oldUserId:{}, newUserId:{}, bizCode:{}",
                    id, oldUserId, newUserId, bizCode, e);
            throw new UserException(ResponseCode.B_UPDATE_ERROR, "error to update userId");
        }

    }

    @Override
    public UserOpenInfoDO getUserOpenInfo(Integer openType, String openUid, String bizCode) throws UserException {
        try{
            UserOpenInfoDO userOpenInfoDO = userOpenInfoDAO.getUserOpenInfo(openType, openUid, bizCode);
            return userOpenInfoDO;
        }catch(Exception e){
            log.error("openType:{}, openUid:{}, bizCode:{}",
                    openType, openUid, bizCode, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to get userOpenInfo");
        }

    }

    @Override
    public UserOpenInfoDO getUserOpenInfoByUserId(Integer openType, Long userId, String bizCode) throws UserException {
        try{
            UserOpenInfoDO userOpenInfoDO = userOpenInfoDAO.getUserOpenInfoByUserId(openType, userId, bizCode);
            return userOpenInfoDO;
        }catch(Exception e){
            log.error("openType:{}, userId:{}, bizCode:{}",
                    openType, userId, bizCode, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to get userOpenInfo");
        }
    }

    @Override
    public int deleteUserOpenInfo(Long id, Long userId) throws UserException {
        try{
            int opNum = userOpenInfoDAO.deleteUserOpenInfo(id, userId);
            return opNum;
        }catch(Exception e){
            log.error("id:{}, userId:{}", id, userId, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to delete userOpenInfo");
        }

    }
}
