package com.mockuai.sellercenter.web.manager;

import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luliang on 15/7/23.
 */
@Service
public interface ItemExportTaskManager {

    public List<ExportTaskDTO> queryItemExportTask(ExportTaskQTO exportTaskQTO) throws ServiceException;

    public Integer deleteItemExportTask(Long id, Long sellerId) throws ServiceException;
}
