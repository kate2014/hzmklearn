package itemsku;

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
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemSkuTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正确查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setBarCode("111111");
		itemSkuQTO.setSellerId(82L);
		//itemSkuQTO.setItemId(2L);
		itemSkuQTO.setCurrentPage(1);
		itemSkuQTO.setPageSize(10);
		itemSkuQTO.setNeedPaging(true);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
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
	 * itemSkuQTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
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
	 * 根据商品ID查询SKU信息
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setItemId(1L);
		itemSkuQTO.setCurrentPage(1);
		itemSkuQTO.setPageSize(10);
		itemSkuQTO.setNeedPaging(true);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
}
