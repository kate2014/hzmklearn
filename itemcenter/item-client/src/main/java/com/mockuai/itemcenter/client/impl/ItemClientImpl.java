package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;

public class ItemClientImpl implements ItemClient {

	@Resource
	private ItemService itemService;

	public Response<ItemDTO> getItem(Long id, Long supplierId,Boolean needDetail) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("supplierId", supplierId);
		request.setParam("needDetail",needDetail);
		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		return itemService.execute(request);
	}
	
	public Response<ItemDTO> addItem(ItemDTO itemDTO){
		Request request = new BaseRequest();
		request.setParam("itemDTO", itemDTO);
		request.setCommand(ActionEnum.ADD_ITEM.getActionName());
		return itemService.execute(request);
	}
	
	public Response<Boolean> updateItem(ItemDTO itemDTO,Boolean updateDetail){
		Request request = new BaseRequest();
		request.setParam("itemDTO", itemDTO);
		request.setParam("updateDetail", updateDetail);
		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
		return itemService.execute(request);
	}
	
	public Response<Boolean> deleteItem(Long itemId,Long supplierId){
		Request request = new BaseRequest();
		request.setParam("ID", itemId);
		request.setParam("sellerId", supplierId);
		request.setCommand(ActionEnum.DELETE_ITEM.getActionName());
		return itemService.execute(request);
	}
	
	public Response<List<ItemDTO>> queryItem(ItemQTO itemQTO){
		Request request = new BaseRequest();
		request.setParam("itemQTO",itemQTO);
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
		return itemService.execute(request);
	}

	public Response<Integer> countGruopItem(ItemQTO itemQTO) {
		Request request = new BaseRequest();
		request.setParam("itemQTO",itemQTO);
		request.setCommand(ActionEnum.COUNT_GROUP_ITEM.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> withdrawItem(Long itemId,Long supplierId){
		Request request = new BaseRequest();
		request.setParam("itemId", itemId);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.WITHDRAW_ITEM.getActionName());
		return itemService.execute(request);
	}
	
	public Response<Boolean> upItem(Long itemId,Long supplierId){
		Request request = new BaseRequest();
		request.setParam("itemId", itemId);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.UP_ITEM.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> preSaleItem(Long itemId,Long supplierId){
		Request request = new BaseRequest();
		request.setParam("itemId", itemId);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.PRE_SALE_ITEM.getActionName());
		return itemService.execute(request);
	}


	public Response<Boolean> thawItem(Long itemId, Long supplierId) {
		Request request = new BaseRequest();
		request.setParam("itemId", itemId);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.THAW_ITEM_ACTION.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> freezeItem(Long itemId, Long supplierId) {
		Request request = new BaseRequest();
		request.setParam("itemId", itemId);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.FREEZE_ITEM_ACTION.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> addItemComment(List<ItemCommentDTO> itemCommentDTOs) {
		Request request = new BaseRequest();
		request.setParam("itemCommentList", itemCommentDTOs);
		request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> updateItemComment(ItemCommentDTO itemCommentDTO) {
		Request request = new BaseRequest();
		request.setParam("itemCommentDTO", itemCommentDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_COMMENT.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemCommentDTO>> queryItemComment(ItemCommentQTO itemCommentQTO) {
		Request request = new BaseRequest();
		request.setParam("itemCommentQTO", itemCommentQTO);
		request.setCommand(ActionEnum.QUERY_ITEMCOMMENT.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> deleteItemComment(Long commentId, Long sellerId) {
		Request request = new BaseRequest();
		request.setParam("ID", commentId);
		request.setParam("sellerId", sellerId);
		request.setCommand(ActionEnum.DELETE_ITEMCOMMENT.getActionName());
		return itemService.execute(request);
	}

	/**
	 * 商品评价等级
	 * @param itemCommentQTO
	 * @return
	 */
	public Response<List<ItemCommentDTO>> queryItemCommentGrade(ItemCommentQTO itemCommentQTO) {
		Request request = new BaseRequest();
		request.setParam("itemCommentQTO",itemCommentQTO);
		request.setCommand(ActionEnum.QUERY_ITEMCOMMENTGRADE.getActionName());
		return itemService.execute(request);
	}

	/**
	 * 分类统计商品评价等级
	 * @param itemCommentQTO
	 * @return
	 */
	public Response<List<Integer>> countItemCommentGrade(ItemCommentQTO itemCommentQTO) {
		Request request = new BaseRequest();
		request.setParam("itemCommentQTO",itemCommentQTO);
		request.setCommand(ActionEnum.COUNT_ITEMCOMMENTGRADE.getActionName());
		return itemService.execute(request);
	}


	public Response<List<ItemDTO>> queryGroupItem(ItemQTO itemQTO) {
		Request request = new BaseRequest();
		request.setParam("itemQTO", itemQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_GROUP_ACTION.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> addGroupItem(Long groupId, Long sellerId, Long itemId) {
		Request request = new BaseRequest();
		request.setParam("sellerId", sellerId);
		request.setParam("groupId", groupId);
		request.setParam("itemId", itemId);
		request.setCommand(ActionEnum.ADD_ITEM_GROUP_ACTION.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> removeGroupItem(Long sellerId, Long itemId) {
		Request request = new BaseRequest();
		request.setParam("sellerId", sellerId);
		request.setParam("itemId", itemId);
		request.setCommand(ActionEnum.REMOVE_ITEM_GROUP_ACTION.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> removeItemToDefaultGroup(Long sellerId, Long groupId) {
		Request request = new BaseRequest();
		request.setParam("sellerId", sellerId);
		request.setParam("groupId", groupId);
		request.setCommand(ActionEnum.REMOVE_ITEM_G_TO_DEFAULT_ROUP_ACTION.getActionName());
		return itemService.execute(request);
	}
}
