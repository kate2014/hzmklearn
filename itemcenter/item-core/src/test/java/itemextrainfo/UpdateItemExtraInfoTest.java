package itemextrainfo;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemExtraInfoDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateItemExtraInfoTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常更新
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemExtraInfoDTO itemExtraInfoDTO = new ItemExtraInfoDTO();
		itemExtraInfoDTO.setId(2L);
		itemExtraInfoDTO.setSupplierId(1L);
		itemExtraInfoDTO.setSoldNum(11);
		request.setParam("itemExtraInfoDTO", itemExtraInfoDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_EXTRA_INFO.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * ID为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		ItemExtraInfoDTO itemExtraInfoDTO = new ItemExtraInfoDTO();
		itemExtraInfoDTO.setSupplierId(1L);
		itemExtraInfoDTO.setSoldNum(11);
		request.setParam("itemExtraInfoDTO", itemExtraInfoDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_EXTRA_INFO.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * 供应商ID为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemExtraInfoDTO itemExtraInfoDTO = new ItemExtraInfoDTO();
		itemExtraInfoDTO.setId(2L);
		itemExtraInfoDTO.setSoldNum(11);
		request.setParam("itemExtraInfoDTO", itemExtraInfoDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_EXTRA_INFO.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * ID在数据库中不存在
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemExtraInfoDTO itemExtraInfoDTO = new ItemExtraInfoDTO();
		itemExtraInfoDTO.setId(99999L);
		itemExtraInfoDTO.setSupplierId(1L);
		itemExtraInfoDTO.setSoldNum(11);
		request.setParam("itemExtraInfoDTO", itemExtraInfoDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_EXTRA_INFO.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	/**
	 * itemExtraInfoDTO为空
	 */
	public void test005() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ITEM_EXTRA_INFO.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
