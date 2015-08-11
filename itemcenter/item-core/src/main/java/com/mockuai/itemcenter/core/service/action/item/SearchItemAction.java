package com.mockuai.itemcenter.core.service.action.item;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
@Service
public class SearchItemAction implements Action{
    @Resource
    private ItemSearchManager itemSearchManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemSearchQTO itemSearchQTO = (ItemSearchQTO)context.getRequest().getParam("itemSearchQTO");
        String bizCode = (String)context.getRequest().getAttribute("bizCode");
        //TODO 应用中心可用之前，这里先写死为洋东西的bizCode
        if(StringUtils.isBlank(bizCode)){
            bizCode = "yangdongxi";
        }
        try{
            itemSearchQTO.setBizCode(bizCode);
            return itemSearchManager.searchItemIndex(itemSearchQTO);
        }catch(ItemException e){
            return ResponseUtil.getErrorResponse(e.getResponseCode());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.SEARCH_ITEM.getActionName();
    }
}
