package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

public interface ItemSkuManager {
	
//	public List<ItemSkuDTO> querySkuItem(Long itemId,Long supplierId) throws ServiceException;
//	
//	public List<SkuPropertyDTO> querySkuProperty(SkuPropertyQTO skuPropertyQTO)throws ServiceException;
	
	public List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO)throws ServiceException;
	
}
