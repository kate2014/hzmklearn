package com.mockuai.shopplatform.core.manager.impl;

import com.mockuai.shopplatform.domain.dto.ShopImageDTO;
import com.mockuai.shopplatform.core.dao.ShopImageDAO;
import com.mockuai.shopplatform.core.domain.ShopImageDO;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.manager.ShopImageManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/27.
 */
@Service
public class ShopImageManagerImpl implements ShopImageManager {

    @Resource
    private ShopImageDAO shopImageDAO;

    @Override
    public Long addShopImage(ShopImageDTO shopImageDTO) throws ShopException {
        ShopImageDO shopImageDO = new ShopImageDO();
        BeanUtils.copyProperties(shopImageDTO, shopImageDO);
        return shopImageDAO.addShopImage(shopImageDO);
    }

    @Override
    public Integer updateShopImage(ShopImageDTO shopImageDTO) throws ShopException {
        ShopImageDO shopImageDO = new ShopImageDO();
        BeanUtils.copyProperties(shopImageDTO, shopImageDO);
        return shopImageDAO.updateShopImage(shopImageDO);
    }

    @Override
    public ShopImageDTO getShopImage(Long id, Long sellerId) throws ShopException {
        ShopImageDO shopImageDO = shopImageDAO.getShopImage(id, sellerId);
        ShopImageDTO shopImageDTO = new ShopImageDTO();
        BeanUtils.copyProperties(shopImageDO, shopImageDTO);
        return shopImageDTO;
    }
}
