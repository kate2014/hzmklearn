package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemDTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by zengzhangqiang on 5/4/15.
*/
public class ItemList extends BaseAction{
    public MopResponse execute(Request request) {
        String keyword = (String)request.getParam("keyword");
        String categoryIdStr = (String)request.getParam("category_id");
        String brandIdStr = (String)request.getParam("brand_id");
        String orderByStr = (String)request.getParam("order_by");
        String asc = (String)request.getParam("asc");
        String offset = (String)request.getParam("offset");
        String count = (String)request.getParam("count");


        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        if(StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)){
            itemSearchQTO.setOffset(Integer.parseInt(offset));
        }

        if(StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)){
            itemSearchQTO.setCount(Integer.parseInt(count));
        }

        if(StringUtils.isNotEmpty(categoryIdStr) && StringUtils.isNumeric(categoryIdStr)){
            itemSearchQTO.setCategoryId(Long.parseLong(categoryIdStr));
        }

        if(StringUtils.isNotEmpty(brandIdStr) && StringUtils.isNumeric(brandIdStr)){
            itemSearchQTO.setBrandId(Long.parseLong(brandIdStr));
        }

        if(StringUtils.isNotEmpty(orderByStr) && StringUtils.isNumeric(orderByStr)){
            itemSearchQTO.setOrderBy(Integer.parseInt(orderByStr));
        }

        if(StringUtils.isNotEmpty(asc) && StringUtils.isNumeric(asc)){
            itemSearchQTO.setAsc(Integer.parseInt(asc));
        }


        itemSearchQTO.setKeyword(keyword);

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.SEARCH_ITEM.getActionName());
        itemReq.setParam("itemSearchQTO", itemSearchQTO);
        Response<List<ItemSearchDTO>> itemResp = this.getItemService().execute(itemReq);
        if(itemResp.getCode() == ResponseCode.SUCCESS.getCode()){
            List<ItemSearchDTO> itemSearchDTOs = itemResp.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("item_list", MopApiUtil.genMopItemListForSearchDTO(itemSearchDTOs));
            data.put("total_count", itemResp.getTotalCount());
            return new MopResponse(data);
        }else{
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/item/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
