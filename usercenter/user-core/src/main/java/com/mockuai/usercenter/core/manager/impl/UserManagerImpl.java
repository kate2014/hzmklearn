package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
import com.mockuai.usercenter.common.dto.UserInfoDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.dao.UserDAO;
import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.util.JsonUtil;
import com.mockuai.usercenter.core.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagerImpl implements UserManager {
	private static final Logger log = LoggerFactory.getLogger(UserManagerImpl.class);

	@Resource
	private UserDAO userDAO;

	private static List<Character> characters;

	static {
		characters = new ArrayList<Character>();
		for(char i='0'; i<='9'; i++){
			characters.add(i);
		}

		for(char i='a'; i<='z'; i++){
			characters.add(i);
		}

		for(char i='A'; i<='Z'; i++){
			characters.add(i);
		}
	}

	@Override
	public UserDTO addUser(UserDTO userDto) throws UserException {

		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userDTO is null");
		}

		// 判断用户的数据是否合法
		UserUtil.userInfoIsLegal(userDto);

		// 判断邮箱是否被注册，一个邮箱只能被使用一次
		String email = userDto.getEmail();
		if (email != null) {
			if (null != getUserByEmail(email)) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"email is already register");
			}
		}

		// 判断手机是否被注册，一个手机号只能被使用一次
		String mobile = userDto.getMobile();

		if (mobile != null) {
			if (null != getUserByMobile(mobile)) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"mobile is already register");
			}
		}

		//TODO 默认命名方式待讨论，上线前务必确定下来
		String name = userDto.getName();
		//如果NAME为空，则系统自动设置NAME
		if(StringUtils.isBlank(name)){
			if(StringUtils.isNotBlank(mobile)){
				userDto.setName(mobile);
			}else if(StringUtils.isNotBlank(email)){
				userDto.setName(email);
			}
		}else{
			// 判断用户名是否被注册，不能出现相同的用户名
			if (null != getUserByName(name)) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"username is already register");
			}
		}

		// 用户的角色类型
		Long roleMark = userDto.getRoleMark();
		if (roleMark != null) {
			if (roleMark != 0 && roleMark != 1) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"user role is error");
			}
		}else{
			userDto.setRoleMark(0L);
		}

		// 验证座机号的格式
		if (null != userDto.getPhone()) {
			UserUtil.checkPhoneNo(userDto.getPhone());
		}

		userDto.setInvitationCode(null);

		// 将dto转换为do
		UserDO userDo = new UserDO();

		BeanUtils.copyProperties(userDto, userDo);

		//为新添加用户生成一个8位随机邀请码
		String myInvitationCode = generateInvitationCode();
		userDo.setInvitationCode(myInvitationCode);

		Long userId = userDAO.addUser(userDo);

		userDto = getUserById(userId);

		return userDto;
	}

	@Override
	public UserDTO addOpenUser(UserDTO userDto) throws UserException {
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userDTO is null");
		}

		//TODO 必要参数的校验

		// 将dto转换为do
		UserDO userDo = new UserDO();
		BeanUtils.copyProperties(userDto, userDo);
		//设置用户类型为2，代表来自第三方开放平台的用户
		userDo.setType(2);
		long timestamp = System.currentTimeMillis();
		//设置系统生成的手机号和密码，防止手机号唯一性冲突
		userDo.setName("open_" + timestamp);
		userDo.setMobile("open_" + timestamp);
		userDo.setPassword("open_"+timestamp);
		//为新添加用户生成一个8位随机邀请码
		String myInvitationCode = generateInvitationCode();
		userDo.setInvitationCode(myInvitationCode);
		try{
			Long userId = userDAO.addUser(userDo);
			userDto = getUserById(userId);
			return userDto;
		}catch(Exception e){
			log.error("params:"+ JsonUtil.toJson(userDto), e);
			throw new UserException(ResponseCode.B_ADD_ERROR);
		}
	}

	@Override
	public UserDTO getUserByName(String name) throws UserException {

		if (null == name) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"username is null");
		}
		UserDO userDo = userDAO.getUserByName(name);
		UserDTO userDto = null;
		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}

		return userDto;
	}

	@Override
	public UserDTO getUserByEmail(String email) throws UserException {

		UserUtil.checkEamil(email);

		UserDO userDo = userDAO.getUserByEmail(email);
		UserDTO userDto = null;
		// 将do转换为dto
		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	@Override
	public UserDTO getUserByMobile(String mobile) throws UserException {
		UserUtil.checkMobile(mobile);
		UserDO userDo = userDAO.getUserByMobile(mobile);
		UserDTO userDto = null;
		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	@Override
	public UserDTO getUserById(Long userId) throws UserException {

		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
		}

		if (userId <= 0) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"userId must greater than 0");
		}

		UserDO userDo = userDAO.getUserById(userId);
		UserDTO userDto = null;

		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	@Override
	public int updatePwd(Long userId, String newPwd)
			throws UserException {

		if (null == newPwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"new password is null");
		}


		// 检查新密码的合法性
		UserUtil.checkPwd(newPwd);

		UserDTO userDto = getUserById(userId);
		if (userDto == null) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user is not exist");
		}

		int result = userDAO.updatePwd(userId, newPwd);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public int updateEmail(Long userId, String email) throws UserException {

		if (null == email) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "email is null");
		}

		UserUtil.checkEamil(email);

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		// 如果修改的邮箱已存在，则添加错误
		userDto = getUserByEmail(email);
		if (userDto != null) {
			throw new UserException(ResponseCode.B_EMAIL_IS_EXIST,
					"email is exist");
		}

		int result = userDAO.updateEmail(userId, email);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public int updateMobile(Long userId, String mobile) throws UserException {
		// TODO Auto-generated method stub

		if (null == mobile) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "mobile is null");
		}

		UserUtil.checkMobile(mobile);

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		userDto = getUserByMobile(mobile);
		if (userDto != null) {
			throw new UserException(ResponseCode.B_MOBILE_IS_EXIST,
					"mobile is exist");
		}

		int result = userDAO.updateMobile(userId, mobile);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public int activeUser(Long userId) throws UserException {

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		// 如果用户的状态为激活状态，则激活失败
		if (userDto.getStatus() == 0) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"user is activity");
		}

		int result = userDAO.activeUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public int freezeUser(Long userId) throws UserException {

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		if (userDto.getStatus() == 1) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user is freeze");
		}

		int result = userDAO.freezeUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public int moveToRecycle(Long userId) throws UserException {

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		if (userDto.getDeleteMark() == 2) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"user in recycle");
		}

		int result = userDAO.moveToRecycle(userId);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}
		return result;
	}

	@Override
	public int deleteUser(Long userId) throws UserException {
		// TODO Auto-generated method stub

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}
		// 如果用户的状态不为2，既用户不在回收站中，则不能删除
