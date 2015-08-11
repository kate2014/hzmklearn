package com.mockuai.sellercenter.web.action;

import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.PageViewManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghailong on 15-8-10.
 */
@Controller
public class PageViewAction extends BaseValidator {

    private String[] ACCEPT_DATE_FORMAT= {"yyyy-MM-dd"};

    @Resource
    private PageViewManager pageViewManager;

    @RequestMapping(value="/data/itemTop/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryItemTop(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);

        List<PageViewDTO> pageViewDTOList;
        try {
            pageViewDTOList = pageViewManager.queryItemTop(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageViewDTOList);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageViewDTOList) + ")";
        }
    }

    @RequestMapping(value="/data/area/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryVisitorArea(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);

        List<PageViewDTO> pageViewDTOList;
        try {
            pageViewDTOList = pageViewManager.queryVisitorArea(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageViewDTOList);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageViewDTOList) + ")";
        }
    }

    @RequestMapping(value="/data/device/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryDeviceType(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);

        List<PageViewDTO> pageViewDTOList;
        try {
            pageViewDTOList = pageViewManager.queryDeviceType(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageViewDTOList);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageViewDTOList) + ")";
        }
    }

    @RequestMapping(value="/data/pv/count.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String countShopPv(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
        Long days;
        try{
            days = RequestUtils.getLong(request, "days", true);

        }catch(ParamException e){
            return ResponseUtils.getFailApiResponseStr(e);
        }

        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);
        pageViewQTO.setDays(days);

        Integer pv;
        try {
            pv = pageViewManager.countShopPv(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pv);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pv) + ")";
        }
    }

    @RequestMapping(value="/data/uv/count.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String countShopUv(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
        Long days;
        try{
            days = RequestUtils.getLong(request, "days", true);

        }catch(ParamException e){
            return ResponseUtils.getFailApiResponseStr(e);
        }
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);
        pageViewQTO.setDays(days);

        Integer uv;
        try {
            uv = pageViewManager.countShopUv(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(uv);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(uv) + ")";
        }
    }

    @RequestMapping(value="/data/pv/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryShopPv(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
//        Long sellerId;
        Date startDate;
        Date endDate;
        try{
//            sellerId = RequestUtils.getLong(request, "seller_id", true);
            startDate = RequestUtils.getFormatDate(request, "start_date", ACCEPT_DATE_FORMAT);
            endDate = RequestUtils.getFormatDate(request, "end_date", ACCEPT_DATE_FORMAT);

        }catch(ParamException e){
            return ResponseUtils.getFailApiResponseStr(e);
        }
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);
        pageViewQTO.setStartTime(startDate);
        pageViewQTO.setEndTime(endDate);

        List<PageViewDTO> pageViewDTOList;
        try {
            pageViewDTOList = pageViewManager.queryShopPv(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageViewDTOList);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageViewDTOList) + ")";
        }
    }

    @RequestMapping(value="/data/uv/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryShopUv(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
//        Long sellerId;
        Date startDate;
        Date endDate;
        try{
//            sellerId = RequestUtils.getLong(request, "seller_id", true);
            startDate = RequestUtils.getFormatDate(request, "start_date", ACCEPT_DATE_FORMAT);
            endDate = RequestUtils.getFormatDate(request, "end_date", ACCEPT_DATE_FORMAT);

        }catch(ParamException e){
            return ResponseUtils.getFailApiResponseStr(e);
        }
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);
        pageViewQTO.setStartTime(startDate);
        pageViewQTO.setEndTime(endDate);

        List<PageViewDTO> pageViewDTOList;
        try {
            pageViewDTOList = pageViewManager.queryShopUv(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageViewDTOList);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageViewDTOList) + ")";
        }
    }

    @RequestMapping(value="/data/uvHour/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryShopHourUv(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
        Date startDate;
        Date endDate;
        try{
            startDate = RequestUtils.getFormatDate(request, "start_date", ACCEPT_DATE_FORMAT);
            endDate = RequestUtils.getFormatDate(request, "end_date", ACCEPT_DATE_FORMAT);

        }catch(ParamException e){
            return ResponseUtils.getFailApiResponseStr(e);
        }
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);
        pageViewQTO.setStartTime(startDate);
        pageViewQTO.setEndTime(endDate);

        List<PageViewDTO> pageViewDTOList;
        try {
            pageViewDTOList = pageViewManager.queryShopHourUv(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageViewDTOList);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageViewDTOList) + ")";
        }
    }

    @RequestMapping(value="/data/newVisitor/count.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String countNewVisitor(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long sellerId = SessionManager.getLoginUserId(request.getSession());

        PageViewQTO pageViewQTO = new PageViewQTO();
        Date startDate;
        Date endDate;
        try{
            startDate = RequestUtils.getFormatDate(request, "start_date", ACCEPT_DATE_FORMAT);
            endDate = RequestUtils.getFormatDate(request, "end_date", ACCEPT_DATE_FORMAT);

        }catch(ParamException e){
            return ResponseUtils.getFailApiResponseStr(e);
        }
        if(sellerId != null)
            pageViewQTO.setSellerId(sellerId);
        pageViewQTO.setStartTime(startDate);
        pageViewQTO.setEndTime(endDate);

        List<PageViewDTO> pageViewDTOList;
        try {
            pageViewDTOList = pageViewManager.countNewVisitor(pageViewQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }

        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageViewDTOList);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageViewDTOList) + ")";
        }
    }
}
