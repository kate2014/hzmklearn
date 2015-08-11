package com.mockuai.sellercenter.web.action;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.manager.ItemManager;
import com.mockuai.sellercenter.web.manager.ShopManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by luliang on 15/7/31.
 */
@Controller
public class ShopAction extends BaseValidator {

    @Resource
    private ShopManager shopManager;

    @Resource
    private ItemManager itemManager;

    @RequestMapping(value="/shop/get.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String getShopInfo(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(sellerId);
        try {
            shopDTO = shopManager.getShopInfo(shopDTO);
        } catch (ServiceException e) {
            ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(), e.getServiceMessage());
            String errorMsg = ResponseUtils.toJsonStr(apiResponse);
            return StringUtils.isBlank(callback)? errorMsg : callback +"(" + errorMsg + ")";
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(shopDTO);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(shopDTO) + ")";
        }
    }

    @RequestMapping(value="/shop/update.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String updateShopInfo(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        String sellerName = null;
        String shopIconUrl = null;
        String shopBannerImageUrl = null;
        String shopAddress = null;
        String customerServicePhone = null;
        try {
            sellerName = RequestUtils.getString(request, "seller_name", false);
            shopIconUrl = RequestUtils.getString(request, "shop_icon_url", false);
            shopBannerImageUrl = RequestUtils.getString(request, "shop_banner_image_url", false);
            shopAddress = RequestUtils.getString(request, "shop_address", false);
            customerServicePhone = RequestUtils.getString(request, "customer_service_phone", false);
        } catch (ParamException e) {
            String result = ResponseUtils.getFailApiResponseStr(e);
            return StringUtils.isBlank(callback)? result : callback +"(" + result + ")";
        }

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(sellerId);
        shopDTO.setSellerName(sellerName);
        shopDTO.setShopAddress(shopAddress);
        shopDTO.setCustomerServicePhone(customerServicePhone);
        shopDTO.setShopIconUrl(shopIconUrl);
        shopDTO.setShopBannerImageUrl(shopBannerImageUrl);

        Boolean result = null;
        try {
            result = shopManager.updateShopInfo(shopDTO);
        } catch (ServiceException e) {
            ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(), e.getServiceMessage());
            String errorMsg = ResponseUtils.toJsonStr(apiResponse);
            return StringUtils.isBlank(callback)? errorMsg : callback +"(" + errorMsg + ")";
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(result);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
        }
    }

    // 查询商品的分组名;
    @RequestMapping(value="/item/group/get.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String getItemGroupInfo(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        Long groupId = null;
        try {
            groupId = RequestUtils.getLong(request, "group_id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ShopItemGroupDTO shopItemGroupDTO = null;
        try {
            shopItemGroupDTO = shopManager.getShopItemGroup(groupId, sellerId);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(shopItemGroupDTO);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(shopItemGroupDTO) + ")";
        }
    }

    // 添加商品的分组
    @RequestMapping(value="/item/group/add.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String addItemGroup(HttpServletRequest request) {

        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        String groupName = null;
        try {
            groupName = RequestUtils.getString(request, "group_name", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ShopItemGroupDTO shopItemGroupDTO = new ShopItemGroupDTO();
        shopItemGroupDTO.setGroupName(groupName);
        shopItemGroupDTO.setSellerId(sellerId);

        try {
            shopItemGroupDTO = shopManager.addShopItemGroup(shopItemGroupDTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(shopItemGroupDTO);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(shopItemGroupDTO) + ")";
        }
    }


    // 添加商品的分组
    @RequestMapping(value="/item/group/delete.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String deleteItemGroup(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        Long groupId = null;
        try {
            groupId = RequestUtils.getLong(request, "group_id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }

        Boolean result = null;
        try {
            result = shopManager.deleteShopItemGroup(groupId, sellerId);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(result);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
        }
    }

    @RequestMapping(value="/shop/item/group/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryItemGroup(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }

        ShopItemGroupQTO shopItemGroupQTO = new ShopItemGroupQTO();
        shopItemGroupQTO.setSellerId(sellerId);
        Integer page = null;
        Integer pageSize = null;
        try {
            page = RequestUtils.getInt(request, "page", false);
            pageSize = RequestUtils.getInt(request, "page_size", false);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        if(pageSize != null) {
            shopItemGroupQTO.setNeedPaging(true);
            shopItemGroupQTO.setPageSize(pageSize);
            shopItemGroupQTO.setCurrentPage(page);
            shopItemGroupQTO.setOffset(page > 0? (page - 1) * pageSize:0);
        }

        List<ShopItemGroupDTO> shopItemGroupDTOList = null;
        try {
            shopItemGroupDTOList = shopManager.queryShopItemGroup(shopItemGroupQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        PageDTO<List<ShopItemGroupDTO>> pageDTO = new PageDTO<List<ShopItemGroupDTO>>();
        pageDTO.setTotalCount(Long.valueOf(shopItemGroupQTO.getTotalCount()));
        pageDTO.setData(shopItemGroupDTOList);
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageDTO);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageDTO) + ")";
        }
    }

    // 添加商品的分组
    @RequestMapping(value="/item/group/update.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String updateItemGroup(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        Long groupId = null;
        String groupName = null;
        try {
            groupId = RequestUtils.getLong(request, "group_id", true);
            groupName = RequestUtils.getString(request, "group_name", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ShopItemGroupDTO shopItemGroupDTO = new ShopItemGroupDTO();
        shopItemGroupDTO.setId(groupId);
        shopItemGroupDTO.setGroupName(groupName);
        shopItemGroupDTO.setSellerId(sellerId);
        Boolean result = null;
        try {
            result = shopManager.updateShopItemGroup(shopItemGroupDTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(result);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
        }
    }

    // 添加商品的分组
    @RequestMapping(value="/shop/item/group/in.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String addItemInGroup(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        Long groupId = null, itemId = null;
        try {
            groupId = RequestUtils.getLong(request, "group_id", true);
            itemId= RequestUtils.getLong(request, "item_id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }

        Boolean result = null;
        try {
            result = itemManager.addItemInGroup(sellerId, itemId, groupId);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(result);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
        }
    }

    // 添加商品的分组
    @RequestMapping(value="/shop/item/group/remove.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String removeItemFromGroup(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());
        if(sellerId == null) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_USER_NOT_LOGIN);
        }
        Long itemId = null;
        try {
            itemId= RequestUtils.getLong(request, "item_id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }

        Boolean result = null;
        try {
            result = itemManager.removeItemFromGroup(sellerId, itemId);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(result);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
        }
    }

}
