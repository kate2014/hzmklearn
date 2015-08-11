package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.core.domain.UserOpenInfoDO;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
public interface UserOpenInfoDAO {

    /**
     * 新增第三方账号信息
     * @param userOpenInfoDO
     * @return
     */
    public long addUserOpenInfo(UserOpenInfoDO userOpenInfoDO);

    /**
     * 更新指定第三方账号所绑定的用户id
     * @param id
     * @param oldUserId
     * @param newUserId
     * @param bizCode
     * @return
     */
    public int updateUserId(Long id, Long oldUserId, Long newUserId, String bizCode);

    /**
     * 根据第三方账号的uid查询第三方账号信息
     * @param openType
     * @param openUid
     * @param bizCode
     * @return
     */
    public UserOpenInfoDO getUserOpenInfo(Integer openType, String openUid, String bizCode);

    /**
     * 查询指定用户所关联的指定类型的第三方账户信息
     * @param openType
     * @param userId
     * @param bizCode
     * @return
     */
    public UserOpenInfoDO getUserOpenInfoByUserId(Integer openType, Long userId, String bizCode);

    /**
     * 删除指定的第三方账号信息
     * @param id
     * @param userId
     * @return
     */
    public int deleteUserOpenInfo(Long id, Long userId);
}
