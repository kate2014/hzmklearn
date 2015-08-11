package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;

public interface ItemBrandClient {
	
	/**
	 * 复合条件查询品牌
	 * @param itemBrandQTO
	 */
	public Response<List<ItemBrandDTO>> queryBrand(ItemBrandQTO itemBrandQTO);

	/**
	 * 复合条件查询品牌
	 * @param itemBrandDTO
	 */
	public Response<ItemBrandDTO> addItemBrand(ItemBrandDTO itemBrandDTO);
	
	/**
	 * 查询某个供应商下关联的品牌
	 * @param sellerBrandQTO
	 * @return
	 */
	public Response<List<SellerBrandDTO>> querySellerBrand(SellerBrandQTO sellerBrandQTO);

	/**
	 * 新增商家关联的品牌
	 * @param sellerBrandDTO
	 * @return
	 */
	public Response<SellerBrandDTO> addSellerBrand(SellerBrandDTO sellerBrandDTO);
	
	public Response<Boolean> deleteSellerBrand(Long id,Long supplierId);

	public Response<Boolean> deleteItemBrand(Long id);
	
	public Response<SellerBrandDTO> getSellerBrand(Long id,Long supplierId);
	
	public Response<Boolean> updateSellerBrand(SellerBrandDTO sellerBrandDTO);
	
}
