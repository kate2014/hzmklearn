package com.mockuai.sellercenter.web.dto;

public class PropStrDTO {
	@Override
	public String toString() {
		return "PropStrDTO [propId=" + propId + ", propName=" + propName
				+ ", valueId=" + valueId + ", valueName=" + valueName + "]";
	}

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
