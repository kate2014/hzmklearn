package com.mockuai.datacenter.client.impl;

import com.mockuai.datacenter.common.api.BaseRequest;
import com.mockuai.datacenter.common.api.DataService;
import com.mockuai.datacenter.client.DataClient;
import com.mockuai.datacenter.common.api.Request;
import com.mockuai.datacenter.common.api.Response;
import com.mockuai.datacenter.common.constant.ActionEnum;
import com.mockuai.datacenter.common.domain.dto.DataDTO;
import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wanghailong on 15-8-6.
 */
public class DataClientImpl implements DataClient {

    @Resource
    private DataService dataService;

    public Response<String> addPageView(DataDTO dataDTO) {
        Request request = new BaseRequest();
        request.setParam("dataDTO", dataDTO);
        request.setCommand(ActionEnum.ADD_PAGEVIEW.getActionName());
        return dataService.execute(request);
    }

    public Response<List<PageViewDTO>> queryItemTop(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewDTO", pageViewQTO);
        request.setCommand(ActionEnum.QUERY_ITEMTOP.getActionName());
        return dataService.execute(request);
    }

    public Response<List<PageViewDTO>> queryVisitorArea(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.QUERY_VISITORAREA.getActionName());
        return dataService.execute(request);
    }

    public Response<List<PageViewDTO>> queryDeviceType(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.QUERY_DEVICETYPE.getActionName());
        return dataService.execute(request);
    }

    public Response<Integer> countShopPv(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.COUNT_SHOP_PV.getActionName());
        return dataService.execute(request);
    }

    public Response<Integer> countShopUv(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.COUNT_SHOP_UV.getActionName());
        return dataService.execute(request);
    }

    public Response<Integer> countShopUvTotal(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.COUNT_SHOP_UV_TOTAL.getActionName());
        return dataService.execute(request);
    }

    public Response<List<PageViewDTO>> queryShopPv(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.QUERY_SHOP_PV.getActionName());
        return dataService.execute(request);
    }

    public Response<List<PageViewDTO>> queryShopUv(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.QUERY_SHOP_UV.getActionName());
        return dataService.execute(request);
    }

    public Response<List<PageViewDTO>> queryShopHourUv(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.QUERY_SHOP_HOUR_UV.getActionName());
        return dataService.execute(request);
    }

    public Response<List<PageViewDTO>> countNewVisitor(PageViewQTO pageViewQTO) {
        Request request = new BaseRequest();
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.COUNT_NEW_VISITOR.getActionName());
        return dataService.execute(request);
    }
}
