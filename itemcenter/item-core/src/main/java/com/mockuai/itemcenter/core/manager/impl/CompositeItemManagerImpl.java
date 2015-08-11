package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.core.dao.CompositeItemDAO;
import com.mockuai.itemcenter.core.domain.CompositeItemDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CompositeItemManager;

@Service
public class CompositeItemManagerImpl implements CompositeItemManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CompositeItemDAO compositeItemDAO;

	@Override
	public Long addCompositeItem(CompositeItemDTO compositeItemDTO)
			throws ItemException {
		logger.debug("enter addCompositeItem: skuId: " + compositeItemDTO.getSkuId() +  " subSkuId : " + compositeItemDTO.getSubSkuId()); 
		CompositeItemDO compositeItemDO = new CompositeItemDO();
		BeanUtils.copyProperties(compositeItemDTO, compositeItemDO);
		Long id = this.compositeItemDAO.addCompositeItem(compositeItemDO);
		return id;
	}

	@Override
	public int deleteCompositeItemByItemId(Long itemId, Long supplierId) {
		logger.debug("enter deleteCompositeItemByItemId " + itemId +  " "+ supplierId);
		return this.compositeItemDAO.deleteCompositeItemByItemId(itemId, supplierId);
	}
	
	@Override
	public List<CompositeItemDTO> getCompositeItemByItemId(Long itemId,Long supplierId){
		List<CompositeItemDO> list = this.compositeItemDAO.getCompositeItemByItemId(itemId, supplierId);
		List<CompositeItemDTO> result = new ArrayList<CompositeItemDTO>();
		if(list != null){
			for(CompositeItemDO item : list){
				CompositeItemDTO dto =new CompositeItemDTO();
				BeanUtils.copyProperties(item, dto);
				result.add(dto);
			}
		}
		return result;
	}
	
	private void validateField4Add(CompositeItemDTO dto)throws ItemException{
		if(dto ==null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"compositeItemDTO is null");
		}
		if(dto.getSkuId() == null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"skuId is null");
		}else if(dto.getNum() == null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"num is null");
		}else if(dto.getItemId() == null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"itemId is null");
		}else if(dto.getSubSkuId() ==null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"subSkuId is null");
		}
	}
	
}
