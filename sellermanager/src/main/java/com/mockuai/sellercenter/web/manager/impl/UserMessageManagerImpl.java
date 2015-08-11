package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.UserMessageManager;
import com.mockuai.usercenter.client.FeedbackClient;
import com.mockuai.usercenter.client.UserMessageClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.FeedbackQTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;

public class UserMessageManagerImpl implements UserMessageManager {

	public UserMessageClient getUserMessageClient() {
		return userMessageClient;
	}

	public void setUserMessageClient(UserMessageClient userMessageClient) {
		this.userMessageClient = userMessageClient;
	}

	public FeedbackClient getFeedbackClient() {
		return feedbackClient;
	}

	public void setFeedbackClient(FeedbackClient feedbackClient) {
		this.feedbackClient = feedbackClient;
	}

	private UserMessageClient userMessageClient;

	private FeedbackClient feedbackClient;

	public Boolean addUserMessage(List<UserMessageDTO> messageList,String appKey)throws ServiceException {
		Response<Boolean> response =null;
		response = this.userMessageClient.addUserMessage(messageList,appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
	
	

	public Response<List<UserMessageDTO>> queryUserMessage(
			UserMessageQTO userMessageQTO ,String appKey) throws ServiceException{
		Response<List<UserMessageDTO>> response =null;
		response = this.userMessageClient.queryUserMessage(userMessageQTO,appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response;
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Response<List<FeedbackDTO>> queryFeedback(FeedbackQTO feedbackQTO,String appKey)throws ServiceException{
		Response<List<FeedbackDTO>> response =null;
		response = this.feedbackClient.queryUserFeedback(feedbackQTO,appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response;
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Boolean addGlobalMessage(List<UserMessageDTO> messageList,String appKey)throws ServiceException {
		Response<Boolean> response =null;
		response = this.userMessageClient.addUserMessage(messageList,appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Response<List<UserMessageDTO>> queryGlobalMessage(UserMessageQTO globalMessageQTO,String appKey)throws ServiceException{
		Response<List<UserMessageDTO>> response =null;
		response = this.userMessageClient.queryUserMessage(globalMessageQTO, appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response;
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

}


