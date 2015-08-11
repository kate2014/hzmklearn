package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

public interface PropertyManager {
	
	/**
	 * 根据类目查找其对应的sku属性
	 * @param bizCode
	 * @param categoryId
	 * @return
	 * @throws ServiceException
	 */
	public List<SkuPropertyTmplDTO> querySkuPropertyTemplate(String bizCode,Long categoryId)throws ServiceException;
	
	/** 
	 * 根据bizCode 查找类目对应的基本属性
	 * @param bizCode
	 * @param categoryId
	 * @return
	 * @throws ServiceException
	 */
	public List<ItemPropertyTmplDTO> queryItemPropertyTemplate(String bizCode,Long categoryId)throws ServiceException;
	
}
