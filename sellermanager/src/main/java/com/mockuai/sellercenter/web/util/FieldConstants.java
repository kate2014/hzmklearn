package com.mockuai.sellercenter.web.util;

import java.util.HashMap;
import java.util.Map;

public class FieldConstants {
	/**
	 * 参数及其对应的名称
	 */
	private static Map<String,String> FIELD_MAP = new HashMap<String,String>();
	static {
		FIELD_MAP.put("num", "数量");
		FIELD_MAP.put("price", "现价");
		FIELD_MAP.put("origin_price", "原价");
	}
	
	public static String getFiledName(String field){
		return FIELD_MAP.get(field);
	}
	
}
