package itemcategory;

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
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemCategoryTest {
	@Resource
	private ItemService itemService;

	
	/**
	 * 正常插入
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCateLevel(2);
		itemCategoryDTO.setCateName("书");
		itemCategoryDTO.setParentId(330L);
		itemCategoryDTO.setSort(1);
		itemCategoryDTO.setTopId(1L);
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemCategoryDTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 父ID不合法
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCateLevel(2);
		itemCategoryDTO.setCateName("书");
		itemCategoryDTO.setParentId(9999L);
		itemCategoryDTO.setSort(1);
		itemCategoryDTO.setTopId(1L);
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * 类目名称为空
	 */
	public void test004() {
		Request request = new BaseRequest();
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCateLevel(2);
		itemCategoryDTO.setParentId(0L);
		itemCategoryDTO.setSort(1);
		itemCategoryDTO.setTopId(1L);
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * 层级为空
	 */
	public void test005() {
		Request request = new BaseRequest();
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCateName("书");
		itemCategoryDTO.setParentId(0L);
		itemCategoryDTO.setSort(1);
		itemCategoryDTO.setTopId(1L);
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * 父ID为空
	 */
	public void test006() {
		Request request = new BaseRequest();
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCateLevel(2);
		itemCategoryDTO.setCateName("书");
		itemCategoryDTO.setSort(1);
		itemCategoryDTO.setTopId(1L);
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * 顶级ID为空
	 */
	public void test007() {
		Request request = new BaseRequest();
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCateLevel(2);
		itemCategoryDTO.setCateName("书");
		itemCategoryDTO.setParentId(0L);
		itemCategoryDTO.setSort(1);
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
