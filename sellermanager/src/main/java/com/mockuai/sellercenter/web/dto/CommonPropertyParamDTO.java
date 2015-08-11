package com.mockuai.sellercenter.web.dto;

import java.util.List;

/**
 * 普通属性数据传输类
 * @author cwr
 */
public class CommonPropertyParamDTO {
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

