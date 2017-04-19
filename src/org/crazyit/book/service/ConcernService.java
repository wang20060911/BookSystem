package org.crazyit.book.service;

import org.crazyit.book.vo.Concern;

import java.util.Collection;

/**
 * 出版社业务接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public interface ConcernService {

    /**
     * 获取全部的出版社
     * @return
     */
    Collection<Concern> getAll();

    /**
     * 根据id查找一个出版社
     * @param id
     * @return
     */
    Concern find(String id);

    /**
     * 添加一个出版社
     * @param concern
     * @return
     */
    Concern add(Concern concern);

    /**
     * 修改一个出版社
     * @param concern
     * @return
     */
    Concern update(Concern concern);

    /**
     * 根据出版社名字模糊查找
     * @param name
     * @return
     */
    Collection<Concern> query(String name);
}
