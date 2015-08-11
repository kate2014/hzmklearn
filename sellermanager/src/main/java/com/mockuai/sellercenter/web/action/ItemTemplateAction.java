package com.mockuai.sellercenter.web.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplatePropertyDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ItemConstants;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.ItemParamDTO;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.ItemProperty;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.manager.CornerIconManager;
import com.mockuai.sellercenter.web.manager.ItemBrandManager;
import com.mockuai.sellercenter.web.manager.ItemCategoryManager;
import com.mockuai.sellercenter.web.manager.ItemTemplateManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;

/**
 * 商品模版处理类
 * @author cwr
 *
 */
@Controller
public class ItemTemplateAction extends BaseValidator {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 删除时非空字段
	private final String[] NOT_NULL_FIELDS_4_DELETE = {
		"template_id"
	};
	
	//新增商品模版时的非空字段
	private final String[] NOT_NULL_FIELDS_4_ADD = {
		"template_name","category_id"
	};
	
	//获取模版非空字段 
	private final String[] NOT_NULL_FIELDS_4_GET = {
			"template_id"
	};
	
	//修改非空字段
	private final String[] NOT_NULL_FIELDS_4_UPDATE = {
		
	};
	
	//前端传递的时间格式
	private String[] ACCEPT_DATE_FORMAT = {"yyyy-MM-dd"};
	
	@Resource
	private ItemTemplateManager itemTemplateManager;
	
	@Resource
	private ItemBrandManager itemBrandManager;
	
	@Resource
	private ItemCategoryManager itemCategoryManager;
	
	@Resource
	private CornerIconManager cornerIconManager;
	
