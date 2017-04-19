package org.crazyit.book.jdbc;

/**
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 17-4-11
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */
public class QueryException extends RuntimeException{
    public QueryException(String message){
        super(message);
    }
}
