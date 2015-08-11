package com.mockuai.usercenter.common.api;

import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;

/**
 * @author zhangqiang.zeng
 */
public interface UserService {

	public UserResponse<UserDTO> addUser(UserDTO userDTO);

	public UserResponse<Void> deleteUser(String appkey, long userId);

	public UserResponse<UserDTO> getUserById(String appkey, long userId);

	public UserResponse<UserDTO> getUserByMobile(String appkey, String mobile);

	public UserResponse<UserDTO> getUserByEmail(String appkey, String email);

	public UserResponse<Void> updatePwd(String appkey, long userId, String newPwd);

}
