package com.mockuai.shopplatform.core.manager;

import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.qto.ShopQTO;
import com.mockuai.shopplatform.core.exception.ShopException;

import java.util.List;

/**
 * Created by ziqi.
 */
public interface ShopManager {

    /**
     * 增加店铺;
     * @param shopDTO
     * @return
     */
    public ShopDTO addShop(ShopDTO shopDTO) throws ShopException;

    /**
     * 店铺唯一性判断;
     * @param shopDTO
     * @return
     */
    public boolean isExistsShop(ShopDTO shopDTO) throws ShopException;

    /**
     * 查询店铺
     * @param shopQTO
     * @return
     */
    public List<ShopDTO> queryShop(ShopQTO shopQTO) throws ShopException;

    /**
     * sellerId获取;
     * @param shopDTO
     * @return
     */
    public ShopDTO getShop(ShopDTO shopDTO) throws ShopException;

    /**
     * 更改店铺状态;
     * @param status
     * @return
     */
    public Boolean updateShopStatus(Long sellerId, Integer status) throws ShopException;

    /**
     * 更新店铺信息;
     * @param shopDTO
     * @return
     * @throws ShopException
     */
    public Boolean updateShop(ShopDTO shopDTO) throws ShopException;
}
