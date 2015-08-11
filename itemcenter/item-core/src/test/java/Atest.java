import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class Atest {

	// @Resource
	// UserClient userClient;

	// @Resource
	// ItemClient itemClient;

//	@Resource
//	ItemSkuClient itemSkuClient;

	@Test
	public void test() {
		System.out.println(1);
		// Response<UserDTO> response = userClient.getUserById(29L);
//		Response<ItemSkuDTO> response = itemSkuClient.getItemSku(12L, 1L);
//		// Response<ItemDTO> response = itemClient.getItem(22L, 1L);
//		System.out.println(response.getModule());
		System.out.println(2);

	}
}
<<<<<<< HEAD

//insert into `app_info` (`biz_code`,`app_type`,`app_key`,`app_pwd`,`app_name`,`app_desc`,`app_version`,`administrator`,`mobile`,`email`,`delete_mark`,`gmt_created`,`gmt_modified`)
//		values('yangdongxi',3,'0efa3aa4c1d059043ec35cec5b6625d1','6415e5876fa6c32641f4f08e1eb693be','活码平台前端页面','test','1.0.0','test','18888888888','test@mockuai.com',0,'2015-07-21 12:31:24','2015-07-21 12:31:24')
=======
>>>>>>> befd0acb3138471901ff3f9312cac071b4ebbb9f
