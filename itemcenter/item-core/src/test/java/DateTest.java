import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;

/**
 * Created by luliang on 15/7/23.
 */
public class DateTest {

    @Test
    public void testDateTransfer() {
        String dateString = "2015-06-12 12:11:11";
        String[] datePattern = {"yyyy-MM-dd HH:mm:ss"};
        try {
            System.out.println(DateUtils.parseDate(dateString, datePattern));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
