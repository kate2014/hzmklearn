package com.mockuai.shopplatform;

import com.mockuai.shopplatform.api.Response;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import com.mockuai.shopplatform.domain.qto.ShopQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/27.
 */
public interface ShopClient {

    public Response<ShopDTO> addShop(ShopDTO shopDTO);

    public Response<List<ShopDTO>> queryShop(ShopQTO shopQTO);

    public Response<ShopDTO> getShop(ShopDTO shopDTO);

    public Response<Boolean> updateShop(ShopDTO shopDTO);

    public Response<ShopItemGroupDTO> addShopItemGroup(ShopItemGroupDTO  shopItemGroupDTO);

    public Response<Boolean> updateShopItemGroup(ShopItemGroupDTO  shopItemGroupDTO);

    public Response<Boolean> deleteShopItemGroup(Long id, Long sellerId);

    public Response<List<ShopItemGroupDTO>> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO);

    public Response<ShopItemGroupDTO> getShopItemGroup(Long sellerId, Long groupId);

    /**
     * 查询店铺的状态;
     * @param sellerId
     * @return
     */
    public Response<Integer> getShopStatus(Long sellerId);

    /**
     * 冻结店铺;
     * @param sellerId
     * @return
     */
    public Response<Boolean> freezeShop(Long sellerId);

    /**
     * 解冻店铺;
     * @param sellerId
     * @return
     */
    public Response<Boolean> thawShop(Long sellerId);

}
