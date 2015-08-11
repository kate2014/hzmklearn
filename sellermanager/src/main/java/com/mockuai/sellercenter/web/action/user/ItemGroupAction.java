package com.mockuai.sellercenter.web.action.user;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.manager.ItemBrandManager;
import com.mockuai.sellercenter.web.manager.ItemCategoryManager;
import com.mockuai.sellercenter.web.manager.ItemManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 商品分组;
 * Created by luliang on 15/7/31.
 */
@Controller
public class ItemGroupAction extends BaseValidator {

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemCategoryManager itemCategoryManager;

    @Resource
    private ItemBrandManager itemBrandManager;

    @RequestMapping(value="/item/group/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    private String queryItemByGroup(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setSellerId(sellerId);
        Integer page = null;
        Integer pageSize = null;
        Long groupId = null;
        try {
            groupId = RequestUtils.getLong(request, "group_id", false);
            page = RequestUtils.getInt(request, "page", false);
            pageSize = RequestUtils.getInt(request, "page_size", false);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        // 组ID;
        itemQTO.setGroupId(groupId);
        if(page != null) {
            itemQTO.setNeedPaging(true);
            itemQTO.setOffset((page - 1) * pageSize);
            itemQTO.setPageSize(pageSize);
        }
        Response<List<ItemDTO>> response = null;
        try {
            response = itemManager.queryItem(itemQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        Set<Long> brandIdSet = new HashSet<Long>();
        Set<Long> categoryIdSet =new HashSet<Long>();
        if(!CollectionUtils.isEmpty(response.getModule())){
            for(ItemDTO item : response.getModule()){
                brandIdSet.add(item.getItemBrandId());
                categoryIdSet.add(item.getCategoryId());
                item.setCreateTime(ResponseUtils.toFormatDate(item.getGmtCreated(),1));
            }

            Long[] brandIds = new Long[brandIdSet.size()];
            brandIdSet.toArray(brandIds);
            Long[] categoryIds = new Long[categoryIdSet.size()];
            categoryIdSet.toArray(categoryIds);
            System.out.println(brandIdSet);
            System.out.println(categoryIdSet);

            ItemCategoryQTO categoryQTO = new ItemCategoryQTO();
            //TODO
            //categoryQTO.setBizCode(bizCode);
            categoryQTO.setIds(categoryIds);

            SellerBrandQTO sellerBrandQTO =new SellerBrandQTO();
            sellerBrandQTO.setIds(brandIds);

            //sellerBrandQTO.setBizCode(bizCode);

            List<ItemCategoryDTO> categoryList =null;
            List<SellerBrandDTO> brandList = null;
            try{
                categoryList = this.itemCategoryManager.queryCategory(categoryQTO);
                brandList = this.itemBrandManager.querySellerBrand(sellerBrandQTO);
            }catch(ServiceException e){
                return ServiceResponseHandler.serviceExceptionHandler(e);
            }
            Map<Long,String> brandNameMap =new HashMap<Long,String>();
            Map<Long,String> categoryNameMap =new HashMap<Long,String>();

            if(!CollectionUtils.isEmpty(categoryList)){
                for(ItemCategoryDTO item : categoryList){
                    categoryNameMap.put(item.getId(), item.getCateName());
                }
            }
            if(!CollectionUtils.isEmpty(brandList)){
                for(SellerBrandDTO item : brandList){
                    brandNameMap.put(item.getId(), item.getBrandName());
                }
            }
            List<ItemDTO> itemList = response.getModule();
            if(itemList != null){
                for(ItemDTO item: response.getModule()){
                    item.setBrandName(brandNameMap.get(item.getItemBrandId()));
                    item.setCategoryName(categoryNameMap.get(item.getCategoryId()));
                }
            }
        }

        PageDTO<List<ItemDTO>> pageInfo =new PageDTO<List<ItemDTO>>();
        pageInfo.setData(response.getModule());
        pageInfo.setTotalCount(response.getTotalCount());
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageInfo);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageInfo) + ")";
        }
    }

//    @RequestMapping(value="/item/group/add.do",produces="application/json; charset=utf-8")
//    @ResponseBody
//    private String addItemInGroup(HttpServletRequest request) {
//        String callback = request.getParameter("callback");
//        Long sellerId = SessionManager.getLoginUserId(request.getSession());
//        if(sellerId == null) {
//            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
//        }
//        Long itemId = null, groupId = null;
//        try {
//            itemId = RequestUtils.getLong(request, "item_id", true);
//            groupId = RequestUtils.getLong(request, "group_id", true);
//        } catch (ParamException e) {
//            return ResponseUtils.getFailApiResponseStr(e);
//        }
//
//        Boolean result = null;
//        try {
//            result = itemManager.addItemInGroup(sellerId, itemId, groupId);
//        } catch (ServiceException e) {
//            return ServiceResponseHandler.serviceExceptionHandler(e);
//        }
//
//        if(StringUtils.isBlank(callback)){
//            return ResponseUtils.getSuccessApiResponseStr(result);
//        }else{
//            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
//        }
//    }
//
//    @RequestMapping(value="/item/group/remove.do",produces="application/json; charset=utf-8")
//    @ResponseBody
//    private String removeItemFromGroup(HttpServletRequest request) {
//        String callback = request.getParameter("callback");
//        Long sellerId = SessionManager.getLoginUserId(request.getSession());
//        if(sellerId == null) {
//            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
//        }
//        Long itemId = null;
//        try {
//            itemId = RequestUtils.getLong(request, "item_id", true);
//        } catch (ParamException e) {
//            return ResponseUtils.getFailApiResponseStr(e);
//        }
//
//        Boolean result = null;
//        try {
//            result = itemManager.removeItemFromGroup(sellerId, itemId);
//        } catch (ServiceException e) {
//            return ServiceResponseHandler.serviceExceptionHandler(e);
//        }
//
//        if(StringUtils.isBlank(callback)){
//            return ResponseUtils.getSuccessApiResponseStr(result);
//        }else{
//            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
//        }
//    }

}
