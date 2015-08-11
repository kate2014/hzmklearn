package com.mockuai.sellercenter.web.action;

import com.aliyun.oss.model.OSSObject;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.sellercenter.web.api.OSSClientAPI;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.manager.ItemExportTaskManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by luliang on 15/7/23.
 */
@Controller
public class ExportTaskAction extends BaseValidator {


    @Resource
    private ItemExportTaskManager itemExportTaskManager;

    @Resource
    private OSSClientAPI ossClientAPI;

    @RequestMapping(value="/item/export/task/query.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String queryItemExportTask(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Long userId = SessionManager.getLoginUserId(request.getSession());
        Integer page = null;
        Integer pageSize = null;
        Integer taskType = null;
        try {
            taskType = RequestUtils.getInt(request, "task_type", false);
            page = RequestUtils.getInt(request, "page", false);
            pageSize = RequestUtils.getInt(request, "page_size", false);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        ExportTaskQTO exportTaskQTO = new ExportTaskQTO();
        exportTaskQTO.setSellerId(userId);
        exportTaskQTO.setTaskType(taskType);
        if(page != null) {
            exportTaskQTO.setNeedPaging(true);
            exportTaskQTO.setOffset((page - 1) * pageSize);
            exportTaskQTO.setPageSize(pageSize);
        }
        List<ExportTaskDTO> exportTaskDTOList = null;
        try {
            exportTaskDTOList = itemExportTaskManager.queryItemExportTask(exportTaskQTO);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        PageDTO<List<ExportTaskDTO>> pageDTO = new PageDTO<List<ExportTaskDTO>>();
        pageDTO.setTotalCount(Long.valueOf(exportTaskQTO.getTotalCount()));
        pageDTO.setData(exportTaskDTOList);
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(pageDTO);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageDTO) + ")";
        }
    }

    @RequestMapping(value="/item/export/task/delete.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String deleteItemExportTask(HttpServletRequest request) {
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
            count = itemExportTaskManager.deleteItemExportTask(id, sellerId);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        if(StringUtils.isBlank(callback)){
            return ResponseUtils.getSuccessApiResponseStr(count);
        }else{
            return callback + "(" + ResponseUtils.getSuccessApiResponseStr(count) + ")";
        }
    }


    @RequestMapping(value="/export/download.do",produces="application/json; charset=utf-8")
    @ResponseBody
    public String downloadExportFile(HttpServletRequest request, HttpServletResponse response) {
        String callback = request.getParameter("callback");
        Long userId = SessionManager.getLoginUserId(request.getSession());
        Long sellerId = userId;
        String ossBucketName = null;
        String ossObjectKey = null;

        try {
            ossBucketName = RequestUtils.getString(request, "oss_bucket_name", true);
            ossObjectKey = RequestUtils.getString(request, "oss_object_key", true);
        } catch (ParamException e) {
            return ResponseUtils.getFailApiResponseStr(e);
        }
        OSSObject ossObject = null;
        try {
            ossObject = ossClientAPI.getObject(ossBucketName, ossObjectKey);
        } catch (ServiceException e) {
            return ServiceResponseHandler.serviceExceptionHandler(e);
        }
        // 获取Object的输入流
        InputStream objectContent = ossObject.getObjectContent();

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + ossObjectKey);

        try {
            OutputStream outputStream = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = objectContent.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }

            outputStream.close();
            objectContent.close();
        } catch (IOException e) {
            ResponseEnum responseEnum = ResponseEnum.S_E_OSS_FILE_ERROR;
            ServiceException serviceException = new ServiceException(responseEnum.getCode(), responseEnum.getMsg());
            return ServiceResponseHandler.serviceExceptionHandler(serviceException);
        }

        return null;
    }
}
