package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.domain.ItemSkuDO;

@Service
public interface ItemSkuDAO {

	/**
	 * 增加商品销售属性(ItemSku)
	 * 
	 * @param itemSkuDO
	 * @return
	 */
	Long addItemSku(ItemSkuDO itemSkuDO);

	/**
	 * 根据id获取商品销售属性(ItemSku)
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	ItemSkuDO getItemSku(Long id, Long sellerId);

	/**
	 * 根据id删除商品销售属性(ItemSku)
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemSku(Long id, Long sellerId);

	/**
	 * 更新商品销售属性(ItemSku)信息
	 * 
	 * @param itemSkuDO
	 * @return
	 */
	int updateItemSku(ItemSkuDO itemSkuDO);

	/**
	 * 返回商品销售属性(ItemSku)列表
	 * 
	 * @param itemSkuQTO
	 * @return
	 */
	List<ItemSkuDO> queryItemSku(ItemSkuQTO itemSkuQTO);

	/**
	 * 减少SKU库存
	 * 
	 * @param skuId
	 * @param sellerId卖家ID
	 * @param number增加的数量
	 * 
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public int increaseItemSkuStock(Long skuId, Long sellerId, Long increasedNumber) throws ItemException;

	/**
	 * 减少SKU库存
	 * 
	 * @param skuId
	 * @param sellerId卖家ID
	 * @param number减少的数量
	 * 
	 * @return
	 * @throws ItemException
	 */
	public int decreaseItemSkuStock(Long skuId, Long sellerId, Long decreasedNumber) throws ItemException;

	/**
	 * 根据skuId,sellerId,更新codeValue值
	 * 
	 * @param skuId
	 * @param sellerId
	 *            供应商Id
	 * @param codeValue
	 * @return
	 * @throws ItemException
	 */
	public int updateItemSkuCodeValue(Long skuId, Long sellerId, String codeValue) throws ItemException;
	
	/**
	 * 根据itemId删除   
	 * @param itemSkuDTO
	 * @return
	 * @author updated by cwr
	 */
	public int deleteByItemId(ItemSkuDO itemSkuDTO);
	
}