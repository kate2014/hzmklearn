package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/29/15.
 */
public class MopItemBrandDTO {
    private long id;
    private String name;
    private String enName;
    private String logoUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
