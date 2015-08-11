package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.core.domain.CompositeItemDO;

public interface CompositeItemDAO {
	
	public Long addCompositeItem(CompositeItemDO compositeItemDO);
	
	public int deleteCompositeItemByItemId(Long id,Long supplierId);
	
	public List<CompositeItemDO> getCompositeItemByItemId(Long id,Long supplierId);
	
}	
