package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
public interface UserOpenInfoManager {
    /**
     * 新增第三方账号信息
     * @param userOpenInfoDO
     * @return
     * @throws UserException
     */
    public long addUserOpenInfo(UserOpenInfoDO userOpenInfoDO) throws UserException;

    /**
     * 更新指定第三方账号所绑定的用户id
     * @param id
     * @param oldUserId
     * @param newUserId
     * @param bizCode
     * @return
     * @throws UserException
     */
    public int updateUserId(Long id, Long oldUserId, Long newUserId, String bizCode) throws UserException;

    /**
     * 根据第三方账号的uid查询第三方账号信息
     * @param openType
     * @param openUid
     * @param bizCode
     * @return
     * @throws UserException
     */
    public UserOpenInfoDO getUserOpenInfo(Integer openType, String openUid, String bizCode) throws UserException;

    /**
     * 查询用户所关联的指定类型的第三方账号信息
     * @param openType
     * @param userId
     * @param bizCode
     * @return
     * @throws UserException
     */
    public UserOpenInfoDO getUserOpenInfoByUserId(Integer openType, Long userId, String bizCode) throws UserException;

    /**
     * 删除指定的第三方账号信息
     * @param id
     * @param userId
     * @return
     * @throws UserException
     */
    public int deleteUserOpenInfo(Long id, Long userId) throws UserException;
}
