package org.crazyit.book.service;

/**
 * 用户业务接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    /**
     * 用户登录的方法，如果登录失败，则抛出BusinessException
     * @param name
     * @param password
     */
    void login(String name, String password);
}
