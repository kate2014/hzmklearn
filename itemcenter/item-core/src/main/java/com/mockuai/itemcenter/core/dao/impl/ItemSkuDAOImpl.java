package com.mockuai.itemcenter.core.dao.impl;

import java.util.List;

import com.mockuai.itemcenter.core.dao.ItemSkuDAO;
import com.mockuai.itemcenter.core.domain.ItemSkuDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;

@Service
public class ItemSkuDAOImpl extends SqlMapClientDaoSupport implements ItemSkuDAO {

	public ItemSkuDAOImpl() {
		super();
	}

	public Long addItemSku(ItemSkuDO itemSkuDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemSkuDAO.addItemSku", itemSkuDO);
		return newInsertedId;
	}

	public ItemSkuDO getItemSku(Long id, Long sellerId) {
		ItemSkuDO qto = new ItemSkuDO();
		qto.setId(id);
		qto.setSellerId(sellerId);
		ItemSkuDO record = (ItemSkuDO) getSqlMapClientTemplate().queryForObject("ItemSkuDAO.getItemSku", qto);
		return record;
	}

	public int deleteItemSku(Long id, Long sellerId) {
		ItemSkuDO itemSkuDO = new ItemSkuDO();
		itemSkuDO.setId(id);
		itemSkuDO.setSellerId(sellerId);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(id);
//		parms.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("ItemSkuDAO.deleteItemSku", itemSkuDO);
		return rows;
	}

	public int updateItemSku(ItemSkuDO itemSkuDO) {
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(itemSkuDO.getId());
//		parms.setSellerId(itemSkuDO.getSellerId());
		int rows = getSqlMapClientTemplate().update("ItemSkuDAO.updateItemSku", itemSkuDO);
		return rows;
	}

	public List<ItemSkuDO> queryItemSku(ItemSkuQTO ItemSkuQTO) {
		List<ItemSkuDO> list = getSqlMapClientTemplate().queryForList("ItemSkuDAO.queryItemSku", ItemSkuQTO);
		return list;
	}

	@Override
	public int increaseItemSkuStock(Long skuId, Long sellerId, Long increasedNumber) throws ItemException {
		ItemSkuDO itemSkuDO = new ItemSkuDO();
		itemSkuDO.setId(skuId);
		itemSkuDO.setSellerId(sellerId);
		itemSkuDO.setStockNum(increasedNumber);
		
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("ItemSkuDAO.increaseItemSkuStock", itemSkuDO);
		return rows;
	}

	@Override
	public int decreaseItemSkuStock(Long skuId, Long sellerId, Long decreasedNumber) throws ItemException {
		ItemSkuDO itemSkuDO = new ItemSkuDO();
		itemSkuDO.setStockNum(decreasedNumber);
		itemSkuDO.setId(skuId);
		itemSkuDO.setSellerId(sellerId);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("ItemSkuDAO.decreaseItemSkuStock", itemSkuDO);
		return rows;
	}

	public int updateItemSkuCodeValue(Long skuId, Long sellerId, String codeValue) {
		ItemSkuDO itemSkuDO = new ItemSkuDO();
		itemSkuDO.setSkuCode(codeValue);
		itemSkuDO.setId(skuId);
		itemSkuDO.setSellerId(sellerId);
		itemSkuDO.setSkuCode(codeValue);
		//WhereParms parms = new WhereParms(itemSkuDO);
		//parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		//parms.setId(skuId);
		//parms.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("ItemSkuDAO.updateItemSkuCodeValue", itemSkuDO);
		return rows;
	}
	
	public int deleteByItemId(ItemSkuDO itemSkuDO){
		int result = this.getSqlMapClientTemplate().update("ItemSkuDAO.deleteByItemId",itemSkuDO);
		return result;
	}

	protected class WhereParms extends ItemSkuQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}