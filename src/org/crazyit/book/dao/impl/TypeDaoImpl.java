package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.TypeDao;
import org.crazyit.book.vo.Type;
import org.crazyit.book.vo.ValueObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * 书本种类DAOs实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class TypeDaoImpl extends CommonDaoImpl implements TypeDao{

    @Override
    public Collection<Type> find() {
        String sql = "SELECT * FROM T_BOOK_TYPE type ORDER BY type.ID DESC;";
        return getDatas(sql, new Vector<ValueObject>(),Type.class);
    }

    @Override
    public Collection<Type> findByName(String name) {
        String sql = "SELECT * FROM T_BOOK_TYPE type WHERE type.TYPE_NAME LIKE '% " + name + " %' ORDER BY type.ID DESC;";
        List<Type> datas = (List<Type>) getDatas(sql, new Vector<ValueObject>(), Type.class);
        return datas;
    }

    @Override
    public Type find(String id) {
        String sql ="SELECT * FROM T_BOOK_TYPE type WHERE type.ID = " + id + ";";
        List<Type> datas = (List<Type>) getDatas(sql, new ArrayList<ValueObject>(), Type.class);
        return datas.get(0);
    }

    @Override
    public String add(Type type) {
        String sql = "INSERT INTO T_BOOK_TYPE VALUES (ID,'" + type.getType_name() + "','" + type.getType_intro() + "');";
        String id = String.valueOf(getJDBCExecutor().executeUpdate(sql));
        return id;
    }

    @Override
    public String update(Type type) {
        String sql = "UPDATE T_BOOK_TYPE type SET type.TYPE_NAME = '" + type.getType_name() +"',type.TYPE_INTRO = '" + type.getType_intro() + "' WHERE type.TYPE_ID = '" + type.getId() + "';";
        getJDBCExecutor().executeUpdate(sql);
        return type.getId();
    }
}
