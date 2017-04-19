package org.crazyit.book.commons;

import org.crazyit.book.jdbc.JDBCExecutor;
import org.crazyit.book.vo.Type;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据转换工具类
 * Created with IntelliJ IDEA.
 * User: wangxudong
 * Date: 17-4-11
 * Time: 下午8:18
 * To change this template use File | Settings | File Templates.
 */
public class DataUtil {

//    将rs中的值封装成一个集合
    public static Collection getDatas(Collection result, ResultSet rs, Class clazz){
        try {
            while(rs.next()){
//                创建类的实例
               Object vo = clazz.newInstance();
//               获取本对象的属性
               Field[] fields = clazz.getDeclaredFields();
//                获取父类的属性
               Field[] superFields = clazz.getSuperclass().getDeclaredFields();
//               父类的属性和自己的属性相加
               Field[] allFields = addFields(superFields, fields);
                for (Field field:allFields) {
//                    获得setter方法的方法名
                    String setterMethodName = getSetterMethodName(field.getName());
                    Method setterMethod = clazz.getMethod(setterMethodName, field.getType());
                    invokeMethod(rs, field, vo, setterMethod);
                }
                result.add(vo);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

//    执行一个方法，从ResultSet中获取一个字段的数据，调用vo的setter方法
    private static void invokeMethod(ResultSet rs, Field field, Object vo, Method setterMethod){
        try {
            String value = rs.getString(field.getName());
            setterMethod.invoke(vo,value);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

//    根据属性名获得setter方法的方法名
     private static String getSetterMethodName(String fieldName){
         String begin = fieldName.substring(0,1).toUpperCase();
         String end = fieldName.substring(1, fieldName.length());
         String methodName = "set" + begin + end;
         return methodName;
     }

//    相加两个数组
    private static Field[] addFields(Field[] f1, Field[] f2){
        List<Field> l = new ArrayList<Field>();
        for(Field f: f1){
            l.add(f);
        }
        for(Field f:f2){
            l.add(f);
        }
        return l.toArray(new Field[f1.length + f2.length]);
    }

    public static void main(String[] args) {
        JDBCExecutor executor = JDBCExecutor.getJdbcExecutor();
        ResultSet rs = executor.executeQuery("SELECT * FROM t_book_type");
    }
}








