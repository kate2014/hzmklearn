package com.mockuai.sellercenter.web.dto;

/**
 * 地址库基本信息
 * @author cwr
 *
 */
public class AddressDTO {
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Integer id;
	
	private Integer level;//地址级别
	
	private String name;
}
