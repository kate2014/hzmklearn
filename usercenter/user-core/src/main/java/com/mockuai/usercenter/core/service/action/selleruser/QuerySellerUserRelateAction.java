package com.mockuai.usercenter.core.service.action.selleruser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.SellerUserManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.manager.UserOrderRelateManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

/**
 * 客户管理查询服务
 * @author hzmk
 *
 */
@Service
public class QuerySellerUserRelateAction implements Action {

	@Resource
	private SellerUserManager sellerUserManager;
	
	@Resource
	private UserManager userManager;

	@Resource
	private UserOrderRelateManager userOrderRelateManagerImpl;
	
    @Resource
    private TransactionTemplate transactionTemplate;
    
    private static final Long DEFAULT_START = 0L;
	private static final int DEFAULT_PAGE_SIZE = 20;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		SellerUserQTO query = (SellerUserQTO) userRequest.getParam("sellerUserQTO");
			
		//TODO 入参校验
		if(query == null){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "sellerUserQTO is null");
		}
		
		if(null==query.getCount()){
			query.setCount(DEFAULT_PAGE_SIZE);
		}
		
		if(null == query.getOffset()){
			query.setOffset(DEFAULT_START);
		}
		List<SellerUserRelateDTO> result = null;
		int totalCount = 0;
		try{
			
			result = this.sellerUserManager.querySellerUserRelate(query);
			// 相同条件的总数
			totalCount = this.sellerUserManager.getQuerySellerUserRelateCount(query);
			
		}catch(UserException e){
			
		}
		
		return new UserResponse(result, Long.valueOf(totalCount));
		
			
				
			
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SELLER_USER_RELATE.getActionName();
	}

}
