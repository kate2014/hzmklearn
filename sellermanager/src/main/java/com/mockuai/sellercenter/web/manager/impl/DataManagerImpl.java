package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.DataManager;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.DataDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;

public class DataManagerImpl implements DataManager{
	
	private OrderClient orderClient;
	
	public void setOrderClient(OrderClient orderClient) {
		this.orderClient = orderClient;
	}

	public Response<DataDTO> getData(DataQTO dataQTO,String appKey) throws ServiceException{
		return orderClient.queryData(dataQTO, appKey);
		//
		
		
		
		
	}

	public Response<List<SalesVolumeDTO>> queryTOP10Item(DataQTO dataQTO, String appKey)throws ServiceException {
		return orderClient.queryTOP10Item(dataQTO, appKey);
	}

}
