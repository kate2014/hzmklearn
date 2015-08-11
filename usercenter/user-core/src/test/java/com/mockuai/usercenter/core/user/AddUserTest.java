package com.mockuai.usercenter.core.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddUserTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下添加用户 测试结果:成功添加
	 * */
	@Test
	public void addTest() {

		Request request = new BaseRequest();

		request = new BaseRequest();
		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉112");
		userDto.setPassword("np2675e6d22"); //
		userDto.setEmail("2ddq7d33d@qq.com");
		userDto.setMobile("15162575176");
		userDto.setInviterId(27L);
		userDto.setImgUrl("http://www.baidu.com");
		userDto.setExperience(888L);
		userDto.setRoleMark(1L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");

		Response response = userDispatchService.execute(request);
		System.out.println("response:"+ JsonUtil.toJson(response));
		Assert.assertNotNull(response.getModule());

	}

	/**
	 * 用户名重复添加测试 测试结果： username is already register
	 * */
	@Test
	public void addTest2() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉14");
		userDto.setPassword("npl26575e6d22"); //
		userDto.setEmail("2dq7d33d@qq.com");
		userDto.setMobile("13162575117");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/***
	 * 邮箱重复添加测试 测试结果：email is already register
	 */
	@Test
	public void addTest3() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d323d@qq.com");
		userDto.setMobile("13962575116");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 邮箱不符合格式测试 测试结果：email format error
	 *
	 * */
	@Test
	public void addTest4() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d323d");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 手机号码重复添加测试 测试结果：mobile is already register
	 */
	@Test
	public void addTest5() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("13162575116");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 手机号码格式错误添加测试 测试结果：mobile format error
	 */
	@Test
	public void addTest6() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("131625751");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 验证错误的推荐人id 测试结果：user is not exist
	 */
	@Test
	public void addTest7() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("13162575166");
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 验证错误的邀请人id 测试结果：user is not exist
	 * */
	@Test
	public void addTest8() {

		Request request = new BaseRequest();

		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉15");
		userDto.setPassword("np26575e6d22"); //
		userDto.setEmail("2dq7d3233d@qq.com");
		userDto.setMobile("13162575166");
		userDto.setInviterId(100L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 验证Dto为空 测试结果：user is not exist
	 * */
	@Test
	public void addTest9() {

		Request request = new BaseRequest();

		UserDTO userDto = null;

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);

	}

	/**
	 * 用户角色错误 测试结果:user role is error
	 * */
	@Test
	public void addTest10() {

		Request request = new BaseRequest();

		request = new BaseRequest();
		UserDTO userDto = new UserDTO();
		userDto.setName("我爱睡觉16");
		userDto.setPassword("np265ed6d22"); //
		userDto.setEmail("2dq7dd23d@qq.com");
		userDto.setMobile("13262875116");
		userDto.setRoleMark(10L);
		userDto.setInviterId(27L);

		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDto);

		Response response = userDispatchService.execute(request);
		Assert.assertNotNull(response.getModule());

	}

}
