package com.mockuai.itemcenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.itemcenter.core.dao.CornerIconDAO;
import com.mockuai.itemcenter.core.domain.CornerIconDO;

@Service
public class CornerIconDAOImpl extends SqlMapClientDaoSupport implements CornerIconDAO {

	@Override
	public Long addCornerIcon(CornerIconDO cornerIconDO) {
		return (Long)this.getSqlMapClientTemplate().insert("CornerIconDAO.addCornerIcon",cornerIconDO);
	}

	@Override
	public int deleteCornerIcon(CornerIconDO cornerIconDO) {
		return this.getSqlMapClientTemplate().update("CornerIconDAO.deleteCornerIcon",cornerIconDO);
	}

	@Override
	public List<CornerIconDO> queryCornerIcon(CornerIconQTO cornerIconQTO) {
		return (List<CornerIconDO>)this.getSqlMapClientTemplate().queryForList("CornerIconDAO.queryCornerIcon",cornerIconQTO);
	}
	
	@Override
	public CornerIconDO getCornerIcon(CornerIconDO cornerIconDO){
		return (CornerIconDO)this.getSqlMapClientTemplate().queryForObject("CornerIconDAO.getCornerIcon",cornerIconDO);
	}
}
