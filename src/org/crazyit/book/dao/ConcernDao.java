package org.crazyit.book.dao;

import org.crazyit.book.vo.Concern;

import java.util.Collection;

/**
 * 出版社DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public interface ConcernDao {

    /**
     * 查找全部的出版社
     * @return
     */
    Collection<Concern> findAll();

    /**
     * 根据id查找出版社
     * @param id
     * @return
     */
    Concern find(String id);

    /**
     * 添加一个出版社
     * @param concern
     * @return
     */
    String add(Concern concern);

    /**
     * 修改一个出版社
     * @param concern
     * @return
     */
    String update(Concern concern);

    /**
     * 根据名字模糊查找出版社
     * @param name
     * @return
     */
    Collection<Concern> findByName(String name);

}
