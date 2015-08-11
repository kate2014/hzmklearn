package com.mockuai.datacenter.client;


import com.mockuai.datacenter.common.api.Response;
import com.mockuai.datacenter.common.domain.dto.DataDTO;
import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;

import java.util.List;

/**
 * Created by wanghailong on 15-8-6.
 */
public interface DataClient {

    public Response<String> addPageView(DataDTO dataDTO);

    /**
     * 查询商品浏览TOP10
     * @param pageViewQTO
     * @return
     */
    public Response<List<PageViewDTO>> queryItemTop(PageViewQTO pageViewQTO);

    /**
     * 查询客户区域
     * @param pageViewQTO
     * @return
     */
    public Response<List<PageViewDTO>> queryVisitorArea(PageViewQTO pageViewQTO);


    /**
     * 查询设备
     * @param pageViewQTO
     * @return
     */
    public Response<List<PageViewDTO>> queryDeviceType(PageViewQTO pageViewQTO);


    /**
     *统计商铺前一天，前一周，总PVUV
     * @param pageViewQTO
     * @return
     */
    public Response<Integer> countShopPv(PageViewQTO pageViewQTO);
    public Response<Integer> countShopUv(PageViewQTO pageViewQTO);
    public Response<Integer> countShopUvTotal(PageViewQTO pageViewQTO);

    /**
     * 查询一个时间段内商铺PVUV
     */
    public Response<List<PageViewDTO>> queryShopPv(PageViewQTO pageViewQTO);
    public Response<List<PageViewDTO>> queryShopUv(PageViewQTO pageViewQTO);

    /**
     * 按小时统计
     */
    public Response<List<PageViewDTO>> queryShopHourUv(PageViewQTO pageViewQTO);

    /**
     * 查询每日新客户数量
     */
    public Response<List<PageViewDTO>> countNewVisitor(PageViewQTO pageViewNVQTO);

}
