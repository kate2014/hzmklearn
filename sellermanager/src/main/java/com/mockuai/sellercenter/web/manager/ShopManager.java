package com.mockuai.sellercenter.web.manager;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * Created by luliang on 15/7/31.
 */
public interface ShopManager {

    public ShopDTO getShopInfo(ShopDTO shopDTO) throws ServiceException;

    public Boolean updateShopInfo(ShopDTO shopDTO) throws ServiceException;

    public ShopItemGroupDTO addShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ServiceException;

    public Boolean updateShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ServiceException;

    public ShopItemGroupDTO getShopItemGroup(Long groupId, Long sellerId) throws ServiceException;

    public Boolean deleteShopItemGroup(Long id, Long sellerId) throws ServiceException;

    public List<ShopItemGroupDTO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ServiceException;

}
