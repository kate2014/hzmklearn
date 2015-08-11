package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.domain.SellerBrandDO;

@Service
public interface SellerBrandDAO {
	
	/**
	 * 复合条件查询商家的品牌
	 * @param sellerBrandQTO
	 * @return
	 */
	public List<SellerBrandDO> querySellerBrand(SellerBrandQTO sellerBrandQTO);
	
	public Long addSellerBrand(SellerBrandDO sellerBrandDO);
	
	public int deleteSellerBrand(SellerBrandDO sellerBrandDO);
	
	public SellerBrandDO getSellerBrand(SellerBrandDO sellerBrandDO);
	
	public int updateSellerBrand(SellerBrandDO sellerBrandDO);
	
}
