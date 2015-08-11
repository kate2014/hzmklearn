package com.mockuai.sellercenter.web.dto;

import java.util.List;

public class SkuPropStrDTO {

	@Override
	public String toString() {
		return "SkuPropStrDTO [vid=" + vid + ", id=" + id + ", name=" + name
				+ ", value=" + value + "]";
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<String> vid;
	
	private String id;
	
	private String name;
	
	private String value;
}
