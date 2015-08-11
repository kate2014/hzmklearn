package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.ArrayList;
import java.util.List;

public class DeleteCollection extends BaseAction{

    public MopResponse execute(Request request) {
        String itemUidListStr = (String)request.getParam("item_uid_list");
        Long userId = (Long)request.getAttribute("user_id");
        List<String> itemUidList = JsonUtil.parseJson(itemUidListStr, List.class);
        List<Long> itemIdList = new ArrayList<Long>();
        List<ItemCollectionDTO> itemCollectionDTOList = new ArrayList<ItemCollectionDTO>();
        for(String itemUid:itemUidList){
            try{
                ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUid);
                itemIdList.add(itemUidDTO.getItemId());
            }catch(Exception e){
                //TODO error handle
            }
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setParam("itemIdList", itemIdList);
        itemReq.setParam("userId", userId);
        itemReq.setCommand("deleteItemCollection");
        Response<ItemCollectionDTO> collectionResp = this.getItemService().execute(itemReq);
        return MopApiUtil.transferResp(collectionResp);
    }

    public String getName() {
        // TODO Auto-generated method stub
        return "/item/collection/delete";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }

}
