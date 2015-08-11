package com.mockuai.itemcenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.core.dao.CompositeItemDAO;
import com.mockuai.itemcenter.core.domain.CompositeItemDO;

@Service
public class CompositeItemDAOImpl extends SqlMapClientDaoSupport implements CompositeItemDAO {

	@Override
	public Long addCompositeItem(CompositeItemDO compositeItemDO) {
		return (Long)this.getSqlMapClientTemplate().insert("CompositeItemDAO.addCompositeItem",compositeItemDO);
	}

	@Override
	public int deleteCompositeItemByItemId(Long itemId, Long supplierId) {
		CompositeItemDO compositeItemDO = new CompositeItemDO();
		compositeItemDO.setItemId(itemId);
		compositeItemDO.setSupplierId(supplierId);
		int result = this.getSqlMapClientTemplate().update("CompositeItemDAO.deleteByItemId",compositeItemDO);
		return result;
	}

	@Override
	public List<CompositeItemDO> getCompositeItemByItemId(Long id,
			Long supplierId) {
		CompositeItemDO compositeItemDO = new CompositeItemDO();
		List<CompositeItemDO> list = this.getSqlMapClientTemplate().queryForList("CompositeItemDAO.queryByItemId",compositeItemDO);
		return list;
	}
	
}
