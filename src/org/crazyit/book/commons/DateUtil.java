package org.crazyit.book.commons;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

//    获取日期参数date的下一天
    public static Date getNextDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        return calendar.getTime();
    }

//    将日期转换成为yyyy-MM-dd的格式
    public static String getDateString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
