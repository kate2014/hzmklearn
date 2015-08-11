package com.mockuai.itemcenter.core.constants;

public enum ItemStatus {
	
	PENDING_AUDIT(1,"待审核"),
	
	AUDIT_SUCCESS(2,"审核通过"),
	
	AUDIT_FAIL(3,"审核不通过"),
	
	ON_SALE(4,"上架"),
	
	WITHDRAW(5,"下架"),
	
	FROZEN(6,"冻结"),

	PRE_SALE(7, "预售");
	
	private int status;
	
	private String statusName;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	private ItemStatus(int status, String statusName) {
		this.status = status;
		this.statusName = statusName;
	}
}
