package org.crazyit.book.commons;

/**
 * 上传的异常
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午6:00
 * To change this template use File | Settings | File Templates.
 */
public class UploadException extends RuntimeException{
    public UploadException(String message){
        super(message);
    }
}
