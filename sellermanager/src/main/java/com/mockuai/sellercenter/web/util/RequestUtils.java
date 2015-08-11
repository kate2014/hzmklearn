package com.mockuai.sellercenter.web.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.GalleryParam;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.ItemProperty;
import com.mockuai.sellercenter.web.dto.ItemParamDTO.SkuPropertyItem;

/**
 * 请求参数工具类
 * @author cwr
 *
 */
public class RequestUtils {
	
	private final static Gson gson = new GsonBuilder().setFieldNamingPolicy(  
		    FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	
	public static List<?> json2List(String src,Type type){
		List<?> objList = null;
		//java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>(){}.getType();
		objList = gson.fromJson(src, type);
		return objList;
	}
	
	public static void setCharacterEncoding(HttpServletRequest request)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
	}

	public static String getString(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		return value;
	}
	
	/**
	 * 如果是为空则抛出异常
	 */
	public static String getString(HttpServletRequest request, String paramName,boolean isMandatory)throws ParamException {
		String v = request.getParameter(paramName);
		if(isMandatory){
			if(StringUtils.isBlank(v)){
				throw new ParamException(paramName,"不能为空");
			}
			return v.trim();
		}else{
			return StringUtils.isBlank(v)? null : v.trim();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static Long getLong(HttpServletRequest request, String paramName,boolean isMandatory)throws ParamException {
		String v = request.getParameter(paramName);
		if(isMandatory){
			if(StringUtils.isBlank(v)){
				throw new ParamException(paramName,"不能为空");
			}
		}else{
			if(StringUtils.isBlank(v)){
				return null; 
			}
		}
		try{
			return Long.valueOf(v.trim());
		}catch(Exception e){
			throw new ParamException(paramName,"格式不正确");
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param paramName
	 * @param isMandatory
	 * @return
	 * @throws ParamException
	 */
	public static Integer getInt(HttpServletRequest request, String paramName,boolean isMandatory)throws ParamException{
		String v = request.getParameter(paramName);
		if(isMandatory){
			if(StringUtils.isBlank(v)){
				throw new ParamException(paramName,"不能为空");
			}
		}else{
			if(StringUtils.isBlank(v)){
				return null; 
			}
		}
		try{
			return Integer.valueOf(v.trim());
		}catch(Exception e){
			throw new ParamException(paramName,"格式不正确");
		}
	}
	
	/**
	 * @param request
	 * @param paramName
	 *            参数名称
	 * @return 从request获取参数对应数组值
	 */
	public static String[] getArray(HttpServletRequest request, String paramName) {
		return request.getParameterValues(paramName);
	}

	/**
	 * @param request
	 * @param paramName
	 *            参数名称
	 * @return 从request获取参数对应整型值
	 */
	public static Integer getInt(HttpServletRequest request, String paramName)throws Exception {
		String value = request.getParameter(paramName);
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param request
	 * @param paramName
	 *            参数名称
	 * @return 从request获取参数对应长整型值
	 */
	public static long getLong(HttpServletRequest request, String paramName)throws Exception{
		String value = request.getParameter(paramName);
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 获取特定规格的日期类型
	 * @param request
	 * @param paramName
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date getFormatDate(HttpServletRequest request, String paramName,String[] dateFormats)throws ParamException{
		String value = request.getParameter(paramName);
		if(StringUtils.isBlank(value)){
			return null;
		}
		try{
			 return DateUtils.parseDate(
					request.getParameter(paramName),
					dateFormats);
		}catch(Exception e){
			throw new ParamException(paramName,"格式不正确");
		}
	}
	
	/**
	 * 解析long型数据格式 
	 * @param src
	 * @param field
	 * @param isMandatory
	 * @return
	 * @throws ParamException 抛出统一异常 便于统一处理
	 */
	public static Long parseLong(String src,String field,boolean isMandatory)throws ParamException{
		if(isMandatory && StringUtils.isBlank(src)){
			throw new ParamException(field,"不能为空");
		}
		Long val;
		try {
			val = Long.parseLong(src.trim());
		} catch (NumberFormatException e) {
			throw new ParamException(field,"格式错误");
		}
		return val;
	}
	
	/**
	 * 解析Int数据格式 
	 * @param src
	 * @param field
	 * @param isMandatory
	 * @return
	 * @throws ParamException 抛出统一异常 便于统一处理
	 */
	public static Integer parseInteger(String src,String field,boolean isMandatory)throws ParamException{
		if(isMandatory && StringUtils.isBlank(src)){
			throw new ParamException(field,"不能为空");
		}
		Integer val;
		try {
			val = Integer.parseInt(src.trim());
		} catch (NumberFormatException e) {
			throw new ParamException(field,"格式错误");
		}
		return val;
	}
	
	public static void main(String args[]){
		//List<?> list = json2List("[{\"img\":\"http:www.sina.com\",\"id\":\"11\",\"color\":\"red\"}]");
//		Type type = new com.google.gson.reflect.TypeToken<List<SkuPropertyItem>>() {
//
//		List<SkuPropertyItem>  skuProps = (List<SkuPropertyItem>) RequestUtils.json2List(skusStr,
//				type);
		
		List<ItemProperty> itemProps;
		List<GalleryParam> gallerys;
		
		System.out.println("解析skus");
		String skusStr = "[{\"prop\": [{\"prop_id\": \"3\",\"prop_name\": \"颜色\",\"value_id\": \"18\",\"value_name\": \"蓝色\"},{\"prop_id\": \"8\",\"prop_name\": \"鞋子尺码\",\"value_id\": \"262\",\"value_name\": \"14码\"}],\"origin_price\": \"1000.00\",\"price\": \"888.00\",\"num\": \"2\",\"barcode\": \"5454\"}]";
		Type type = new com.google.gson.reflect.TypeToken<List<SkuPropertyItem>>() {
		}.getType();
		List<SkuPropertyItem> skuProps = (List<SkuPropertyItem>) RequestUtils.json2List(skusStr,
				type);
		System.out.println(skuProps);
		
		System.out.println("解析props");
		String propsStr = "[{\"vid\": [\"-1\"],\"id\": \"5023\",\"name\": \"流行元素\",\"value\":[\"-请选择-\"]}]";
		type = new com.google.gson.reflect.TypeToken<List<ItemProperty>>() {
		}.getType();
		itemProps = (List<ItemProperty>) RequestUtils.json2List(propsStr,
				type);
		System.out.println(propsStr);
		
		System.out.println("解析gallery");
		String galleryStr = "[{\"color\": \"蓝色\",\"img\": \"http://img.ve.cn/public/attachment/201502/06/14/20150206140333535.jpg\",\"id\": \"18\"}]";
		type = new com.google.gson.reflect.TypeToken<List<GalleryParam>>() {
		}.getType();
		gallerys = (List<GalleryParam>) RequestUtils.json2List(galleryStr,
				type);
		System.out.println(gallerys);
	}
}
