package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.PartCommentDTO;
import com.mockuai.itemcenter.core.dao.ItemCommentDAO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import com.mockuai.itemcenter.core.domain.PartCommentDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class ItemCommentManagerImpl implements ItemCommentManager {
	@Resource
	private ItemCommentDAO itemCommentDAO;

	@Override
	public Long addItemComment(ItemCommentDO itemCommentDO) throws ItemException {
		try {

			//入参校验
			// TODO 验证ItemCommentDO字段属性
			// 1.验证订单状状态调用交易接口
			// 2.验证供应商ID sellerId
			// 3.验证用户ID是否合法
			if (itemCommentDO == null) {
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemCommentDO property incorrect");
			}

			if(itemCommentDO.getUserId()==null || itemCommentDO.getUserId().longValue()<=0){
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemComment userId is null");
			}

			if(itemCommentDO.getSellerId()==null || itemCommentDO.getSellerId().longValue()<=0){
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemComment sellerId is null");
			}

			if (StringUtils.isBlank(itemCommentDO.getContent())) {
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemComment content is null");
			}

			long itemCommentId = itemCommentDAO.addItemComment(itemCommentDO);// 新增的记录返回的ID
			return itemCommentId;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public Boolean updateItemComment(ItemCommentDTO itemCommentDTO) throws ItemException {
		try {
			if (itemCommentDTO == null) {
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemCommentDTO property incorrect");
			}
			ItemCommentDO itemCommentDO = new ItemCommentDO();
			ItemUtil.copyProperties(itemCommentDTO, itemCommentDO);// DTO转DO
			int updateCount = itemCommentDAO.updateItemComment(itemCommentDO);
			if(updateCount > 0) {
				return true;
			} else {
				throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item comment error");
			}
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	public ItemCommentDTO getItemComment(Long id, Long sellerId) throws ItemException {
		ItemCommentDO itemCommentDO = itemCommentDAO.getItemComment(id, sellerId);
		if (itemCommentDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table item_comment-->id:"
							+ id);
		}
		ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
		ItemUtil.copyProperties(itemCommentDO, itemCommentDTO);// DO转DTO
		return itemCommentDTO;
	}

	/**
	 * 获取商品评分等级
	 */
	@Override
	public List<ItemCommentDTO> queryItemCommentGrade(ItemCommentQTO itemCommentQTO) throws ItemException {
		List<ItemCommentDO> list = itemCommentDAO.queryItemCommentGrade(itemCommentQTO);
		List<ItemCommentDTO> DTOList = new ArrayList<ItemCommentDTO>();
		for (ItemCommentDO itemCommentDO : list) {
			ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
			ItemUtil.copyProperties(itemCommentDO, itemCommentDTO);
			DTOList.add(itemCommentDTO);
		}
		return DTOList;
	}

	@Override
	public List<PartCommentDTO> countItemCommentGrade(ItemCommentQTO itemCommentQTO) throws ItemException {
		List<PartCommentDO> list = itemCommentDAO.countItemCommentGrade(itemCommentQTO);
		List<PartCommentDTO> DTOList = new ArrayList<PartCommentDTO>();
		for (PartCommentDO partCommentDO : list) {
			PartCommentDTO partCommentDTO = new PartCommentDTO();
			ItemUtil.copyProperties(partCommentDO, partCommentDTO);
			DTOList.add(partCommentDTO);
		}
		return DTOList;
	}

	@Override
	public boolean deleteItemComment(Long id, Long sellerId) throws ItemException {
		// TODO 校验sellerId ,id
		ItemCommentDO itemCommentDO = new ItemCommentDO();
		itemCommentDO.setId(id);
		itemCommentDO.setSellerId(sellerId);
		int num = itemCommentDAO.deleteItemComment(id, sellerId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item_comment-->id:->id:"
							+ id + " sellerId:" + sellerId);
		}
	}


	public List<ItemCommentDO> queryItemComment(ItemCommentQTO itemCommentQTO) throws ItemException {
		List<ItemCommentDO> itemCommentList = itemCommentDAO.queryItemCommentByItemId(itemCommentQTO);
		return itemCommentList;
	}


}
