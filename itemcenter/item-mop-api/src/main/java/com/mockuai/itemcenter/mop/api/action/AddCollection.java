package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
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

/**
* Created by qqyu
*/
public class AddCollection extends BaseAction{
    public MopResponse execute(Request request) {
        String itemUidListStr = (String)request.getParam("item_uid_list");
        Long userId = (Long)request.getAttribute("user_id");
        List<String> itemUidList = JsonUtil.parseJson(itemUidListStr, List.class);
        List<ItemCollectionDTO> itemCollectionDTOList = new ArrayList<ItemCollectionDTO>();
        for(String itemUid:itemUidList){
            try{
                ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUid);
                ItemCollectionDTO itemCollectionDTO = new ItemCollectionDTO();
                itemCollectionDTO.setItemId(itemUidDTO.getItemId());
                itemCollectionDTO.setSellerId(itemUidDTO.getSellerId());
                itemCollectionDTO.setUserId(userId);
                itemCollectionDTOList.add(itemCollectionDTO);
            }catch(Exception e){
                //TODO error handle
            }
        }
        com.mockuai.itemcenter.common.api.Request collectionReq = new BaseRequest();
        collectionReq.setCommand(ActionEnum.ADD_ITEM_COLLECTION.getActionName());
        collectionReq.setParam("itemCollectionList", itemCollectionDTOList);
        Response<ItemCollectionDTO> collectionResp = this.getItemService().execute(collectionReq);

        return new MopResponse(collectionResp.getCode(), collectionResp.getMessage());
    }

    public String getName() {
        return "/item/collection/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
