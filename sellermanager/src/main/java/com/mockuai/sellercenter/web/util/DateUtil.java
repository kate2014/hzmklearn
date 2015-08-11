package com.mockuai.sellercenter.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luliang on 15/7/30.
 */
public class DateUtil {

    public static final String SIMPLE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date, String format) {
        //创建格式化对象实例，并带日期格式字符串
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String res = sdf.format(date);
        return res;
    }
}
