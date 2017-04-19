package org.crazyit.book.dao;

import org.crazyit.book.vo.Type;

import java.util.Collection;

/**
 * 书本种类DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
public interface TypeDao {

    /**
     * 查找所有的种类
     * @return  种类的集合
     */
    Collection<Type> find();

    /**
     * 根据名字模糊查找种类
     * @param name 种类的名字
     * @return
     */
    Collection<Type> findByName(String name);

    /**
     * 根据id查找种类
     * @param id  种类的id
     * @return 种类的值对象
     */
    Type find(String id);

    /**
     * 添加一个种类
     * @param type 需要添加的种类
     * @return
     */
    String add(Type type);

    /**
     * 修改一个种类
     * @param type 修改的种类
     * @return
     */
    String update(Type type);
}
