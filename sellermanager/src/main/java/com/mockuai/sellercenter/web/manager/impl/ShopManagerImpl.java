package com.mockuai.sellercenter.web.manager.impl;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ShopManager;
import com.mockuai.shopplatform.ShopClient;
import com.mockuai.shopplatform.api.Response;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import org.springframework.stereotype.Service;
import sun.beans.editors.BooleanEditor;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/31.
 */
@Service
public class ShopManagerImpl implements ShopManager {

    @Resource
    private ShopClient shopClient;

    @Resource
    private ItemClient itemClient;

    public ShopDTO getShopInfo(ShopDTO shopDTO) throws ServiceException{
        Response<ShopDTO> response = null;
        response = shopClient.getShop(shopDTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Boolean updateShopInfo(ShopDTO shopDTO) throws ServiceException{
        Response<Boolean> response = null;
        response = shopClient.updateShop(shopDTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return Boolean.TRUE;
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Boolean queryShopItemGroup(ShopDTO shopDTO) throws ServiceException{
        Response<ShopDTO> response = null;
        response = shopClient.getShop(shopDTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return Boolean.TRUE;
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public ShopItemGroupDTO addShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ServiceException{
        Response<ShopItemGroupDTO> response = null;
        response = shopClient.addShopItemGroup(shopItemGroupDTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Boolean updateShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ServiceException {
        Response<Boolean> response = null;
        response = shopClient.updateShopItemGroup(shopItemGroupDTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public ShopItemGroupDTO getShopItemGroup(Long groupId, Long sellerId) throws ServiceException {
        Response<ShopItemGroupDTO> response = null;
        response = shopClient.getShopItemGroup(sellerId, groupId);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Boolean deleteShopItemGroup(Long id, Long sellerId) throws ServiceException {
        Response<Boolean> response = null;
        // 先移动商品;
        com.mockuai.itemcenter.common.api.Response itemResponse = itemClient.removeItemToDefaultGroup(sellerId, id);
        if(itemResponse.getCode() != GlobalConstants.SERVICE_PROCESS_SUCCESS) {
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
        // 在删除分组;
        response = shopClient.deleteShopItemGroup(id, sellerId);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return Boolean.TRUE;
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public List<ShopItemGroupDTO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ServiceException {
        Response<List<ShopItemGroupDTO>> response = null;

        response = shopClient.queryShopItemGroup(shopItemGroupQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            shopItemGroupQTO.setTotalCount((int) response.getTotalCount());
            List<ShopItemGroupDTO> shopItemGroupDTOs = response.getModule();
            for(ShopItemGroupDTO shopItemGroupDTO: shopItemGroupDTOs) {
                ItemQTO itemQTO = new ItemQTO();
                itemQTO.setGroupId(shopItemGroupDTO.getId());
                itemQTO.setSellerId(shopItemGroupQTO.getSellerId());
                com.mockuai.itemcenter.common.api.Response<Integer> response1 = itemClient.countGruopItem(itemQTO);
                if(response1.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
                    shopItemGroupDTO.setCount(response1.getModule());
                } else {
                    int errorCode = Integer.valueOf(response1.getCode());
                    throw new ServiceException(errorCode,response1.getMessage());
                }
            }
            return shopItemGroupDTOs;
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }
}
