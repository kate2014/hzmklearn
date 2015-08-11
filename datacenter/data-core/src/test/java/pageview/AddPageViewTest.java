//package pageview;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mockuai.appcenter.common.util.JsonUtil;
//import com.mockuai.datacenter.common.api.BaseRequest;
//import com.mockuai.datacenter.common.api.DataService;
//import com.mockuai.datacenter.common.api.Request;
//import com.mockuai.datacenter.common.api.Response;
//import com.mockuai.datacenter.common.constant.ActionEnum;
//import com.mockuai.datacenter.common.domain.dto.DataDTO;
//import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class AddPageViewTest {
//	@Resource
//	private DataService dataService;
//
//	@Test
//	public void test(){
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.ADD_PAGEVIEW.getActionName());
//		System.out.println("begin");
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date visitTime = new Date();
//		try {
//			visitTime = sdf.parse(sdf.format(visitTime));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		DataDTO pageViewDTO = new DataDTO();
//		pageViewDTO.setSeller_id(91l);
//		pageViewDTO.setVid(91l);
//		pageViewDTO.setVisitType(1l);
//		pageViewDTO.setIP("127.0.0.1");
//		pageViewDTO.setUser_id(84l);
//		pageViewDTO.setApp_type(0);
//
//
//		request.setParam("dataDTO", pageViewDTO);
//		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
//
//		Response response = dataService.execute(request);
//		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
//
//		while(true){
//
//		}
//	}
//}
