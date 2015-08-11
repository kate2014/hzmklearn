package com.mockuai.shopplatform.constant;

public enum ActionEnum {

	/**
	 * ----店铺相关---------------------------------------
	 */

	ADD_SHOP("addShop"),

	GET_SHOP("getShop"),

	QUERY_SHOP("queryShop"),

	UPDATE_SHOP("updateShop"),

	ADD_SHOP_ITEM_GROUP("addShopItemGroup"),

	DELETE_SHOP_ITEM_GROUP("deleteShopItemGroup"),

	GET_SHOP_ITEM_GROUP("getShopItemGroup"),

	UPDATE_SHOP_ITEM_GROUP("updateShopItemGroup"),

	QUERY_SHOP_ITEM_GROUP("queryShopItemGroup"),

	GET_SHOP_STATUS("getShopStatus"),

	FREEZE_SHOP("freezeShop"),

	THAW_SHOP("thawShop");

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
