package com.mockuai.shopplatform.core.manager;

import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/27.
 */
public interface ShopItemGroupManager {

    public Long addShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ShopException;

    public Boolean updateShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ShopException;

    public Boolean deleteShopItemGroup(Long id, Long sellerId) throws ShopException;

    public List<ShopItemGroupDTO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ShopException;

    public ShopItemGroupDTO getShopItemGroup(Long id, Long sellerId) throws ShopException;
}
