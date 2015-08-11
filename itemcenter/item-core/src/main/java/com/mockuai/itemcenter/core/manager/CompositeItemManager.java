package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;

public interface CompositeItemManager {
	
	/**
	 * 新增组合商品
	 * @param compositeItemDTO
	 * @return
	 * @throws ItemException
	 */
	public Long addCompositeItem(CompositeItemDTO compositeItemDTO)throws ItemException;
	
	/**
	 * 根据item_id删除关联的组合商品
	 * @param itemId
	 * @param supplierId
	 * @return
	 */
	public int deleteCompositeItemByItemId(Long itemId,Long supplierId)throws ItemException;
	
	/**
	 * 根据itemId获取组合商品列表
	 * @param itemId
	 * @param supplierId
	 * @return
	 */
	public List<CompositeItemDTO> getCompositeItemByItemId(Long itemId,Long supplierId);
	
	
}
