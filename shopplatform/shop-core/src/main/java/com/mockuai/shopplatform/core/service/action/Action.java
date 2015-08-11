package com.mockuai.shopplatform.core.service.action;

import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 * 
 * @author wujin.zzq
 *
 */
@Service
public interface Action {

	public ShopResponse execute(RequestContext context) throws ShopException;

	public String getName();
}
