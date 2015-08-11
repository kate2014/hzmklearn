package com.mockuai.itemcenter.core.service.action.collection;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCollectionManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 查询商品收藏列表Action
 */
@Service
public class QueryItemCollectionAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemCollectionAction.class);
	@Resource
	private ItemCollectionManager itemCollectionManager;
	@Resource
	private ItemManager itemManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		ItemCollectionQTO itemCollectionQTO = (ItemCollectionQTO)request.getParam("itemCollectionQTO");

		if(itemCollectionQTO == null){
			return new ItemResponse(ResponseCode.PARAM_E_MISSING, "itemCollectionQTO is null");
		}


		try {
			//TODO 分页参数以及逻辑待重构
			itemCollectionQTO.setNeedPaging(true);
			List<ItemCollectionDTO> itemCollectionDTOList = itemCollectionManager.queryItemCollection(itemCollectionQTO);

			//将itemCollection根据sellerId分组
			Map<Long, List<Long>> itemListMap = new HashMap<Long, List<Long>>();
			for(ItemCollectionDTO itemCollectionDTO: itemCollectionDTOList){
				Long sellerId = itemCollectionDTO.getSellerId();
				if(itemListMap.containsKey(sellerId)){
					itemListMap.get(sellerId).add(itemCollectionDTO.getItemId());
				}else{
					List<Long> idList = new ArrayList<Long>();
					idList.add(itemCollectionDTO.getItemId());
					itemListMap.put(sellerId, idList);
				}
			}

			//TODO 以下代码待重构，需要考虑收藏商品被卖家删除的情况。另外，totalCount需要从QTO中拿出来
			List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
			for(Map.Entry<Long,List<Long>> entry: itemListMap.entrySet()){
				ItemQTO itemQTO = new ItemQTO();
				itemQTO.setSellerId(entry.getKey());
				itemQTO.setIdList(entry.getValue());
				List<ItemDTO> queryResult = itemManager.queryItem(itemQTO);
				itemDTOs.addAll(queryResult);
			}

			Map<Long,ItemDTO> itemDTOMap = new HashMap<Long, ItemDTO>();
			//TODO 兼容item.wireless_price价格不存在的情况，上线前改掉
			for(ItemDTO itemDTO: itemDTOs){
				if(itemDTO.getWirelessPrice() == null){
					itemDTO.setWirelessPrice(itemDTO.getPromotionPrice());
				}
				itemDTOMap.put(itemDTO.getId(), itemDTO);
			}

			//对商品收藏列表进行排序，以第一步查出来的itemCollection列表为准
			itemDTOs = new ArrayList<ItemDTO>();
			for(ItemCollectionDTO itemCollectionDTO: itemCollectionDTOList){
				if(itemDTOMap.containsKey(itemCollectionDTO.getItemId())){
					itemDTOs.add(itemDTOMap.get(itemCollectionDTO.getItemId()));
				}
			}
			response = ResponseUtil.getSuccessResponse(itemDTOs, itemCollectionQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_COLLECTION.getActionName();
	}
}
