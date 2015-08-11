package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;

public interface ItemManager {

	/**
	 * 添加商品
	 * 
	 * @param itemDTO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public ItemDTO addItem(ItemDTO itemDTO) throws ItemException;

	/**
	 * 添加商品
	 * 
	 * @param itemDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItem(ItemDTO itemDTO) throws ItemException;

	public boolean removeItemFromGroup(ItemDTO itemDTO) throws ItemException;

	public boolean removeItemToDefaultGroup(Long sellerId, Long groupId) throws ItemException;

	/**
	 * 查看商品
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public ItemDTO getItem(Long id, Long supplierId) throws ItemException;

	/**
	 * 删除商品
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItem(Long id, Long supplierId) throws ItemException;

	/**
	 * 将商品移入回收站
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean removeItem(Long id, Long supplierId) throws ItemException;

	public boolean removeItemSaleEnd(Long id, Long supplierId) throws ItemException;


	/**
	 * 查询商品列表
	 * 
	 * @param itemQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemDTO> queryItem(ItemQTO itemQTO) throws ItemException;

	public Integer countGroupItem(ItemQTO itemQTO) throws ItemException;

	/**
	 * 商品上下架
	 *
	 * @return
	 * @throws ItemException
	 */
	public void updateItemSaleUp() throws ItemException;
	public void updateItemSaleDown() throws ItemException;
	public List<ItemDTO> queryItemSaleUp(ItemQTO itemQTO) throws ItemException;
	public List<ItemDTO> queryItemSaleDown(ItemQTO itemQTO) throws ItemException;

	/**
	 * 商品下架
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int withdrawItem(Long itemId,Long supplierId)throws ItemException;
	
	/**
	 * 上架商品
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int upItem(Long itemId,Long supplierId)throws ItemException;


	/**
	 * 商品预售
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int preSaleItem(Long itemId,Long supplierId)throws ItemException;


	/**
	 * 商品下架
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int thawItem(Long itemId,Long supplierId)throws ItemException;

	/**
	 * 上架商品
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int freezeItem(Long itemId,Long supplierId)throws ItemException;

	/**
	 * 上传商品详情内容
	 * @param itemDesc
	 * @return
	 * @throws ItemException
	 */
	public String uploadItemDesc(String itemDesc) throws ItemException;
	
	/**
	 * 复合条件查询item是否存在 某些场景需要判断商品是否存在 比如删除品牌时候
	 * @param itemQTO
	 * @return
	 * @throws ItemException   
	 */
	public Boolean isItemExist(ItemQTO itemQTO )throws ItemException;

}
