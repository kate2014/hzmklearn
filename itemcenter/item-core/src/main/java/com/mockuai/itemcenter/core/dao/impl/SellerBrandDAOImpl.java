package com.mockuai.itemcenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.dao.SellerBrandDAO;
import com.mockuai.itemcenter.core.domain.SellerBrandDO;

@Service
public class SellerBrandDAOImpl extends SqlMapClientDaoSupport implements SellerBrandDAO {

	@Override
	public List<SellerBrandDO> querySellerBrand(SellerBrandQTO sellerBrandQTO) {
		return (List<SellerBrandDO>)this.getSqlMapClientTemplate().queryForList("SellerBrandDAO.querySellerBrand",sellerBrandQTO);
	}

	@Override
	public Long addSellerBrand(SellerBrandDO sellerBrandDO) {
		return  (Long)this.getSqlMapClientTemplate().insert("SellerBrandDAO.addSellerBrand",sellerBrandDO);
	}

	@Override
	public int deleteSellerBrand(SellerBrandDO sellerBrandDO) {
		return this.getSqlMapClientTemplate().update("SellerBrandDAO.deleteSellerBrand",sellerBrandDO);
	}

	@Override
	public SellerBrandDO getSellerBrand(SellerBrandDO sellerBrandDO) {
		return (SellerBrandDO)this.getSqlMapClientTemplate().queryForObject("SellerBrandDAO.getSellerBrand",sellerBrandDO);
	}

	@Override
	public int updateSellerBrand(SellerBrandDO sellerBrandDO) {
		return this.getSqlMapClientTemplate().update("SellerBrandDAO.updateSellerBrand",sellerBrandDO);
	}
}
