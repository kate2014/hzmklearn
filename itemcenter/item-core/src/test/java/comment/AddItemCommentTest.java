package comment;

import java.util.ArrayList;
import java.util.List;

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
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemCommentTest {
	@Resource
	private ItemService itemService;

	/**
	 * 正确添加评论
	 */
	@Test
	public void test001() {
		Request request = new BaseRequest();
		ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
		itemCommentDTO.setUserId(1L);
		itemCommentDTO.setUserName("qqq");
		itemCommentDTO.setItemId(456L);
		itemCommentDTO.setOrderId(3L);
		itemCommentDTO.setSellerId(456L);
		itemCommentDTO.setContent("cwr测试");
		
		List<ItemCommentDTO> list =new ArrayList<ItemCommentDTO>();
		list.add(itemCommentDTO);
		request.setParam("itemCommentList",list);
		request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * ItemCommentDTO 为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * 评论内容为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
		itemCommentDTO.setUserId(1L);
		itemCommentDTO.setUserName("hu6");
		itemCommentDTO.setItemId(999L);
		itemCommentDTO.setOrderId(3L);
		itemCommentDTO.setSellerId(4L);
		request.setParam("itemCommentDTO", itemCommentDTO);
		request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

}
