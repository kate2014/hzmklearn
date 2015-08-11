package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.itemcenter.client.CornerIconClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.CornerIconManager;

public class CornerIconManagerImpl implements CornerIconManager {

	public CornerIconClient getCornerIconClient() {
		return cornerIconClient;
	}

	public void setCornerIconClient(CornerIconClient cornerIconClient) {
		this.cornerIconClient = cornerIconClient;
	}

	private CornerIconClient cornerIconClient;
	
	public CornerIconDTO addCornerIcon(CornerIconDTO cornerIconDTO) throws ServiceException{
			Response<CornerIconDTO> response=null;
			response = this.cornerIconClient.addCornerIcon(cornerIconDTO);
			if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
				return response.getModule();
			}else{
				throw new ServiceException(response.getCode(),response.getMessage());
			}
	}

	public Boolean deleteCornerIcon(Long id, Long sellerId)
			throws ServiceException {
		Response<Boolean> response=null;
		response = this.cornerIconClient.deleteCornerIcon(id, sellerId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			throw new ServiceException(response.getCode(),response.getMessage());
		}
	}

	public List<CornerIconDTO> queryCornerIcon(CornerIconQTO cornerIconQTO)
			throws ServiceException {
		Response<List<CornerIconDTO>> response=null;
		response = this.cornerIconClient.queryCornerIcon(cornerIconQTO);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			throw new ServiceException(response.getCode(),response.getMessage());
		}
	}
	
	public CornerIconDTO getCornerIcon(Long id)throws ServiceException{
		Response<CornerIconDTO> response=null;
		response = this.cornerIconClient.getCornerIcon(id);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			throw new ServiceException(response.getCode(),response.getMessage());
		}
	}
}