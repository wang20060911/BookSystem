package org.crazyit.book.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性工具类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 17-4-11
 * Time: 下午8:22
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtil {
//    属性列表
    private static Properties properties = new Properties();
//    配置文件的路径
    private static String config = "/cfg/jdbc.properties";
//    读取资源文件，设置输入流
    private static InputStream is = PropertiesUtil.class.getResourceAsStream(config);
//    数据库驱动
    public static String jdbc_driver;
//    jdbc连接url
    public static String jdbc_url;
//    数据库用户名
    public static String jdbc_user;
//    数据库密码
    public static String jdbc_pass;

    static{
        try {
//            加载输入流
            properties.load(is);
            jdbc_driver = properties.getProperty("jdbc_driver");
            jdbc_url = properties.getProperty("jdbc_url");
            jdbc_user = properties.getProperty("jdbc_user");
            jdbc_pass = properties.getProperty("jdbc_pass");
            System.out.println("driver=" + jdbc_driver + ",url=" +jdbc_url + ",user=" + jdbc_user + ",pass =" +jdbc_pass);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
