package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

public interface ItemPropertyManager {
	
	/**
	 * 根据类目id获取对应的基本属性的列表
	 */
	public List<ItemPropertyTmplDTO> queryItemPropertyTmpl(ItemPropertyTmplQTO qto,Boolean needPropertyValue)throws ServiceException;
	
	/**
	 * 根据类目id获取对应的销售属性的列表
	 * @param qto
	 * @return
	 * @throws ServiceException
	 */
	public List<SkuPropertyTmplDTO> querySkuPropertyTmpl(SkuPropertyTmplQTO qto,Boolean needPropertyValue)throws ServiceException;
	
	
}
