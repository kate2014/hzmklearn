package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;

public interface ItemSkuClient {


	/**
	 * 根据商品skuId查询商品sku信息
	 * @param itemSkuId
	 * @param sellerId
	 * @return
	 */
	public Response<ItemSkuDTO> getItemSku(Long itemSkuId, Long sellerId);

	/**
	 * 根据商品ID查询该商品下的所有SKU信息
	 * @param itemId
	 * @param sellerId
	 * @return
	 */
	public Response<List<ItemSkuDTO>> queryItemSku(Long itemId, Long sellerId);

	/**
	 * 根据商品SKU ID列表批量查询SKU信息
	 * @param skuIdList
	 * @param sellerId
	 * @return
	 */
	public Response<List<ItemSkuDTO>> queryItemSku(List<Long> skuIdList, Long sellerId);
	
	/**
	 * 根据qto复合条件查询
	 * @param itemSkuQTO
	 * @return
	 */
	public Response<List<ItemSkuDTO>> queryItemSku(ItemSkuQTO itemSkuQTO);

}
