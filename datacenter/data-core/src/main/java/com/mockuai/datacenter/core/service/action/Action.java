package com.mockuai.datacenter.core.service.action;

import com.mockuai.datacenter.common.api.DataResponse;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 * 
 * @author wujin.zzq
 *
 */
@Service
public interface Action {

	public DataResponse execute(RequestContext context) throws DataException;

	public String getName();
}
