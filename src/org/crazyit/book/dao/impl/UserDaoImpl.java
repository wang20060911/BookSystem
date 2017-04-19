package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.UserDao;
import org.crazyit.book.vo.User;
import org.crazyit.book.vo.ValueObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户DAO实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoImpl extends CommonDaoImpl implements UserDao{

    @Override
    public User getUser(String name, String password) {
        String sql = "SELECT * FROM T_USER user WHERE user.USER_NAME = '" + name + "' AND user.USER_PASSWORD = '" + password + "';";
        List<User> datas = (List<User>) getDatas(sql, new ArrayList<ValueObject>(),User.class);
        System.out.println("返回结果：" + datas.size());
        return (datas.size() == 1) ? datas.get(0) : null;
    }
}
