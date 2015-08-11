package com.mockuai.shopplatform.core.util;

import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.core.exception.ShopException;

public class ExceptionUtil {

	public static ShopException getException(ResponseCode responseCode,
			String message) {
		return new ShopException(responseCode, message);
	}
}
