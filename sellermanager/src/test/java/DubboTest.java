

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemSkuManager;

/**
 * Dubbo服务启动
 * 
 * @author Kerwin
 * @date 2013-10-24 下午08:52:35
 */
public class DubboTest {

	public static void querySku(ClassPathXmlApplicationContext ctx){
		
		ItemSkuManager skumanager = (ItemSkuManager) ctx.getBean("itemSkuManager");
		
		ItemSkuQTO query = new ItemSkuQTO();
		
		query.setId(205l);
		
		try {
			 List<ItemSkuDTO> list = skumanager.queryItemSku(query);
			 
			 for(ItemSkuDTO sku:list){
				 
				 System.out.println("props"+sku.getSkuPropertyDTOList());
				 
			 }
			 
			 System.out.println(""+list.size());
			 
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        context.start();
        querySku(context);

        System.out.println("stared...");
        System.in.read();
    }
}
