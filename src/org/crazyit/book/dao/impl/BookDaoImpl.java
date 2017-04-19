package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.BookDao;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.ValueObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * 书本DAO实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public class BookDaoImpl extends CommonDaoImpl implements BookDao{
    @Override
    public Collection<Book> findAll() {
        String sql = "SELECT * FROM T_BOOK book ORDER BY book.ID DESC;";
        return getDatas(sql, new Vector<ValueObject>(), Book.class);
    }

    @Override
    public Book find(String id) {
        String sql = "SELECT * FROM T_BOOK book WHERE book.ID = '" + id + "';";
        List<Book> datas = (List<Book>) getDatas(sql, new ArrayList<ValueObject>(), Book.class);
        return (datas.size() == 1) ? datas.get(0) : null;
    }

    @Override
    public String add(Book book) {
        String sql = "INSERT INTO T_BOOK VALUES(ID,'" + book.getBook_name() + "','" + book.getBook_intro() + "','" + book.getBook_price() + "','" + book.getType_id_fk() + "','" + book.getPub_id_fk() + "','" +book.getImage_url() + "','" + book.getAuthor() + "','" + book.getRepertory_size() + "');";
        return String.valueOf(getJDBCExecutor().executeUpdate(sql));
    }

    @Override
    public String update(Book book) {
        String sql = "UPDATE T_BOOK book SET book.BOOK_NAME = '" + book.getBook_name() +"',book.BOOK_INTRO = '" + book.getBook_intro() + "',book.BOOK_PRICE = '" + book.getBook_price() + "', book.TYPE_ID_FK = '" + book.getType_id_fk() + "',book.PUB_ID_FK = '," + book.getPub_id_fk() + "',book.IMAGE_URL = '" + book.getImage_url() + "',book.AUTHOR = '" + book.getAuthor() + "' WHERE book.ID = '" + book.getId() + "';" ;
        getJDBCExecutor().executeUpdate(sql);
        return book.getId();
    }

    @Override
    public Collection<Book> findByName(String name) {
        String sql = "SELECT * FROM T_BOOK book WHERE book.BOOK_NAME LIKE '%" + name + "%' ORDER BY book.ID DESC;";
        return getDatas(sql, new Vector<ValueObject>(),Book.class);
    }

    @Override
    public void updateRepertory(Book book) {
        String sql = "UPDATE T_BOOK book SET book.REPERTORY_SIE = '" + book.getRepertory_size() + "' WHERE book.ID = '" + book.getId() + "';";
        getJDBCExecutor().executeUpdate(sql);
    }
}
