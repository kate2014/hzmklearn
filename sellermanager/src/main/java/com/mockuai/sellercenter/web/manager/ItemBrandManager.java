package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.client.ItemBrandClient;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

/**
 * 买家对应的品牌管理
 * @author cwr
 */
public interface ItemBrandManager {

	public List<SellerBrandDTO> querySellerBrand(SellerBrandQTO sellerBrandQTO)throws ServiceException;
	
	public SellerBrandDTO addSellerBrand(SellerBrandDTO sellerBrandDTO)throws ServiceException;
	
	public Boolean deleteSellerBrand(Long id,Long supplierId)throws ServiceException;
	
	public SellerBrandDTO getSellerBrand(Long id,Long supplierId)throws ServiceException;
	
	public Boolean updateSellerBrand(SellerBrandDTO sellerBrandDTO)throws ServiceException;
	
}
