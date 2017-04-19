package org.crazyit.book.service.impl;

import org.crazyit.book.commons.BusinessException;
import org.crazyit.book.dao.UserDao;
import org.crazyit.book.service.UserService;
import org.crazyit.book.vo.User;

/**
 * 用户业务实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void login(String name, String password) {
        User user = userDao.getUser(name, password);
        if(user == null){
            throw  new BusinessException("用户名或密码错误！");
        }
    }
}
