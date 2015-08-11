package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.dao.EnterpriseAuthExtendDAO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 8/5/15.
 */

@Service
public class EnterpriseAuthExtendManagerImpl implements EnterpriseAuthExtendManager{
    private static final Logger log = LoggerFactory.getLogger(EnterpriseAuthExtendManagerImpl.class);

    @Resource
    private EnterpriseAuthExtendDAO enterpriseAuthExtendDAO;

    public Long addEnterpriseAuthExtend(EnterpriseAuthExtendDO enterpriseAuthExtendDO) throws UserException {
        try{
            Long enterpriseAuthExtendId = enterpriseAuthExtendDAO.addEnterpriseAuthExtend(enterpriseAuthExtendDO);
            return enterpriseAuthExtendId;
        }catch(Exception e){
            log.error("", e);
            throw new UserException(ResponseCode.B_ADD_ERROR);
        }
    }

    public EnterpriseAuthExtendDO getEnterpriseAuthExtend(long userId) throws UserException {
        try{
            EnterpriseAuthExtendDO enterpriseAuthExtendDO = enterpriseAuthExtendDAO.getEnterpriseAuthExtend(userId);
            return enterpriseAuthExtendDO;
        }catch(Exception e){
            log.error("", e);
            throw new UserException(ResponseCode.B_SELECT_ERROR);
        }
    }

    public List<EnterpriseAuthExtendDO> queryEnterpriseAuthExtend(List<Long> userIdList) throws UserException {
        try{
            List<EnterpriseAuthExtendDO> enterpriseAuthExtendDOs =
                    enterpriseAuthExtendDAO.queryEnterpriseAuthExtend(userIdList);
            return enterpriseAuthExtendDOs;
        }catch(Exception e){
            log.error("", e);
            throw new UserException(ResponseCode.B_SELECT_ERROR);
        }
    }
}
