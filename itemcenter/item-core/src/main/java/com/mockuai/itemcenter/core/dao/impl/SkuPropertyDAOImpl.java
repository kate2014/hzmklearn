package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyDAO;
import com.mockuai.itemcenter.core.domain.SkuPropertyDO;

@Service
public class SkuPropertyDAOImpl extends SqlMapClientDaoSupport implements SkuPropertyDAO {

	public SkuPropertyDAOImpl() {
		super();
	}

	public Long addSkuProperty(SkuPropertyDO skuPropertyDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("SkuPropertyDAO.addSkuProperty", skuPropertyDO);
		return newInsertedId;
	}

	public SkuPropertyDO getSkuProperty(Long id, Long sellerId) {
		SkuPropertyDO qto = new SkuPropertyDO();
		qto.setId(id);
		qto.setSellerId(sellerId);
		SkuPropertyDO skuPropertyDO = (SkuPropertyDO) getSqlMapClientTemplate()
				.queryForObject("SkuPropertyDAO.getSkuProperty", qto);
		return skuPropertyDO;
	}

	public int deleteSkuProperty(Long id, Long sellerId) {
		SkuPropertyDO skuPropertyDO = new SkuPropertyDO();
		skuPropertyDO.setId(id);
		skuPropertyDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("SkuPropertyDAO.deleteSkuProperty", skuPropertyDO);
		return rows;
	}

	public int deleteSkuPropertyBySkuId(Long skuId, Long sellerId) {
		SkuPropertyDO skuPropertyDO = new SkuPropertyDO();
		skuPropertyDO.setSkuId(skuId);
		skuPropertyDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("SkuPropertyDAO.deleteSkuProperty", skuPropertyDO);
		return rows;
	}

	public int updateSkuProperty(SkuPropertyDO skuPropertyDO) {
		int rows = getSqlMapClientTemplate().update("SkuPropertyDAO.updateSkuProperty", skuPropertyDO);
		return rows;
	}

	public List<SkuPropertyDO> querySkuProperty(SkuPropertyQTO skuPropertyQTO) {
		
		if (null != skuPropertyQTO.getNeedPaging() && skuPropertyQTO.getNeedPaging().booleanValue()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("SkuPropertyDAO.countSkuProperty", skuPropertyQTO);// 总记录数
			skuPropertyQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<SkuPropertyDO>();
			} else {
				skuPropertyQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
			totalCount = null;
		}
		List<SkuPropertyDO> list = getSqlMapClientTemplate()
				.queryForList("SkuPropertyDAO.querySkuProperty", skuPropertyQTO);
		return list;
	}
	
	public List<SkuPropertyDO> querySkuPropertyWithValue(SkuPropertyQTO skuPropertyQTO) {
		if (null != skuPropertyQTO.getNeedPaging() && skuPropertyQTO.getNeedPaging().booleanValue()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("SkuPropertyDAO.countSkuProperty", skuPropertyQTO);// 总记录数
			skuPropertyQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<SkuPropertyDO>();
			} else {
				skuPropertyQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
			totalCount = null;
		}
		List<SkuPropertyDO> list = getSqlMapClientTemplate()
				.queryForList("SkuPropertyDAO.querySkuPropertyWithValue", skuPropertyQTO);
		return list;
	}
	
	
	public int deleteByItemId(SkuPropertyQTO qto){
		return this.getSqlMapClientTemplate().update("SkuPropertyDAO.deleteByItemId",qto);
	}

//	protected class WhereParms extends SkuPropertyQTO {
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