	@RequestMapping(value="/item/template/delete.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String deleteTemplate(HttpServletRequest request){
		String callback = request.getParameter("callback");
		if(!StringUtils.isEmpty(this.notNullValidate(NOT_NULL_FIELDS_4_DELETE, request))){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_ISNULL);
		}
		Long templateId = null;
		try {
			templateId = RequestUtils.getLong(request, "template_id",true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		Boolean result = null;
		
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		//String bizCode = SessionManager.getLoginUserBizCode(request.getSession());
		
		try{
			result =this.itemTemplateManager.deleteItemTemplate(templateId, userId);
		}catch(ServiceException e){
			logger.error("deleteTemplate, service error : " + e.getCode() + "  " + e.getMessage());
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	
	@RequestMapping(value="/item/template/add.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String addItemTemplate(HttpServletRequest request){
		long start = System.currentTimeMillis() ;
		String callback = request.getParameter("callback");
		
		Long templateId = null;
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		
		String templateName;//模版名称
		String name;
		String subName;
		Long categoryId;
		String description;
		Long brandId;
		Integer minBought;
		Integer maxBought;
		Long cornerIcon;
		Integer deliveryType;//发货方式
		ItemTemplateDTO itemDto = new ItemTemplateDTO();
		
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		//String bizCode = SessionManager.getLoginUserBizCode(request.getSession());
		itemDto.setSellerId(userId);
		//itemDto.setBizCode(bizCode);
		// 参数处理
		try {
			templateName = RequestUtils.getString(request, "template_name",true);
			name = RequestUtils.getString(request, "name",false);
			subName = RequestUtils.getString(request, "sub_name",false);
			categoryId = RequestUtils.getLong(request, "cate_id",true);
			String brief = RequestUtils.getString(request, "brief",false);
			description = RequestUtils.getString(request, "description",false);
			brandId = RequestUtils.getLong(request, "brand_id",false);
			minBought = RequestUtils.getInt(request, "user_min_bought",false);
			maxBought = RequestUtils.getInt(request, "user_max_bought",false);
			cornerIcon = RequestUtils.getLong(request, "corner_icon",false);
			deliveryType = RequestUtils.getInt(request, "delivery_type",false);
			
			//TODO 商品类型 和商品的状态的维护
			String itemTypeStr = RequestUtils.getString(request, "item_type",false);
			Integer itemType = StringUtils.isBlank(itemTypeStr)? 1: 0;//默认是实体商品1： 实体  0： 虚拟
			String itemStatusStr = RequestUtils.getString(request, "item_status",false);
			Integer itemStatus = StringUtils.isBlank(itemStatusStr)? 0: Integer.valueOf(itemStatusStr);
			
			// 公共部分属性
			itemDto.setTemplateName(templateName);
			itemDto.setItemName(name);
			//itemDto.setItemBriefName(subName);
			itemDto.setCategoryId(categoryId);
			itemDto.setItemBrandId(brandId);
			itemDto.setItemDesc(description);//商品图文详情
			itemDto.setSaleMinNum(minBought);
			itemDto.setSaleMaxNum(maxBought);
			//itemDto.setSaleBegin(now); // 默认开始就上架了的就是开始时间
			itemDto.setItemType(itemType);
			itemDto.setItemStatus(itemStatus);
			itemDto.setCornerIconId(cornerIcon);
			itemDto.setDeliveryType(deliveryType == null ? ItemConstants.DeliveryType.NOT_LIMIT.getCode() : deliveryType);
			
			String propsStr = RequestUtils.getString(request, "props",false);
			itemProps = this.parseItemProperty(propsStr);
			
		} catch (Exception e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		
		List<ItemTemplatePropertyDTO> itemPropertyList = new ArrayList<ItemTemplatePropertyDTO>();
		// 普通属性处理
		int sort = 0;// 用于控制显示顺序  商品显示普通属性时候需要按照特定的顺序来显示
		if(!CollectionUtils.isEmpty(itemProps)){
			for (int i = 0, len = itemProps.size(); i < len; i++) {
				sort++;
				ItemProperty itemProp = itemProps.get(i);
				// 一个属性可能有多个值 比如： 适用年龄 属性 可能有多个值：
				List<String> valueList = itemProp.getValue();
				List<String> vidList = itemProp.getVid();
				if(!CollectionUtils.isEmpty(valueList)){
					for(int index=0,size=valueList.size();index<size;index++){
						ItemTemplatePropertyDTO itemPropertyDto = new ItemTemplatePropertyDTO();
						itemPropertyDto.setItemPropertyTmplId(Long.valueOf(itemProp.getId()));//属性的id
						itemPropertyDto.setName(itemProp.getName());//属性名称
						itemPropertyDto.setPropertyValueId(Long.valueOf(vidList.get(index)));//值的id
						itemPropertyDto.setValue(valueList.get(index));//具体的值 比如 0-6个月
						itemPropertyDto.setSellerId(userId);
						itemPropertyDto.setSort(sort);
						itemPropertyList.add(itemPropertyDto);
					}
				}
			}
		}
		itemDto.setItemPropertyList(itemPropertyList);//普通属性的列表
		ItemTemplateDTO result;
		try {
			result = this.itemTemplateManager.addItemTemplate(itemDto);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/item/template/get.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String getItemTemplate(HttpServletRequest request){
		
		String callback = request.getParameter("callback");

		Long userId = SessionManager.getLoginUserId(request.getSession());
		Long templateId = null;
		try {
			templateId = RequestUtils.getLong(request, "template_id",true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}

		ItemTemplateDTO itemDto = null;
		try {
			itemDto = this.itemTemplateManager.getItemTemplate(templateId, userId);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
	
		// 公共属性部分
		ItemParamDTO itemParamDto = new ItemParamDTO();
		itemParamDto.setTemplateName(itemDto.getTemplateName());//模板名称
		itemParamDto.setId(String.valueOf(itemDto.getId()));//id
		itemParamDto.setCateId(String.valueOf(itemDto.getCategoryId()));//类目id
		itemParamDto.setName(itemDto.getItemName());//商品名称
		//itemParamDto.setSubName(itemDto.getItemBriefName());//商品简称
		itemParamDto.setBrandId(String.valueOf(itemDto.getItemBrandId())); // 品牌id
		itemParamDto.setUserMinBought(String.valueOf(itemDto.getSaleMinNum()));//最小购买量
		itemParamDto.setUserMaxBought(String.valueOf(itemDto.getSaleMaxNum())); //最大购买量限制
		itemParamDto.setDescription(itemDto.getItemDesc());
		itemParamDto.setDeliveryType(itemDto.getDeliveryType()==null ? String.valueOf(ItemConstants.DeliveryType.NOT_LIMIT.getCode()) : String.valueOf(itemDto.getDeliveryType()));
		
		// 如果该商品有角标获取url
		if (itemDto.getCornerIconId() != null) {
			try {
				CornerIconDTO cornerIcon = this.cornerIconManager
						.getCornerIcon(itemDto.getCornerIconId());
				itemParamDto.setCornerIcon(cornerIcon);// 角标
			} catch (ServiceException e) {
				return ServiceResponseHandler.serviceExceptionHandler(e);
			}
		}
		
		/*
		List<SkuPropertyDTO> skuPropertys = itemDto.getSkuPropertyList();
		
		//图片部分
		//List<ItemImageDTO> gallery = itemDto.getItemImageDTOList();
		ItemImageQTO itemImageQto = new ItemImageQTO();
		itemImageQto.setSupplierId(userId);
		List<ItemImageDTO> itemImages = itemDto.getItemImageDTOList();
		for(ItemImageDTO itemImage : itemImages){
			GalleryParam gallery = new GalleryParam();
			// 对应于具体的一个规格的图片
			gallery.setColor(itemImage.getImageName());
			gallery.setImg(itemImage.getImgUrl());
			gallery.setId(String.valueOf(itemImage.getVid()));// 具体的值的id，比如： 蓝色对应于一个id
		}
		*/
		
		//普通属性的处理
		List<ItemTemplatePropertyDTO> itemPropertyList = itemDto.getItemPropertyList();
		//按照同一个属性模版中的id来分组 由于同一个属性可能有多个值比如 适用年龄：0－6个月, 6-12个月 ...
		Map<Long,List<ItemTemplatePropertyDTO>> propertyMap = new HashMap<Long,List<ItemTemplatePropertyDTO>>(); 
		for(ItemTemplatePropertyDTO item : itemPropertyList){
			Long propertyId = item.getItemPropertyTmplId();
			List<ItemTemplatePropertyDTO> list = propertyMap.get(propertyId);
			if(list == null){
				List<ItemTemplatePropertyDTO> newList = new ArrayList<ItemTemplatePropertyDTO>();
				newList.add(item);
				propertyMap.put(propertyId, newList);
			}else{
				list.add(item);
			}
		}
		
		java.util.Iterator<Entry<Long, List<ItemTemplatePropertyDTO>>> it = propertyMap.entrySet().iterator();
		// 返回给前端的普通属性的列表
		List<ItemProperty> itemPropertyParamList = new ArrayList<ItemProperty>();
		while(it.hasNext()){
			Entry entry = it.next();
			Long propertyId = (Long)entry.getKey();
			List<ItemTemplatePropertyDTO> propertyList = (List<ItemTemplatePropertyDTO>)entry.getValue();
			List<String> vids = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			ItemProperty itemProperty = new ItemProperty();
			String name=null;
			if(!CollectionUtils.isEmpty(propertyList)){
				for(ItemTemplatePropertyDTO item : propertyList){
					vids.add(String.valueOf(item.getPropertyValueId()));
					values.add(item.getValue());
					name = item.getName();
				}
			}
			itemProperty.setName(name);
			itemProperty.setVid(vids);
			itemProperty.setValue(values);
			itemProperty.setId(String.valueOf(propertyId));
			itemPropertyParamList.add(itemProperty);
		}
		itemParamDto.setProps(itemPropertyParamList);
		
		// 获取类目信息 需要现显示三级类目
		String cateName2 = null, cateName = null, cateName3 = null;
		Long categoryId2 = null, categoryId = null, categoryId3 = null;
		try {
			categoryId3 = itemDto.getCategoryId();// 三级类目
			if(categoryId3 != null && categoryId3.intValue() >0){
				ItemCategoryDTO category = this.itemCategoryManager
						.getItemCategory(categoryId3);
				cateName3 = category.getCateName();
				Long parentId = category.getParentId();
				if (parentId != null && parentId.intValue() > 0) {// 二级类目
					category = this.itemCategoryManager.getItemCategory(parentId);
					parentId = null;
					if (category != null) {
						categoryId2 = category.getId();
						parentId = category.getParentId();
						cateName2 = category.getCateName();
					}
				}
				if (parentId != null && parentId.intValue() > 0) {// 一级类目
					category = this.itemCategoryManager.getItemCategory(parentId);
					if (category != null) {
						categoryId = category.getId();
						cateName = category.getCateName();
					}
				}
			}
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		List<String> categoryLevels = new ArrayList<String>();
		String cateNameLevels = "";
		if (categoryId != null) {
			categoryLevels.add(String.valueOf(categoryId));
			cateNameLevels += cateName + " - ";
		}
		if (categoryId2 != null) {
			categoryLevels.add(String.valueOf(categoryId2));
			cateNameLevels += cateName2 + " - ";
		}
		if (categoryId3 != null) {
			categoryLevels.add(String.valueOf(categoryId3));
			cateNameLevels += cateName3;
		}
		itemParamDto.setCateName(cateNameLevels); // "服饰 - 童装 - T恤"
		itemParamDto.setParents(categoryLevels);// ["1","101","1101"]
		
		/*
		//商品的销售属性的列表  按照sku_item_id分组（item_sku表的的主键）
		List<SkuPropertyDTO> skuPropertyList = itemDto.getSkuPropertyList();
		Map<Long,List<SkuPropertyDTO>> skuPropertyMap = new HashMap<Long,List<SkuPropertyDTO>>();
		//根据商品销售属性的id来分组  比如颜色 有多个取值
		Map<Long,List<String>> skuPropertyIdMap = new HashMap<Long,List<String>>();
		Map<Long,List<String>> skuPropertyValueMap = new HashMap<Long,List<String>>();
		Map<Long,String> skuPropertyNameMap =new HashMap<Long,String>();
		
		for(SkuPropertyDTO item : skuPropertyList){
			Long skuItemId = item.getSkuId();
			List<SkuPropertyDTO> list= skuPropertyMap.get(skuItemId);
			if(list == null){
				List<SkuPropertyDTO> newList = new ArrayList<SkuPropertyDTO>();
				newList.add(item);
				skuPropertyMap.put(skuItemId, newList);
 			}else{
				list.add(item);
			}
			Long skuPropertyId = item.getSkuPropertyTmplId();
			List<String> idList = skuPropertyIdMap.get(skuPropertyId);
			List<String> valueList = skuPropertyValueMap.get(skuPropertyId);
			if(idList == null){
				List<String> ids= new ArrayList<String>();
				ids.add(String.valueOf(item.getVid()));
				List<String> values = new ArrayList<String>();
				values.add(item.getVal());
				skuPropertyIdMap.put(skuPropertyId,ids);
				skuPropertyValueMap.put(skuPropertyId, values);
			}else{
				idList.add(String.valueOf(item.getVid()));
				valueList.add(item.getVal());
			}
			if(skuPropertyNameMap.get(skuPropertyId) == null){
				skuPropertyNameMap.put(skuPropertyId, item.getKeyName());
			}
		}
		
		//商品具体的规格
		List<ItemSkuDTO> itemSkuList =itemDto.getItemSkuDTOList();
		//返回给前端的sku属性的列表
		List<SkuPropertyItem> skus = new ArrayList<SkuPropertyItem>();
		for(ItemSkuDTO item: itemSkuList){
			Integer originPrice = item.getMarketPrice();
			Integer price = item.getPromotionPrice();
			Long num = item.getStockNum();
			String barCode = item.getBarCode();
			//返回给前端的对象
			SkuPropertyItem sku = new SkuPropertyItem();
			sku.setBarcode(barCode);
			sku.setPrice(String.valueOf(price));
			sku.setOriginPrice(String.valueOf(originPrice));
			sku.setNum(String.valueOf(num));
			//一种特定规格下有对应的属性列表 比如：  颜色： 红色，尺码： s  对应的一个规格下有2个属性
			List<SkuPropertyDTO> skuPropList = skuPropertyMap.get(item.getId());
			List<PropParam> returnSkuProps = new ArrayList<PropParam>();
			Long skuPropertyTmplId = null;
			String skuPropertyTmplName = null;
			List<String> vids = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			if(!CollectionUtils.isEmpty(skuPropList)){
				for(SkuPropertyDTO prop: skuPropList){
					PropParam propParam = new PropParam();
					propParam.setPropId(String.valueOf(prop.getSkuPropertyTmplId()));
					propParam.setPropName(prop.getKeyName());
					vids.add(String.valueOf(prop.getVid()));
					values.add(prop.getVal());
					returnSkuProps.add(propParam);
				}
			}
			sku.setProp(returnSkuProps);
			skus.add(sku);
		}
		itemParamDto.setSkus(skus);
		
		List<SkuPropertyParam> skuPropertyParamList = new ArrayList<SkuPropertyParam>();
		java.util.Iterator<Entry<Long, List<String>>> skuIterator = skuPropertyIdMap.entrySet().iterator();
		while(skuIterator.hasNext()){
			Entry entry = skuIterator.next();
			Long id = (Long)entry.getKey();
			skuPropertyNameMap.get(id);
			SkuPropertyParam skuPropertyParam =new SkuPropertyParam();
			skuPropertyParam.setId(String.valueOf(id));
			skuPropertyParam.setName(skuPropertyNameMap.get(id));
			skuPropertyParam.setVid((List<String>)entry.getValue());
			skuPropertyParam.setValue(skuPropertyValueMap.get(id));
			skuPropertyParamList.add(skuPropertyParam);
		}
		
		itemParamDto.setSkuProps(skuPropertyParamList);
		*/
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(itemParamDto);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(itemParamDto) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/template/update.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String updateItemTemplate(HttpServletRequest request){
		long start = System.currentTimeMillis() ;
		
//		if(!StringUtils.isEmpty(this.notNullValidate(this.NOT_NULL_FIELDS_4_UPDATE, request))){
//			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_ISNULL);
//		}
		String callback = request.getParameter("callback");
		Long templateId;
		String templateName;//模版名称
		String name;
		String subName;
		Long categoryId;
		String description;
		Long brandId;
		Integer minBought;
		Integer maxBought;
		Long cornerIcon;
		Integer deliveryType;
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表

		ItemTemplateDTO itemDto = new ItemTemplateDTO();
		Long userId = SessionManager.getLoginUserId(request.getSession());
		itemDto.setSellerId(userId);
		// 参数处理
		try {
			templateName = RequestUtils.getString(request, "template_name");
			templateId = RequestUtils.getLong(request, "template_id",true);
			name = RequestUtils.getString(request, "name",false);
			subName = RequestUtils.getString(request, "sub_name",false);
			categoryId = RequestUtils.getLong(request, "cate_id",true);
			String brief = RequestUtils.getString(request, "brief",false);
			description = RequestUtils.getString(request, "description",false);
			brandId = RequestUtils.getLong(request, "brand_id",false);
			minBought = RequestUtils.getInt(request, "user_min_bought",false);
			maxBought = RequestUtils.getInt(request, "user_max_bought",false);
			cornerIcon = RequestUtils.getLong(request, "corner_icon",false);
			deliveryType = RequestUtils.getInt(request, "delivery_type",false);
			
			//TODO 商品类型 和商品的状态的维护
			String itemTypeStr = RequestUtils.getString(request, "item_type",false);
			Integer itemType = StringUtils.isBlank(itemTypeStr)? 1: 0;//默认是实体商品1： 实体  0： 虚拟
			String itemStatusStr = RequestUtils.getString(request, "item_status",false);
			Integer itemStatus = StringUtils.isBlank(itemStatusStr)? 0: Integer.valueOf(itemStatusStr);
			Date now =new Date();
			
			// 公共部分属性
			itemDto.setTemplateName(templateName);
			itemDto.setId(templateId);
			itemDto.setItemName(name);
			//itemDto.setItemBriefName(subName);
			itemDto.setCategoryId(categoryId);
			itemDto.setItemBrandId(brandId);
			itemDto.setItemDesc(description);//商品图文详情
			itemDto.setSaleMinNum(minBought);
			itemDto.setSaleMaxNum(maxBought);
			itemDto.setSaleBegin(now); // 默认开始就上架了的就是开始时间
			itemDto.setItemType(itemType);
			itemDto.setItemStatus(itemStatus);
			itemDto.setCornerIconId(cornerIcon);
			itemDto.setDeliveryType(deliveryType == null ? ItemConstants.DeliveryType.NOT_LIMIT.getCode() : deliveryType);
			
			// 普通属性列表的处理
			String propsStr = RequestUtils.getString(request, "props",false);
			itemProps = this.parseItemProperty(propsStr);
			
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		List<ItemTemplatePropertyDTO> itemPropertyList = new ArrayList<ItemTemplatePropertyDTO>();
		// 普通属性处理
		int sort = 0;// 用于控制显示顺序  商品显示普通属性时候需要按照特定的顺序来显示
		if(!CollectionUtils.isEmpty(itemProps)){
			for (int i = 0, len = itemProps.size(); i < len; i++) {
				sort++;
				ItemProperty itemProp = itemProps.get(i);
				// 一个属性可能有多个值 比如： 适用年龄 可能有多个值：
				List<String> valueList = itemProp.getValue();
				List<String> vidList = itemProp.getVid();
				if(!CollectionUtils.isEmpty(valueList)){
					for(int index=0,size=valueList.size();index<size;index++){
						ItemTemplatePropertyDTO itemPropertyDto = new ItemTemplatePropertyDTO();
						itemPropertyDto.setItemPropertyTmplId(Long.valueOf(itemProp.getId()));//属性的id
						itemPropertyDto.setName(itemProp.getName());//属性名称
						itemPropertyDto.setPropertyValueId(Long.valueOf(vidList.get(index)));//值的id
						itemPropertyDto.setValue(valueList.get(index));//具体的值 比如 0-6个月
						itemPropertyDto.setSellerId(userId);
						itemPropertyDto.setSort(sort);
						itemPropertyList.add(itemPropertyDto);
					}
				}
			}
		}
		
		itemDto.setItemPropertyList(itemPropertyList);//普通属性的列表
		Boolean result;
		try {
			result = this.itemTemplateManager.updateItemTemplate(itemDto);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/template/query.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String queryItemTemplate(HttpServletRequest request){
		String callback = request.getParameter("callback");
		String keyWord = null;
		Long itemId =null;
		boolean byId =false,byKeyWord =false;
		Integer brandId =null,itemStatus=null;
		Date createTimeStart = null,createTimeEnd=null;
		Integer currentPage,pageSize = null;
		
		if(!StringUtils.isBlank(request.getParameter("key"))){
			byKeyWord = true;
			if(StringUtils.isNumeric(request.getParameter("key"))){ // 按id查询
				itemId = Long.valueOf(request.getParameter("key"));
				byId = true;
			}else{ // 产品关键字查询
				keyWord = request.getParameter("key").trim();
			}
		}
		
		try {
			brandId = RequestUtils.getInt(request, "brand_key",false);
			currentPage = RequestUtils.getInt(request, "current_page",false);
			pageSize = RequestUtils.getInt(request, "page_size",false);
			
			if(!StringUtils.isBlank(request.getParameter("start_time"))){
				createTimeStart = RequestUtils.getFormatDate(request, "start_time",
						ACCEPT_DATE_FORMAT);
			}
			if(!StringUtils.isBlank(request.getParameter("end_time"))){
				createTimeEnd = RequestUtils.getFormatDate(request, "end_time",
						ACCEPT_DATE_FORMAT);
			}
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		} catch(Exception e){
			logger.error("parse param error : " ,e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		
		ItemTemplateQTO itemTemplateQTO = new ItemTemplateQTO();
		
		//String bizCode = SessionManager.getLoginUserBizCode(request.getSession());
		Long userId = SessionManager.getLoginUserId(request.getSession());
		itemTemplateQTO.setSellerId(userId);
		//itemTemplateQTO.setBizCode(bizCode);
		//itemTemplateQTO.setSupplierId(userId);
		if(byKeyWord){
			if(byId){
				itemTemplateQTO.setId(itemId);
			}else{
				itemTemplateQTO.setTemplateName(keyWord);
			}
		}
		itemTemplateQTO.setItemBrandId(brandId);
		//按照了录入的时间范围查询
		itemTemplateQTO.setCreateTimeBegin(createTimeStart);
		itemTemplateQTO.setCreateTimeEnd(createTimeEnd);
		
		//分页
		if(currentPage !=null && pageSize != null){
			itemTemplateQTO.setNeedPaging(true);
			itemTemplateQTO.setCurrentPage(currentPage);
			itemTemplateQTO.setPageSize(pageSize);
		}
		
		List<ItemTemplateDTO> itemTemplateList =null;
		Response<List<ItemTemplateDTO>> itemResponse = null; 
		try {
			itemResponse = this.itemTemplateManager
					.queryItemTemplate(itemTemplateQTO);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		// 构造参数去查询类目名称 和 品牌名称
		// 后续可能需要优化
		Set<Long> brandIdSet = new HashSet<Long>();
		Set<Long> categoryIdSet = new HashSet<Long>();
		if (!CollectionUtils.isEmpty(itemResponse.getModule())) {
			for (ItemTemplateDTO item : itemResponse.getModule()) {
				brandIdSet.add(item.getItemBrandId());
				categoryIdSet.add(item.getCategoryId());
			}
			
			Long[] brandIds = new Long[brandIdSet.size()];
			brandIdSet.toArray(brandIds);
			Long[] categoryIds = new Long[categoryIdSet.size()];
			categoryIdSet.toArray(categoryIds);

			ItemCategoryQTO categoryQTO = new ItemCategoryQTO();
			//categoryQTO.setBizCode(bizCode);
			categoryQTO.setIds(categoryIds);

			SellerBrandQTO sellerBrandQTO = new SellerBrandQTO();
			sellerBrandQTO.setIds(brandIds);
			// TODO sellerBrandQTO.setBizCode(bizCode);

			List<ItemCategoryDTO> categoryList = null;
			List<SellerBrandDTO> brandList = null;
			try {
				categoryList = this.itemCategoryManager.queryCategory(categoryQTO);
				brandList = this.itemBrandManager.querySellerBrand(sellerBrandQTO);
			} catch (ServiceException e) {
				return ServiceResponseHandler.serviceExceptionHandler(e);
			}
			Map<Long, String> brandNameMap = new HashMap<Long, String>();
			Map<Long, String> categoryNameMap = new HashMap<Long, String>();

			if (!CollectionUtils.isEmpty(categoryList)) {
				for (ItemCategoryDTO item : categoryList) {
					categoryNameMap.put(item.getId(), item.getCateName());
				}
			}
			if (!CollectionUtils.isEmpty(brandList)) {
				for (SellerBrandDTO item : brandList) {
					brandNameMap.put(item.getId(), item.getBrandName());
				}
			}
			System.out.println(categoryNameMap);
			System.out.println(brandNameMap);
			
			List<ItemTemplateDTO> itemList = itemResponse.getModule();
			if (itemList != null) {
				for (ItemTemplateDTO item : itemResponse.getModule()) {
					item.setBrandName(brandNameMap.get(item.getItemBrandId()));
					item.setCategoryName(categoryNameMap.get(item.getCategoryId()));
					item.setCreateTime(ResponseUtils.toFormatDate(item.getGmtCreated(), 1));
				}
			}
			
		}
		
		PageDTO pageDTO = new PageDTO();
		pageDTO.setData(itemResponse.getModule());
		pageDTO.setTotalCount(itemResponse.getTotalCount());
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(pageDTO);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageDTO) + ")";
		}
//		
//		String keyWord = StringUtils.isBlank(request.getParameter("key")) ? null : request.getParameter("key").trim();
//		Integer brandId = null;
//		Integer categoryId =null;
//		try {
//			brandId = StringUtils.isBlank(request
//					.getParameter("brand_key")) ? null : RequestUtils.getInt(
//					request, "brand_key");
//			categoryId = StringUtils.isBlank(request
//					.getParameter("category_id")) ? null : RequestUtils.getInt(
//					request, "category_id");
//		} catch (Exception e) {
//			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
//		}
//		
//		Long userId = 1L;
//		ItemTemplateQTO itemTemplateQTO =new ItemTemplateQTO();
//		itemTemplateQTO.setItemBrandId(brandId);
//		List<ItemTemplateDTO> itemTemplateList =null;
//		try {
//			itemTemplateList = this.itemTemplateManager
//					.queryItemTemplate(itemTemplateQTO);
//		} catch (ServiceException e) {
//			return ServiceResponseHandler.serviceExceptionHandler(e);
//		}
		
	}
	
	/**
	 * 解析http请求中的普通属性字符串生成对应的列表
	 * @return
	 */
	private List<ItemProperty> parseItemProperty(String src)throws ParamException{
		if(!StringUtils.isBlank(src)){
			try{
				Type type = new com.google.gson.reflect.TypeToken<List<ItemProperty>>() {
				}.getType();
				List<ItemProperty> itemProps = (List<ItemProperty>) RequestUtils.json2List(src,
						type);
				return itemProps;
			}catch(Exception e){
				throw new ParamException("props","格式不正确");
			}
		}
		return null;
	}
	
}
