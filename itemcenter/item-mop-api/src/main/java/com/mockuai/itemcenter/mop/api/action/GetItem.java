package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemDTO;
import com.mockuai.itemcenter.mop.api.domain.PropertyDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by zengzhangqiang on 5/4/15.
*/
public class GetItem extends BaseAction{
    private static final Logger log = LoggerFactory.getLogger(GetItem.class);

    public MopResponse execute(Request request) {
        String itemUidStr = (String)request.getParam("item_uid");
        Long userId = (Long)request.getAttribute("user_id");

        ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUidStr);
        if(itemUidDTO == null){
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "item_uid is invalid");
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.GET_ITEM.getActionName());
        itemReq.setParam("id", itemUidDTO.getItemId());
        itemReq.setParam("supplierId", itemUidDTO.getSellerId());
        itemReq.setParam("needDetail", true);

        try{
            Response<ItemDTO> itemResp = this.getItemService().execute(itemReq);

            if(itemResp.getCode() == ResponseCode.SUCCESS.getCode()){
                Map<String,Object> data = new HashMap<String, Object>();
                MopItemDTO mopItemDTO = MopApiUtil.genMopItem(itemResp.getModule());
                data.put("item", mopItemDTO);

                //TODO 收藏状态判断逻辑，性能待优化
                //如果用户已登录，则需要判断商品的收藏状态
                boolean isCollected = false;
                if(userId != null){
                    ItemCollectionQTO itemCollectionQTO = new ItemCollectionQTO();
                    itemCollectionQTO.setUserId(userId);
                    itemCollectionQTO.setItemId(itemResp.getModule().getId());
                    itemReq = new BaseRequest();
                    itemReq.setCommand(ActionEnum.QUERY_ITEM_COLLECTION.getActionName());
                    itemReq.setParam("itemCollectionQTO", itemCollectionQTO);
                    Response<List<ItemDTO>> queryItemCollectResp = this.getItemService().execute(itemReq);
                    List<PropertyDTO> bizPropertyDTOList = mopItemDTO.getBizPropertyList();
                    if(bizPropertyDTOList == null){
                        bizPropertyDTOList = new ArrayList<PropertyDTO>(1);
                        mopItemDTO.setBizPropertyList(bizPropertyDTOList);
                    }
                    PropertyDTO bizProperty = new PropertyDTO();
                    bizPropertyDTOList.add(bizProperty);
                    bizProperty.setName("collect_status");
                    bizProperty.setCode("IC_SYS_P_BIZ_000001");
                    //TODO valueType取值列表要明确定下来
                    bizProperty.setValueType(1);
                    if(queryItemCollectResp.getCode()==ResponseCode.SUCCESS.getCode()
                            && queryItemCollectResp.getTotalCount()>0){
                        bizProperty.setValue("1");
                    }else{
                        bizProperty.setValue("0");
                    }
                }

                return new MopResponse(data);
            }else{
                return new MopResponse(itemResp.getCode(), itemResp.getMessage());
            }

        }catch(Exception e){
            log.error("itemUid="+itemUidStr, e);
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }

    }

    public String getName() {
        return "/item/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

    public static void main(String[] args){
        MopItemDTO mopItemDTO = new MopItemDTO();
        List<PropertyDTO> bizPropertyList = mopItemDTO.getBizPropertyList();
        bizPropertyList = new ArrayList<PropertyDTO>();
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setCode("123");
        bizPropertyList.add(propertyDTO);
        mopItemDTO.setBizPropertyList(bizPropertyList);
        System.out.println(JsonUtil.toJson(mopItemDTO));
    }

}
