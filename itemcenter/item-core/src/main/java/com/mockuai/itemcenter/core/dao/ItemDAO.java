package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.domain.ItemDO;

@Service
public interface ItemDAO {

	/**
	 * 增加商品
	 * 
	 * @param itemDO
	 * @return
	 */
	Long addItem(ItemDO itemDO);

	/**
	 * 根据id获取商品
	 * 
	 * @param id
	 * @return
	 */
	ItemDO getItem(Long id, Long supplier_id);

	/**
	 * 根据id删除商品
	 * 
	 * @param id
	 * @return
	 */
	int deleteItem(Long id, Long supplier_id);

	/**
	 * 更新商品状态
	 * 
	 * @param id
	 * @return
	 */
	int updateItemState(Long id, Long supplier_id, Integer state);

	/**
	 * 更新商品状态
	 *
	 * @param id
	 * @return
	 */
	int removeItemSaleEnd(Long id, Long supplier_id);

	/**
	 * 商品上下架更新,查询
	 *
	 * @return
	 */
	void updateItemSaleStateUp();
	void updateItemSaleStateDown();
	List<ItemDO> queryItemSaleUp(ItemQTO itemQTO);
	List<ItemDO> queryItemSaleDown(ItemQTO itemQTO);


	/**
	 * 更新商品信息
	 * 
	 * @param itemDO
	 * @return
	 */
	int updateItem(ItemDO itemDO);

	int removeItemFromGroup(ItemDO itemDO);

	int removeItemToDefaultGroup(ItemDO itemDO);

	int countGroupItem(ItemQTO itemQTO);

	/**
	 * 返回商品列表
	 * 
	 * @param itemQTO
	 * @return
	 */
	List<ItemDO> queryItem(ItemQTO itemQTO);
	
	/**
	 * 判断item是否存在
	 */
	Object isItemExist(ItemQTO itemQTO);

}