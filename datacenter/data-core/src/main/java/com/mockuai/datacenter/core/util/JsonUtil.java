package com.mockuai.datacenter.core.util;

import java.util.List;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by zengzhangqiang on 4/24/15.
 */
public class JsonUtil {
    public static Gson gson;

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        gb.setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = gb.create();
    }

    public static <T> T parseJson(String jsonStr, Class<T> tClass){
        return gson.fromJson(jsonStr, tClass);
    }

    /**
     * 使用Gson生成json字符串
     * @param src
     * @return
     */
    public static String toJson(Object src){
        return gson.toJson(src);
    }
    
    public static List<Map<String, Object>> toObject(String src){
    	return gson.fromJson(src, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
    }
    
}
