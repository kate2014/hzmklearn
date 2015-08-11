package com.mockuai.datacenter.core.dao;

import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.domain.PageViewDO;

import java.util.List;


/**
 * Created by wanghailong on 15-8-5.
 */
public interface PageViewDAO {

    /**
     * 增加访问记录
     */
    int addPageView(PageViewDO pageViewDO);

    /**
     * 查询商品浏览TOP10
     * @param pageViewQTO
     * @return
     */
    List<PageViewDO> queryItemTop(PageViewQTO pageViewQTO);

    /**
     * 查询客户区域
     */
    List<PageViewDO> queryVisitorArea(PageViewQTO pageViewQTO);

    /**
     * 查询设备
     */
    List<PageViewDO> queryDeviceType(PageViewQTO pageViewQTO);

    /**
     *统计商铺前一天，前一周，总PVUV
     * @param pageViewQTO
     * @return
     */
    Integer countShopPv(PageViewQTO pageViewQTO);
    Integer countShopUv(PageViewQTO pageViewQTO);
    Integer countShopUvTotal(PageViewQTO pageViewQTO);

    /**
     * 查询一个时间段内商铺PVUV
     */
    List<PageViewDO> queryShopPv(PageViewQTO pageViewQTO);
    List<PageViewDO> queryShopUv(PageViewQTO pageViewQTO);

    /**
     * 按小时统计
     */
    List<PageViewDO> queryShopHourUv(PageViewQTO pageViewQTO);

    /**
     * 新访客
     * @param pageViewQTO
     * @return
     */
    PageViewDO countNewVisitor(PageViewQTO pageViewQTO);
}
