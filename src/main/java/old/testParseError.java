package old;/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 18-12-6
 * Time: 下午2:11
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class testParseError {

    static long getTime(String dateStr) throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        return date.getTime();
    }

    public static void main(String[] args) throws Exception {
        String startDate = "2018/12/05 00:00:00";
        long startTime = getTime(startDate);
        String endDate = "2018/12/06 23:59:59";
        long endTime = getTime(endDate);
        System.out.println("max:"+ Integer.MAX_VALUE); // 2**31-1 = 0x7fffffff
        for (long time = startTime; time <= endTime; ++time) {
            for (int size = 1; size <= 3; ++size) {
                int queryIndex = (int) time % size;
//                System.out.println("time at " + time + ", (int)time is " + (int)time + ", size of " + size + ", queryIndex = " + queryIndex);
//                System.exit(0);
                if (queryIndex < 0) {
                    System.out.println("error time: " + time + ", (int)time is " + (int)time + ", size of " + size + ", queryIndex = " + queryIndex);
                    time = time-1;
                    queryIndex = (int) time % size;
                    System.out.println("lasttime: " + time + ", (int)time is " + (int)time + ", size of " + size + ", queryIndex = " + queryIndex);
                    System.exit(0);
                }
            }
        }
    }
}
