package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.CommentManager;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;

public class CommentManagerImpl implements CommentManager{
	
	OrderClient orderClient ;
	
	public void setOrderClient(OrderClient orderClient) {
		this.orderClient = orderClient;
	}
	
	public Boolean addComment(long orderId, long userId, long itemId, long sellerId,int score, String titel, String content,String appKey) throws ServiceException{
		Response<Boolean> response = (Response<Boolean>)this. orderClient.addOrderComment(orderId, userId, itemId, sellerId,score, titel, content, appKey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Response<List<ItemCommentDTO>> queryComment(long sellerId, Integer score,Integer offSet,Integer pageSize,String appKey)throws ServiceException {
		Response<List<ItemCommentDTO>> response = orderClient.queryComment(sellerId,score, offSet,pageSize,appKey);
		if(response.getCode() != GlobalConstants.SERVICE_PROCESS_SUCCESS){
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
		return response;
	}

	@Override
	public Boolean replyComment(long userId, long orderId, long sellerId, long itemId, long commentId, long replyUserId,
			String content, String appkey)throws ServiceException  {
		Response<Boolean> response = (Response<Boolean>)this. orderClient.replyComment(userId, orderId, sellerId, itemId, commentId, replyUserId, content, appkey);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
}
