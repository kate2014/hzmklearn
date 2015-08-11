package com.mockuai.sellercenter.web.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.mockuai.dts.client.ItemExportClient;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.itemcenter.common.domain.dto.LimitEntity;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.manager.*;
import com.mockuai.sellercenter.web.util.DateUtil;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.Result;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ItemConstants;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.CompositeItemParamDTO.CompositeItem;
import com.mockuai.sellercenter.web.dto.ItemParamDTO;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.GalleryParam;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.ItemProperty;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.PropParam;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.SkuPropertyItem;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.SkuPropertyParam;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import sun.security.util.Resources_sv;

@Controller
public class ItemAction extends BaseValidator {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static Gson gson = new GsonBuilder().setFieldNamingPolicy(  
		    FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	
	// 新增时候的非空字段
	private final String[] NOT_NULL_FIELDS_ADD = {
//			"name","sub_name","cate_id","brief","description","brand_id","user_min_bought","user_max_bought","skus",
//			"brand_id","user_min_bought","user_max_bought"
			"name","sub_name","cate_id","brief","description","skus","brand_id","user_min_bought","user_max_bought"
	};
	
	// 修改时候的非空字段
	private final String[] NOT_NULL_FIELDS_UPDATE = {
		"name","sub_name","cate_id","brief","description","skus","brand_id","user_min_bought","user_max_bought"
	};
	
	private final String[] NOT_NULL_FIELDS_GET = {
		"item_id"	
	};
	
	private String[] ACCEPT_DATE_FORMAT= {"yyyy-MM-dd"};

	private String[] USER_DATE_FORMAT = {"yyyy-MM-dd HH:mm:ss"};
	
	@Resource
	private ItemManager itemManager;
	
	@Resource
	private ItemCategoryManager itemCategoryManager;
	
	@Resource
	private ItemBrandManager itemBrandManager;
	
	@Resource
	private CornerIconManager cornerIconManager;
	
	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private ItemExportClient itemExportClient;

	@Resource
	private ShopManager shopManager;
	
	@RequestMapping(value="/item/query.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String itemQuery(HttpServletRequest request){
		String callback = request.getParameter("callback");
		String keyWord = null,barCode;
		Long categoryId = null;
		Long itemId =null,brandId=null;
		boolean byId =false,byKeyWord =false;
		Integer itemStatus=null;
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
			itemStatus = RequestUtils.getInt(request, "item_status",false);
			brandId = RequestUtils.getLong(request, "brand_key", false);
			barCode = RequestUtils.getString(request, "bar_code", false);
			categoryId = RequestUtils.getLong(request, "category_id", false);

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
		
		ItemQTO itemQto = new ItemQTO();
		//String bizCode= SessionManager.getLoginUserBizCode(request.getSession());
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		if(supplierId == null) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
		}
		itemQto.setSellerId(supplierId);
		itemQto.setCategoryId(categoryId);

		//如果不是根据二维码来查询
		if(StringUtils.isEmpty(barCode)){
			if(byKeyWord){
				if(byId){
					itemQto.setId(itemId);
				}else{
					itemQto.setItemName(keyWord);
				}
			}
			itemQto.setItemBrandId(brandId);
			itemQto.setItemStatus(itemStatus);
			//按照了录入的时间范围查询
			itemQto.setCreateTimeBegin(createTimeStart);
			itemQto.setCreateTimeEnd(createTimeEnd);
		}
		
		//分页
		if(currentPage !=null && pageSize != null){
			itemQto.setNeedPaging(true);
			itemQto.setCurrentPage(currentPage);
			itemQto.setPageSize(pageSize);
		}
		
		Response<List<ItemDTO>> serviceResponse = null;
		
		try {
			if(!StringUtils.isEmpty(barCode)){//如果是需要根据barCode来查询 需要先到sku表查找  后到item表查找
				System.out.println("query by barCode");
				List<Long> itemIdList = this.queryItemIdByBarCode(barCode, supplierId);
				if(CollectionUtils.isEmpty(itemIdList)){
					PageDTO<List<ItemDTO>> pageInfo =new PageDTO<List<ItemDTO>>();
					pageInfo.setData(Collections.EMPTY_LIST);
					pageInfo.setTotalCount(0L);
					if(StringUtils.isBlank(callback)){
						return ResponseUtils.getSuccessApiResponseStr(pageInfo); 
					}else{
						return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageInfo) + ")"; 
					}
				}
				System.out.println("itemIdList: " + itemIdList);
				itemQto.setIdList(itemIdList);
			}
			// 设置未删除
			itemQto.setDeleteMark(0);
			serviceResponse = this.itemManager.queryItem(itemQto);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
	
		//构造参数去查询类目名称 和 品牌名称
		// 后续可能需要优化
		Set<Long> brandIdSet = new HashSet<Long>();
		Set<Long> categoryIdSet =new HashSet<Long>();
		if(!CollectionUtils.isEmpty(serviceResponse.getModule())){
			for(ItemDTO item : serviceResponse.getModule()){
				brandIdSet.add(item.getItemBrandId());
				categoryIdSet.add(item.getCategoryId());
				item.setCreateTime(ResponseUtils.toFormatDate(item.getGmtCreated(),1));
			}
			
			Long[] brandIds = new Long[brandIdSet.size()];
			brandIdSet.toArray(brandIds);
			Long[] categoryIds = new Long[categoryIdSet.size()];
			categoryIdSet.toArray(categoryIds);
			System.out.println(brandIdSet);
			System.out.println(categoryIdSet);
			
			ItemCategoryQTO categoryQTO = new ItemCategoryQTO();
			//TODO
			//categoryQTO.setBizCode(bizCode);
			categoryQTO.setIds(categoryIds);
			
			SellerBrandQTO sellerBrandQTO =new SellerBrandQTO();
			sellerBrandQTO.setIds(brandIds);
			
			//sellerBrandQTO.setBizCode(bizCode);
			
			List<ItemCategoryDTO> categoryList =null;
			List<SellerBrandDTO> brandList = null;
			try{
				categoryList = this.itemCategoryManager.queryCategory(categoryQTO);
				brandList = this.itemBrandManager.querySellerBrand(sellerBrandQTO);
			}catch(ServiceException e){
				return ServiceResponseHandler.serviceExceptionHandler(e);
			}
			Map<Long,String> brandNameMap =new HashMap<Long,String>();
			Map<Long,String> categoryNameMap =new HashMap<Long,String>();
			
			if(!CollectionUtils.isEmpty(categoryList)){
				for(ItemCategoryDTO item : categoryList){
					categoryNameMap.put(item.getId(), item.getCateName());
				}
			}
			if(!CollectionUtils.isEmpty(brandList)){
				for(SellerBrandDTO item : brandList){
					brandNameMap.put(item.getId(), item.getBrandName());
				}
			}
			List<ItemDTO> itemList = serviceResponse.getModule();
			if(itemList != null){
				for(ItemDTO item: serviceResponse.getModule()){
					item.setBrandName(brandNameMap.get(item.getItemBrandId()));
					item.setCategoryName(categoryNameMap.get(item.getCategoryId()));
				}
			}
			
		}
		
		PageDTO<List<ItemDTO>> pageInfo =new PageDTO<List<ItemDTO>>();
		pageInfo.setData(serviceResponse.getModule());
		pageInfo.setTotalCount(serviceResponse.getTotalCount());
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(pageInfo); 
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageInfo) + ")"; 
		}
	}
	
	/**
	 * 新加入商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/item/add.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String addItem(HttpServletRequest request)throws Exception{
		long start=System.currentTimeMillis();
		String callback = request.getParameter("callback");
		String name;
		String subName;
		Long categoryId;
		String description;
		Long brandId;
		Integer minBought;
		Integer maxBought;
		String brief;

		Date saleBegin = null;
		Date saleEnd = null;
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		ItemDTO itemDto = new ItemDTO();
		itemDto.setSellerId(userId);
		Long cornerIcon;
		Integer deliveryType;
		// 处理参数参数中的复合对象部分
		List<SkuPropertyItem> skuProps = null;//解析出来的普通属性列表
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表
		List<GalleryParam> gallerys = null;//解析出来的图片列表

		// 商品限购;
		Integer buyLimitCount = null;
		Date buyLimitBeginTime = null;
		Date buyLimitEndTime = null;

		Long groupId = null;

		// 参数处理
		try {
			name = RequestUtils.getString(request, "name",true);
			subName = RequestUtils.getString(request, "sub_name",false);
			categoryId = RequestUtils.getLong(request, "cate_id",true);
			brief = RequestUtils.getString(request, "brief",false);
			description = RequestUtils.getString(request, "description",true);
			brandId = RequestUtils.getLong(request, "brand_id",true);
			minBought = RequestUtils.getInt(request, "user_min_bought",false);
			maxBought = RequestUtils.getInt(request, "user_max_bought",false);
			cornerIcon = RequestUtils.getLong(request, "corner_icon",false);
			deliveryType = RequestUtils.getInt(request, "delivery_type",false);
			
			//TODO 后续待完成   有可能有虚拟商品
			Integer itemType = RequestUtils.getInt(request, "item_type",false);
			// 限购字段;
			buyLimitCount = RequestUtils.getInt(request, "buy_limit_count", false);
			buyLimitBeginTime = RequestUtils.getFormatDate(request, "buy_limit_begin_time", USER_DATE_FORMAT);
			buyLimitEndTime = RequestUtils.getFormatDate(request, "buy_limit_end_time", USER_DATE_FORMAT);

			// 开始售卖和结束售卖时间
			saleBegin = RequestUtils.getFormatDate(request, "time_begin", USER_DATE_FORMAT);
			saleEnd = RequestUtils.getFormatDate(request, "time_end", USER_DATE_FORMAT);
			itemDto.setSaleBegin(saleBegin);
			itemDto.setSaleEnd(saleEnd);
			groupId = RequestUtils.getLong(request, "group_id", false);

			//默认是为实物商品
			if(itemType == null){
				itemType = ItemConstants.ItemType.PHYSICAL_GOODS.getType();
			}else{
				 if(itemType.intValue() != ItemConstants.ItemType.PHYSICAL_GOODS.getType() && 
						 itemType.intValue() != ItemConstants.ItemType.VIRUAL_GOODS.getType()){
					 itemType =  ItemConstants.ItemType.PHYSICAL_GOODS.getType();
				 }
			}
			Integer itemStatus = RequestUtils.getInt(request, "item_status",false);
			
			//TODO 默认是下架状态 后续可能有待审核状态之类的
			if(itemStatus == null){
				itemStatus = ItemConstants.ItemStatus.OFF_SALE.getStatus();
			}else{
				if(itemStatus.intValue() < ItemConstants.ItemStatus.PENDING_AUDIT.getStatus()  
					||itemStatus.intValue() > ItemConstants.ItemStatus.FROZEN.getStatus()){
					itemStatus = ItemConstants.ItemStatus.OFF_SALE.getStatus();
				}
			}
		
			itemDto.setItemName(name);
			//itemDto.setItemBriefName(subName);
			itemDto.setItemBrief(brief);
			itemDto.setCategoryId(categoryId);
			itemDto.setItemBrandId(brandId);
			itemDto.setItemDesc(description);//商品图文详情
			itemDto.setSaleMinNum(minBought);
			itemDto.setSaleMaxNum(maxBought);
			//itemDto.setSaleBegin(); // 开售时间控制 待完善？
			itemDto.setItemType(itemType);
			itemDto.setItemStatus(itemStatus);
			itemDto.setCornerIconId(cornerIcon);
			itemDto.setDeliveryType(deliveryType == null ? ItemConstants.DeliveryType.NOT_LIMIT.getCode() : deliveryType);
			itemDto.setGroupId(groupId);
			// 限购;
			LimitEntity limitEntity = new LimitEntity();
			if(buyLimitCount > 0) {// 大于0才设置;
				limitEntity.setLimitCount(buyLimitCount);
				limitEntity.setBeginTime(buyLimitBeginTime);
				limitEntity.setEndTime(buyLimitEndTime);
				List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
				limitEntities.add(limitEntity);
				itemDto.setBuyLimit(limitEntities);
			}

			String skuStr = RequestUtils.getString(request, "skus",true); //必须有销售属性
			skuProps = this.parseSkuProperty(skuStr);
			String itemPropStr = RequestUtils.getString(request, "props",false);
			itemProps = this.parseItemProperty(itemPropStr);
			String galleryStr = RequestUtils.getString(request, "gallery",false);
			gallerys = this.parseGallery(galleryStr);
			
		} catch (ParamException e) {
			logger.error("parse error happen : " ,e);
			logger.error(e.getField() + " " + e.getMessage());
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		/*
		try {
			//skus sku属性部分
			//List<SkuPropertyItem> skuProps = gson.fromJson(skus, SkuPropertyItem.class);
			Type type;
			if(!StringUtils.isBlank(RequestUtils.getString(request, "skus"))){
				type = new com.google.gson.reflect.TypeToken<List<SkuPropertyItem>>() {
				}.getType();
				skuProps = (List<SkuPropertyItem>) RequestUtils.json2List(RequestUtils.getString(request, "skus").trim(),
						type);
			}
			
			//props 普通属性部分
			if(!StringUtils.isBlank(RequestUtils.getString(request, "props"))){
				type = new com.google.gson.reflect.TypeToken<List<ItemProperty>>() {
				}.getType();
				itemProps = (List<ItemProperty>) RequestUtils.json2List(RequestUtils.getString(request, "props").trim(),
						type);
			}
			
			//gallery 图片部分
			//List<GalleryParam> gallerys = itemStrDTO.getGallery();
			if(!StringUtils.isBlank(RequestUtils.getString(request, "gallery"))){
				type = new com.google.gson.reflect.TypeToken<List<GalleryParam>>() {
				}.getType();
				gallerys = (List<GalleryParam>) RequestUtils.json2List(RequestUtils.getString(request, "gallery").trim(),
						type);
			}
		} catch (Exception e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		*/
		
		String firstImg = null;//主图url
		String firstGeneralImg = null;//默认应该取第一张通用图 作为主图 如果时没有的话取第一张图片
		//商品图片的处理
		int sort = 0; //sort用于控制图片的排列顺序 默认取上传时候的顺序
		List<ItemImageDTO> itemImageList = new ArrayList<ItemImageDTO>();
		int totalGeneralImg = 0;//暂时通用图片只能控制上传4张
		if(!CollectionUtils.isEmpty(gallerys)){
			for(int i =0,len=gallerys.size();i<len;i++){
				GalleryParam gallery = gallerys.get(i);
				if(sort == 0){
					firstImg = gallery.getImg();// 首张保存为主图
				}
				if(firstGeneralImg == null){
					if(gallery.getId() != null && gallery.getId().equals("0")){//通用图ui端固定传递为0 表示不跟具体sku属性关联
						firstGeneralImg = gallery.getImg();
					}
				}
				sort++;
				ItemImageDTO itemImageDto =new ItemImageDTO();
				itemImageDto.setImageUrl(gallery.getImg());
				itemImageDto.setSort(sort);
				itemImageDto.setImageName(itemDto.getItemName());
				itemImageDto.setImageType(Integer.valueOf(gallery.getType()));
				itemImageDto.setSellerId(userId);
				itemImageDto.setPropertyValueId(Long.valueOf(gallery.getId()));
				
				if(itemImageDto.getPropertyValueId() != null && itemImageDto.getPropertyValueId().longValue()==0){//通用图ui端固定传递为0 表示不跟具体sku属性关联
					if(totalGeneralImg++ <ItemConstants.ItemImage.MAX_GENERAL_IMAGES){
						itemImageList.add(itemImageDto);
					}
				}else{
					itemImageList.add(itemImageDto);
				}
			}
		}
		//TODO 主图必须要有
		
		//设置主图
		if(firstGeneralImg == null){
			itemDto.setIconUrl(firstImg);
		}else{
			itemDto.setIconUrl(firstGeneralImg);
		}
		
		//  sku销售属性处理
		sort = 0;
		long minOriginPrice =0L,minPrice =0L;//  找到最低的价格作为item的价格
		boolean isFirst = true;
		List<ItemSkuDTO> itemSkuList = new ArrayList<ItemSkuDTO>();
		if(!CollectionUtils.isEmpty(skuProps)){
			for(SkuPropertyItem skuStrDto : skuProps){
				ItemSkuDTO itemSkuDto = new ItemSkuDTO();
				itemSkuDto.setBarCode(skuStrDto.getBarcode());
				
				itemSkuDto.setMarketPrice(Long.valueOf(skuStrDto.getOriginPrice()));
				itemSkuDto.setWirelessPrice(Long.valueOf(skuStrDto.getPrice()));
				itemSkuDto.setPromotionPrice(Long.valueOf(skuStrDto.getPrice()));
				//价格负数验证
				
				try {
					if(itemSkuDto.getMarketPrice().longValue() < 0 ){
						throw new ParamException("origin_price","不能小于0");
					}else if(itemSkuDto.getPromotionPrice().longValue() < 0){
						throw new ParamException("price","不能小于0");
					}
				} catch (ParamException e) {
					return ResponseUtils.getFailApiResponseStr(e);
				}
				
				if(isFirst){//第一次就是取第一个价格
					minPrice=itemSkuDto.getPromotionPrice();
					minOriginPrice = itemSkuDto.getMarketPrice();
					isFirst=false;
				}else{//取更小的一个价格
					if(itemSkuDto.getPromotionPrice()!=null && itemSkuDto.getPromotionPrice().longValue() < minPrice){
						minPrice = itemSkuDto.getPromotionPrice();
					}if(itemSkuDto.getMarketPrice() !=null && itemSkuDto.getMarketPrice().longValue() < minOriginPrice){
						minOriginPrice = itemSkuDto.getMarketPrice();
					}	
				}
				
				itemSkuDto.setStockNum(Long.valueOf(skuStrDto.getNum()));
				itemSkuDto.setSellerId(userId);
				List<PropParam> propStrDtoList = skuStrDto.getProp();
				//sku具体的属性值比如    颜色： 蓝色
				List<SkuPropertyDTO> skuPropertyList = new ArrayList<SkuPropertyDTO>();
				if(!CollectionUtils.isEmpty(propStrDtoList)){
					for(PropParam proStrDto : propStrDtoList){
						SkuPropertyDTO skuPropertyDto = new SkuPropertyDTO();
						skuPropertyDto.setSkuPropertyTmplId(Long.valueOf(proStrDto.getPropId()));//sku属性id
						skuPropertyDto.setName(proStrDto.getPropName());//属性名称
						skuPropertyDto.setValue(proStrDto.getValueName());//属性值
						skuPropertyDto.setPropertyValueId(Long.valueOf(proStrDto.getValueId()));//属性值id
						skuPropertyDto.setSellerId(userId);
						skuPropertyDto.setSort(sort++);//控制显示顺序
						skuPropertyList.add(skuPropertyDto);
					}
					itemSkuDto.setSkuPropertyDTOList(skuPropertyList);
				}
				itemSkuList.add(itemSkuDto);
			}
		}
		//填充sku列表中的最小的价格作为item的价格
		itemDto.setPromotionPrice(minPrice);
		itemDto.setMarketPrice(minOriginPrice);
		
		List<ItemPropertyDTO> itemPropertyList = new ArrayList<ItemPropertyDTO>();
		// 普通属性处理
		sort = 0;// 用于控制显示顺序  商品显示普通属性时候需要按照特定的顺序来显示
		if(!CollectionUtils.isEmpty(itemProps)){
			for (int i = 0, len = itemProps.size(); i < len; i++) {
				sort++;
				ItemProperty itemProp = itemProps.get(i);
				// 一个属性可能有多个值 比如： 适用年龄 属性 可能有多个值：
				List<String> valueList = itemProp.getValue();
				List<String> vidList = itemProp.getVid();
				if(!CollectionUtils.isEmpty(valueList)){
					for(int index=0,size=valueList.size();index<size;index++){
						ItemPropertyDTO itemPropertyDto = new ItemPropertyDTO();
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
		
		//列表值
		itemDto.setItemSkuDTOList(itemSkuList);//商品下具体的规格列表
		itemDto.setItemPropertyList(itemPropertyList);//普通属性的列表
		itemDto.setItemImageDTOList(itemImageList);//图片的列表
		
		try {
			Boolean result = this.itemManager.addItem(itemDto);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(true);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(true) + ")"; 
		}
	}
	
	/**
	 * 删除商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/item/delete.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteItem(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long itemId = null;
		try {
			itemId = RequestUtils.getLong(request, "item_id", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		Boolean result;
		try {
			result = this.itemManager.deleteItem(itemId, supplierId);
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
	 * 下架商品
	 */
	@RequestMapping(value="/item/withdraw.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String withdrawItem(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long itemId = null;
		try {
			itemId = RequestUtils.getLong(request, "item_id", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		Boolean result;
		try {
			result = this.itemManager.withdraw(itemId, supplierId);
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
	 * 上架商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/item/up.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String upItem(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long itemId = null;
		try {
			itemId = RequestUtils.getLong(request, "item_id", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		Boolean result;
		try {
			result = this.itemManager.upItem(itemId, supplierId);
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
	 * 上架商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/item/presale.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String preSaleItem(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long itemId = null;
		try {
			itemId = RequestUtils.getLong(request, "item_id", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}

		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		Boolean result;
		try {
			result = this.itemManager.preSaleItem(itemId, supplierId);
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
	 * 更新商品信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/item/update.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String updateItem(HttpServletRequest request) {
		String callback = request.getParameter("callback");
	
		Long itemId;
		String name;
		String subName;
		Long categoryId;
		String description;
		Long brandId;
		Integer minBought;
		Integer maxBought;
		String brief;
		Long cornerIcon;
		Integer deliveryType;
		
		ItemDTO itemDto = new ItemDTO();
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		itemDto.setSellerId(userId);
		
		// 处理参数参数中的复合对象部分
		List<SkuPropertyItem> skuProps = null;//解析出来的普通属性列表
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表
		List<GalleryParam> gallerys = null;//解析出来的图片列表

		// 商品限购;
		Integer buyLimitCount = null;
		Date buyLimitBeginTime = null;
		Date buyLimitEndTime = null;

		Date saleBegin = null;
		Date saleEnd = null;
		Long groupId = null;
		
		try {
			itemId = RequestUtils.getLong(request, "id", true);//商品id必须
			name = RequestUtils.getString(request, "name",true);
			subName = RequestUtils.getString(request, "sub_name",false);
			categoryId = RequestUtils.getLong(request, "cate_id",true);
			brief = RequestUtils.getString(request, "brief",false);
			description = RequestUtils.getString(request, "description",true);
			brandId = RequestUtils.getLong(request, "brand_id",true);
			minBought = RequestUtils.getInt(request, "user_min_bought",false);
			maxBought = RequestUtils.getInt(request, "user_max_bought",false);
			cornerIcon = RequestUtils.getLong(request, "corner_icon",false);
			deliveryType = RequestUtils.getInt(request, "delivery_type",false);
			
			String itemTypeStr = RequestUtils.getString(request, "item_type",false);
			String itemStatusStr = RequestUtils.getString(request, "item_status");
			groupId = RequestUtils.getLong(request, "group_id",false);

			//TODO
			Integer itemType = RequestUtils.getInt(request, "item_type",false);
			//默认是为实物商品
			if(itemType == null){
				itemType = ItemConstants.ItemType.PHYSICAL_GOODS.getType();
			}else{
				 if(itemType.intValue() != ItemConstants.ItemType.PHYSICAL_GOODS.getType() && 
						 itemType.intValue() != ItemConstants.ItemType.VIRUAL_GOODS.getType()){
					 itemType =  ItemConstants.ItemType.PHYSICAL_GOODS.getType();
				 }
			}
			Integer itemStatus = RequestUtils.getInt(request, "item_status",false);

			// 限购字段;
			buyLimitCount = RequestUtils.getInt(request, "buy_limit_count", false);
			buyLimitBeginTime = RequestUtils.getFormatDate(request, "buy_limit_begin_time", USER_DATE_FORMAT);
			buyLimitEndTime = RequestUtils.getFormatDate(request, "buy_limit_end_time", USER_DATE_FORMAT);

			// 开始售卖和结束售卖时间
			saleBegin = RequestUtils.getFormatDate(request, "time_begin", USER_DATE_FORMAT);
			saleEnd = RequestUtils.getFormatDate(request, "time_end", USER_DATE_FORMAT);
			itemDto.setSaleBegin(saleBegin);
			itemDto.setSaleEnd(saleEnd);

			itemDto.setGroupId(groupId);
			
			//修改时候只改信息不该状态
			/*
			if(itemStatus == null){
				itemStatus = ItemConstants.ItemStatus.ON_SALE.getStatus();
			}else{
				if(itemStatus.intValue() < ItemConstants.ItemStatus.PENDING_AUDIT.getStatus()  
					||itemStatus.intValue() > ItemConstants.ItemStatus.FROZEN.getStatus()){
					itemStatus = ItemConstants.ItemStatus.ON_SALE.getStatus();
				}
			}
			*/
			
			// 公共部分属性
			itemDto.setId(itemId);
			itemDto.setItemName(name);
			//itemDto.setItemBriefName(subName);
			itemDto.setItemBrief(brief);
			itemDto.setCategoryId(categoryId);
			itemDto.setItemBrandId(brandId);
			itemDto.setItemDesc(description);//商品图文详情
			itemDto.setSaleMinNum(minBought);
			itemDto.setSaleMaxNum(maxBought);
			//itemDto.setSaleBegin(now);
			itemDto.setItemType(itemType);
			//itemDto.setItemStatus(itemStatus);//修改时候只改信息不该状态
			itemDto.setCornerIconId(cornerIcon);
			itemDto.setDeliveryType(deliveryType == null ? ItemConstants.DeliveryType.NOT_LIMIT.getCode() : deliveryType);//0就是不限定发货方式

			// 限购;
			LimitEntity limitEntity = new LimitEntity();
			if(buyLimitCount > 0) {// 大于0才设置;
				limitEntity.setLimitCount(buyLimitCount);
				limitEntity.setBeginTime(buyLimitBeginTime);
				limitEntity.setEndTime(buyLimitEndTime);
				List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
				limitEntities.add(limitEntity);
				itemDto.setBuyLimit(limitEntities);
			}
			
			String skuStr = RequestUtils.getString(request, "skus",true); //必须有销售属性
			skuProps = this.parseSkuProperty(skuStr);
			String itemPropStr = RequestUtils.getString(request, "props",false);
			itemProps = this.parseItemProperty(itemPropStr);
			String galleryStr = RequestUtils.getString(request, "gallery",false);
			gallerys = this.parseGallery(galleryStr);
			
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		/*
		// 处理参数参数中的复合对象部分
		List<SkuPropertyItem> skuProps = null;//解析出来的普通属性列表
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表
		List<GalleryParam> gallerys = null;//解析出来的图片列表
		try {
			//skus sku属性部分
			//List<SkuPropertyItem> skuProps = gson.fromJson(skus, SkuPropertyItem.class);
			Type type;
			if(!StringUtils.isBlank(RequestUtils.getString(request, "skus"))){
				type = new com.google.gson.reflect.TypeToken<List<SkuPropertyItem>>() {
				}.getType();
				skuProps = (List<SkuPropertyItem>) RequestUtils.json2List(RequestUtils.getString(request, "skus").trim(),
						type);
			}
			
			//props 普通属性部分
			if(!StringUtils.isBlank(RequestUtils.getString(request, "props"))){
				type = new com.google.gson.reflect.TypeToken<List<ItemProperty>>() {
				}.getType();
				itemProps = (List<ItemProperty>) RequestUtils.json2List(RequestUtils.getString(request, "props").trim(),
						type);
			}
			
			//gallery 图片部分
			//List<GalleryParam> gallerys = itemStrDTO.getGallery();
			if(!StringUtils.isBlank(RequestUtils.getString(request, "gallery"))){
				type = new com.google.gson.reflect.TypeToken<List<GalleryParam>>() {
				}.getType();
				gallerys = (List<GalleryParam>) RequestUtils.json2List(RequestUtils.getString(request, "gallery").trim(),
						type);
			}
		} catch (Exception e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		*/
		
		String firstImg = null;  // 主图url
		String firstGeneralImg = null;//默认应该取第一张通用图 作为主图 如果时没有的话取第一张图片
		//商品图片的处理
		int sort = 0; //sort用于控制图片的排列顺序 默认取上传时候的顺序
		List<ItemImageDTO> itemImageList = new ArrayList<ItemImageDTO>();
		int totalGeneralImg = 0;
		if(!CollectionUtils.isEmpty(gallerys)){
			for(int i =0,len=gallerys.size();i<len;i++){
				GalleryParam gallery = gallerys.get(i);
				if(sort == 0){
					firstImg = gallery.getImg();// 首张保存为主图
				}
				if(firstGeneralImg == null){
					if(gallery.getId() != null && gallery.getId().equals("0")){//通用图ui端固定传递为0 表示不跟具体sku属性关联
						firstGeneralImg = gallery.getImg();
					}
				}
				sort++;
				ItemImageDTO itemImageDto =new ItemImageDTO();
				itemImageDto.setImageUrl(gallery.getImg());
				itemImageDto.setSort(sort);
				itemImageDto.setImageName(itemDto.getItemName());
				itemImageDto.setSellerId(userId);
				itemImageDto.setPropertyValueId(Long.valueOf(gallery.getId()));
				
				if(itemImageDto.getPropertyValueId() != null && itemImageDto.getPropertyValueId().longValue()==0){//通用图ui端固定传递为0 表示不跟具体sku属性关联
					if(totalGeneralImg++ <ItemConstants.ItemImage.MAX_GENERAL_IMAGES){//通用图片只是取前面4张
						itemImageList.add(itemImageDto);
					}
				}else{
					itemImageList.add(itemImageDto);
				}
			}
		}
		if(firstGeneralImg == null){
			itemDto.setIconUrl(firstImg);
		}else{
			itemDto.setIconUrl(firstGeneralImg);
		}
		
		//sku销售属性处理
		sort = 0;
		long minPrice=0L,minOriginPrice=0L;
		boolean isFirst=true;
		List<ItemSkuDTO> itemSkuList = new ArrayList<ItemSkuDTO>();
		if(!CollectionUtils.isEmpty(skuProps)){
			for(SkuPropertyItem skuStrDto : skuProps){
				ItemSkuDTO itemSkuDto = new ItemSkuDTO();
				itemSkuDto.setId(skuStrDto.getSkuId());
				itemSkuDto.setBarCode(skuStrDto.getBarcode());
				itemSkuDto.setMarketPrice(Long.valueOf(skuStrDto.getOriginPrice()));
				itemSkuDto.setWirelessPrice(Long.valueOf(skuStrDto.getPrice()));
				itemSkuDto.setPromotionPrice(Long.valueOf(skuStrDto.getPrice()));
				
				try {
					if(itemSkuDto.getMarketPrice().longValue() < 0 ){
						throw new ParamException("origin_price","不能小于0");
					}else if(itemSkuDto.getPromotionPrice().longValue() < 0){
						throw new ParamException("price","不能小于0");
					}
				} catch (ParamException e) {
					return ResponseUtils.getFailApiResponseStr(e);
				}
				
				if(isFirst){//第一次就是取第一个价格
					minPrice=itemSkuDto.getPromotionPrice();
					minOriginPrice = itemSkuDto.getMarketPrice();
					isFirst=false;
				}else{//取更小的一个价格
					if(itemSkuDto.getPromotionPrice()!=null && itemSkuDto.getPromotionPrice().longValue() < minPrice){
						minPrice = itemSkuDto.getPromotionPrice();
					}if(itemSkuDto.getMarketPrice() !=null && itemSkuDto.getMarketPrice().longValue() < minOriginPrice){
						minOriginPrice = itemSkuDto.getMarketPrice();
					}	
				}
				
				itemSkuDto.setStockNum(Long.valueOf(skuStrDto.getNum()));
				itemSkuDto.setSellerId(userId);
				List<PropParam> propStrDtoList = skuStrDto.getProp();
				//sku具体的属性值比如    颜色： 蓝色
				List<SkuPropertyDTO> skuPropertyList = new ArrayList<SkuPropertyDTO>();
				if(!CollectionUtils.isEmpty(propStrDtoList)){
					for(PropParam proStrDto : propStrDtoList){
						SkuPropertyDTO skuPropertyDto = new SkuPropertyDTO();
						skuPropertyDto.setSkuPropertyTmplId(Long.valueOf(proStrDto.getPropId()));//sku属性id
						skuPropertyDto.setName(proStrDto.getPropName());//属性名称
						skuPropertyDto.setValue(proStrDto.getValueName());//属性值
						skuPropertyDto.setPropertyValueId(Long.valueOf(proStrDto.getValueId()));//属性值id
						skuPropertyDto.setSellerId(userId);
						skuPropertyDto.setSort(sort++);//控制显示顺序
						skuPropertyList.add(skuPropertyDto);
					}
					itemSkuDto.setSkuPropertyDTOList(skuPropertyList);
				}
				itemSkuList.add(itemSkuDto);
			}
		}
		
		itemDto.setPromotionPrice(minPrice);
		itemDto.setMarketPrice(minOriginPrice);
		
		List<ItemPropertyDTO> itemPropertyList = new ArrayList<ItemPropertyDTO>();
		// 普通属性处理
		sort = 0;// 用于控制显示顺序  商品显示普通属性时候需要按照特定的顺序来显示
		if(!CollectionUtils.isEmpty(itemProps)){
			for (int i = 0, len = itemProps.size(); i < len; i++) {
				sort++;
				ItemProperty itemProp = itemProps.get(i);
				// 一个属性可能有多个值 比如： 适用年龄 属性 可能有多个值：
				List<String> valueList = itemProp.getValue();
				List<String> vidList = itemProp.getVid();
				if(!CollectionUtils.isEmpty(valueList)){
					for(int index=0,size=valueList.size();index<size;index++){
						ItemPropertyDTO itemPropertyDto = new ItemPropertyDTO();
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
		
		//列表值的处理
		itemDto.setItemSkuDTOList(itemSkuList);//商品下具体的规格列表
		itemDto.setItemPropertyList(itemPropertyList);//普通属性的列表
		itemDto.setItemImageDTOList(itemImageList);//图片的列表
		
		try {
			Boolean result = this.itemManager.updateItem(itemDto);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(true);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(true) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/get.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String getItem(HttpServletRequest request){
		String callback = request.getParameter("callback");

		if (!StringUtils
				.isEmpty(this.notNullValidate(NOT_NULL_FIELDS_GET, request))) {
			return ResponseUtils
					.getFailApiResponseStr(ResponseEnum.P_E_PARAM_ISNULL);
		}
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		Long itemId =null;
		try {
			itemId = RequestUtils.getLong(request, "item_id");
		} catch (Exception e) {
			return ResponseUtils
					.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		ItemDTO itemDto = null;
		try {
			itemDto = this.itemManager.getItem(userId, itemId);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
	
		// 公共属性部分
		ItemParamDTO itemParamDto = new ItemParamDTO();
		itemParamDto.setId(String.valueOf(itemDto.getId()));//id
		itemParamDto.setCateId(String.valueOf(itemDto.getCategoryId()));//类目id
		itemParamDto.setGroupId(itemDto.getGroupId());
		if(itemDto.getGroupId() != null) {
			Long groupId = itemDto.getGroupId();
			try {
				// 查询group name;
				ShopItemGroupDTO shopItemGroupDTO = shopManager.getShopItemGroup(groupId, userId);
				itemParamDto.setGroupName(shopItemGroupDTO.getGroupName());
			} catch (ServiceException e) {
				return ServiceResponseHandler.serviceExceptionHandler(e);
			}
		}

		itemParamDto.setName(itemDto.getItemName());//商品名称
		//itemParamDto.setSubName(itemDto.getItemBriefName());//商品简称
		itemParamDto.setBrief(itemDto.getItemBrief());
		itemParamDto.setBrandId(String.valueOf(itemDto.getItemBrandId())); // 品牌id
		itemParamDto.setUserMinBought(String.valueOf(itemDto.getSaleMinNum()));//最小购买量
		itemParamDto.setUserMaxBought(String.valueOf(itemDto.getSaleMaxNum())); //最大购买量限制
		itemParamDto.setDescription(itemDto.getItemDesc());
		itemParamDto.setDeliveryType(itemDto.getDeliveryType() == null ? String.valueOf(ItemConstants.DeliveryType.NOT_LIMIT.getCode()) : String.valueOf(itemDto.getDeliveryType()));

		Date saleBegin = itemDto.getSaleBegin();
		Date saleEnd = itemDto.getSaleEnd();
		if(saleBegin != null) {
			itemParamDto.setSaleBegin(DateUtil.formatDate(itemDto.getSaleBegin(), DateUtil.SIMPLE_FORMAT_TIME));
		}
		if(saleEnd != null) {
			itemParamDto.setSaleEnd(DateUtil.formatDate(itemDto.getSaleEnd(), DateUtil.SIMPLE_FORMAT_TIME));
		}

		if(itemDto.getBuyLimit() != null) {
			List<ItemParamDTO.LimitEntry> limitEntrys = new ArrayList<ItemParamDTO.LimitEntry>();
			for(LimitEntity entity: itemDto.getBuyLimit()) {
				ItemParamDTO.LimitEntry entry = new ItemParamDTO.LimitEntry();
				entry.setLimitCount(entity.getLimitCount());
				entry.setBeginTime(DateUtil.formatDate(entity.getBeginTime(), DateUtil.SIMPLE_FORMAT_TIME));
				entry.setEndTime(DateUtil.formatDate(entity.getEndTime(), DateUtil.SIMPLE_FORMAT_TIME));
				limitEntrys.add(entry);
			}
			itemParamDto.setLimitEntityList(limitEntrys);
		}
		//如果该商品有角标获取url
		if(itemDto.getCornerIconId() != null){
			try {
				CornerIconDTO cornerIcon = this.cornerIconManager
						.getCornerIcon(itemDto.getCornerIconId());
				itemParamDto.setCornerIcon(cornerIcon);//角标
			} catch (ServiceException e) {
				return ServiceResponseHandler.serviceExceptionHandler(e);
			}
		}
		
		List<SkuPropertyDTO> skuPropertys = itemDto.getSkuPropertyList();
		
		//图片部分
		//List<ItemImageDTO> gallery = itemDto.getItemImageDTOList();
		ItemImageQTO itemImageQto = new ItemImageQTO();
		itemImageQto.setSellerId(userId);
		List<ItemImageDTO> itemImages = itemDto.getItemImageDTOList();
		List<GalleryParam> galleryList = new ArrayList<GalleryParam>();
		for(ItemImageDTO itemImage : itemImages){
			GalleryParam gallery = new GalleryParam();
			// 对应于具体的一个规格的图片
			gallery.setColor(itemImage.getImageName());
			gallery.setImg(itemImage.getImageUrl());
			gallery.setId(String.valueOf(itemImage.getPropertyValueId()));// 具体的值的id，比如： 蓝色对应于一个id
			galleryList.add(gallery);
		}
		itemParamDto.setGallery(galleryList);
		
		//普通属性的处理
		List<ItemPropertyDTO> itemPropertyList = itemDto.getItemPropertyList();
		//按照同一个属性模版中的id来分组 由于同一个属性可能有多个值比如 适用年龄：0－6个月, 6-12个月 ...
		Map<Long,List<ItemPropertyDTO>> propertyMap = new HashMap<Long,List<ItemPropertyDTO>>(); 
		for(ItemPropertyDTO item : itemPropertyList){
			Long propertyId = item.getItemPropertyTmplId();
			List<ItemPropertyDTO> list = propertyMap.get(propertyId);
			if(list == null){
				List<ItemPropertyDTO> newList = new ArrayList<ItemPropertyDTO>();
				newList.add(item);
				propertyMap.put(propertyId, newList);
			}else{
				list.add(item);
			}
		}
		
		System.out.println("itemPropertySize : " + itemPropertyList.size());
		System.out.println("propertyMap: " + propertyMap);
		System.out.println("properyMap size : " + propertyMap.size());
		
		java.util.Iterator<Entry<Long, List<ItemPropertyDTO>>> it = propertyMap.entrySet().iterator();
		// 返回给前端的普通属性的列表
		List<ItemProperty> itemPropertyParamList = new ArrayList<ItemProperty>();
		while(it.hasNext()){
			Entry entry = it.next();
			Long propertyId = (Long)entry.getKey();
			List<ItemPropertyDTO> propertyList = (List<ItemPropertyDTO>)entry.getValue();
			List<String> vids = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			ItemProperty itemProperty = new ItemProperty();
			String name=null;
			if(!CollectionUtils.isEmpty(propertyList)){
				for(ItemPropertyDTO item : propertyList){
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
		
		//商品的销售属性的列表  按照sku_item_id分组（item_sku表的的主键）
		List<SkuPropertyDTO> skuPropertyList = itemDto.getSkuPropertyList();
		Map<Long,List<SkuPropertyDTO>> skuPropertyMap = new HashMap<Long,List<SkuPropertyDTO>>();
		
		//根据商品销售属性的id来分组  比如颜色 有多个取值  需要维护顺序 用LinkdHashMap 
		Map<Long,List<String>> skuPropertyIdMap = new LinkedHashMap<Long,List<String>>();
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
				ids.add(String.valueOf(item.getPropertyValueId()));
				List<String> values = new ArrayList<String>();
				values.add(item.getValue());
				skuPropertyIdMap.put(skuPropertyId,ids);
				skuPropertyValueMap.put(skuPropertyId, values);
			}else{
				//不包含的时候才写入 避免重复  一个item 有多个相同的属性  及其 属性值
				if(!idList.contains(String.valueOf(item.getPropertyValueId()))){
					idList.add(String.valueOf(item.getPropertyValueId()));
					valueList.add(item.getValue());
				}
			}
			if(skuPropertyNameMap.get(skuPropertyId) == null){
				skuPropertyNameMap.put(skuPropertyId, item.getName());
			}
		}
		
		//商品具体的规格
		List<ItemSkuDTO> itemSkuList =itemDto.getItemSkuDTOList();
		//返回给前端的sku属性的列表
		List<SkuPropertyItem> skus = new ArrayList<SkuPropertyItem>();
		System.out.println("itemSKuList : " +  itemSkuList);
		for(ItemSkuDTO itemSku: itemSkuList){
			Long originPrice = itemSku.getMarketPrice();
			Long price = itemSku.getPromotionPrice();
			System.out.println("market price :  " + itemSku.getMarketPrice());
			System.out.println("promotion price :  " + itemSku.getPromotionPrice());
			Long num = itemSku.getStockNum();
			String barCode = itemSku.getBarCode();
			//返回给前端的对象
			SkuPropertyItem sku = new SkuPropertyItem();
			sku.setSkuId(itemSku.getId());
			sku.setBarcode(barCode);
			sku.setPrice(String.valueOf(price));
			sku.setOriginPrice(String.valueOf(originPrice));
			sku.setNum(String.valueOf(num));
			//一种特定规格下有对应的属性列表 比如：  颜色： 红色，尺码： s  对应的一个规格下有2个属性
			List<SkuPropertyDTO> skuPropList = skuPropertyMap.get(itemSku.getId());
			List<PropParam> returnSkuProps = new ArrayList<PropParam>();
			Long skuPropertyTmplId = null;
			String skuPropertyTmplName = null;
			List<String> vids = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			if(!CollectionUtils.isEmpty(skuPropList)){
				for(SkuPropertyDTO prop: skuPropList){
					PropParam propParam = new PropParam();
					propParam.setPropId(String.valueOf(prop.getSkuPropertyTmplId()));
					propParam.setPropName(prop.getName());
					propParam.setValueId(String.valueOf(prop.getPropertyValueId()));//值的id 红色的id
					propParam.setValueName(prop.getValue());//具体的值 比如 红色
					//vids.add(String.valueOf(prop.getVid()));
					//values.add(prop.getVal());
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
		
		// 获取类目信息 需要现显示三级类目
		String cateName2=null,cateName=null,cateName3=null; 
		Long categoryId2 = null,categoryId =null, categoryId3=null;
		try {
			System.out.println("categoryId: " + categoryId);
			categoryId3 = itemDto.getCategoryId();//三级类目
			if(categoryId3 != null && categoryId3.intValue() > 0){
				ItemCategoryDTO category = this.itemCategoryManager
						.getItemCategory(categoryId3);
				cateName3 =  category.getCateName();
				Long parentId = category.getParentId();
				System.out.println("parentId: " + parentId);
				if(parentId != null && parentId.intValue() > 0){//二级类目
					category = this.itemCategoryManager.getItemCategory(parentId);
					parentId =null;
					if(category !=null){
						categoryId2 = category.getId();
						parentId =category.getParentId();
						cateName2 = category.getCateName();
					}
				}
				System.out.println("parentId: " + parentId);
				if(parentId != null && parentId.intValue() > 0){//一级类目
					category = this.itemCategoryManager.getItemCategory(parentId);
					if(category != null){
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
		if(categoryId!=null){
			categoryLevels.add(String.valueOf(categoryId));
			cateNameLevels +=  cateName + " - ";
		}
		if(categoryId2 != null){
			categoryLevels.add(String.valueOf(categoryId2));
			cateNameLevels  += cateName2 + " - ";
		}
		if(categoryId3 != null){
			categoryLevels.add(String.valueOf(categoryId3));
			cateNameLevels += cateName3;
		}
		itemParamDto.setCateName(cateNameLevels); //"服饰 - 童装 - T恤"
		itemParamDto.setParents(categoryLevels);// ["1","101","1101"]
			
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(itemParamDto);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(itemParamDto) + ")"; 
		}
	}
	
	/**
	 * 新加组合商品
	 * @param request
	 */
	//@RequestMapping(value="/item/composite/add.do",produces="application/json; charset=utf-8")
	//@ResponseBody
	private String addCompositeItem(HttpServletRequest request){
		long start=System.currentTimeMillis();
		
		String callback = request.getParameter("callback");
		
		String name;
		String subName;
		Long categoryId;
		String description;
		Long brandId;
		Integer minBought;
		Integer maxBought;
		String brief;
		Long price,originPrice;
		ItemDTO itemDto = new ItemDTO();
		Integer num;
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		itemDto.setSellerId(userId);
		
		// 处理参数参数中的复合对象部分
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表
		List<GalleryParam> gallerys = null;//解析出来的图片列表
		List<CompositeItem> compositeItems =null;//组合中的商品
		// 参数处理
		try {
			name = RequestUtils.getString(request, "name",true);
			System.out.println("name " + name);
			subName = RequestUtils.getString(request, "sub_name",false);
			categoryId = RequestUtils.getLong(request, "cate_id",true);
			brief = RequestUtils.getString(request, "brief",false);
			description = RequestUtils.getString(request, "description",true);
			brandId = RequestUtils.getLong(request, "brand_id",true);
			minBought = RequestUtils.getInt(request, "user_min_bought",false);
			maxBought = RequestUtils.getInt(request, "user_max_bought",false);
			price = RequestUtils.getLong(request, "price", true);
			originPrice = RequestUtils.getLong(request, "origin_price", true);
			num = RequestUtils.getInt(request,"num",true);
			
			//TODO
			Integer itemType = RequestUtils.getInt(request, "item_type",false);
			//默认是为实物商品
			if(itemType == null){
				itemType = ItemConstants.ItemType.PHYSICAL_GOODS.getType();
			}else{
				 if(itemType.intValue() != ItemConstants.ItemType.PHYSICAL_GOODS.getType() && 
						 itemType.intValue() != ItemConstants.ItemType.VIRUAL_GOODS.getType()){
					 itemType =  ItemConstants.ItemType.PHYSICAL_GOODS.getType();
				 }
			}
			Integer itemStatus = RequestUtils.getInt(request, "item_status",false);
			//TODO 默认是发布商品上架
			if(itemStatus == null){
				itemStatus = ItemConstants.ItemStatus.ON_SALE.getStatus();
			}else{
				if(itemStatus.intValue() < ItemConstants.ItemStatus.PENDING_AUDIT.getStatus()  
					||itemStatus.intValue() > ItemConstants.ItemStatus.FROZEN.getStatus()){
					itemStatus = ItemConstants.ItemStatus.ON_SALE.getStatus();
				}
			}
		
			// 公共部分属性
			itemDto.setItemName(name);//商品名称
			//itemDto.setItemBriefName(subName);//商品简称
			itemDto.setItemBrief(brief);//商品简介
			itemDto.setCategoryId(categoryId);//类目id
			itemDto.setItemBrandId(brandId);
			itemDto.setItemDesc(description);//商品图文详情
			itemDto.setSaleMinNum(minBought);
			itemDto.setSaleMaxNum(maxBought);
			//TODO 开始结束销售时间的处理
			//itemDto.setSaleBegin(now);
			itemDto.setItemType(itemType);
			itemDto.setItemStatus(itemStatus);
			
			String itemPropStr = RequestUtils.getString(request, "props",false);
			itemProps = this.parseItemProperty(itemPropStr);
			String galleryStr = RequestUtils.getString(request, "gallery",false);
			gallerys = this.parseGallery(galleryStr);
			String compositeItemsStr = RequestUtils.getString(request,"composite_items",true);
			compositeItems = this.parseCompositeItems(compositeItemsStr);
			
		} catch (ParamException e) {
			logger.error("parse error happen : " ,e);
			logger.error(e.getField() + " " + e.getMessage());
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		String mainImgUrl = null;//主图的处理首张图片作为主图
		//商品图片的处理
		int sort = 0; //sort用于控制图片的排列顺序 默认取上传时候的顺序
		List<ItemImageDTO> itemImageList = new ArrayList<ItemImageDTO>();
		if(!CollectionUtils.isEmpty(gallerys)){
			for(int i =0,len=gallerys.size();i<len;i++){
				GalleryParam gallery = gallerys.get(i);
				if(sort == 0){
					mainImgUrl = gallery.getImg();// 首张保存为主图
				}
				sort++;
				ItemImageDTO itemImageDto =new ItemImageDTO();
				itemImageDto.setImageUrl(gallery.getImg());
				itemImageDto.setSort(sort);
				itemImageDto.setImageName(itemDto.getItemName());
				itemImageDto.setSellerId(userId);
				itemImageDto.setPropertyValueId(Long.valueOf(gallery.getId()));
				itemImageList.add(itemImageDto);
			}
		}
		
		List<ItemPropertyDTO> itemPropertyList = new ArrayList<ItemPropertyDTO>();
		// 普通属性处理
		sort = 0;// 用于控制显示顺序  商品显示普通属性时候需要按照特定的顺序来显示
		if(!CollectionUtils.isEmpty(itemProps)){
			for (int i = 0, len = itemProps.size(); i < len; i++) {
				sort++;
				ItemProperty itemProp = itemProps.get(i);
				// 一个属性可能有多个值 比如： 适用年龄 属性 可能有多个值：
				List<String> valueList = itemProp.getValue();
				List<String> vidList = itemProp.getVid();
				if(!CollectionUtils.isEmpty(valueList)){
					for(int index=0,size=valueList.size();index<size;index++){
						ItemPropertyDTO itemPropertyDto = new ItemPropertyDTO();
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
		
		//组合商品列表的处理
		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
		List<CompositeItemDTO> skuList = new ArrayList<CompositeItemDTO>();
		if(!CollectionUtils.isEmpty(compositeItems)){
			for (int i = 0,len=compositeItems.size(); i < len; i++) {
				try {
				CompositeItem item = compositeItems.get(i);
				CompositeItemDTO compositeItemDTO = new CompositeItemDTO();
				
					Integer number = RequestUtils.parseInteger(item.getNum(),
							"num", true);
					compositeItemDTO.setNum(number);
					Long skuId = RequestUtils.parseLong(item.getSkuId(), "sku_id", true);
					compositeItemDTO.setSubSkuId(skuId);
					skuList.add(compositeItemDTO);
				} catch (ParamException e) {
					return ResponseUtils.getFailApiResponseStr(e);
				}
			}
		}
		
		// 暂时组合商品下只有一个组合  也就是只有一个sku
		//itemSkuDTO.setCompositeItemList(skuList);
		//itemSkuDTO.setIsComposite(true);//标记为商品
		
		List<ItemSkuDTO> itemSkuList =new ArrayList<ItemSkuDTO>();
		itemSkuList.add(itemSkuDTO);
		itemDto.setItemSkuDTOList(itemSkuList);
		
		
		itemDto.setIconUrl(mainImgUrl);//主图
		itemDto.setItemPropertyList(itemPropertyList);//普通属性的列表
		itemDto.setItemImageDTOList(itemImageList);//图片的列表
		
		try {
			Boolean result = this.itemManager.addItem(itemDto);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(true);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(true) + ")"; 
		}
		
	}
	
	/**
	 * 组合商品的修改
	 * @param request
	 * @return
	 */
	//@RequestMapping(value="/item/composite/update.do",produces="application/json; charset=utf-8")
	//@ResponseBody
	private String updateCompositeItem(HttpServletRequest request){
		long start=System.currentTimeMillis();
		String callback = request.getParameter("callback");
		Long itemId;
		String name;
		String subName;
		Long categoryId;
		String description;
		Long brandId;
		Integer minBought;
		Integer maxBought;
		String brief;
		Long price,originPrice,cornerIcon;
		Integer num;
		
		ItemDTO itemDto = new ItemDTO();
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		itemDto.setSellerId(userId);
		
		// 处理参数参数中的复合对象部分
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表
		List<GalleryParam> gallerys = null;//解析出来的图片列表
		List<CompositeItem> compositeItems =null;//组合中的商品
		// 参数处理
		try {
			itemId = RequestUtils.getLong(request, "id",true);
			name = RequestUtils.getString(request, "name",true);
			System.out.println("name " + name);
			subName = RequestUtils.getString(request, "sub_name",false);
			categoryId = RequestUtils.getLong(request, "cate_id", true);
			brief = RequestUtils.getString(request, "brief", false);
			description = RequestUtils.getString(request, "description",true);
			brandId = RequestUtils.getLong(request, "brand_id", true);
			minBought = RequestUtils.getInt(request, "user_min_bought", false);
			maxBought = RequestUtils.getInt(request, "user_max_bought",false);
			price = RequestUtils.getLong(request, "price", true);
			originPrice = RequestUtils.getLong(request, "origin_price", true);
			num = RequestUtils.getInt(request, "num", true);
			cornerIcon = RequestUtils.getLong(request, "num", false);
			
			//TODO
			Integer itemType = RequestUtils.getInt(request, "item_type",false);
			//默认是为实物商品
			if(itemType == null){
				itemType = ItemConstants.ItemType.PHYSICAL_GOODS.getType();
			}else{
				 if(itemType.intValue() != ItemConstants.ItemType.PHYSICAL_GOODS.getType() && 
						 itemType.intValue() != ItemConstants.ItemType.VIRUAL_GOODS.getType()){
					 itemType =  ItemConstants.ItemType.PHYSICAL_GOODS.getType();
				 }
			}
			Integer itemStatus = RequestUtils.getInt(request, "item_status",false);
			//TODO 默认是发布商品上架
			if(itemStatus == null){
				itemStatus = ItemConstants.ItemStatus.ON_SALE.getStatus();
			}else{
				if(itemStatus.intValue() < ItemConstants.ItemStatus.PENDING_AUDIT.getStatus()  
					||itemStatus.intValue() > ItemConstants.ItemStatus.FROZEN.getStatus()){
					itemStatus = ItemConstants.ItemStatus.ON_SALE.getStatus();
				}
			}
		
			//Date now =new Date();
			
			// 公共部分属性
			itemDto.setId(itemId);
			itemDto.setItemName(name);//商品名称
			//itemDto.setItemBriefName(subName);//商品简称
			itemDto.setItemBrief(brief);//商品简介
			itemDto.setCategoryId(categoryId);//类目id
			itemDto.setItemBrandId(brandId);
			itemDto.setItemDesc(description);//商品图文详情
			itemDto.setSaleMinNum(minBought);
			itemDto.setSaleMaxNum(maxBought);
			//TODO 开始结束销售时间的处理
			//itemDto.setSaleBegin(now);// 默认开始就上架了的就是开始时间
			itemDto.setItemType(itemType);
			itemDto.setItemStatus(itemStatus);
			itemDto.setCornerIconId(cornerIcon);
			
			String itemPropStr = RequestUtils.getString(request, "props",false);
			itemProps = this.parseItemProperty(itemPropStr);
			String galleryStr = RequestUtils.getString(request, "gallery",false);
			gallerys = this.parseGallery(galleryStr);
			String compositeItemsStr = RequestUtils.getString(request,"composite_items",true);
			compositeItems = this.parseCompositeItems(compositeItemsStr);
			
		} catch (ParamException e) {
			logger.error("parse error happen : " ,e);
			logger.error(e.getField() + " " + e.getMessage());
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		String mainImgUrl = null;//主图的处理首张图片作为主图
		//商品图片的处理
		int sort = 0; //sort用于控制图片的排列顺序 默认取上传时候的顺序
		List<ItemImageDTO> itemImageList = new ArrayList<ItemImageDTO>();
		if(!CollectionUtils.isEmpty(gallerys)){
			for(int i =0,len=gallerys.size();i<len;i++){
				GalleryParam gallery = gallerys.get(i);
				if(sort == 0){
					mainImgUrl = gallery.getImg();// 首张保存为主图
				}
				sort++;
				ItemImageDTO itemImageDto =new ItemImageDTO();
				itemImageDto.setImageUrl(gallery.getImg());
				itemImageDto.setSort(sort);
				itemImageDto.setImageName(itemDto.getItemName());
				itemImageDto.setSellerId(userId);
				itemImageDto.setPropertyValueId(Long.valueOf(gallery.getId()));
				itemImageList.add(itemImageDto);
			}
		}
		
		List<ItemPropertyDTO> itemPropertyList = new ArrayList<ItemPropertyDTO>();
		// 普通属性处理
		sort = 0;// 用于控制显示顺序  商品显示普通属性时候需要按照特定的顺序来显示
		if(!CollectionUtils.isEmpty(itemProps)){
			for (int i = 0, len = itemProps.size(); i < len; i++) {
				sort++;
				ItemProperty itemProp = itemProps.get(i);
				// 一个属性可能有多个值 比如： 适用年龄 属性 可能有多个值：
				List<String> valueList = itemProp.getValue();
				List<String> vidList = itemProp.getVid();
				if(!CollectionUtils.isEmpty(valueList)){
					for(int index=0,size=valueList.size();index<size;index++){
						ItemPropertyDTO itemPropertyDto = new ItemPropertyDTO();
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
		
		//组合商品列表的处理
		ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
		List<CompositeItemDTO> skuList = new ArrayList<CompositeItemDTO>();
		if(!CollectionUtils.isEmpty(compositeItems)){
			for (int i = 0,len=compositeItems.size(); i < len; i++) {
				try {
				CompositeItem item = compositeItems.get(i);
				CompositeItemDTO compositeItemDTO = new CompositeItemDTO();
				
					Integer number = RequestUtils.parseInteger(item.getNum(),
							"num", true);
					compositeItemDTO.setNum(number);
					Long skuId = RequestUtils.parseLong(item.getSkuId(), "sku_id", true);
					compositeItemDTO.setSubSkuId(skuId);
					skuList.add(compositeItemDTO);
				} catch (ParamException e) {
					return ResponseUtils.getFailApiResponseStr(e);
				}
			}
		}
		
		// 暂时组合商品下只有一个组合  也就是只有一个sku
		//itemSkuDTO.setCompositeItemList(skuList);
		//itemSkuDTO.setIsComposite(true);//标记为商品
		
		List<ItemSkuDTO> itemSkuList =new ArrayList<ItemSkuDTO>();
		itemSkuList.add(itemSkuDTO);
		itemDto.setItemSkuDTOList(itemSkuList);
		
		
		itemDto.setIconUrl(mainImgUrl);//主图
		itemDto.setItemPropertyList(itemPropertyList);//普通属性的列表
		itemDto.setItemImageDTOList(itemImageList);//图片的列表
		
		try {
			Boolean result = this.itemManager.updateItem(itemDto);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(true);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(true) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/composite/get.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String getCompositeItem(HttpServletRequest request){
		String callback = request.getParameter("callback");
		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		Long itemId =null;
		try {
			itemId = RequestUtils.getLong(request, "item_id");
		} catch (Exception e) {
			return ResponseUtils
					.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		
		ItemDTO itemDto = null;
		try {
			itemDto = this.itemManager.getItem(userId, itemId);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
	
		// 公共属性部分
		ItemParamDTO itemParamDto = new ItemParamDTO();
		itemParamDto.setId(String.valueOf(itemDto.getId()));//id
		itemParamDto.setCateId(String.valueOf(itemDto.getCategoryId()));//类目id
		itemParamDto.setName(itemDto.getItemName());//商品名称
		//itemParamDto.setSubName(itemDto.getItemBriefName());//商品简称
		itemParamDto.setBrief(itemDto.getItemBrief());
		itemParamDto.setBrandId(String.valueOf(itemDto.getItemBrandId())); // 品牌id
		itemParamDto.setUserMinBought(String.valueOf(itemDto.getSaleMinNum()));//最小购买量
		itemParamDto.setUserMaxBought(String.valueOf(itemDto.getSaleMaxNum())); //最大购买量限制
		itemParamDto.setDescription(itemDto.getItemDesc());
		
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
		
		//图片部分
		//List<ItemImageDTO> gallery = itemDto.getItemImageDTOList();
		ItemImageQTO itemImageQto = new ItemImageQTO();
		itemImageQto.setSellerId(userId);
		List<ItemImageDTO> itemImages = itemDto.getItemImageDTOList();
		List<GalleryParam> galleryList = new ArrayList<GalleryParam>();
		for(ItemImageDTO itemImage : itemImages){
			GalleryParam gallery = new GalleryParam();
			// 对应于具体的一个规格的图片
			gallery.setColor(itemImage.getImageName());
			gallery.setImg(itemImage.getImageUrl());
			gallery.setId(String.valueOf(itemImage.getPropertyValueId()));// 具体的值的id，比如： 蓝色对应于一个id
			galleryList.add(gallery);
		}
		itemParamDto.setGallery(galleryList);
		
		//普通属性的处理
		List<ItemPropertyDTO> itemPropertyList = itemDto.getItemPropertyList();
		//按照同一个属性模版中的id来分组 由于同一个属性可能有多个值比如 适用年龄：0－6个月, 6-12个月 ...
		Map<Long,List<ItemPropertyDTO>> propertyMap = new HashMap<Long,List<ItemPropertyDTO>>(); 
		for(ItemPropertyDTO item : itemPropertyList){
			Long propertyId = item.getItemPropertyTmplId();
			List<ItemPropertyDTO> list = propertyMap.get(propertyId);
			if(list == null){
				List<ItemPropertyDTO> newList = new ArrayList<ItemPropertyDTO>();
				newList.add(item);
				propertyMap.put(propertyId, newList);
			}else{
				list.add(item);
			}
		}
		
		
		java.util.Iterator<Entry<Long, List<ItemPropertyDTO>>> it = propertyMap.entrySet().iterator();
		// 返回给前端的普通属性的列表
		List<ItemProperty> itemPropertyParamList = new ArrayList<ItemProperty>();
		while(it.hasNext()){
			Entry entry = it.next();
			Long propertyId = (Long)entry.getKey();
			List<ItemPropertyDTO> propertyList = (List<ItemPropertyDTO>)entry.getValue();
			List<String> vids = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			ItemProperty itemProperty = new ItemProperty();
			String name=null;
			if(!CollectionUtils.isEmpty(propertyList)){
				for(ItemPropertyDTO item : propertyList){
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
				ids.add(String.valueOf(item.getPropertyValueId()));
				List<String> values = new ArrayList<String>();
				values.add(item.getValue());
				skuPropertyIdMap.put(skuPropertyId,ids);
				skuPropertyValueMap.put(skuPropertyId, values);
			}else{
				//不包含的时候才写入 避免重复  一个item 有多个相同的属性  及其 属性值
				if(!idList.contains(String.valueOf(item.getPropertyValueId()))){
					idList.add(String.valueOf(item.getPropertyValueId()));
					valueList.add(item.getValue());
				}
			}
			if(skuPropertyNameMap.get(skuPropertyId) == null){
				skuPropertyNameMap.put(skuPropertyId, item.getName());
			}
		}
		
		//商品具体的规格
		List<ItemSkuDTO> itemSkuList =itemDto.getItemSkuDTOList();
		//返回给前端的sku属性的列表
		List<SkuPropertyItem> skus = new ArrayList<SkuPropertyItem>();
		System.out.println("itemSKuList : " +  itemSkuList);
		for(ItemSkuDTO item: itemSkuList){
			Long originPrice = item.getMarketPrice();
			Long price = item.getPromotionPrice();
			System.out.println("market price :  " + item.getMarketPrice());
			System.out.println("promotion price :  " + item.getPromotionPrice());
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
					propParam.setPropName(prop.getName());
					propParam.setValueId(String.valueOf(prop.getPropertyValueId()));//值的id 红色的id
					propParam.setValueName(prop.getValue());//具体的值 比如 红色
					//vids.add(String.valueOf(prop.getVid()));
					//values.add(prop.getVal());
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
		
		// 获取类目信息 需要现显示三级类目
		String cateName2=null,cateName=null,cateName3=null; 
		Long categoryId2 = null,categoryId =null, categoryId3=null;
		try {
			categoryId3 = itemDto.getCategoryId();//三级类目
			if(categoryId3 != null && categoryId3.intValue() >0){
				ItemCategoryDTO category = this.itemCategoryManager
						.getItemCategory(categoryId3);
				cateName3 =  category.getCateName();
				Long parentId = category.getParentId();
				if(parentId != null && parentId.intValue() > 0){//二级类目
					category = this.itemCategoryManager.getItemCategory(parentId);
					parentId =null;
					if(category !=null){
						categoryId2 = category.getId();
						parentId =category.getParentId();
						cateName2 = category.getCateName();
					}
				}
				if(parentId != null && parentId.intValue() > 0){//一级类目
					category = this.itemCategoryManager.getItemCategory(parentId);
					if(category != null){
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
		if(categoryId!=null){
			categoryLevels.add(String.valueOf(categoryId));
			cateNameLevels +=  cateName + " - ";
		}
		if(categoryId2 != null){
			categoryLevels.add(String.valueOf(categoryId2));
			cateNameLevels  += cateName2 + " - ";
		}
		if(categoryId3 != null){
			categoryLevels.add(String.valueOf(categoryId3));
			cateNameLevels += cateName3;
		}
		itemParamDto.setCateName(cateNameLevels); //"服饰 - 童装 - T恤"
		itemParamDto.setParents(categoryLevels);// ["1","101","1101"]
			
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(itemParamDto);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(itemParamDto) + ")"; 
		}
		
	}

	@RequestMapping(value="/item/export.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String exportItemList(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		Long userId = SessionManager.getLoginUserId(request.getSession());
		ItemExportQTO itemExportQTO = new ItemExportQTO();
		try {
			String key = RequestUtils.getString(request, "key", false);
			Integer itemStatus = RequestUtils.getInt(request, "item_status",false);
			Long brandId = RequestUtils.getLong(request, "brand_key", false);
			String barCode = RequestUtils.getString(request, "bar_code", false);
			Long categoryId = RequestUtils.getLong(request, "category_id", false);

			Date createTimeStart = null, createTimeEnd = null;
			if(!StringUtils.isBlank(request.getParameter("start_time"))){
				createTimeStart = RequestUtils.getFormatDate(request, "start_time",
						ACCEPT_DATE_FORMAT);
			}
			if(!StringUtils.isBlank(request.getParameter("end_time"))){
				createTimeEnd = RequestUtils.getFormatDate(request, "end_time",
						ACCEPT_DATE_FORMAT);
			}
			itemExportQTO.setSellerId(userId);
			itemExportQTO.setKey(key);
			itemExportQTO.setItemStatus(itemStatus);
			itemExportQTO.setBrandId(brandId);
			itemExportQTO.setBarCode(barCode);
			itemExportQTO.setCategoryId(categoryId);
			itemExportQTO.setStartTime(createTimeStart);
			itemExportQTO.setEndTime(createTimeEnd);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		} catch(Exception e){
			logger.error("parse param error : " ,e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}

		com.mockuai.dts.common.api.action.Response<Boolean> response = itemExportClient.exportItems(itemExportQTO);
		boolean result = false;
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
			result = response.getModule();
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
		}
	}
	
	
	public static void main(String args[]){
		// 获取的属性列表
//		String s ="[{\"id\":\"2712\",\"cid\":\"256\",\"prop_id\":\"3\",\"name\":\"\u989c\u8272\",\"must\":\"1\",\"multi\":\"1\",\"user_defined\":\"0\",\"is_sku\":\"1\",\"prop_values\":{\"vid\":[\"818\"],\"value\":[\"\u901a\u7528\"]}},{\"id\":\"2155\",\"cid\":\"256\",\"prop_id\":\"88\",\"name\":\"\u9002\u7528\u5e74\u9f84\",\"must\":\"1\",\"multi\":\"1\",\"user_defined\":\"0\",\"is_sku\":\"0\",\"prop_values\":{\"vid\":[\"4717\",\"4718\",\"4719\",\"4720\",\"4721\",\"4722\"],\"value\":[\"\u5b55\u671f\",\"0-6\u4e2a\u6708\",\"6-12\u4e2a\u6708\",\"1-3\u5c81\",\"3-6\u5c81\",\"6-14\u5c81\"]}},{\"id\":\"2673\",\"cid\":\"256\",\"prop_id\":\"5008\",\"name\":\"\u5305\u88c5\u65b9\u5f0f\",\"must\":\"1\",\"multi\":\"0\",\"user_defined\":\"0\",\"is_sku\":\"0\",\"prop_values\":{\"vid\":[\"4799\",\"4800\",\"4801\",\"4802\",\"4803\",\"4804\",\"4805\"],\"value\":[\"\u7f50\u88c5\",\"\u76d2\u88c5\",\"\u888b\u88c5\",\"\u7bb1\u88c5\",\"\u7ec4\u5408\u88c5\",\"\u5c0f\u6837\",\"\u5176\u4ed6\"]}},{\"id\":\"2675\",\"cid\":\"256\",\"prop_id\":\"5007\",\"name\":\"\u9002\u7528\u9636\u6bb5\",\"must\":\"1\",\"multi\":\"1\",\"user_defined\":\"0\",\"is_sku\":\"1\",\"prop_values\":{\"vid\":[\"4777\",\"4778\",\"4779\",\"4780\",\"4781\",\"4782\",\"4783\",\"4784\",\"4785\"],\"value\":[\"Pre\",\"\u4e00\u6bb5\",\"\u4e8c\u6bb5\",\"\u4e09\u6bb5\",\"\u56db\u6bb5\",\"\u4e94\u6bb5\",\"\u6210\u957f1+\",\"\u6210\u957f2+\",\"\u5168\u9636\u6bb5\"]}},{\"id\":\"2694\",\"cid\":\"256\",\"prop_id\":\"5009\",\"name\":\"\u91cd\u91cf(g)\",\"must\":\"1\",\"multi\":\"0\",\"user_defined\":\"1\",\"is_sku\":\"0\",\"prop_values\":{\"vid\":[\"0\"],\"value\":[\"\"]}},{\"id\":\"2714\",\"cid\":\"256\",\"prop_id\":\"5006\",\"name\":\"\u4ea7\u5730\",\"must\":\"0\",\"multi\":\"0\",\"user_defined\":\"0\",\"is_sku\":\"0\",\"prop_values\":{\"vid\":[\"4767\",\"4768\",\"4769\",\"4771\",\"4773\",\"4776\",\"4786\",\"4787\",\"4788\",\"4789\",\"4790\",\"4793\",\"4794\",\"4795\",\"4797\",\"4798\"],\"value\":[\"\u4e2d\u56fd\",\"\u65e5\u672c\",\"\u97e9\u56fd\",\"\u4e39\u9ea6\",\"\u7f8e\u56fd\",\"\u5176\u4ed6\",\"\u8377\u5170\",\"\u5fb7\u56fd\",\"\u65b0\u897f\u5170\",\"\u7231\u5c14\u5170\",\"\u65b0\u52a0\u5761\",\"\u897f\u73ed\u7259\",\"\u6cd5\u56fd\",\"\u745e\u58eb\",\"\u6fb3\u5927\u5229\u4e9a\",\"\u82f1\u56fd\"]}},{\"id\":\"2715\",\"cid\":\"256\",\"prop_id\":\"5010\",\"name\":\"\u53e3\u5473\",\"must\":\"0\",\"multi\":\"0\",\"user_defined\":\"1\",\"is_sku\":\"0\",\"prop_values\":{\"vid\":[\"0\"],\"value\":[\"\"]}},{\"id\":\"2799\",\"cid\":\"256\",\"prop_id\":\"2\",\"name\":\"\u8d27\u53f7\",\"must\":\"0\",\"multi\":\"0\",\"user_defined\":\"1\",\"is_sku\":\"0\",\"prop_values\":{\"vid\":[\"0\"],\"value\":[\"\"]}}]";
//		s = "[{'id':'2712','cid':'256','prop_id':'3','name':'\u989c\u8272','must':'1','multi':'1','user_defined':'0','is_sku':'1','prop_values':{'vid':['818'],'value':['\u901a\u7528']}},{'id':'2155','cid':'256','prop_id':'88','name':'\u9002\u7528\u5e74\u9f84','must':'1','multi':'1','user_defined':'0','is_sku':'0','prop_values':{'vid':['4717','4718','4719','4720','4721','4722'],'value':['\u5b55\u671f','0-6\u4e2a\u6708','6-12\u4e2a\u6708','1-3\u5c81','3-6\u5c81','6-14\u5c81']}},{'id':'2673','cid':'256','prop_id':'5008','name':'\u5305\u88c5\u65b9\u5f0f','must':'1','multi':'0','user_defined':'0','is_sku':'0','prop_values':{'vid':['4799','4800','4801','4802','4803','4804','4805'],'value':['\u7f50\u88c5','\u76d2\u88c5','\u888b\u88c5','\u7bb1\u88c5','\u7ec4\u5408\u88c5','\u5c0f\u6837','\u5176\u4ed6']}},{'id':'2675','cid':'256','prop_id':'5007','name':'\u9002\u7528\u9636\u6bb5','must':'1','multi':'1','user_defined':'0','is_sku':'1','prop_values':{'vid':['4777','4778','4779','4780','4781','4782','4783','4784','4785'],'value':['Pre','\u4e00\u6bb5','\u4e8c\u6bb5','\u4e09\u6bb5','\u56db\u6bb5','\u4e94\u6bb5','\u6210\u957f1+','\u6210\u957f2+','\u5168\u9636\u6bb5']}},{'id':'2694','cid':'256','prop_id':'5009','name':'\u91cd\u91cf(g)','must':'1','multi':'0','user_defined':'1','is_sku':'0','prop_values':{'vid':['0'],'value':['']}},{'id':'2714','cid':'256','prop_id':'5006','name':'\u4ea7\u5730','must':'0','multi':'0','user_defined':'0','is_sku':'0','prop_values':{'vid':['4767','4768','4769','4771','4773','4776','4786','4787','4788','4789','4790','4793','4794','4795','4797','4798'],'value':['\u4e2d\u56fd','\u65e5\u672c','\u97e9\u56fd','\u4e39\u9ea6','\u7f8e\u56fd','\u5176\u4ed6','\u8377\u5170','\u5fb7\u56fd','\u65b0\u897f\u5170','\u7231\u5c14\u5170','\u65b0\u52a0\u5761','\u897f\u73ed\u7259','\u6cd5\u56fd','\u745e\u58eb','\u6fb3\u5927\u5229\u4e9a','\u82f1\u56fd']}},{'id':'2715','cid':'256','prop_id':'5010','name':'\u53e3\u5473','must':'0','multi':'0','user_defined':'1','is_sku':'0','prop_values':{'vid':['0'],'value':['']}},{'id':'2799','cid':'256','prop_id':'2','name':'\u8d27\u53f7','must':'0','multi':'0','user_defined':'1','is_sku':'0','prop_values':{'vid':['0'],'value':['']}}]";
//		long start = System.currentTimeMillis();
//		//商品新加
//		String str ="{\"name\":\"商品名称\",\"sub_name\":\"商品名称\",\"cate_id\":\"284\",\"brief\":\"商品简述\",\"description\":\"<p>cehis:nbsp;</p>\",\"gallery\":[{\"color\":\"红色\",\"img\":\"http://img.ve.cn/public/attachment/201504/22/16/20150422164210100.png\",\"id\":\"15\"},{\"color\":\"黑色teset\",\"img\":\"http://img.ve.cn/public/attachment/201504/22/16/20150422164212885.png\",\"id\":\"16\"}],\"props\":[{\"vid\":[\"5197\"],\"id\":\"5058\",\"name\":\"袖长\",\"value\":[\"长袖\"]},{\"vid\":[\"5148\"],\"id\":\"5054\",\"name\":\"领型\",\"value\":[\"翻领\"]},{\"vid\":[\"61\"],\"id\":\"12\",\"name\":\"衣长\",\"value\":[\"中款\"]},{\"vid\":[\"4909\"],\"id\":\"5024\",\"name\":\"图案\",\"value\":[\"圆点\"]},{\"vid\":[\"5022\"],\"id\":\"5040\",\"name\":\"厚薄\",\"value\":[\"厚款\"]},{\"vid\":[\"5107\",\"5112\"],\"id\":\"5051\",\"name\":\"面料\",\"value\":[\"棉混纺布\",\"亚麻\"]},{\"vid\":[\"4954\"],\"id\":\"5029\",\"name\":\"风格\",\"value\":[\"其他\"]},{\"vid\":[\"124\"],\"id\":\"16\",\"name\":\"适用季节\",\"value\":[\"夏季\"]},{\"vid\":[\"1098\"],\"id\":\"83\",\"name\":\"适用性别\",\"value\":[\"男\"]},{\"vid\":[\"4717\",\"4718\"],\"id\":\"88\",\"name\":\"适用年龄\",\"value\":[\"孕期\",\"0-6个月\"]}],\"sku_props\":[{\"vid\":[\"15\",\"16\"],\"id\":\"3\",\"name\":\"颜色\",\"value\":[\"红色\",\"黑色teset\"]},{\"vid\":[\"28\",\"29\",\"203\"],\"id\":\"4\",\"name\":\"尺码\",\"value\":[\"53test\",\"48test\",\"80test\"]}],\"skus\":[{\"prop\":[{\"prop_id\":\"3\",\"prop_name\":\"颜色\",\"value_id\":\"15\",\"value_name\":\"红色\"},{\"prop_id\":\"4\",\"prop_name\":\"尺码\",\"value_id\":\"28\",\"value_name\":\"53test\"}],\"origin_price\":\"11.00\",\"price\":\"9.00\",\"num\":\"11\",\"barcode\":\"2121\"},{\"prop\":[{\"prop_id\":\"3\",\"prop_name\":\"颜色\",\"value_id\":\"15\",\"value_name\":\"红色\"},{\"prop_id\":\"4\",\"prop_name\":\"尺码\",\"value_id\":\"29\",\"value_name\":\"48test\"}],\"origin_price\":\"11.00\",\"price\":\"9.00\",\"num\":\"11\",\"barcode\":\"2121\"},{\"prop\":[{\"prop_id\":\"3\",\"prop_name\":\"颜色\",\"value_id\":\"15\",\"value_name\":\"红色\"},{\"prop_id\":\"4\",\"prop_name\":\"尺码\",\"value_id\":\"203\",\"value_name\":\"80test\"}],\"origin_price\":\"11.00\",\"price\":\"9.00\",\"num\":\"11\",\"barcode\":\"2121\"},{\"prop\":[{\"prop_id\":\"3\",\"prop_name\":\"颜色\",\"value_id\":\"16\",\"value_name\":\"黑色teset\"},{\"prop_id\":\"4\",\"prop_name\":\"尺码\",\"value_id\":\"28\",\"value_name\":\"53test\"}],\"origin_price\":\"11.00\",\"price\":\"9.00\",\"num\":\"11\",\"barcode\":\"2121\"},{\"prop\":[{\"prop_id\":\"3\",\"prop_name\":\"颜色\",\"value_id\":\"16\",\"value_name\":\"黑色teset\"},{\"prop_id\":\"4\",\"prop_name\":\"尺码\",\"value_id\":\"29\",\"value_name\":\"48test\"}],\"origin_price\":\"11.00\",\"price\":\"9.00\",\"num\":\"11\",\"barcode\":\"2121\"},{\"prop\":[{\"prop_id\":\"3\",\"prop_name\":\"颜色\",\"value_id\":\"16\",\"value_name\":\"黑色teset\"},{\"prop_id\":\"4\",\"prop_name\":\"尺码\",\"value_id\":\"203\",\"value_name\":\"80test\"}],\"origin_price\":\"11.00\",\"price\":\"9.00\",\"num\":\"11\",\"barcode\":\"2121\"}],\"brand_id\":\"4\",\"user_min_bought\":\"1\",\"user_max_bought\":\"11\",\"id\":\"52603\",\"draftId\":\"\"}";
//		ItemParamDTO item = gson.fromJson(str, ItemParamDTO.class);
//		System.out.println("cost : " + (System.currentTimeMillis() - start));
//		System.out.println(item.getBrandId());
		
		// skus 的测试
		System.out.println("解析skus");
		String src = "[{\"prop\": [{\"prop_id\": \"3\",\"prop_name\": \"颜色\",\"value_id\": \"818\",\"value_name\": \"通用\"},{\"prop_id\": \"5007\",\"prop_name\": \"适用阶段\",\"value_id\": \"4781\",\"value_name\": \"四段\"}],\"origin_price\": \"11.00\",\"price\": \"10.00\",\"num\": \"11\",\"barcode\": \"111111\"},{\"prop\": [{\"prop_id\": \"3\",\"prop_name\": \"颜色\",\"value_id\": \"818\",\"value_name\": \"通用\"},{\"prop_id\": \"5007\",\"prop_name\": \"适用阶段\",\"value_id\": \"4780\",\"value_name\": \"三段\"}],\"origin_price\": \"11.00\",\"price\": \"10.00\",\"num\": \"11\",\"barcode\": \"111111\"},{\"prop\": [{\"prop_id\": \"3\",\"prop_name\": \"颜色\",\"value_id\": \"818\",\"value_name\": \"通用\"},{\"prop_id\": \"5007\",\"prop_name\": \"适用阶段\",\"value_id\": \"4778\",\"value_name\": \"一段test\"}],\"origin_price\": \"11.00\",\"price\": \"10.00\",\"num\": \"11\",\"barcode\": \"111111\"},{\"prop\": [{\"prop_id\": \"3\",\"prop_name\": \"颜色\",\"value_id\": \"818\",\"value_name\": \"通用\"},{\"prop_id\": \"5007\",\"prop_name\": \"适用阶段\",\"value_id\": \"4777\",\"value_name\": \"Pretest\" }],\"origin_price\": \"11.00\",\"price\": \"10.00\",\"num\": \"11\",\"barcode\": \"111111\"}]";
		List<SkuPropertyItem> skuProps = null;//解析出来的普通属性列表
		List<ItemProperty> itemProps = null;//解析出来的sku属性列表
		List<GalleryParam> gallerys = null;//解析出来的图片列表
		//skus sku属性部分
		//List<SkuPropertyItem> skuProps = gson.fromJson(skus, SkuPropertyItem.class);
		Type type;
		if(!StringUtils.isBlank(src)){
			type = new com.google.gson.reflect.TypeToken<List<SkuPropertyItem>>() {
			}.getType();
			skuProps = (List<SkuPropertyItem>) RequestUtils.json2List(src.trim(),
					type);
		}
		for(SkuPropertyItem sku : skuProps){
			System.out.println(sku.getBarcode());
			System.out.println(sku.getOriginPrice());
		}
			
		System.out.println("*****************************");
		//,{\"vid\": [\"4799\"],\"id\": \"5008\",\"name\": \"包装方式\",\"value\": [\"罐装\"]},{\"vid\": [0],\"id\": \"5009\",\"name\": \"重量(g)\",\"value\": [\"111\"]},{\"vid\": [\"4769\"],\"id\": \"5006\",\"name\": \"产地\",\"value\": [\"韩国\"]},{\"vid\": [0],\"id\": \"5010\",\"name\": \"口味\",\"value\": [\"11\"]},{\"vid\": [0],\"id\": \"2\",\"name\": \"货号\",\"value\": [\"11\"]}],\"sku_props\": [{\"vid\": [\"818\"],\"id\": \"3\",\"name\": \"颜色\",\"value\": [\"通用\"]},{\"vid\": [\"4777\",\"4778\",\"4780\",\"4781\"],\"id\": \"5007\",\"name\": \"适用阶段\",\"value\": [\"Pretest\",\"一段test\",\"三段\",\"四段\"]}
		System.out.println("解析props");
		String propsStr = "[{\"vid\": [\"4717\",\"4718\",\"4719\",\"4720\"],\"id\": \"88\",\"name\": \"适用年龄\",\"value\": [\"孕期\",\"0-6个月\",\"6-12个月\",\"1-3岁\"]},{\"vid\": [\"4799\"],\"id\": \"5008\",\"name\": \"包装方式\",\"value\": [\"罐装\"]}]";			
			
		type = new com.google.gson.reflect.TypeToken<List<ItemProperty>>() {
		}.getType();
		itemProps = (List<ItemProperty>) RequestUtils.json2List(propsStr.trim(),
				type);
			
		System.out.println(itemProps.size());
			
		System.out.println("*****************************");
		System.out.println("解析gallery");
		String gallery =  "[{\"color\": \"通用\",\"img\": \"http://img.ve.cn/public/attachment/201504/22/20/20150422202348424.png\",\"id\": \"818\"},{\"color\": \"通用\",\"img\": \"http://img.ve.cn/public/attachment/201504/22/20/20150422202348424.png\",\"id\": \"818\"}]";
		if(!StringUtils.isBlank(gallery)){
			type = new com.google.gson.reflect.TypeToken<List<GalleryParam>>() {
			}.getType();
			gallerys = (List<GalleryParam>) RequestUtils.json2List(gallery.trim(),
					type);
		}
		System.out.println(gallerys.size());
		
	}
	
	/**
	 * 解析sku属性的字符串  前端传递的是数组的字符串
	 * @param src
	 * @return
	 */
	private List<SkuPropertyItem> parseSkuProperty(String src)throws ParamException{
		Type type;
		if(!StringUtils.isBlank(src)){
			try {
				type = new com.google.gson.reflect.TypeToken<List<SkuPropertyItem>>() {
				}.getType();
				return (List<SkuPropertyItem>) RequestUtils.json2List(
						src.trim(), type);
			} catch (Exception e) {
				throw new ParamException("skus","参数格式错误");
			}
		}
		return null;
	}

	/**
	 * 解析普通属性参数列表 前端传递的是数组的字符串 
	 * @param src
	 * @return
	 * @throws ParamException
	 */
	private List<ItemProperty> parseItemProperty(String src )throws ParamException{
		Type type;
		if(!StringUtils.isBlank(src)){
			try {
				type = new com.google.gson.reflect.TypeToken<List<ItemProperty>>() {
				}.getType();
				return (List<ItemProperty>) RequestUtils.json2List(src.trim(),
						type);
			} catch (Exception e) {
				throw new ParamException("props","参数格式错误");
			}
		}
		return null;
	}
	
	/**
	 * 解析图片参数 前端传递的是数组的字符串 
	 * @param src
	 * @return
	 * @throws ParamException
	 */
	private List<GalleryParam> parseGallery(String src)throws ParamException{
		Type type;
		if(!StringUtils.isBlank(src)){
			try {
				type = new com.google.gson.reflect.TypeToken<List<GalleryParam>>() {
				}.getType();
				return (List<GalleryParam>) RequestUtils.json2List(src.trim(),
						type);
			} catch (Exception e) {
				throw new ParamException("gallery","参数格式错误");
			}
		}
		return null;
	}
	
	/**
	 * 解析出组合商品中的商品列表
	 * @param src
	 * @return
	 * @throws ParamException
	 */
	private List<CompositeItem> parseCompositeItems(String src)throws ParamException{
		Type type;
		if(!StringUtils.isBlank(src)){
			try {
				type = new com.google.gson.reflect.TypeToken<List<CompositeItem>>() {
				}.getType();
				return (List<CompositeItem>) RequestUtils.json2List(src.trim(),
						type);
			} catch (Exception e) {
				throw new ParamException("compositeItems","参数格式错误");
			}
		}
		return null;
	}
	
	/**
	 * 如果是根据条码查询商品 需要先查找到sku 在找到对应的id
	 * @param barCode
	 * @param sellerId
	 * @return
	 * @throws ServiceException
	 */
	private List<Long> queryItemIdByBarCode(String barCode,Long sellerId)throws ServiceException{
		ItemSkuQTO itemSkuQTO =new ItemSkuQTO();
		itemSkuQTO.setBarCode(barCode);
		itemSkuQTO.setSellerId(sellerId);
		List<ItemSkuDTO> list = this.itemSkuManager.queryItemSku(itemSkuQTO);
		if(CollectionUtils.isEmpty(list))
			return null;
		//
		Set<Long> itemIdSet =new HashSet<Long>();
		for(ItemSkuDTO item : list){
			if(item.getItemId() != null){
				itemIdSet.add(item.getItemId());
			}
		}
		List<Long> result = new ArrayList<Long>();
		result.addAll(itemIdSet);
		return result;
	}
	
}

