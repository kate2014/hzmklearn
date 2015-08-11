package com.mockuai.sellercenter.web.action;

import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemDetailTemplateManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by luliang on 15/7/23.
 */
@Controller
public class ItemDetailTemplateAction extends BaseValidator {

    @Resource
    private ItemDetailTemplateManager itemDetailTemplateManager;

    @RequestMapping(value="/item/detail/template/add.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String addItemDetailTemplate(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long userId = SessionManager.getLoginUserId(request.getSession());
        String templateName = null;
        String headerHtml = null;
        String headerTmsId = null;
        String footerHtml = null;
        String footerTmsId = null;
        try {
            templateName = RequestUtils.getString(request, "template_name", true);
            headerHtml = RequestUtils.getString(request, "header_html", true);
            headerTmsId = RequestUtils.getString(request, "header_tms_id", true);
            footerHtml = RequestUtils.getString(request, "footer_html", true);
            footerTmsId = RequestUtils.getString(request, "footer_tms_id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ItemDetailTemplateDTO itemDetailTemplateDTO = new ItemDetailTemplateDTO();
        itemDetailTemplateDTO.setTemplateName(templateName);
        itemDetailTemplateDTO.setHeaderHtml(headerHtml);
        itemDetailTemplateDTO.setHeaderTmsId(headerTmsId);
        itemDetailTemplateDTO.setFooterHtml(footerHtml);
        itemDetailTemplateDTO.setFooterTmsId(footerTmsId);
        itemDetailTemplateDTO.setSellerId(userId);
        Long id = null;
        try {
            id = itemDetailTemplateManager.addItemDetailTemplate(itemDetailTemplateDTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        itemDetailTemplateDTO.setId(id);
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(itemDetailTemplateDTO);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(itemDetailTemplateDTO) + ")";
        }
    }

    @RequestMapping(value="/item/detail/template/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryItemDetailTemplate(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long userId = SessionManager.getLoginUserId(request.getSession());
        Integer page = null;
        Integer pageSize = null;

        try {
            page = RequestUtils.getInt(request, "page", false);
            pageSize = RequestUtils.getInt(request, "page_size", false);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ItemDetailTemplateQTO itemDetailTemplateQTO = new ItemDetailTemplateQTO();
        itemDetailTemplateQTO.setSellerId(userId);
        if(page != null) {
            itemDetailTemplateQTO.setNeedPaging(true);
            itemDetailTemplateQTO.setOffset((page - 1) * pageSize);
            itemDetailTemplateQTO.setPageSize(pageSize);
        }
        List<ItemDetailTemplateDTO> itemDetailTemplateDTOs = null;
        try {
            itemDetailTemplateDTOs = itemDetailTemplateManager.queryItemDetailTemplate(itemDetailTemplateQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(itemDetailTemplateDTOs);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(itemDetailTemplateDTOs) + ")";
        }
    }

    @RequestMapping(value="/item/detail/template/get.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String getItemDetailTemplate(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long userId = SessionManager.getLoginUserId(request.getSession());
        Long id = null;
        Long sellerId = userId;

        try {
            id = RequestUtils.getLong(request, "id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ItemDetailTemplateDTO itemDetailTemplateDTO = null;
        try {
            itemDetailTemplateDTO = itemDetailTemplateManager.getItemDetailTemplate(id, sellerId);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(itemDetailTemplateDTO);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(itemDetailTemplateDTO) + ")";
        }
    }

    @RequestMapping(value="/item/detail/template/update.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String updateItemDetailTemplate(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long userId = SessionManager.getLoginUserId(request.getSession());
        String templateName = null;
        String headerHtml = null;
        String headerTmsId = null;
        String footerHtml = null;
        String footerTmsId = null;
        Long id = null;
        try {
            id = RequestUtils.getLong(request, "id", true);
            templateName = RequestUtils.getString(request, "template_name", true);
            headerHtml = RequestUtils.getString(request, "header_html", true);
            headerTmsId = RequestUtils.getString(request, "header_tms_id", true);
            footerHtml = RequestUtils.getString(request, "footer_html", true);
            footerTmsId = RequestUtils.getString(request, "footer_tms_id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ItemDetailTemplateDTO itemDetailTemplateDTO = new ItemDetailTemplateDTO();
        itemDetailTemplateDTO.setId(id);
        itemDetailTemplateDTO.setTemplateName(templateName);
        itemDetailTemplateDTO.setHeaderHtml(headerHtml);
        itemDetailTemplateDTO.setHeaderTmsId(headerTmsId);
        itemDetailTemplateDTO.setFooterHtml(footerHtml);
        itemDetailTemplateDTO.setFooterTmsId(footerTmsId);
        Integer count = null;
        try {
            count = itemDetailTemplateManager.updateItemDetailTemplate(itemDetailTemplateDTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(count);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(count) + ")";
        }
    }

    @RequestMapping(value="/item/detail/template/delete.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String deleteItemDetailTemplate(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long userId = SessionManager.getLoginUserId(request.getSession());
        Long id = null;
        Long sellerId = userId;

        try {
            id = RequestUtils.getLong(request, "id", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        Integer count = null;
        try {
            count = itemDetailTemplateManager.deleteItemDetailTemplate(id, sellerId);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(count);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(count) + ")";
        }
    }
}
