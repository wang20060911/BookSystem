package org.crazyit.book.dao;

import org.crazyit.book.vo.User;

/**
 * 用户DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao{
        User getUser(String name, String password);
}