//		if (userDto.getDeleted() != 2) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user not in recycle");
//		}

		int result = userDAO.deleteUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
		}

		return result;
	}

	@Override
	public List<UserDTO> queryUser(UserQTO userQto) throws UserException {

		if (null == userQto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}

		// 判断邀请人id是否为空，如果不为空，判断是否存在指定的用户
		Long inviterId = userQto.getInviterId();
		if (inviterId != null) {
			if (getUserById(inviterId) == null) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"inviter user is not exist");
			}
		}

		if(null == userQto.getOffset() || userQto.getOffset() < 0){
			userQto.setOffset(0L);
		}

		// 没传入每页显示总数或者每页显示的数量大于500的话，默认每页显示20条
		if (null == userQto.getCount() || userQto.getCount() > 500) {
			userQto.setCount(100);
		}

		// 传入的页数如果大于最大页数，则跳入最后一页
		Long totalCount = getTotalCount(userQto);


		List<UserDO> userDos = userDAO.queryUser(userQto);
		List<UserDTO> userDtos = new ArrayList<UserDTO>();

		for (UserDO userDo1 : userDos) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(userDo1, userDto);
			userDtos.add(userDto);
		}

		return userDtos;

	}

	@Override
	public int setUserRoleMark(Long userId, Long roleMark) throws UserException {

		if (null == roleMark) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "roleMark is null");
		}

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		// 用户角色只能为0或1
		if (roleMark != 0 && roleMark != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"role value is error");
		}
		int result = userDAO.setUserRoleMark(userId, roleMark);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}
		return result;
	}

	@Override
	public int restoreUser(Long userId) throws UserException {

		int result = userDAO.restoreUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public UserDTO getRecycleUser(Long userId) throws UserException {

		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
		}

		if (userId < 0) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"userId must greater than zero");
		}

		UserDO userDo = userDAO.getRecycleUser(userId);
		UserDTO userDto = null;

		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	@Override
	public UserDTO userLogin(String loginName, String loginPwd)
			throws UserException {

		if (null == loginPwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"loginPwd is null");
		}

		// 判断账号是否存在
		getUserByLoginName(loginName);

		UserDO userDo = userDAO.userLogin(loginName, loginPwd);

		if (null == userDo) {
			throw new UserException(ResponseCode.B_PASSWORD_ERROR,
					"password error");
		}

		UserDTO userDto = null;
		if (userDo != null) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}

		return userDto;

	}


	@Override
	public int updateName(Long userId, String name) throws UserException {
		// TODO Auto-generated method stub
		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userInfoDto is null");
		}
		if (null == name) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "name is null");
		}

		UserDTO userDto = getUserById(userId);

		int result = userDAO.updateName(userId, name);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public Integer updateUser(UserDTO userDto) throws UserException {
		// TODO Auto-generated method stub
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userDTO is null");
		}

		// 校验手机号码的正确性
		if(userDto.getMobile() != null){
			UserUtil.checkMobile(userDto.getMobile());
		}

		// 校验密码的合法性
		if(userDto.getPassword() != null){
			UserUtil.checkPwd(userDto.getPassword());
		}

		UserDO userDo = new UserDO();
		BeanUtils.copyProperties(userDto, userDo);

		int result = userDAO.updateUser(userDo);
		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}
		return result;
	}

	@Override
	public Long getTotalCount(UserQTO userQto) throws UserException {
		// TODO Auto-generated method stub
		if (null == userQto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}
		UserDO userDo = new UserDO();
		BeanUtils.copyProperties(userQto, userDo);
		Long totalCount = userDAO.getTotalCount(userDo);

		return totalCount;
	}

	@Override
	public int updateHeadImg(Long userId, String headImg) throws UserException {

		if (null == headImg) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"headImg is null");
		}

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		int result = userDAO.updateHeadImg(userId, headImg);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public UserDTO getUserByLoginName(String loginName) throws UserException {
		if (null == loginName) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"loginName is null");
		}
		UserDO userDo = userDAO.getByLoginName(loginName);
		if (null == userDo) {
			throw new UserException(ResponseCode.B_ACCOUNT_NOT_EXIST,
					"account not exist");
		}
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(userDo, userDto);
		return userDto;
	}

	@Override
	public UserDTO getUserByInvitationCode(String invitationCode)
	{
		UserDO userDo = userDAO.getByInvitationCode(invitationCode);

		UserDTO userDto = null;
		if (null != userDo)
		{
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	@Override
	public String generateInvitationCode() {
		//8位随机邀请码生成逻辑，add by zzq on 2015-06-07
		StringBuilder sb = new StringBuilder();
		long timestamp = System.currentTimeMillis();
		for(int i=0; i<8; i++){
			sb.append(characters.get((int) ((timestamp>>6*i)&0x3f)%characters.size()));
		}

		//TODO 查询数据库，防止唯一性冲突

		return sb.reverse().toString();
	}


	@Override
	public int updateInvitationCode(Long userId, String invitationCode) throws UserException
	{
		if (null == invitationCode)
		{
			throw new UserException(ResponseCode.P_PARAM_NULL, "invitationCode is null");
		}
		UserDTO userDto = null;
		if (null != userId)
		{
			userDto = getUserById(userId);
		}

		if (userDto == null)
		{
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userInfoDto is null");
		}

		int result = userDAO.updateInvitationCode(userId, invitationCode);

		if (result != 1)
		{
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}
		return result;
	}

}
