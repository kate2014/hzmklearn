package com.mockuai.usercenter.core.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.usercenter.common.action.ActionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdatePwdTest {

	@Resource
	private UserDispatchService userDispatchService;

	/**
	 * 参数正确的情况下修改密码 测试结果：修改成功
	 * */
	@Test
	public void updatePwdTest() {
		Request request = new BaseRequest();
		request.setParam("userId", 14L);
		request.setParam("oldPwd", "zl123456");
		request.setParam("newPwd", "np5356822");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 用户名和密码不匹配的情况下修改密码 测试结果：old password is error
	 * */
	@Test
	public void updatePwdTest1() {
		Request request = new BaseRequest();
		request.setParam("userId", 14L);
		request.setParam("oldPwd", "zl123456");
		request.setParam("newPwd", "np5356822");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 新老密码相同的情况下修改密码 测试结果：new password and old password is same
	 * */
	@Test
	public void updatePwdTest2() {
		Request request = new BaseRequest();
		request.setParam("userId", 14L);
		request.setParam("oldPwd", "np5356822");
		request.setParam("newPwd", "np5356822");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
	}

	/**
	 * 错误的密码格式下修改密码 测试结果：password format error
	 * */
	@Test
	public void updatePwdTest3() {
		Request request = new BaseRequest();
		request.setParam("userId", 14L);
		request.setParam("oldPwd", "np5356822");
		request.setParam("newPwd", "np5");
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response response = userDispatchService.execute(request);
	}
}
