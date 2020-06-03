package date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @ClassName DateUtil
 * @description: TODO  日期转换类
 * @author: li zhi x
 * @create: 2020/5/20
 **/
public class DateUtil {

    public static void main(String[] args) throws ParseException {
//        String[] monthByDayStr = getMonthByDayStr("2020-09-08");
//        System.out.println(monthByDayStr[0]);
//        System.out.println(monthByDayStr[1]);
        int yyyyMMdd = getTimeSecond("20200524", "yyyyMMdd");
        System.out.println(yyyyMMdd);
    }

    /**
     * 根据日期格式获取秒值
     * @param date
     * @param format
     * @return
     */
    public static int getTimeSecond(String date,String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        int time = (int) (df.parse(date).getTime()/1000);
        return time ;
    }

    /**
     * 获取传入天数当天的开始时间
     * 如果为空，默认今天
     * @param day yyyy-MM-dd
     * @return 1589904000000
     */
    public static String getDayOfBegin(String day){  //2020-05-12
        Calendar cal = Calendar.getInstance();
        Date date = null;
        if(day==null||"".equals(day)){
            date = new Date();
        }else {
            date = parseDateStr(day);
        }

        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return String.valueOf(cal.getTimeInMillis());
    }

    /**
     * 获取传入天数当天的开始时间
     * 如果为空，默认今天
     * @param day java.util.Date
     * @return 1589904000000
     */
    public static String getDayOfBegin(Date day){
        Calendar cal = Calendar.getInstance();
        Date date = null;
        if(day==null||"".equals(day)){
            date = new Date();
        }else {
           date = day;
        }
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return String.valueOf(cal.getTimeInMillis());
    }


    /**
     * 获取传入天数当天的结束时间
     *  * 如果为空，默认今天
     * @param day yyyy-MM-dd
     * @return 1589904000000
     */
    public static String getDayOfEnd(String day){  //2020-05-12
        Calendar c = Calendar.getInstance();
        Date date = null;
        if(day==null||"".equals(day)){
            date = new Date();
        }else {
            date = parseDateStr(day);
        }
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND,999);
        return String.valueOf(c.getTimeInMillis());
    }
    /**
     * 获取传入天数当天的结束时间
     *  * 如果为空，默认今天
     * @param day yyyy-MM-dd
     * @return 1589904000000
     */
    public static String getDayOfEnd(Date day){  //2020-05-12
        Calendar c = Calendar.getInstance();
        Date date = null;
        if(day==null||"".equals(day)){
            date = new Date();
        }else {
            date = day;
        }
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND,999);
        return String.valueOf(c.getTimeInMillis());
    }


    /**
     *  //根据日期获取当月的开始和当月的结束
     * @param day yyyy-MM-dd
     * @return [1598889600000,1601481599999]
     */
    public static String[] getMonthByDayStr(String day){
        String[] str  = new String[2];
        Calendar c = Calendar.getInstance();
        Date date = parseDateStr(day);
        c.setTime(date);
        c.add(Calendar.MONTH,0); //当月
        c.set(Calendar.DAY_OF_MONTH,1); //当月的1号
        //设置小时、分钟、秒、毫秒
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        str[0] = String.valueOf(c.getTimeInMillis());

        //下个月
        c.add(Calendar.MONTH,+1);
        c.set(Calendar.DAY_OF_MONTH,1); //下月的1号
        c.add(Calendar.DAY_OF_MONTH,-1); //上月的最后一天
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND,999);
        str[1] = String.valueOf(c.getTimeInMillis());

        return str;


    }





    /**
     * 将字符串解析成Date类型
     * @param day  yyyy-MM-dd
     * @return java.util.Date
     */
    public static Date parseDateStr(String day){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
