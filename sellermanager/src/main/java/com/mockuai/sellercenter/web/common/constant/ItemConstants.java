package com.mockuai.sellercenter.web.common.constant;

public abstract interface ItemConstants
{
    public static class ItemImage
    {
        public static int MAX_GENERAL_IMAGES = 4;
    }

    public static enum DeliveryType
    {
        NOT_LIMIT(0, "不指定"),

        GENERAL_IMPORT(1, "一般进口"),

        TARIFF_FREE_ZONE_DELIVERY(2, "保税区发货"),

        OVERSEA_DIRECT_DELIVERY(3, "海外直邮");

        private int code;
        private String name;

        public int getCode() {
            return this.code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }

        private DeliveryType(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    public static enum ItemStatus
    {
        PENDING_AUDIT(1, "待审核"),

        AUDIT_SUCCESS(1, "审核通过"),

        AUDIT_FAIL(3, "审核不通过"),

        ON_SALE(4, "上架"),

        OFF_SALE(5, "下架"),

        FROZEN(6, "冻结"),

        PRE_SALE(7, "预售");

        private int status;
        private String statusName;

        public int getStatus() { return this.status; }

        public void setStatus(int status) {
            this.status = status;
        }
        public String getStatusName() {
            return this.statusName;
        }
        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        private ItemStatus(int status, String statusName)
        {
            this.status = status;
            this.statusName = statusName;
        }
    }

    public static enum ItemType
    {
        PHYSICAL_GOODS(1, "实物商品"),

        VIRUAL_GOODS(1, "实物商品");

        private int type;
        private String typeName;

        public int getType() { return this.type; }

        public void setType(int type)
        {
            this.type = type;
        }

        public String getTypeName() {
            return this.typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        private ItemType(int type, String typeName)
        {
            this.type = type;
            this.typeName = typeName;
        }
    }
}