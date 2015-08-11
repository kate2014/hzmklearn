package com.mockuai.sellercenter.web.dto;

import java.util.List;

public class SkuStrDTO {

	@Override
	public String toString() {
		return "SkuStrDTO [prop=" + prop + ", originPrice=" + originPrice
				+ ", price=" + price + ", num=" + num + ", barcode=" + barcode
				+ "]";
	}

	public List<PropStrDTO> getProp() {
		return prop;
	}

	public void setProp(List<PropStrDTO> prop) {
		this.prop = prop;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	private List<PropStrDTO> prop ;
	
	private String originPrice;
	
	private String price;
	
	private String num;
	
	private String barcode;
	
}
