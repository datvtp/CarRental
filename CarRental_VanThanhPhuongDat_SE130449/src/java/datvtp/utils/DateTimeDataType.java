package datvtp.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeDataType implements Serializable {

    public static String getTimeNow() {
        Date date = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = spf.format(date);

        return timeStr;
    }

    public static String getToday() {
        Date date = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = spf.format(date);

        return timeStr;
    }

    public static String getTomorrow() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = spf.format(date);

        return timeStr;
    }

    public static boolean checkAfterToday(String checkInDate) throws Exception {
        boolean result = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateIn = sdf.parse(checkInDate);
        Date date = new Date();
        String dateStr = sdf.format(date);
        Date today = sdf.parse(dateStr);
        // ngay in < hom nay
        if (dateIn.before(today)) {
            result = false;
        }
        return result;
    }

    public static boolean checkDateInOut(String checkInDate, String checkOutDate) throws Exception {
        boolean result = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateIn = sdf.parse(checkInDate);
        Date dateOut = sdf.parse(checkOutDate);
        int temp = dateIn.compareTo(dateOut);
        // 2 ngay bang nhau
        if (temp == 0) {
            result = false;
        }
        // ngay 1 > ngay 2
        if (temp > 0) {
            result = false;
        }
        return result;
    }
}
