package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mockuai.itemcenter.core.dao.ItemDAO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;

@Service
public class ItemDAOImpl extends SqlMapClientDaoSupport implements ItemDAO {

	public ItemDAOImpl() {
		super();
	}

	public Long  addItem(ItemDO itemDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemDAO.addItem", itemDO);
		return newInsertedId;
	}

	public ItemDO getItem(Long id, Long supplierId) {
		ItemDO itemDO = new ItemDO();
		itemDO.setSellerId(supplierId);
		itemDO.setId(id);
		ItemDO record = (ItemDO) getSqlMapClientTemplate().queryForObject("ItemDAO.getItem", itemDO);
		return record;
	}

	public int deleteItem(Long id, Long supplierId) {
		ItemDO itemDO = new ItemDO();
		itemDO.setId(id);
		itemDO.setSellerId(supplierId);

		int rows = getSqlMapClientTemplate().delete("ItemDAO.deleteItem", itemDO);
		return rows;
	}

	public int updateItem(ItemDO itemDO) {
//		WhereParms parms = new WhereParms(itemDO);
//		parms.setId(itemDO.getId());
//		parms.setSellerId(itemDO.getSellerId());
		int rows = getSqlMapClientTemplate().update("ItemDAO.updateItem", itemDO);
		return rows;
	}

	@Override
	public int removeItemFromGroup(ItemDO itemDO) {
		int rows = getSqlMapClientTemplate().update("ItemDAO.removeItemFromGroup", itemDO);
		return rows;
	}

	public int removeItemToDefaultGroup(ItemDO itemDO) {
		int rows = getSqlMapClientTemplate().update("ItemDAO.removeItemToDefaultGroup", itemDO);
		return rows;
	}

	@Override
	public int countGroupItem(ItemQTO itemQTO) {
		Integer totalCount = (Integer) getSqlMapClientTemplate().queryForObject("ItemDAO.countItem", itemQTO);
		return totalCount;
	}

	public List<ItemDO> queryItem(ItemQTO itemQTO) {
		if (null != itemQTO.getNeedPaging() && itemQTO.getNeedPaging().booleanValue()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate().queryForObject("ItemDAO.countItem", itemQTO);// 总记录数
			itemQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemDO>();
			} else {
				itemQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemDO> list = getSqlMapClientTemplate().queryForList("ItemDAO.queryItemList", itemQTO);
		return list;
	}

	public int updateItemState(Long id, Long supplier_id, Integer state) {
		ItemDO itemDO = new ItemDO();
		itemDO.setItemStatus(state);
		itemDO.setId(id);
		itemDO.setSellerId(supplier_id);
		itemDO.setDeleteMark(0);
		
		// updated by cwr 
//		WhereParms parms = new WhereParms(itemDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(id);
//		parms.setSupplierId(supplier_id);
		
		int rows = getSqlMapClientTemplate().update("ItemDAO.updateItemState", itemDO);
		return rows;
	}

	@Override
	public int removeItemSaleEnd(Long id, Long supplier_id) {
		ItemDO itemDO = new ItemDO();
		itemDO.setId(id);
		itemDO.setSellerId(supplier_id);
		itemDO.setDeleteMark(0);

		int rows = getSqlMapClientTemplate().update("ItemDAO.removeItemSaleEndTime", itemDO);
		return rows;
	}

	//	商品上下架更新
	@Override
	public void updateItemSaleStateUp() {
		getSqlMapClientTemplate().update("ItemDAO.updateItemSaleUp");
	}

	@Override
	public void updateItemSaleStateDown() {
		getSqlMapClientTemplate().update("ItemDAO.updateItemSaleDown");
	}

	@Override
	public List<ItemDO> queryItemSaleUp(ItemQTO itemQTO) {
		List<ItemDO> list = getSqlMapClientTemplate().queryForList("ItemDAO.queryItemSaleUp", itemQTO);
		if (null != itemQTO.getNeedPaging() && itemQTO.getNeedPaging().booleanValue()) {
			Integer totalCount = list.size();// 总记录数
			itemQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemDO>();
			} else {
				itemQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		return list;
	}

	@Override
	public List<ItemDO> queryItemSaleDown(ItemQTO itemQTO) {
		List<ItemDO> list = getSqlMapClientTemplate().queryForList("ItemDAO.queryItemSaleDown", itemQTO);
		if (null != itemQTO.getNeedPaging() && itemQTO.getNeedPaging().booleanValue()) {
			Integer totalCount = list.size();// 总记录数
			itemQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemDO>();
			} else {
				itemQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		return list;
	}

	
	public Object isItemExist(ItemQTO itemQTO){
		Object result = this.getSqlMapClientTemplate().queryForObject("ItemDAO.isItemExist",itemQTO);
		return result;
	}
	
	protected class WhereParms extends ItemQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}