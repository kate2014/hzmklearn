package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.core.dao.ItemCategoryDAO;
import com.mockuai.itemcenter.core.domain.ItemCategoryDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class ItemCategoryManagerImpl implements ItemCategoryManager {
	@Resource
	private ItemCategoryDAO itemCategoryDAO;

	@Override
	public ItemCategoryDTO addItemCategory(ItemCategoryDTO itemCategoryDTO) throws ItemException {
		try {
			//入参检查
			if(itemCategoryDTO == null){
				throw new ItemException(ResponseCode.PARAM_E_MISSING, "itemCategoryDTO is null");
			}

			if(StringUtils.isBlank(itemCategoryDTO.getCateName())){
				throw new ItemException(ResponseCode.PARAM_E_INVALID, "category name is null");
			}

			//参数默认值填充
			if(itemCategoryDTO.getParentId()==null || itemCategoryDTO.getParentId().longValue()==0){
				itemCategoryDTO.setParentId(0L);
				itemCategoryDTO.setTopId(0L);
				itemCategoryDTO.setCateLevel(1);
			}else{
				ItemCategoryDTO parentCate = getItemCategory(itemCategoryDTO.getParentId());
				if(parentCate == null){
					throw new ItemException(ResponseCode.PARAM_E_INVALID, "parent category is not exist");
				}

				//根据父类目信息来填充子类目的topId和cateLevel信息
				if(parentCate.getTopId().longValue() != 0){
					itemCategoryDTO.setTopId(parentCate.getTopId());
				}else{
					itemCategoryDTO.setTopId(0L);
				}

				itemCategoryDTO.setCateLevel(parentCate.getCateLevel()+1);
			}

			ItemCategoryDO itemCategoryDO = new ItemCategoryDO();
			ItemUtil.copyProperties(itemCategoryDTO, itemCategoryDO);// DTO转DO
			Long newInsertedId = itemCategoryDAO.addItemCategory(itemCategoryDO);// 新增的记录返回的ID
			//itemCategoryDTO = getItemCategory(newInsertedId);// 新增加的记录对应的itemCategoryDO
			itemCategoryDTO.setId(newInsertedId);
			return itemCategoryDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateItemCategory(ItemCategoryDTO itemCategoryDTO) throws ItemException {
		verifyUpdatedItemCategoryDTOProperty(itemCategoryDTO);
		ItemCategoryDO itemCategoryDO = new ItemCategoryDO();
		ItemUtil.copyProperties(itemCategoryDTO, itemCategoryDO);
		int num = itemCategoryDAO.updateItemCategory(itemCategoryDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update ItemCategory error-->primary id:"
					+ itemCategoryDTO.getId());
		}
	}

	@Override
	public ItemCategoryDTO getItemCategory(Long id) throws ItemException {
		ItemCategoryDO itemCategoryDO = itemCategoryDAO.getItemCategory(id);
		if (itemCategoryDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table itemcategory-->id:"
							+ id);
		}
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		ItemUtil.copyProperties(itemCategoryDO, itemCategoryDTO);
		return itemCategoryDTO;
	}

	@Override
	public boolean deleteItemCategory(Long id) throws ItemException {
		// 查询子类目 :如果存在子类目,则不允许删除该类目
		ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
		itemCategoryQTO.setParentId(id);
		List<ItemCategoryDTO> itemCategoryDTOList = queryItemCategory(itemCategoryQTO);
		if (itemCategoryDTOList.size() > 0) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_STATE_E_NOT_ALLOW_CATEGORY_DELETED, "category not allowed to delete primary id:"
							+ id);
		}
		int num = itemCategoryDAO.deleteItemCategory(id);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete ItemCategory error-->primary id:"
					+ id);
		}
	}

	/**
	 * 验证新增的ItemCategoryDTO
	 */
	private void verifyUpdatedItemCategoryDTOProperty(ItemCategoryDTO itemCategoryDTO) throws ItemException {
		if (itemCategoryDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemCategory id is null");
		}
	}

	public List<ItemCategoryDTO> queryItemCategory(ItemCategoryQTO itemCategoryQTO) throws ItemException {
		List<ItemCategoryDO> list = itemCategoryDAO.queryItemCategory(itemCategoryQTO);
		List<ItemCategoryDTO> itemCategoryDTOList = new ArrayList<ItemCategoryDTO>();// 需要返回的DTO列表
		for (ItemCategoryDO itemCategoryDO : list) {
			ItemCategoryDTO dto = new ItemCategoryDTO();
			ItemUtil.copyProperties(itemCategoryDO, dto);
			itemCategoryDTOList.add(dto);
		}
		return itemCategoryDTOList;
	}

	@Override
	public List<ItemCategoryDTO> queryItemLeafCategory() throws ItemException {
		List<ItemCategoryDO> list = itemCategoryDAO.queryItemLeafCategory();
		List<ItemCategoryDTO> itemCategoryDTOList = new ArrayList<ItemCategoryDTO>();// 需要返回的DTO列表
		for (ItemCategoryDO itemCategoryDO : list) {
			ItemCategoryDTO dto = new ItemCategoryDTO();
			ItemUtil.copyProperties(itemCategoryDO, dto);
			itemCategoryDTOList.add(dto);
		}
		return itemCategoryDTOList;
	}

}
