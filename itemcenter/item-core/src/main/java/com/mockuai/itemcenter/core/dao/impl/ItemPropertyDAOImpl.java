package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.core.dao.ItemPropertyDAO;
import com.mockuai.itemcenter.core.domain.ItemPropertyDO;

@Service
public class ItemPropertyDAOImpl extends SqlMapClientDaoSupport implements ItemPropertyDAO {

	public ItemPropertyDAOImpl() {
		super();
	}

	public Long addItemProperty(ItemPropertyDO itemPropertyDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("ItemPropertyDAO.addItemProperty", itemPropertyDO);
		return newInsertedId;
	}

	public ItemPropertyDO getItemProperty(Long id, Long sellerId) {
		ItemPropertyDO qto = new ItemPropertyDO();
		qto.setSellerId(sellerId);
		qto.setId(id);
		ItemPropertyDO record = (ItemPropertyDO) getSqlMapClientTemplate()
				.queryForObject("ItemPropertyDAO.getItemProperty", qto);
		return record;
	}

	public int deleteItemProperty(Long id, Long sellerId) {
		ItemPropertyDO itemPropertyDO = new ItemPropertyDO();
		itemPropertyDO.setId(id);
		itemPropertyDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().delete("ItemPropertyDAO.deleteItemProperty", itemPropertyDO);
		return rows;
	}

	public int updateItemProperty(ItemPropertyDO itemPropertyDO) {
		int rows = getSqlMapClientTemplate().update("ItemPropertyDAO.updateItemProperty", itemPropertyDO);
		return rows;
	}

	public List<ItemPropertyDO> queryItemProperty(ItemPropertyQTO itemPropertyQTO) {
		if (null != itemPropertyQTO.getNeedPaging() && itemPropertyQTO.getNeedPaging().booleanValue()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemPropertyDAO.countItemProperty", itemPropertyQTO);// 总记录数
			itemPropertyQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemPropertyDO>();
			} else {
				itemPropertyQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemPropertyDO> list = getSqlMapClientTemplate()
				.queryForList("ItemPropertyDAO.queryItemProperty", itemPropertyQTO);
		return list;
	}

	public int deleteByItemId(ItemPropertyDO itemPropertyDO){
		return this.getSqlMapClientTemplate().update("ItemPropertyDAO.deleteByItemId",itemPropertyDO);
	}
	
//	protected class WhereParms extends ItemPropertyQTO {
//		private Object record;
//
//		public WhereParms(Object record) {
//			this.record = record;
//		}
//
//		public Object getRecord() {
//			return record;
//		}
//	}

}