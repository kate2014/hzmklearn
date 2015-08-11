package com.mockuai.sellercenter.web.manager;

import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

import java.util.List;

/**
 * Created by wanghailong on 15-8-10.
 */
public interface PageViewManager {
    public List<PageViewDTO> queryItemTop(PageViewQTO pageViewQTO) throws ServiceException;

    public List<PageViewDTO> queryVisitorArea(PageViewQTO pageViewQTO) throws ServiceException;

    public List<PageViewDTO> queryDeviceType(PageViewQTO pageViewQTO) throws ServiceException;

    public Integer countShopPv(PageViewQTO pageViewQTO) throws ServiceException;

    public Integer countShopUv(PageViewQTO pageViewQTO) throws ServiceException;

    public List<PageViewDTO> queryShopPv(PageViewQTO pageViewQTO) throws ServiceException;

    public List<PageViewDTO> queryShopUv(PageViewQTO pageViewQTO) throws ServiceException;

    public List<PageViewDTO> queryShopHourUv(PageViewQTO pageViewQTO) throws ServiceException;

    public List<PageViewDTO> countNewVisitor(PageViewQTO pageVieQTO) throws ServiceException;
}
