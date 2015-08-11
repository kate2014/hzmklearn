package com.mockuai.datacenter.core.manager;


import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.domain.PageViewDO;
import com.mockuai.datacenter.core.exception.DataException;

import java.util.List;


/**
 * Created by wanghailong on 15-8-5.
 */
public interface PageViewManager {

    /**
     * 添加访问信息
     * @param pageViewDTO
     * @return
     */
    public int addPageView(PageViewDO pageViewDO) throws DataException;

    /**
     * 查询商品浏览TOP10
     * @param pageViewQTO
     * @return
     */
    public List<PageViewDTO> queryItemTop(PageViewQTO pageViewQTO) throws DataException;

    /**
     *查询客户区域
     * @param pageViewQTO
     * @return
     */
    public List<PageViewDTO> queryVisitorArea(PageViewQTO pageViewQTO) throws DataException;

    /**
     *查询设备
     * @param pageViewQTO
     * @return
     */
    public List<PageViewDTO> queryDeviceType(PageViewQTO pageViewQTO) throws DataException;

    /**
     *统计商铺前一天，前一周，总PVUV
     * @param pageViewQTO
     * @return
     */
    public Integer countShopPv(PageViewQTO pageViewQTO) throws DataException;
    public Integer countShopUv(PageViewQTO pageViewQTO) throws DataException;
    public Integer countShopUvTotal(PageViewQTO pageViewQTO) throws DataException;

    /**
     * 查询一个时间段内商铺PVUV
     */
    public List<PageViewDTO> queryShopPv(PageViewQTO pageViewQTO) throws DataException;
    public List<PageViewDTO> queryShopUv(PageViewQTO pageViewQTO) throws DataException;

    /**
     * 按小时统计
     */
    public List<PageViewDTO> queryShopHourUv(PageViewQTO pageViewQTO) throws DataException;

    /**
     * 查询每日新客户数量
     */
    public PageViewDTO countNewVisitor(PageViewQTO pageVieQTO) throws DataException;

}
