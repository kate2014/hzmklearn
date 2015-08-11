package pageview;

import com.mockuai.datacenter.common.api.BaseRequest;
import com.mockuai.datacenter.common.api.DataService;
import com.mockuai.datacenter.common.api.Request;
import com.mockuai.datacenter.common.api.Response;
import com.mockuai.datacenter.common.constant.ActionEnum;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by wanghailong on 15-8-7.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CountShopPvTest {

    @Resource
    private DataService dataService;

    @Test
    public void test() throws ParseException {
        Request request = new BaseRequest();
        PageViewQTO pageViewQTO = new PageViewQTO();
//        pageViewQTO.setSellerId(91L);
//        pageViewQTO.setDays(2L);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date startDate  = simpleDateFormat.parse("2015-08-01");
//        java.util.Date endDate  = simpleDateFormat.parse("2015-08-08");
//        pageViewQTO.setStartTime(startDate);
//        pageViewQTO.setEndTime(endDate);
        request.setParam("pageViewQTO", pageViewQTO);
        request.setCommand(ActionEnum.QUERY_ITEMTOP.getActionName());
        Response response = dataService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }
}
