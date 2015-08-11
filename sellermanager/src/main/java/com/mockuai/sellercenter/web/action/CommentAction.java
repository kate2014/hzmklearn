package com.mockuai.sellercenter.web.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.URL;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.manager.CommentManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
/**
 * 
 * @author hzmk
 *
 */
@Controller
public class CommentAction {
	protected String appKey = "3bc25302234640259fadea047cb7c7d3";
	@Resource
	private CommentManager commentManager;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/comment/reply.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String commentReply(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		Long orderId , userId , itemId ;
		Long commentId ;
		String content = null;

		try {

			orderId = RequestUtils.getLong(request, "order_id", true);
			userId = RequestUtils.getLong(request, "user_id", true);
			itemId = RequestUtils.getLong(request, "item_id", true);
			commentId = RequestUtils.getLong(request, "comment_id", true);

			content = RequestUtils.getString(request, "content", true);
			content = URL.decode(content);
			Long sellerId = SessionManager.getLoginUserId(request.getSession());
			Boolean result = commentManager.replyComment(userId, orderId, sellerId, itemId, commentId, sellerId, content, appKey);

			if (StringUtils.isBlank(callback)) {
				return ResponseUtils.getSuccessApiResponseStr(result);
			} else {
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
			}

		} catch (NumberFormatException e) {
			logger.error("commentAction.commentReply error", e);
			ApiResponse apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.S_E_SERVICE_ERROR.getCode(),
					e.getMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		} catch (ParamException e) {
			logger.error("commentAction.commentReply error", e);
			return ResponseUtils.getFailApiResponseStr(e);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
	}

	@RequestMapping(value = "/comment/query.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryComment(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		long sellerId = SessionManager.getLoginUserId(request.getSession());

		Response<List<ItemCommentDTO>> serviceResponse = null;

		try {
			
			Integer currentPage,pageSize = null;
			
			currentPage = RequestUtils.getInt(request, "current_page",false);
			pageSize = RequestUtils.getInt(request, "page_size",false);
			
			if(null==currentPage){
				currentPage = 1;
			}
			
			if(null==pageSize){
				pageSize = 20;
			}
			
			
			Integer offSet = ((currentPage-1)*pageSize);
			Integer score = RequestUtils.getInt(request, "score", false);
			serviceResponse = this.commentManager.queryComment(sellerId,score,offSet, pageSize,appKey);
			
			
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}catch (ParamException e) {
			logger.error("commentAction.commentReply error", e);
			return ResponseUtils.getFailApiResponseStr(e);
		} 

		PageDTO<List<ItemCommentDTO>> pageInfo = new PageDTO<List<ItemCommentDTO>>();
		pageInfo.setData(serviceResponse.getModule());
		pageInfo.setTotalCount(serviceResponse.getTotalCount());
		if (StringUtils.isBlank(callback)) {
			return ResponseUtils.getSuccessApiResponseStr(pageInfo);
		} else {
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageInfo) + ")";
		}
	}
}
