package org.crazyit.book.service;

import org.crazyit.book.vo.Type;

import java.util.Collection;

/**
 * 书本种类业务接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public interface TypeService {

    /**
     * 查找所有的种类
     * @return
     */
    Collection<Type> getAll();

    /**
     * 根据种类名字模糊查找种类
     * @param name
     * @return
     */
    Collection<Type> query(String name);

    /**
     * 新增一个书本种类
     * @param type
     * @return
     */
    Type add(Type type);

    /**
     * 修改一个书本种类
     * @param type
     * @return
     */
    Type update(Type type);

    /**
     * 根据主键查找一个种类
     * @param id
     * @return
     */
    Type get(String id);
}

