package com.mockuai.datacenter.core.manager;

import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.domain.PageViewDO;

public abstract interface IpAreaManager
{
  public abstract String getArea(PageViewQTO paramPageViewQTO);

  public abstract int addIpArea(PageViewDO paramPageViewDO);
}
