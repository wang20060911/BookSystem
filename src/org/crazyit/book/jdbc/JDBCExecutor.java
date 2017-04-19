package org.crazyit.book.jdbc;

import java.sql.*;

/**
 * SQL执行类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 17-4-12
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
public class JDBCExecutor {
//    获得驱动
    private static String driver = PropertiesUtil.jdbc_driver;
//    获得url
    private static String url = PropertiesUtil.jdbc_url;
//    获得连接数据库的用户名
    private static String user = PropertiesUtil.jdbc_user;
//    获得联结数据库的密码
    private static String pass = PropertiesUtil.jdbc_pass;
//    连接对象
    private Connection connection;
//    维护一个本类型的对象
    private static JDBCExecutor jdbcExecutor;
//    Statement对象，可以执行SQL语句并返回结果
    private Statement stmt;

//    私有的构造器
    private JDBCExecutor(){
        try {
//            初始化JDBC驱动器并让驱动加在到JVM中
            Class.forName(driver);
//            创建数据库连接
            connection = DriverManager.getConnection(url,user,pass);
//            创建Statement对象
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new JDBCException(e.getMessage());
        }
    }

//    提供一个静态方法返回本类的实例
    public static JDBCExecutor getJdbcExecutor(){
        if(jdbcExecutor == null){
            jdbcExecutor = new JDBCExecutor();
        }
        return jdbcExecutor;
    }

//    执行一条查询的SQL
    public ResultSet executeQuery(String sql){
        try {
            ResultSet result = stmt.executeQuery(sql);
            return result;
        } catch (SQLException e) {
            throw new QueryException(e.getMessage());
        }
    }

//    执行单条INSERT、UPDATE或DELETE语句，如果执行INSERT时，返回主键
     public int executeUpdate(String sql){
         int result = -1;
         try {
//             执行SQL语句
             stmt.executeUpdate(sql);
//             获得主键
             ResultSet rs = stmt.getGeneratedKeys();
            while(rs.next()){
                result = rs.getInt(1);
            }
            rs.close();
             return result;
         } catch (SQLException e) {
             throw new QueryException(e.getMessage());
         }
     }
}












