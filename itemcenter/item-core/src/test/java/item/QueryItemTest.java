package item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemTest {
	@Resource
	private ItemService itemService;
	
	@Ignore
	/**
	 * 正常查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setCurrentPage(1);
		itemQTO.setPageSize(4);
		List list = Arrays.asList(new Long[]{131L,132L});
		itemQTO.setIdList(list);
		itemQTO.setSellerId(82L);
//		itemQTO.setId(19L);
		//itemQTO.setSellerId(100L);
//		itemQTO.setItemName("衣服");
//		itemQTO.setCreateTimeEnd(new Date());
		itemQTO.setNeedPaging(true);
		request.setParam("itemQTO", itemQTO);
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemQTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	/**
	 * 供应商ID为空
	 */
	@Ignore
	public void test003() {
		Request request = new BaseRequest();
		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setCurrentPage(1);
		itemQTO.setPageSize(3);
		itemQTO.setDeleteMark(0);
		List<Long> idList = new ArrayList<Long>();
		idList.add(37L);
		itemQTO.setIdList(idList);
		itemQTO.setSellerId(91L);
		request.setParam("itemQTO", itemQTO);
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	/**
	 * 供应商ID为空
	 */
	@Test
	public void test004() {
		Request request = new BaseRequest();
		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setCurrentPage(1);
		itemQTO.setPageSize(3);
		itemQTO.setDeleteMark(0);
		itemQTO.setNeedPaging(true);
//		List<Long> idList = new ArrayList<Long>();
//		idList.add(91L);
//		itemQTO.setIdList(idList);
//		itemQTO.setGroupId(1L);
//		itemQTO.setId(1L);
		itemQTO.setSellerId(91L);
		request.setParam("itemQTO", itemQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_GROUP_ACTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
