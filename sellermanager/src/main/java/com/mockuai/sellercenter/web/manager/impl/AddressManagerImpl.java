package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.AddressManager;

public class AddressManagerImpl implements AddressManager {
	
	/**
	 * 底层服务平台
	 */
	//private FeeClient feeClient;

	public List<RegionDTO> getSubAddress(Integer addressId) throws ServiceException{
		//TODO 测试数据
//		RegionDTO region = new RegionDTO();
//		region.setId(1);
//		region.setCode("11");
//		region.setName("福建");
//		region.setRegionLevel(2);
//		List<RegionDTO> list=  new ArrayList<RegionDTO>();
//		list.add(region);
//		
//		RegionDTO region2 = new RegionDTO();
//		region2.setId(1);
//		region2.setCode("11");
//		region2.setName("福建");
//		region2.setRegionLevel(2);
//		list.add(region2);
//		
//		return list;
		
//		RegionQTO regionQto = new RegionQTO();
//		regionQto.setParentId(addressId);
//		Response<List<RegionDTO>> response = this.feeClient.queryRegion(regionQto);
//		if(response.getCode().equals("10000")){
//			return response.getModule();
//		}else{
//			int errorCode  = Integer.valueOf(response.getCode());			
//			throw new ServiceException(errorCode,response.getMessage());
//		}
		return null;
	}
	
}
