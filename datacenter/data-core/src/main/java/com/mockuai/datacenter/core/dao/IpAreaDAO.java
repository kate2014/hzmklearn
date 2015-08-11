package com.mockuai.datacenter.core.dao;

import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.domain.PageViewDO;

public interface IpAreaDAO {
	public abstract String getArea(PageViewQTO paramPageViewQTO);

	public abstract int addIpArea(PageViewDO paramPageViewDO);
}
