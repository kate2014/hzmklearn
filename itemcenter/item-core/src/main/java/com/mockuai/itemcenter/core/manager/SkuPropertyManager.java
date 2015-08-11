package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;

@Service
public interface SkuPropertyManager {

	/**
	 * 添加SKU属性
	 * 
	 * @param skuId
	 * @param skuPropertyDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public List<SkuPropertyDTO> addSkuProperty(Long itemId,Long skuId, Long sellerId, List<SkuPropertyDTO> skuPropertyDTOList)
			throws ItemException;

	/**
	 * 添加SKU属性
	 * 
	 * @param skuPropertyDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateSkuProperty(SkuPropertyDTO skuPropertyDTO) throws ItemException;

	/**
	 * 查看SKU属性
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public SkuPropertyDTO getSkuProperty(Long id, Long sellerId) throws ItemException;

	/**
	 * 删除SKU属性
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteSkuProperty(Long id, Long sellerId) throws ItemException;

	/**
	 * 根据ItemSkuId批量删除SkuProperty
	 * 
	 * @param itemSkuId
	 * @return
	 * @throws ItemException
	 */
	public int deleteSkuPropertyListBySkuId(Long itemSkuId, Long sellerId) throws ItemException;

	/**
	 * 查询SKU属性列表
	 * 
	 * @param skuPropertyQTO
	 * @return
	 * @throws ItemException
	 */
	public List<SkuPropertyDTO> querySkuProperty(SkuPropertyQTO skuPropertyQTO) throws ItemException;
	
	
	/**
	 * 根据类目查找对应的销售属性 及其基本值
	 * @param skuPropertyQTO
	 * @return
	 * @throws ItemException
	 */
	public List<SkuPropertyDTO> querySkuPropertyWithValue(SkuPropertyQTO skuPropertyQTO) throws ItemException;

	
	/**
	 * 根据itemId删除
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemeException
	 */
	public int deleteByItemId(Long itemId,Long supplierId)throws ItemException;

}
