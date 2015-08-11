package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;

public interface CommentManager {
	/**
	 * 添加评论或评论回复
	 * @param orderId
	 * @param userId
	 * @param itemId
	 * @param score
	 * @param titel
	 * @param content
	 * @return
	 */
	public Boolean addComment(long orderId,long userId,long itemId,long sellerId,int score,String title,String content,String appKey)throws ServiceException;
	
	public Response<List<ItemCommentDTO>> queryComment(long sellerId, Integer score,Integer currentPage,Integer pageSize,String appKey)throws ServiceException;
	
	/**
	 * 回复评论
	 * @param userId
	 * @param orderId
	 * @param sellerId
	 * @param itemId
	 * @param commentId
	 * @param replyUserId
	 * @param content
	 * @param appkey
	 * @return
	 */
	public Boolean replyComment(long userId, long orderId, long sellerId, long itemId, long commentId,long replyUserId,
			String content,String appkey)throws ServiceException ;

}
