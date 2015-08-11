package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import org.omg.PortableInterceptor.INACTIVE;

public interface ItemClient {

	/**
	 * 根据itemId和supplierId获取商品信息 ,区分是否需要详细信息
	 * @param id
	 * @param supplierId
	 * @param needDetail 表示了是否需要获取详细信息包括 销售属性 基本属性信息
	 * @return
	 */
	public Response<ItemDTO> getItem(Long id, Long supplierId,Boolean needDetail);

	/**
	 * 新加商品
	 * @param itemDTO
	 * @return
	 */
	public Response<ItemDTO> addItem(ItemDTO itemDTO);
	
	/**
	 * 更新商品信息
	 * @param itemDTO
	 * @param updateDetail 是否需要更新下面的属性 销售属性 图片等
	 * @return
	 */
	public Response<Boolean> updateItem(ItemDTO itemDTO,Boolean updateDetail);
	

	/**
	 * 删除商品
	 * @param itemId
	 * @param supplierId
	 * @return
	 */
	public Response<Boolean> deleteItem(Long itemId,Long supplierId);
	
	/**
	 * 根据复合条件查询商品列表
	 * @param itemQTO
	 * @return
	 */
	public Response<List<ItemDTO>> queryItem(ItemQTO itemQTO);

	/**
	 * 根据复合条件查询商品列表
	 * @param itemQTO
	 * @return
	 */
	public Response<Integer> countGruopItem(ItemQTO itemQTO);
	
	/**
	 * 下架商品
	 * @return
	 */
	public Response<Boolean> withdrawItem(Long itemId,Long supplierId);
	
	/**
	 * @param itemId
	 * @param supplierId
	 * @return
	 */
	public Response<Boolean> upItem(Long itemId,Long supplierId);

	/**
	 * @param itemId
	 * @param supplierId
	 * @return
	 */
	public Response<Boolean> preSaleItem(Long itemId,Long supplierId);

	/**
	 * 解冻商品
	 * @return
	 */
	public Response<Boolean> thawItem(Long itemId,Long supplierId);

	/**
	 * 冻结商品
	 * @param itemId
	 * @param supplierId
	 * @return
	 */
	public Response<Boolean> freezeItem(Long itemId,Long supplierId);

	/**
	 * 添加商品评论
	 * @param itemCommentDTOs
	 * @return
	 */
	public Response<Boolean> addItemComment(List<ItemCommentDTO> itemCommentDTOs);

	/**
	 * 更新商品评论
	 * @param itemCommentDTO
	 * @return
	 */
	public Response<Boolean> updateItemComment(ItemCommentDTO itemCommentDTO);

	/**
	 * 查询交易评价;
	 * @param itemCommentQTO
	 * @return
	 */
	public Response<List<ItemCommentDTO>> queryItemComment(ItemCommentQTO itemCommentQTO);

	/**
	 * 删除商品评论
	 * @return
	 */
	public Response<Boolean> deleteItemComment(Long commentId, Long sellerId);

	/**
	 * 查询分组内ID;
	 * @param itemQTO
	 * @return
	 */
	public Response<List<ItemDTO>> queryGroupItem(ItemQTO itemQTO);


	/**
	 * 查询商品评价等级
	 * @param itemCommentQTO
	 * @return
	 */
	public Response<List<ItemCommentDTO>> queryItemCommentGrade(ItemCommentQTO itemCommentQTO);

	/**
	 * 分类统计商品评价等级
	 * @param itemCommentQTO
	 * @return
	 */
	public Response<List<Integer>> countItemCommentGrade(ItemCommentQTO itemCommentQTO);

	/**
	 * 查询分组内ID;
	 * @param groupId
	 * @param sellerId
	 * @return
	 */
	public Response<Boolean> addGroupItem(Long groupId, Long sellerId, Long itemId);

	/**
	 * 查询分组内ID;
	 * @param itemId
	 * @param sellerId
	 * @return
	 */
	public Response<Boolean> removeGroupItem(Long sellerId, Long itemId);

	public Response<Boolean> removeItemToDefaultGroup(Long sellerId, Long groupId);

}
