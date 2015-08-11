package com.mockuai.datacenter.core.manager;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.datacenter.core.exception.DataException;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public interface AppManager {
    /**
     * 根据bizCode获取业务信息
     * @param bizCode
     * @return
     * @throws DataException
     */
    public BizInfoDTO getBizInfo(String bizCode) throws DataException;

    /**
     * 根据appKey获取应用信息
     * @param appKey
     * @return
     * @throws DataException
     */
    public AppInfoDTO getAppInfo(String appKey) throws DataException;
}
