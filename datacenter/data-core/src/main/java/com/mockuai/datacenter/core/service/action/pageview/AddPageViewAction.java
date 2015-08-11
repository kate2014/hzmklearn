package com.mockuai.datacenter.core.service.action.pageview;

import com.mockuai.datacenter.common.api.DataResponse;
import com.mockuai.datacenter.common.constant.ActionEnum;
import com.mockuai.datacenter.common.domain.DataQueue;
import com.mockuai.datacenter.common.domain.dto.DataDTO;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.manager.PageViewManager;
import com.mockuai.datacenter.core.service.DataRequest;
import com.mockuai.datacenter.core.service.RequestContext;
import com.mockuai.datacenter.core.service.action.TransAction;
import com.mockuai.datacenter.core.util.ResponseUtil;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddPageViewAction extends TransAction
{
  private static final Logger log = LoggerFactory.getLogger(AddPageViewAction.class);

  @Resource
  PageViewManager pageViewManager;

  protected DataResponse<String> doTransaction(RequestContext context) throws DataException
  {
	 //把数据放入队列，队列满了就抛弃
    DataRequest dataRequest = context.getRequest();
    DataDTO dataDTO = (DataDTO)dataRequest.getParam("dataDTO");
    if(!DataQueue.put(dataDTO))
    	return ResponseUtil.getSuccessResponse("Queue is full");
    
    return ResponseUtil.getSuccessResponse(null);
  }

@Override
public String getName() {
	 return ActionEnum.ADD_PAGEVIEW.getActionName();
}
}