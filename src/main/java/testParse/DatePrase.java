package testParse;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;

public class DatePrase {

    public static void main(String[] args) {
        getDate();
        getCurrentDate();
        System.out.println("---");
        calculate();
        System.out.println("---");
        getFirstOrLastDayOfMonth();
        System.out.println("---");
        getCurrentMillis();
    }


    /**
     * 测试Calendar类中获取时间、日期
     */
    private static void getDate() {
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println("一年中的第" + dayOfYear + "天！");

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("一个月中的第" + dayOfMonth + "天！");

        int year = calendar.get(Calendar.YEAR);  //2018
        System.out.println(year);

        int month = calendar.get(Calendar.MONTH);  //3 （月份少了1）
        System.out.println(month);

        int day = calendar.get(Calendar.DATE); //11日
        System.out.println(day);

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println(hourOfDay); //11时

        int hour = calendar.get(Calendar.HOUR);
        System.out.println(hour);  //11时

        int minute = calendar.get(Calendar.MINUTE);
        System.out.println(minute);  //17分

        int second = calendar.get(Calendar.SECOND);
        System.out.println(second);  //44秒

        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        System.out.println("今天是一个月的第" + weekOfMonth + "周！");

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("今天是一周中的第" + dayOfWeek + "天！");
    }


    /**
     * 使用Calendar类获取当前日期
     */
    private static void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(
                calendar.get(Calendar.YEAR)
                        + "-" +
                        (calendar.get(Calendar.MONTH) + 1)  //月份+1
                        + "-" +
                        calendar.get(Calendar.DATE)
                        + " " +
                        calendar.get(Calendar.HOUR_OF_DAY)
                        + ":" +
                        calendar.get(Calendar.MINUTE)
                        + ":" +
                        calendar.get(Calendar.SECOND)
                        + "." + calendar.get(Calendar.MILLISECOND)

        );
    }


    /**
     * 日期计算
     */
    private static void calculate() {
        Calendar calendar = Calendar.getInstance();

        //通过Calendar类的add()方法可以指定日期字段，增加或减少日期；
        calendar.add(Calendar.YEAR, 10);
        System.out.println(calendar.get(Calendar.YEAR));

        //通过Calendar的set()方法可以直接设置时间
        calendar.set(1993, 6, 17, 23, 59, 59);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
    }


    /**
     * 获取当前月的第一天和最后一天
     */
    private static void getFirstOrLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);  //设置时间为一个月中第一天

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDay = sdf.format(calendar.getTime());
        System.out.println("当前月第一天：" + firstDay);


        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = sdf.format(calendar.getTime());
        System.out.println("当前月最后一天：" + lastDay);
    }

    private static void getCurrentMillis() {
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        System.out.println(timeInMillis);

        timeInMillis = System.currentTimeMillis();
        System.out.println(timeInMillis);

        timeInMillis= Clock.systemDefaultZone().millis();
        System.out.println(timeInMillis);
    }

}
