package com.mockuai.datacenter.core.dao.impl;

import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.dao.IpAreaDAO;
import com.mockuai.datacenter.core.domain.PageViewDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class IpAreaDAOImpl extends SqlMapClientDaoSupport implements IpAreaDAO{
	public String getArea(PageViewQTO pageViewQTO) {
		String area = (String)getSqlMapClientTemplate().queryForObject("ip_area.getArea", pageViewQTO);
		return area;
   }

  public int addIpArea(PageViewDO pageViewDO){
     getSqlMapClientTemplate().insert("ip_area.addIpArea", pageViewDO);
     return 1;
  }
  
}
