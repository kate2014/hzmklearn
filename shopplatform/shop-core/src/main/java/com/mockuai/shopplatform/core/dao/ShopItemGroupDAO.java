package com.mockuai.shopplatform.core.dao;

import com.mockuai.shopplatform.core.domain.ShopItemGroupDO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/26.
 */
public interface ShopItemGroupDAO {

    /**
     * 添加店铺商品分组;
     * @param shopItemGroupDO
     * @return
     */
    Long addShopItemGroup(ShopItemGroupDO shopItemGroupDO) throws ShopException;

    ShopItemGroupDO getShopItemGroup(Long id, Long sellerId) throws ShopException;

    List<ShopItemGroupDO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ShopException;

    Integer updateShopItemGroup(ShopItemGroupDO shopItemGroupDO) throws ShopException;

    Integer deleteShopItemGroup(Long id, Long sellerId) throws ShopException;
}
