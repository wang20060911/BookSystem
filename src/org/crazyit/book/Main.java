package org.crazyit.book;

import org.crazyit.book.dao.UserDao;
import org.crazyit.book.dao.impl.UserDaoImpl;
import org.crazyit.book.service.UserService;
import org.crazyit.book.service.impl.UserServiceImpl;
import org.crazyit.book.ui.LoginFrame;

/**
 * 程序入口类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/19
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        LoginFrame loginFrame = new LoginFrame(userService);
    }
}
