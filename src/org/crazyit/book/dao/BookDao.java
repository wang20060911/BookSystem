package org.crazyit.book.dao;

import org.crazyit.book.vo.Book;

import java.util.Collection;

/**
 * 书本DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public interface BookDao {

    /**
     * 查找全部的书本
     * @return
     */
    Collection<Book> findAll();

    /**
     * 根据书本的ID获取书
     * @param id
     * @return
     */
    Book find(String id);

    /**
     * 添加已一本图书，并返回添加后书的id
     * @param book
     * @return
     */
    String add(Book book);

    /**
     * 修改一本书，返回书的id
     * @param book
     * @return
     */
    String update(Book book);

    /**
     * 根据书名称模糊查找书
     * @param name
     * @return
     */
    Collection<Book> findByName(String name);

    /**
     * 修改书的库存
     * @param book
     */
    void updateRepertory(Book book);
}
