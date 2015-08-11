package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;
import com.mockuai.usercenter.common.qto.UserQTO;

import java.util.List;

public interface UserManager {
	
	public UserClient getUserClient();

	/**
	 * 登入
	 * @param userDTO
	 * @return
	 * @throws ServiceException
	 */
	public UserDTO login(String loginName,String password,String appKey) throws ServiceException;
	/**
	 * 更改密码
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public Boolean updatePassword(Long userId,String oldPassword,String newPassword,String appKey)throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param sellerId
	 * @param orderId
	 * @param tradeType
	 * @param orderAmt
	 * @param appKey
	 * @return
	 */
	Response<List<SellerUserRelateDTO>> querySellerUserRelate(SellerUserQTO query,String appKey)throws ServiceException;

	/**
	 * 查询用户
	 * @param userQTO
	 * @param appKey
	 * @return
	 * @throws ServiceException
	 */
	public List<UserDTO> query(UserQTO userQTO, String appKey) throws ServiceException;
}
