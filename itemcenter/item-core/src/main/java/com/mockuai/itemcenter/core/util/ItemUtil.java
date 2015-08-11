package com.mockuai.itemcenter.core.util;

import org.springframework.beans.BeanUtils;

import com.mockuai.itemcenter.core.constants.ItemStatus;

public class ItemUtil {

	private static final String UNDERLINE = "_";
	
	/**
	 * 使用Spring框架下的BeanUtil方法copy属性， 将obj1的属性的值拷到obj2的属性
	 * 
	 * @param obj1
	 * @param obj2
	 */

	public static void copyProperties(Object fromObj, Object ToObj) {
		BeanUtils.copyProperties(fromObj, ToObj);
	}

	/**
	 * 商品状态转化
	 * @param status
	 * @return
	 */
	public static String convertItemStatus(Integer status){
		if(status == null){
			return null;
		}
		int val = status.intValue();
		if(val == ItemStatus.PENDING_AUDIT.getStatus())
			return ItemStatus.PENDING_AUDIT.getStatusName();
		if(val == ItemStatus.AUDIT_SUCCESS.getStatus())
			return ItemStatus.AUDIT_SUCCESS.getStatusName();
		if(val == ItemStatus.AUDIT_FAIL.getStatus())
			return ItemStatus.AUDIT_FAIL.getStatusName();
		if(val == ItemStatus.ON_SALE.getStatus())
			return ItemStatus.ON_SALE.getStatusName();
		if(val== ItemStatus.WITHDRAW.getStatus())
			return ItemStatus.WITHDRAW.getStatusName();
		if(val == ItemStatus.FROZEN.getStatus())
			return ItemStatus.FROZEN.getStatusName();
		if(val == ItemStatus.PRE_SALE.getStatus())
			return ItemStatus.PRE_SALE.getStatusName();
		
		return null;
	}
	
	/**
	 * 生产uid通用类
	 * @param id
	 * @param supplierId
	 * @return
	 */
	public static String genUid(Long id,Long supplierId){
		if(id ==null || supplierId  == null){
			return null;
		}
		return String.valueOf(supplierId) + UNDERLINE + String.valueOf(id);
	}
}
