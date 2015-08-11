package com.mockuai.sellercenter.web.manager.impl;

import com.mockuai.itemcenter.client.ItemDetailTemplateClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemDetailTemplateManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/23.
 */
@Service
public class ItemDetailTemplateManagerImpl implements ItemDetailTemplateManager {

    @Resource
    private ItemDetailTemplateClient itemDetailTemplateClient;

    public Long addItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ServiceException {
        Response<Long> response = null;
        response = this.itemDetailTemplateClient.addItemDetailTemplate(itemDetailTemplateDTO);

        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public List<ItemDetailTemplateDTO> queryItemDetailTemplate(ItemDetailTemplateQTO itemDetailTemplateQTO) throws ServiceException {
        Response<List<ItemDetailTemplateDTO>> response = null;
        response = this.itemDetailTemplateClient.queryItemDetailTemplate(itemDetailTemplateQTO);

        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public ItemDetailTemplateDTO getItemDetailTemplate(Long id, Long sellerId) throws ServiceException {
        Response<ItemDetailTemplateDTO> response = null;
        response = this.itemDetailTemplateClient.getItemDetailTemplate(id, sellerId);

        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Integer updateItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ServiceException {
        Response<Integer> response = null;
        response = this.itemDetailTemplateClient.updateItemDetailTemplate(itemDetailTemplateDTO);

        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Integer deleteItemDetailTemplate(Long id, Long sellerId) throws ServiceException {
        Response<Integer> response = null;
        response = this.itemDetailTemplateClient.deleteItemDetailTemplate(id, sellerId);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }
}
