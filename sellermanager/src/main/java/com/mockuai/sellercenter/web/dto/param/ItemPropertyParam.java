package com.mockuai.sellercenter.web.dto.param;

import java.util.List;

public class ItemPropertyParam {
	
	public static class PropValue{
		private List<String> vid;
		public List<String> getVid() {
			return vid;
		}
		public void setVid(List<String> vid) {
			this.vid = vid;
		}
		public List<String> getValue() {
			return value;
		}
		public void setValue(List<String> value) {
			this.value = value;
		}
		private List<String> value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMust() {
		return must;
	}

	public void setMust(String must) {
		this.must = must;
	}

	public String getMulti() {
		return multi;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

	public String getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(String userDefined) {
		this.userDefined = userDefined;
	}


	public PropValue getPropValues() {
		return propValues;
	}

	public void setPropValues(PropValue propValues) {
		this.propValues = propValues;
	}
	
	public String getIsSku() {
		return isSku;
	}

	public void setIsSku(String isSku) {
		this.isSku = isSku;
	}
	
	private String id;//属性的id
	
	private String cid;//类目id
	
	private String name;//属性名
	
	private String must;//是否必须
	
	private String multi;//是否多选
	
	private String userDefined;//是否可以修改文字
	
	private String isSku;//是否是sku属性
	
	private PropValue propValues;
	
}
