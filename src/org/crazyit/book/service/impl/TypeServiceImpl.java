package org.crazyit.book.service.impl;

import org.crazyit.book.dao.TypeDao;
import org.crazyit.book.service.TypeService;
import org.crazyit.book.vo.Type;

import java.util.Collection;

/**
 * 书本种类业务实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class TypeServiceImpl implements TypeService{

    private TypeDao typeDao;

    public TypeServiceImpl(TypeDao typeDao){
        this.typeDao = typeDao;
    }

    @Override
    public Collection<Type> getAll() {
        return typeDao.find();
    }

    @Override
    public Collection<Type> query(String name) {
        return typeDao.findByName(name);
    }

    @Override
    public Type add(Type type) {
        String id = typeDao.add(type);
        return get(id);
    }

    @Override
    public Type update(Type type) {
        String id = typeDao.update(type);
        return get(id);
    }

    @Override
    public Type get(String id) {
        return typeDao.find(id);
    }
}
