package org.crazyit.book.service.impl;

import org.crazyit.book.dao.ConcernDao;
import org.crazyit.book.service.ConcernService;
import org.crazyit.book.vo.Concern;

import java.util.Collection;

/**
 * 出版社业务实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class ConcernServiceImpl implements ConcernService{

    private ConcernDao concernDao;

    public ConcernServiceImpl(ConcernDao concernDao){
        this.concernDao = concernDao;
    }

    @Override
    public Collection<Concern> getAll() {
        return concernDao.findAll();
    }

    @Override
    public Concern find(String id) {
        return concernDao.find(id);
    }

    @Override
    public Concern add(Concern concern) {
        String id = concernDao.add(concern);
        return find(id);
    }

    @Override
    public Concern update(Concern concern) {
        String id = concernDao.update(concern);
        return find(id);
    }

    @Override
    public Collection<Concern> query(String name) {
        return concernDao.findByName(name);
    }
}
