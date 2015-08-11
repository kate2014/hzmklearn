package com.mockuai.sellercenter.web.dto;

import java.util.List;

/**
 * 新增商品的json字符串对应类
 * @author cwr
 */
public class CompositeItemParamDTO {
	/**
	 * 普通属性
	 * @author cwr
	 */
	public static class ItemProperty{
		public List<String> getVid() {
			return vid;
		}

		public void setVid(List<String> vid) {
			this.vid = vid;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<String> getValue() {
			return value;
		}

		public void setValue(List<String> value) {
			this.value = value;
		}

		private List<String> vid;
		
		private String id;
		
		private String name;
		
		private List<String> value;
	}
	
//	public static class SkuPropertyParam{
//		public List<String> getVid() {
//			return vid;
//		}
//
//		public void setVid(List<String> vid) {
//			this.vid = vid;
//		}
//
//		public String getId() {
//			return id;
//		}
//
//		public void setId(String id) {
//			this.id = id;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//
//		public List<String> getValue() {
//			return value;
//		}
//
//		public void setValue(List<String> value) {
//			this.value = value;
//		}
//
//		private List<String> vid;
//		
//		private String id;
//		
//		private String name;
//		
//		private List<String> value;
//		
//	}
	
	public static class PropParam{
		public String getPropId() {
			return propId;
		}

		public void setPropId(String propId) {
			this.propId = propId;
		}

		public String getPropName() {
			return propName;
		}

		public void setPropName(String propName) {
			this.propName = propName;
		}

		public String getValueId() {
			return valueId;
		}

		public void setValueId(String valueId) {
			this.valueId = valueId;
		}

		public String getValueName() {
			return valueName;
		}

		public void setValueName(String valueName) {
			this.valueName = valueName;
		}

		private String propId;
		
		private String propName;
		
		private String valueId;
		
		private String valueName;
	}
	
//	public static class SkuPropertyItem{
//		
//		public List<PropParam> getProp() {
//			return prop;
//		}
//
//		public void setProp(List<PropParam> prop) {
//			this.prop = prop;
//		}
//
//		public String getOriginPrice() {
//			return originPrice;
//		}
//
//		public void setOriginPrice(String originPrice) {
//			this.originPrice = originPrice;
//		}
//
//		public String getPrice() {
//			return price;
//		}
//
//		public void setPrice(String price) {
//			this.price = price;
//		}
//
//		public String getNum() {
//			return num;
//		}
//
//		public void setNum(String num) {
//			this.num = num;
//		}
//
//		public String getBarcode() {
//			return barcode;
//		}
//
//		public void setBarcode(String barcode) {
//			this.barcode = barcode;
//		}
//
//		private List<PropParam> prop;
//		
//		private String originPrice;
//		
//		private String price;
//		
//		private String num;
//		
//		private String barcode;
//	}
	
	public static class CompositeItem{
		public String getSkuId() {
			return skuId;
		}

		public void setSkuId(String skuId) {
			this.skuId = skuId;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		private String skuId;//具体规格的商品id
		
		private String num;//数量
		

	}
	
	/**
	 * 新建组合商品时候需要关联具体的规格的商品
	 * @author cwr
	 */
	public static class GalleryParam{
		private String color;
		
		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		private String img;
		
		private String id;
	}
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getUserMinBought() {
		return userMinBought;
	}

	public void setUserMinBought(String userMinBought) {
		this.userMinBought = userMinBought;
	}

	public String getUserMaxBought() {
		return userMaxBought;
	}

	public void setUserMaxBought(String userMaxBought) {
		this.userMaxBought = userMaxBought;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GalleryParam> getGallery() {
		return gallery;
	}

	public void setGallery(List<GalleryParam> gallery) {
		this.gallery = gallery;
	}


	public List<ItemProperty> getProps() {
		return props;
	}

	public void setProps(List<ItemProperty> props) {
		this.props = props;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public Boolean getIsComposite() {
		return isComposite;
	}

	public void setIsComposite(Boolean isComposite) {
		this.isComposite = isComposite;
	}
	
	private String cateName;//类目的中文名称比如： 服饰 - 童装 - T恤
	
	private String templateName;

	private List<String> parents; //类目层次
	
	private String subName; 
	
	private String cateId;
	
	private String brief;
	
	private String description;
	
	private String brandId;
	
	private String userMinBought;
	
	private String userMaxBought;
	
	private String id;
	
	private List<GalleryParam> gallery;
	
	private List<ItemProperty> props;
	
	private Boolean isComposite;//是否是组合商品
	
	private Long originPrice;//组合商品原价
	
	private Long price;//现价
	
	private Integer num;//库存

	public Long getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(Long originPrice) {
		this.originPrice = originPrice;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
