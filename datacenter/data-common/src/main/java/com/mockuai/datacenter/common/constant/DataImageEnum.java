package com.mockuai.datacenter.common.constant;

/**
 * Created by ziqi.
 */
public enum DataImageEnum {

    SHOP_ICON_IMG(1,"店铺icon图"),

    SHOP_BANNER_IMG(2,"店铺banner图");

    private int type;
    private String typeName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private DataImageEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
