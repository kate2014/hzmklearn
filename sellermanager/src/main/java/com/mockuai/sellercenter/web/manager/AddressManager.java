package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
public interface AddressManager {
	
	/**
	 * 获取省市区县子级别地址  根据父级别的地址id查询自级别的地址
	 * @param addressId
	 * @param level
	 * @return
	 */
	public List<RegionDTO> getSubAddress(Integer addressId)throws ServiceException;
	
}
