package org.crazyit.book.commons;

/**
 * 数据异常
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
public class DataException extends RuntimeException{
    public DataException(String message){
        super(message);
    }
}
