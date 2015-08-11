package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

public interface ItemCategoryManager {
	/**
	 * 根据父类目查询子类目
	 * @param parentId
	 * @return
	 * @throws ServiceException
	 */
	public List<ItemCategoryDTO> querySubCategory(Long parentId)throws ServiceException;
	
	
	/**
	 * 根据类目id查询类目 
	 * @param categoryId
	 * @return
	 * @throws ServiceException
	 */
	public ItemCategoryDTO getItemCategory(Long categoryId)throws ServiceException;
	
	/**
	 * 根据bizCode查询所有的类目
	 * @param bizCode
	 * @return
	 * @throws ServiceException
	 */
	public List<ItemCategoryDTO> queryCategory(ItemCategoryQTO itemCategoryQTO) throws ServiceException;

	/**
	 * 根据bizCode查询所有的类目
	 * @return
	 * @throws ServiceException
	 */
	public List<ItemCategoryDTO> queryLeafCategory() throws ServiceException;

	
}
