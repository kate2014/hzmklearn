package com.mockuai.sellercenter.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.domain.UserDetail;
import com.mockuai.sellercenter.web.util.ResponseUtils;

@Controller
public class LogoutAction extends BaseValidator{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/logout.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	protected String handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String callback = request.getParameter("callback");
		HttpSession session = request.getSession();
		UserDetail user = (UserDetail)session.getAttribute(GlobalConstants.USER_SESSION_KEY);
		session.invalidate();
		ApiResponse<Boolean> apiResponse = ResponseUtils.getSuccessApiResponse(true);
		return StringUtils.isBlank(callback) ? ResponseUtils.toJsonStr(apiResponse):
			callback + "(" + ResponseUtils.toJsonStr(apiResponse) + ")"	;
	}
}
