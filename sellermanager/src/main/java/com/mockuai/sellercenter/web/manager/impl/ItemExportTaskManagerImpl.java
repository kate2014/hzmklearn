package com.mockuai.sellercenter.web.manager.impl;

import com.mockuai.dts.client.ItemExportClient;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemExportTaskManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/23.
 */
@Service
public class ItemExportTaskManagerImpl implements ItemExportTaskManager {

    @Resource
    private ItemExportClient itemExportClient;


    public List<ExportTaskDTO> queryItemExportTask(ExportTaskQTO exportQTO) throws ServiceException {
        Response<List<ExportTaskDTO>> response = null;
        response = this.itemExportClient.queryExportTask(exportQTO);
        exportQTO.setTotalCount((int)response.getTotalCount());
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }

    public Integer deleteItemExportTask(Long id, Long sellerId) throws ServiceException {
        Response<Integer> response = null;
        response = this.itemExportClient.deleteExportTask(id, sellerId);
        if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int errorCode = Integer.valueOf(response.getCode());
            throw new ServiceException(errorCode,response.getMessage());
        }
    }
}
