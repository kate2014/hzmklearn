package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class SubCateList extends BaseAction{
    public MopResponse execute(Request request) {
        String categoryIdStr = (String)request.getParam("category_id");
        long categoryId = 0;
        if(StringUtils.isBlank(categoryIdStr) == false){
            categoryId = Long.valueOf(categoryIdStr);
        }
        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_ITEM_CATEGORY.getActionName());
        ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
        itemCategoryQTO.setParentId(categoryId);
        itemReq.setParam("itemCategoryQTO", itemCategoryQTO);
        Response<List<ItemCategoryDTO>> itemResp = this.getItemService().execute(itemReq);
        if(itemResp.getCode() == ResponseCode.SUCCESS.getCode()){
            return new MopResponse(MopApiUtil.genMopCategoryDTOList(itemResp.getModule()));
        }else{
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/category/subcate/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
