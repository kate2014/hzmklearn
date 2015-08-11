//package test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.util.CollectionUtils;
//
//import com.mockuai.itemcenter.client.ItemClient;
//import com.mockuai.itemcenter.common.api.Response;
//import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
//import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
//import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
//import com.mockuai.sellercenter.web.dto.ItemParamDTO;
//import com.mockuai.sellercenter.web.dto.ItemParamDTO.GalleryParam;
//import com.mockuai.sellercenter.web.dto.ItemParamDTO.ItemProperty;
//import com.mockuai.sellercenter.web.dto.ItemParamDTO.PropParam;
//import com.mockuai.sellercenter.web.dto.ItemParamDTO.SkuPropertyItem;
//import com.mockuai.sellercenter.web.dto.ItemParamDTO.SkuPropertyParam;
//import com.mockuai.sellercenter.web.util.ResponseUtils;
//
//public class GetItemTest2 {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println("开始测试");
//
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"spring-dubbo-consumer.xml");
//		long start = System.currentTimeMillis();
//		ItemClient client = (ItemClient) ctx.getBean("itemClient");
//		Response<ItemDTO> response = client.getItem(82L, 1L, true);
//
//		ItemDTO itemDto = response.getModule();
//		Long userId = 1L;
//
//		// 公共属性部分
//		ItemParamDTO itemParamDto = new ItemParamDTO();
//		itemParamDto.setId(String.valueOf(itemDto.getId()));// id
//		itemParamDto.setCateId(String.valueOf(itemDto.getCategoryId()));// 类目id
//		itemParamDto.setName(itemDto.getItemName());// 商品名称
//		itemParamDto.setSubName(itemDto.getItemBriefName());// 商品简称
//		itemParamDto.setBrandId(String.valueOf(itemDto.getItemBrandId())); // 品牌id
//		itemParamDto.setUserMinBought(String.valueOf(itemDto.getSaleMinNum()));// 最小购买量
//		itemParamDto.setUserMaxBought(String.valueOf(itemDto.getSaleMaxNum())); // 最大购买量限制
//		itemParamDto.setDescription(itemDto.getBriefIntroduction());
//
//		List<SkuPropertyDTO> skuPropertys = itemDto.getSkuPropertyList();
//
//		// 图片部分
//		// List<ItemImageDTO> gallery = itemDto.getItemImageDTOList();
//		ItemImageQTO itemImageQto = new ItemImageQTO();
//		itemImageQto.setSupplierId(userId);
//		List<ItemImageDTO> itemImages = itemDto.getItemImageDTOList();
//		List<GalleryParam> galleryList = new ArrayList<GalleryParam>();
//		for (ItemImageDTO itemImage : itemImages) {
//			GalleryParam gallery = new GalleryParam();
//			// 对应于具体的一个规格的图片
//			gallery.setColor(itemImage.getImageName());
//			gallery.setImg(itemImage.getImgUrl());
//			gallery.setId(String.valueOf(itemImage.getVid()));// 具体的值的id，比如：
//																// 蓝色对应于一个id
//			galleryList.add(gallery);
//		}
//
//		itemParamDto.setGallery(galleryList);
//
//		// 普通属性的处理
//		List<ItemPropertyDTO> itemPropertyList = itemDto.getItemPropertyList();
//		// 按照同一个属性模版中的id来分组 由于同一个属性可能有多个值比如 适用年龄：0－6个月, 6-12个月 ...
//		Map<Long, List<ItemPropertyDTO>> propertyMap = new HashMap<Long, List<ItemPropertyDTO>>();
//		for (ItemPropertyDTO item : itemPropertyList) {
//			Long propertyId = item.getItemPropertyTmplId();
//			List<ItemPropertyDTO> list = propertyMap.get(propertyId);
//			if (list == null) {
//				List<ItemPropertyDTO> newList = new ArrayList<ItemPropertyDTO>();
//				newList.add(item);
//				propertyMap.put(propertyId, newList);
//			} else {
//				list.add(item);
//			}
//		}
//
//		java.util.Iterator<Entry<Long, List<ItemPropertyDTO>>> it = propertyMap
//				.entrySet().iterator();
//		// 返回给前端的普通属性的列表
//		List<ItemProperty> itemPropertyParamList = new ArrayList<ItemProperty>();
//		while (it.hasNext()) {
//			Entry entry = it.next();
//			Long propertyId = (Long) entry.getKey();
//			List<ItemPropertyDTO> propertyList = it.next().getValue();
//			List<String> vids = new ArrayList<String>();
//			List<String> values = new ArrayList<String>();
//			ItemProperty itemProperty = new ItemProperty();
//			String name = null;
//			if (!CollectionUtils.isEmpty(propertyList)) {
//				for (ItemPropertyDTO item : propertyList) {
//					vids.add(String.valueOf(item.getVid()));
//					values.add(item.getVal());
//					name = item.getKeyName();
//				}
//			}
//			itemProperty.setName(name);
//			itemProperty.setVid(vids);
//			itemProperty.setValue(values);
//			itemProperty.setId(String.valueOf(propertyId));
//			itemPropertyParamList.add(itemProperty);
//		}
//
//		// 商品的销售属性的列表 按照sku_item_id分组（item_sku表的的主键）
//		List<SkuPropertyDTO> skuPropertyList = itemDto.getSkuPropertyList();
//		Map<Long, List<SkuPropertyDTO>> skuPropertyMap = new HashMap<Long, List<SkuPropertyDTO>>();
//		// 根据商品销售属性的id来分组 比如颜色 有多个取值
//		Map<Long, List<String>> skuPropertyIdMap = new HashMap<Long, List<String>>();
//		Map<Long, List<String>> skuPropertyValueMap = new HashMap<Long, List<String>>();
//		Map<Long, String> skuPropertyNameMap = new HashMap<Long, String>();
//
//		for (SkuPropertyDTO item : skuPropertyList) {
//			Long skuItemId = item.getSkuId();
//			List<SkuPropertyDTO> list = skuPropertyMap.get(skuItemId);
//			if (list == null) {
//				List<SkuPropertyDTO> newList = new ArrayList<SkuPropertyDTO>();
//				newList.add(item);
//				skuPropertyMap.put(skuItemId, newList);
//			} else {
//				list.add(item);
//			}
//			Long skuPropertyId = item.getSkuPropertyTmplId();
//			List<String> idList = skuPropertyIdMap.get(skuPropertyId);
//			List<String> valueList = skuPropertyValueMap.get(skuPropertyId);
//			if (idList == null) {
//				List<String> ids = new ArrayList<String>();
//				ids.add(String.valueOf(item.getVid()));
//				List<String> values = new ArrayList<String>();
//				values.add(item.getVal());
//				skuPropertyIdMap.put(skuPropertyId, ids);
//				skuPropertyValueMap.put(skuPropertyId, values);
//			} else {
//				idList.add(String.valueOf(item.getVid()));
//				valueList.add(item.getVal());
//			}
//			if (skuPropertyNameMap.get(skuPropertyId) == null) {
//				skuPropertyNameMap.put(skuPropertyId, item.getKeyName());
//			}
//		}
//
//		// 商品具体的规格
//		List<ItemSkuDTO> itemSkuList = itemDto.getItemSkuDTOList();
//		// 返回给前端的sku属性的列表
//		List<SkuPropertyItem> skus = new ArrayList<SkuPropertyItem>();
//		for (ItemSkuDTO item : itemSkuList) {
//			Long originPrice = item.getMarketPrice();
//			Long price = item.getPromotionPrice();
//			Long num = item.getStockNum();
//			String barCode = item.getBarCode();
//			// 返回给前端的对象
//			SkuPropertyItem sku = new SkuPropertyItem();
//			sku.setBarcode(barCode);
//			sku.setPrice(String.valueOf(price));
//			sku.setOriginPrice(String.valueOf(originPrice));
//			sku.setNum(String.valueOf(num));
//			// 一种特定规格下有对应的属性列表 比如： 颜色： 红色，尺码： s 对应的一个规格下有2个属性
//			List<SkuPropertyDTO> skuPropList = skuPropertyMap.get(item.getId());
//			List<PropParam> returnSkuProps = new ArrayList<PropParam>();
//			Long skuPropertyTmplId = null;
//			String skuPropertyTmplName = null;
//			List<String> vids = new ArrayList<String>();
//			List<String> values = new ArrayList<String>();
//			if (!CollectionUtils.isEmpty(skuPropList)) {
//				for (SkuPropertyDTO prop : skuPropList) {
//					PropParam propParam = new PropParam();
//					propParam.setPropId(String.valueOf(prop
//							.getSkuPropertyTmplId()));
//					propParam.setPropName(prop.getKeyName());
//					vids.add(String.valueOf(prop.getVid()));
//					values.add(prop.getVal());
//					returnSkuProps.add(propParam);
//				}
//			}
//			sku.setProp(returnSkuProps);
//			skus.add(sku);
//		}
//		itemParamDto.setSkus(skus);
//
//		List<SkuPropertyParam> skuPropertyParamList = new ArrayList<SkuPropertyParam>();
//		java.util.Iterator<Entry<Long, List<String>>> skuIterator = skuPropertyIdMap
//				.entrySet().iterator();
//		while (skuIterator.hasNext()) {
//			Entry entry = skuIterator.next();
//			Long id = (Long) entry.getKey();
//			skuPropertyNameMap.get(id);
//			SkuPropertyParam skuPropertyParam = new SkuPropertyParam();
//			skuPropertyParam.setId(String.valueOf(id));
//			skuPropertyParam.setName(skuPropertyNameMap.get(id));
//			skuPropertyParam.setVid((List<String>) entry.getValue());
//			skuPropertyParam.setValue(skuPropertyValueMap.get(id));
//			skuPropertyParamList.add(skuPropertyParam);
//		}
//
//		itemParamDto.setSkuProps(skuPropertyParamList);
//
//		String s = ResponseUtils.toJsonStr(itemParamDto);
//		System.out.println(s);
//
//	}
//
//}
