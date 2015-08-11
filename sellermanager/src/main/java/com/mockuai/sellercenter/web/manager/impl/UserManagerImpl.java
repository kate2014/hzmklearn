package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.UserManager;
import com.mockuai.usercenter.client.SellerUserRelateClient;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.UserQTO;

public class UserManagerImpl implements UserManager {
	
	public UserClient getUserClient() {
		return userClient;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}
	
	

	public void setSellerUserRelateClient(SellerUserRelateClient sellerUserRelateClient) {
		this.sellerUserRelateClient = sellerUserRelateClient;
	}



	private UserClient userClient;
	
	private SellerUserRelateClient sellerUserRelateClient;
	
	public UserDTO login(String loginName,String password,String appKey) throws ServiceException {
		Response<UserDTO> response = null;
		response = this.userClient.userLogin(loginName,password,appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
	public Boolean updatePassword(Long userId, String oldPassword,
		String newPassword,String appKey) throws ServiceException {
		Response<Void> response =null;
		response = this.userClient.updatePwd(userId, newPassword,appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return true;
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

//	@Override
	public Response<List<SellerUserRelateDTO>> querySellerUserRelate(SellerUserQTO query, String appKey)
			throws ServiceException {
		Response<List<SellerUserRelateDTO>> response = sellerUserRelateClient.querySellerUserRelate(query, appKey);
		if(response.getCode() != GlobalConstants.SERVICE_PROCESS_SUCCESS){
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
		return response;
	}
	
	public List<UserDTO> query(UserQTO userQTO, String appKey) throws ServiceException {
		Response<List<UserDTO>> response =null;
		response = this.userClient.queryUser(userQTO, appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

}
