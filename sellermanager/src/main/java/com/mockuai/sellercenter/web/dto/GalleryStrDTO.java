package com.mockuai.sellercenter.web.dto;

public class GalleryStrDTO {
	@Override
	public String toString() {
		return "GalleryStrDTO [color=" + color + ", img=" + img + ", id=" + id
				+ "]";
	}

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

	private String color;
	
	private String img;
	
	private String id;
	
}
