package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyDO;

@Service
public interface ItemPropertyDAO {

	/**
	 * 增加商品属性
	 * 
	 * @param itemPropertyDO
	 * @return
	 */
	Long addItemProperty(ItemPropertyDO itemPropertyDO);

	/**
	 * 根据id获取商品属性
	 * 
	 * @param id
	 * @return
	 */
	ItemPropertyDO getItemProperty(Long id, Long sellerId);

	/**
	 * 根据id删除商品属性
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemProperty(Long id, Long sellerId);

	/**
	 * 更新商品属性信息
	 * 
	 * @param itemPropertyDO
	 * @return
	 */
	int updateItemProperty(ItemPropertyDO itemPropertyDO);

	/**
	 * 返回商品属性列表
	 * 
	 * @param itemPropertyQTO
	 * @return
	 */
	List<ItemPropertyDO> queryItemProperty(ItemPropertyQTO itemPropertyQTO);
	
	/**
	 * 根据itemId 和 supplierId删除
	 * @param itemId
	 * @param supplierId
	 * @return
	 */
	public int deleteByItemId(ItemPropertyDO itemPropertyDO);

}