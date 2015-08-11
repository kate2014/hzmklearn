package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

public interface ItemManager {
	
	/**
	 * 复合条件查询商品列表
	 * @param itemQTO
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<ItemDTO>> queryItem(ItemQTO itemQTO)throws ServiceException;
	
	/**
	 * 新增商品
	 * @param item
	 * @return
	 * @throws ServiceException
	 */
	public Boolean addItem(ItemDTO item)throws ServiceException;
	
	/**
	 * 获取商品信息
	 * @param userId
	 * @param itemId
	 * @return
	 * @throws ServiceException
	 */
	public ItemDTO getItem(Long userId,Long itemId)throws ServiceException;
	
	/**
	 * 修改商品
	 * @param itemDTO
	 * @return
	 * @throws ServiceException
	 */
	public Boolean updateItem(ItemDTO itemDTO)throws ServiceException;
	
	/**
	 * 删除商品
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	public Boolean deleteItem(Long itemId,Long supplierId)throws ServiceException;
	
	/**
	 * 下架
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	public Boolean withdraw(Long itemId,Long supplierId)throws ServiceException;
	
	
	/**
	 * 商品上架
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	public Boolean upItem(Long itemId,Long supplierId)throws ServiceException;

	/**
	 * 商品预售
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	public Boolean preSaleItem(Long itemId,Long supplierId)throws ServiceException;

	/**
	 * 生成指定的sku访问链接（实际上是一个只包含指定SKU的商品详情页）
	 * @param skuId
	 * @param sellerId
	 * @return
	 * @throws ServiceException
	 */
	public String genSkuUrl(long skuId, long sellerId);

	public Boolean addItemInGroup(Long sellerId, Long itemId, Long groupId) throws ServiceException;

	public Boolean removeItemFromGroup(Long sellerId, Long itemId) throws ServiceException;


	/**
	 * 查询商品图片
	 * @param qto
	 * @return
	 * @throws ServiceException
	 */
	//public List<ItemImageDTO> queryItemImages(ItemImageQTO qto)throws ServiceException;
	
	/**
	 * 查询商品的sku属性值
	 * @param qto
	 * @return
	 * @throws ServiceException
	 */
	//public List<SkuPropertyDTO> querySkuProperty(SkuPropertyQTO qto)throws ServiceException;
	
	/**
	 * 查询商品的普通属性
	 * @param qto
	 * @return
	 * @throws ServiceException
	 */
	//public List<ItemPropertyDTO> queryItemProperty(ItemPropertyQTO qto)throws ServiceException;
	
}
