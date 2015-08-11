package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.DataDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;

public interface DataManager {
	/**
	 * 根据时间和sellerid得到数据
	 * @param orderQTO
	 * @param appKey
	 * @return
	 */
	public Response<DataDTO> getData(DataQTO dataQTO,String appKey)throws ServiceException;
	
	/**
	 * 得到销量前10的商品
	 * @param orderQTO
	 * @param appKey
	 * @return
	 */
	public Response<List<SalesVolumeDTO>> queryTOP10Item(DataQTO dataQTO,String appKey)throws ServiceException;

}
