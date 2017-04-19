package org.crazyit.book.commons;

/**
 * 业务异常
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
