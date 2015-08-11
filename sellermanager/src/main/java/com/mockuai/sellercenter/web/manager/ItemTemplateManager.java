package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

public interface ItemTemplateManager {

	/**
	 * 根据商品模版删除
	 * @param templateId
	 * @param supplierId
	 * @return
	 */
	public Boolean deleteItemTemplate(Long templateId,Long supplierId) throws ServiceException;
	
	/**
	 * 新增商品模版
	 * @param itemDTO
	 * @return
	 */
	public ItemTemplateDTO addItemTemplate(ItemTemplateDTO itemDTO) throws ServiceException;
	
	/**
	 * 修改模版
	 * @param itemDto
	 * @return
	 * @throws ServiceException
	 */
	public Boolean updateItemTemplate(ItemTemplateDTO itemDto)throws ServiceException;
	
	/**
	 * 根据id查询录入模板
	 * @param templateId
	 * @param supplierId
	 * @return
	 * @throws ServiceException
	 */
	public ItemTemplateDTO getItemTemplate(Long templateId,Long supplierId) throws ServiceException;
	
	
	/**
	 * 复合条件查询录入模板
	 * @param itemTemplateQTO
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<ItemTemplateDTO>> queryItemTemplate(ItemTemplateQTO itemTemplateQTO)throws ServiceException;
	
}
