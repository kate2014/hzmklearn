package com.mockuai.datacenter.core.service.action.pageview;

import com.mockuai.datacenter.common.api.DataResponse;
import com.mockuai.datacenter.common.constant.ActionEnum;
import com.mockuai.datacenter.common.constant.ResponseCode;
import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.manager.PageViewManager;
import com.mockuai.datacenter.core.service.DataRequest;
import com.mockuai.datacenter.core.service.RequestContext;
import com.mockuai.datacenter.core.service.action.TransAction;
import com.mockuai.datacenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wanghailong on 15-8-7.
 */
@Service
public class CountShopPvAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(CountShopPvAction.class);

    @Resource
    PageViewManager pageViewManager;
    @Override
    protected DataResponse doTransaction(RequestContext context) throws DataException {
        DataResponse dataResponse = null;
        DataRequest dataRequest = context.getRequest();
        PageViewQTO pageViewQTO = (PageViewQTO) dataRequest.getParam("pageViewQTO");

        try {
            Integer pvList = pageViewManager.countShopPv(pageViewQTO);
            dataResponse = ResponseUtil.getSuccessResponse(pvList);

        } catch(DataException e) {
            dataResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + dataRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return dataResponse;
        }
        return dataResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.COUNT_SHOP_PV.getActionName();
    }
}
