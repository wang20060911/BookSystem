package org.crazyit.book.service.impl;

import org.crazyit.book.dao.BookDao;
import org.crazyit.book.dao.ConcernDao;
import org.crazyit.book.dao.TypeDao;
import org.crazyit.book.service.BookService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.Concern;
import org.crazyit.book.vo.Type;

import java.util.Collection;

/**
 * 书本业务实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    private TypeDao typeDao;

    private ConcernDao concernDao;

    public BookServiceImpl(BookDao bookDao, TypeDao typeDao, ConcernDao concernDao){
        this.bookDao = bookDao;
        this.typeDao = typeDao;
        this.concernDao = concernDao;
    }

    @Override
    public Collection<Book> getAll() {
        Collection<Book> result = bookDao.findAll();
        return setAssociate(result);
    }

    @Override
    public Book get(String id) {
        Book book = bookDao.find(id);
        //查找书对应的种类
        Type type = typeDao.find(book.getType_id_fk());
        //查找书对应的出版社
        Concern concern = concernDao.find(book.getPub_id_fk());
        book.setType(type);
        book.setConcern(concern);
        return book;
    }

    @Override
    public Book add(Book book) {
        String id = bookDao.add(book);
        return get(id);
    }

    @Override
    public Book update(Book book) {
        String id = bookDao.update(book);
        return get(id);
    }

    @Override
    public Collection<Book> find(String name) {
        Collection<Book> result = bookDao.findByName(name);
        return setAssociate(result);
    }

    /**
     * 设置关系对象
      * @param result
     * @return
     */
    private Collection<Book> setAssociate(Collection<Book> result){
        //遍历结果集合，设置每一本书的对象
        for(Book book : result){
            //查找出对应的种类，再为书设置种类对象
            book.setType(typeDao.find(book.getType_id_fk()));
            //查找出对应出版社，再为书设置出版社对象
            book.setConcern(concernDao.find(book.getPub_id_fk()));
        }
        return result;
    }
}
