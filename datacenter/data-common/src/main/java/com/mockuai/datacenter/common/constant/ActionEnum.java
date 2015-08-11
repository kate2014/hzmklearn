package com.mockuai.datacenter.common.constant;

/**
 * Created by wanghailong on 15-8-6.
 */
public enum ActionEnum {

	/**
	 * ----店铺相关---------------------------------------
	 */

	ADD_PAGEVIEW("addPageView"),

	QUERY_ITEMTOP("queryItemTop"),

	GET_PAGEVIEW("getPageView"),

	QUERY_PAGEVIEW("queryPageView"),

	QUERY_VISITORAREA("queryVisitorArea"),

	QUERY_DEVICETYPE("queryDeviceType"),

	QUERY_VISITTIME("queryVisitTime"),

	COUNT_SHOP_PV("countShopPv"),

	COUNT_SHOP_UV("countShopUv"),

	COUNT_SHOP_UV_TOTAL("countShopUvTotal"),

	QUERY_SHOP_PV("queryShopPv"),

	QUERY_SHOP_UV("queryShopUv"),

	QUERY_SHOP_HOUR_UV("queryShopHourUv"),

	COUNT_NEW_VISITOR("countNewVisitor");

	private String actionName;

	private ActionEnum(String actionName) {
		this.actionName = actionName;
	}

	public static ActionEnum getActionEnum(String actionName) {
		for (ActionEnum ae : ActionEnum.values()) {
			if (ae.actionName.equals(actionName)) {
				return ae;
			}
		}
		return null;
	}

	public String getActionName() {
		return actionName;
	}
}
