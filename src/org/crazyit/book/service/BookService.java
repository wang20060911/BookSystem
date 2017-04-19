package org.crazyit.book.service;

import org.crazyit.book.vo.Book;

import java.util.Collection;

/**
 * 书本业务接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public interface BookService {

    /**
     * 查找全部的书
     * @return
     */
    Collection<Book> getAll();

    /**
     * 根据id获取书
     * @param id
     * @return
     */
    Book get(String id);

    /**
     * 新增一本书
     * @param book
     * @return
     */
    Book add(Book book);

    /**
     * 修改一本书
     * @param book
     * @return
     */
    Book update(Book book);

    /**
     * 根据名称模糊查询
     * @param name
     * @return
     */
    Collection<Book> find(String name);
}
