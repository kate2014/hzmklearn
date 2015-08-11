package com.mockuai.sellercenter.web.manager.impl;

import com.mockuai.datacenter.common.api.Response;
import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.PageViewManager;
import com.mockuai.datacenter.client.DataClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wanghailong on 15-8-10.
 */
@Service
public class PageViewManagerImpl implements PageViewManager {

    @Resource
    private DataClient dataClient;

    public List<PageViewDTO> queryItemTop(PageViewQTO pageViewQTO) throws ServiceException {
        Response<List<PageViewDTO>> response = null;
        response = dataClient.queryItemTop(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            List<PageViewDTO> pageViewDTOList = response.getModule();
            return pageViewDTOList;

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }

    }

    public List<PageViewDTO> queryVisitorArea(PageViewQTO pageViewQTO) throws ServiceException {
        Response<List<PageViewDTO>> response = null;
        response = dataClient.queryVisitorArea(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            List<PageViewDTO> pageViewDTOList = response.getModule();
            return pageViewDTOList;

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public List<PageViewDTO> queryDeviceType(PageViewQTO pageViewQTO) throws ServiceException {
        Response<List<PageViewDTO>> response = null;
        response = dataClient.queryDeviceType(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            List<PageViewDTO> pageViewDTOList = response.getModule();
            return pageViewDTOList;

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Integer countShopPv(PageViewQTO pageViewQTO) throws ServiceException {
        Response<Integer> response = null;
        response = dataClient.countShopPv(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Integer countShopUv(PageViewQTO pageViewQTO) throws ServiceException {
        Response<Integer> response = null;
        response = dataClient.countShopUv(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public List<PageViewDTO> queryShopPv(PageViewQTO pageViewQTO) throws ServiceException {
        Response<List<PageViewDTO>> response = null;
        response = dataClient.queryShopPv(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            List<PageViewDTO> pageViewDTOList = response.getModule();
            return pageViewDTOList;

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public List<PageViewDTO> queryShopUv(PageViewQTO pageViewQTO) throws ServiceException {
        Response<List<PageViewDTO>> response = null;
        response = dataClient.queryShopUv(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            List<PageViewDTO> pageViewDTOList = response.getModule();
            return pageViewDTOList;

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public List<PageViewDTO> queryShopHourUv(PageViewQTO pageViewQTO) throws ServiceException {
        Response<List<PageViewDTO>> response = null;
        response = dataClient.queryShopHourUv(pageViewQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            List<PageViewDTO> pageViewDTOList = response.getModule();
            return pageViewDTOList;

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public List<PageViewDTO> countNewVisitor(PageViewQTO pageVieQTO) throws ServiceException {
        Response<List<PageViewDTO>> response = null;
        response = dataClient.countNewVisitor(pageVieQTO);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            List<PageViewDTO> pageViewDTOList = response.getModule();
            return pageViewDTOList;

        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }
}
