package com.mockuai.itemcenter.client;


import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;

public interface ItemCategoryClient {

	/**
	 * 查询类目信息
	 * @param itemCategoryQTO
	 * @return
	 */
	public Response<List<ItemCategoryDTO>> queryItemCategory(ItemCategoryQTO itemCategoryQTO, String appKey);
	
	/**
	 * 查询叶子类目节点
	 * @return
	 */
	public Response<List<ItemCategoryDTO>> queryItemLeafCategory();

	/**
	 * 根据类目id获取类目信息
	 * @param categoryId
	 * @return
	 */
	public Response<ItemCategoryDTO> getItemCategory(Long categoryId, String appKey);

	/**
	 * 新增类目信息
	 * @param itemCategoryDTO
	 * @return
	 */
	public Response<ItemCategoryDTO> addItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey);

	/**
	 * 删除指定类目信息
	 * @param categoryId
	 * @param appKey
	 * @return
	 */
	public Response<Void> deleteItemCategory(Long categoryId, String appKey);

	/**
	 * 更新指定类目信息
	 * @param itemCategoryDTO
	 * @param appKey
	 * @return
	 */
	public Response<Void> updateItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey);
}
