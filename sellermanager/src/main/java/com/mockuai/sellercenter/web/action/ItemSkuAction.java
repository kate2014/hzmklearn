package com.mockuai.sellercenter.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.ItemParamDTO;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.PropParam;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.SkuPropertyItem;
import com.mockuai.sellercenter.web.manager.ItemManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;


@Controller
public class ItemSkuAction {
	
	@Resource
	private ItemManager itemManager;

	/**
	 * 根据item_id获取sku列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/item/sku/query.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryItemSku(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long itemId = null;
		try {
			itemId = RequestUtils.getLong(request, "item_id", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		//TODO 
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		ItemDTO itemDto ;
		try {
			itemDto = this.itemManager.getItem(supplierId, itemId);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		List<ItemSkuDTO> itemSkuList = itemDto.getItemSkuDTOList();
		List<SkuPropertyItem> returnList =new ArrayList<SkuPropertyItem>();
		List<SkuPropertyDTO> skuPropertyList = itemDto.getSkuPropertyList();
		Map<Long,List<PropParam>> skuPropertyMap =new HashMap<Long,List<PropParam>>();
		if(skuPropertyList != null){
			for(SkuPropertyDTO skuProperty : skuPropertyList){
				PropParam prop =new PropParam();
				Long skuId = skuProperty.getSkuId();
				prop.setPropId(String.valueOf(skuProperty.getSkuPropertyTmplId()));
				prop.setPropName(skuProperty.getName());
				prop.setValueId(String.valueOf(skuProperty.getPropertyValueId()));//值的id 红色的id
				prop.setValueName(skuProperty.getValue());//具体的值 比如 红色
				List<PropParam> list = skuPropertyMap.get(skuId);
				if(list ==null){
					List<PropParam> newList = new ArrayList<PropParam>();
					newList.add(prop);
					skuPropertyMap.put(skuId, newList);
				}else{
					list.add(prop);
				}
			}
		}
		if(itemSkuList != null){
			for(ItemSkuDTO sku : itemSkuList){
				SkuPropertyItem item = new SkuPropertyItem();
				item.setSkuId(sku.getId());//具体的skuId
				item.setBarcode(sku.getBarCode());
				item.setNum(String.valueOf(sku.getStockNum()));
				item.setOriginPrice(String.valueOf(sku.getMarketPrice()));
				item.setPrice(String.valueOf(sku.getPromotionPrice()));
				item.setImageUrl(sku.getImageUrl());
				item.setSkuUrl(itemManager.genSkuUrl(sku.getId(), sku.getSellerId()));
				item.setProp(skuPropertyMap.get(sku.getId()));//一个sku下面对应的属性比如  颜色 红  尺码 M 两个属性
				returnList.add(item);
			}
		}
		
		ItemParamDTO itemParamDTO =new ItemParamDTO();
		itemParamDTO.setSkus(returnList);
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(itemParamDTO);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(itemParamDTO) + ")"; 
		}
	}
	
	
	
}
