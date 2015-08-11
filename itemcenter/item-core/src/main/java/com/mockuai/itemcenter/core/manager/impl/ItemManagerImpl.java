package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.constants.ItemStatus;
import com.mockuai.itemcenter.core.dao.ItemDAO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.HttpUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemManagerImpl implements ItemManager {
	@Resource
	private ItemDAO itemDAO;
	private static final String ITEM_DESC_UPLOAD_URL = "";
	private static final String FILE_PATH = "/home/admin/upload_files/";
	private static final String TMP_PATH = "/home/admin/tmp/";

	@Override
	public ItemDTO addItem(ItemDTO itemDTO) throws ItemException {
		// 验证itemDTO内的属性
		verifyNewAddedItemDTOProperty(itemDTO);
		ItemDO itemDO = new ItemDO();
		ItemUtil.copyProperties(itemDTO, itemDO);// DTO转DO
		long newInsertedId = itemDAO.addItem(itemDO);// 新增的记录返回的ID

		//update by cwr 填充id 不需要重新获取一次
		//itemDTO = getItem(newInsertedId, itemDTO.getSellerId());// 新增加的记录对应的itemDO
		itemDTO.setId(newInsertedId);
		return itemDTO;
	}

	@Override
	public boolean updateItem(ItemDTO itemDTO) throws ItemException {
		// 验证参数
		verifyUpdatedItemDTOProperty(itemDTO);
		ItemDO itemDO = new ItemDO();
		ItemUtil.copyProperties(itemDTO, itemDO);
		int num = itemDAO.updateItem(itemDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item error-->primary id:"
					+ itemDTO.getId() + " sellerId:" + itemDTO.getSellerId());
		}
	}

	@Override
	public boolean removeItemFromGroup(ItemDTO itemDTO) throws ItemException {
		ItemDO itemDO = new ItemDO();
		ItemUtil.copyProperties(itemDTO, itemDO);
		int num = itemDAO.removeItemFromGroup(itemDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item error-->primary id:"
					+ itemDTO.getId() + " sellerId:" + itemDTO.getSellerId());
		}
	}

	@Override
	public boolean removeItemToDefaultGroup(Long sellerId, Long groupId) throws ItemException {
		ItemDO itemDO = new ItemDO();
		itemDO.setSellerId(sellerId);
		itemDO.setGroupId(groupId);
		itemDAO.removeItemToDefaultGroup(itemDO);
		return true;
	}

	@Override
	public ItemDTO getItem(Long id, Long supplierId) throws ItemException {
		ItemDO itemDO = itemDAO.getItem(id, supplierId);
		if (itemDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table item-->id:"
							+ id + " sellerId:" + supplierId);
		}
		ItemDTO itemDTO = new ItemDTO();
		ItemUtil.copyProperties(itemDO, itemDTO);
		itemDTO.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
		return itemDTO;
	}

	@Override
	public boolean deleteItem(Long id, Long supplierId) throws ItemException {
		int num = itemDAO.deleteItem(id, supplierId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item-->id:"
							+ id + " sellerId:" + supplierId);
		}
	}

	@Override
	public boolean removeItem(Long id, Long supplierId) throws ItemException {
		// TODO 验证id
		int num = itemDAO.updateItemState(id, supplierId, DBConst.ITEM_IN_TRASH.getCode());
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item-->id:"
							+ id + " sellerId:" + supplierId);
		}
	}

	public boolean removeItemSaleEnd(Long id, Long supplierId) throws ItemException {
		// TODO 验证id
		int num = itemDAO.removeItemSaleEnd(id, supplierId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item-->id:"
							+ id + " sellerId:" + supplierId);
		}
	}

	private void verifyNewAddedItemDTOProperty(ItemDTO itemDTO) throws ItemException {
		// 验证ItemDTO字段属性
		if (itemDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
		}
		if (itemDTO.getItemName() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_name is null");
		}
		//TODO商品简称］后续考虑可能去掉
//		if (itemDTO.getItemBriefName() == null) {
//			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_brief_name is null");
//		}
		if (itemDTO.getItemBrandId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_brand_id is null");
		}
		if (itemDTO.getItemType() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_type is null");
		}
		if (itemDTO.getIconUrl() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "icon_url is null");
		}

		if (itemDTO.getCategoryId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "category_id is null");
		}
		// TODO 验证 CategoryId在数据库是否存在

		// TODO 验证品牌ID在数据库是否存在

		// TODO 供应商ID验证
		if (itemDTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}

	}

	private void verifyUpdatedItemDTOProperty(ItemDTO itemDTO) throws ItemException {
		// TODO 验证ItemDTO字段属性
		if (itemDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
		}
		if (itemDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
		}
		// TODO 供应商ID验证
		if (itemDTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
	}

	public List<ItemDTO> queryItem(ItemQTO itemQTO) throws ItemException {
		// TODO 供应商ID验证
		if (itemQTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
		// sellerId = 0默认BOSS
		if(itemQTO.getSellerId() == 0) {
			itemQTO.setSellerId(null);
		}

		List<ItemDO> list = itemDAO.queryItem(itemQTO);
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表
		for (ItemDO itemDO : list) {
			ItemDTO dto = new ItemDTO();
			ItemUtil.copyProperties(itemDO, dto);
			dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
			dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));
			itemDTOList.add(dto);
		}
		return itemDTOList;
	}

	public Integer countGroupItem(ItemQTO itemQTO) throws ItemException {
		if (itemQTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
		return itemDAO.countGroupItem(itemQTO);
	}
	@Override
	public void updateItemSaleUp() throws ItemException {
		try {
			itemDAO.updateItemSaleStateUp();
		} catch (Throwable e) {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "up down item error");
		}
	}
	@Override
	public void updateItemSaleDown() throws ItemException {

		try {
			itemDAO.updateItemSaleStateDown();
		} catch (Throwable e) {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "up down item error");
		}

	}
	@Override
	public List<ItemDTO> queryItemSaleUp(ItemQTO itemQTO) throws ItemException {
		List<ItemDO> list = itemDAO.queryItemSaleUp(itemQTO);
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表
		for (ItemDO itemDO : list) {
			ItemDTO dto = new ItemDTO();
			ItemUtil.copyProperties(itemDO, dto);
			dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
			dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));
			itemDTOList.add(dto);
		}
		return itemDTOList;
	}
	@Override
	public List<ItemDTO> queryItemSaleDown(ItemQTO itemQTO) throws ItemException {
		List<ItemDO> list = itemDAO.queryItemSaleDown(itemQTO);
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表
		for (ItemDO itemDO : list) {
			ItemDTO dto = new ItemDTO();
			ItemUtil.copyProperties(itemDO, dto);
			dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
			dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));
			itemDTOList.add(dto);
		}
		return itemDTOList;
	}

	@Override
	public int withdrawItem(Long itemId, Long supplierId) throws ItemException {
		Integer itemStatus = ItemStatus.WITHDRAW.getStatus();//下架的状态
		int result = this.itemDAO.updateItemState(itemId, supplierId, itemStatus);
		return result;
	}
	
	@Override
	public int upItem(Long itemId, Long supplierId) throws ItemException {
		Integer itemStatus = ItemStatus.ON_SALE.getStatus();//上架的状态
		int result = this.itemDAO.updateItemState(itemId, supplierId, itemStatus);
		return result;
	}

	@Override
	public int preSaleItem(Long itemId, Long supplierId) throws ItemException {
		Integer itemStatus = ItemStatus.PRE_SALE.getStatus();//上架的状态
		int result = this.itemDAO.updateItemState(itemId, supplierId, itemStatus);
		return result;
	}

	@Override
	public int thawItem(Long itemId, Long supplierId) throws ItemException {
		Integer itemStatus = ItemStatus.ON_SALE.getStatus();// 上架的状态
		int result = this.itemDAO.updateItemState(itemId, supplierId, itemStatus);
		return result;
	}

	@Override
	public int freezeItem(Long itemId, Long supplierId) throws ItemException {
		Integer itemStatus = ItemStatus.FROZEN.getStatus();// 冻结的状态
		int result = this.itemDAO.updateItemState(itemId, supplierId, itemStatus);
		return result;
	}

	public String uploadItemDesc(String itemDesc) throws ItemException {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("media_auth_key", "6r4XkF6EcE");
		File file = null;
		try{
			file = processUploadFile(itemDesc);
		}catch(Exception e){
			//TODO error handle
		}

		//暂时只支持单文件上传
		if(file != null){
//			String response = HttpUtil.uploadFile(ITEM_DESC_UPLOAD_URL, "images", file, paramMap);
		}else{
			//TODO error handle
		}

		return null;
	}

	// 处理上传的文件
	private File processUploadFile(String itemDesc) throws Exception {
		// 此时的文件名包含了完整的路径，得注意加工一下
		String filename = ""+System.currentTimeMillis();
		System.out.println("完整的文件名：" + filename);
		int index = filename.lastIndexOf("\\");
		filename = filename.substring(index + 1, filename.length());

		long fileSize = itemDesc.length();

		if("".equals(filename) && fileSize == 0)
		{
//			log.error("文件名为空 ...");
			return null;
		}

		File uploadFile = new File(FILE_PATH + "/" + filename);
		if(!uploadFile.exists()){
			uploadFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(uploadFile);
		byte[] data = itemDesc.getBytes();
		fos.write(data, 0, data.length);
		fos.close();
		return uploadFile;
	}

	@Override
	public Boolean isItemExist(ItemQTO itemQTO) throws ItemException {
		Object obj = this.itemDAO.isItemExist(itemQTO);
		return (obj != null);
	}
	
}
