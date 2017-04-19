package org.crazyit.book.jdbc;

/**
 * JDBC异常类
 * Created with IntelliJ IDEA.
 * User: wangxudong
 * Date: 17-4-11
 * Time: 下午8:19
 * To change this template use File | Settings | File Templates.
 */
public class JDBCException extends RuntimeException{
    public JDBCException(String message){
        super(message);
    }
}
