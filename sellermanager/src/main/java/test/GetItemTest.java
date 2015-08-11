package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;

public class GetItemTest {

	public static void main(String args[]) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring-dubbo-consumer.xml");
		long start = System.currentTimeMillis();
		ItemClient client = (ItemClient) ctx.getBean("itemClient");
		Response<ItemDTO> response = client.getItem(82L, 1L, true);
		System.err.println(response);
		System.out.println(response.getCode());
		System.out.println(response.getModule());
		System.out.println(response.getMessage());
		System.out.println(response.getTotalCount());

	}

}
