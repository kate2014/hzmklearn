package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.FeedbackQTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;

public interface UserMessageManager {

	/**
	 * 管理员新增消息 消息只能时管理员发给用户 而不能用户发给管理员 一次可以给多个用户发送消息
	 */
	public Boolean addUserMessage(List<UserMessageDTO> messageList,String appKey)throws ServiceException;

	/**
	 * 复合条件查询用户消息列表
	 * @param userMessageQTO
	 * @return
	 */
	public Response<List<UserMessageDTO>> queryUserMessage(UserMessageQTO userMessageQTO,String appKey)throws ServiceException;

	/**
	 *  符合条件查询用户反馈
	 * @param feedbackQTO
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<FeedbackDTO>> queryFeedback(FeedbackQTO feedbackQTO,String appKey)throws ServiceException;


	/**
	 * 发送全局消息
	 * @param messageList
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	public Boolean addGlobalMessage(List<UserMessageDTO> messageList,String appKey)throws ServiceException;


	/**
	 * 查询全局消息
	 * @param globalMessageQTO
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<UserMessageDTO>> queryGlobalMessage(UserMessageQTO globalMessageQTO,String appKey)throws ServiceException;

}
