package com.mockuai.shopplatform.core.manager;

import com.mockuai.shopplatform.domain.dto.ShopImageDTO;
import com.mockuai.shopplatform.core.exception.ShopException;

/**
 * Created by luliang on 15/7/27.
 */
public interface ShopImageManager {
    /**
     * 增加店铺;
     * @param shopImageDTO
     * @return
     */
    public Long addShopImage(ShopImageDTO shopImageDTO) throws ShopException;

    /**
     * 更新店铺;
     * @param shopImageDTO
     * @return
     */
    public Integer updateShopImage(ShopImageDTO shopImageDTO) throws ShopException;

    /**
     * sellerId获取;
     * @param sellerId
     * @return
     */
    public ShopImageDTO getShopImage(Long id, Long sellerId) throws ShopException;
}
