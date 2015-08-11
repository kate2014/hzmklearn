package com.mockuai.datacenter.core.manager.impl;

import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.dao.IpAreaDAO;
import com.mockuai.datacenter.core.domain.PageViewDO;
import com.mockuai.datacenter.core.manager.IpAreaManager;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class IpAreaManagerImpl
  implements IpAreaManager
{

  @Resource
  private IpAreaDAO ipAreaDAO;

  public String getArea(PageViewQTO pageViewQTO)
  {
    String area = this.ipAreaDAO.getArea(pageViewQTO);
    return area;
  }

  public int addIpArea(PageViewDO pageViewDO)
  {
    return this.ipAreaDAO.addIpArea(pageViewDO);
  }
}