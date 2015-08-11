package com.mockuai.itemcenter.common.constant;

public enum DBConst {

	/**
	 * 商品正常状态
	 */
	ITEM_NORMAL(0),

	/**
	 * 商品进入回收站的状态
	 */
	ITEM_IN_TRASH(2),

	/**
	 * 品牌状态
	 */
	BRAND_NORMAL(0),

	/**
	 * 已删除
	 */
	DELETED(1),
	/**
	 * 未删除
	 */
	UN_DELETED(0),

	/**
	 * 待审核
	 */
	NOT_AUDITED(0),

	/**
	 * 审核通过
	 */
	AUDIT_PASS(1),

	/**
	 * 审核未通过
	 */
	AUDIT_UNPASS(2);

	private int code;

	private DBConst(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
