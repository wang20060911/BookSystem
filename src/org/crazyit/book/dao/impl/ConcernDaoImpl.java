package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.ConcernDao;
import org.crazyit.book.vo.Concern;
import org.crazyit.book.vo.ValueObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * 出版社DAO实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
public class ConcernDaoImpl extends CommonDaoImpl implements ConcernDao{

     @Override
    public Collection<Concern> findAll() {
         String sql = "SELECT * FROM T_PUBLISHER pub ORDER BY pub.ID DESC";
        return getDatas(sql, new Vector<ValueObject>(), Concern.class);
    }

    @Override
    public Concern find(String id) {
        String sql = "SELECT * FROM T_PUBLISHER pub WHERE pub.ID = '" + id + "';";
        List<Concern> datas = (List<Concern>) getDatas(sql, new ArrayList<ValueObject>(), Concern.class);
        return (datas.size() == 1) ? datas.get(0) : null;
    }

    @Override
    public String add(Concern concern) {
        String sql = "INSERT INTO T_PUBLISHER VALUES(ID,'" + concern.getPub_name() + "','" + concern.getPub_tel() + "','" + concern.getPub_link_man() + "','" + concern.getPub_intro() + "';";
        String id = String.valueOf(getJDBCExecutor().executeUpdate(sql));
        return id;
    }

    @Override
    public String update(Concern concern) {
        String sql = "UPDATE T_PUBLISHER pub SET pub.PUB_NAME = '" + concern.getPub_name() + "',pub.PUB_TEL ='" + concern.getPub_tel() + "',pub.PUB_LINK_MAN = '" + concern.getPub_link_man() + "',pub.PUB_INTRO = '" +concern.getPub_intro() + "' WHERE pub.ID = '" + concern.getId() + "';";
        getJDBCExecutor().executeUpdate(sql);
        return concern.getId();
    }

    @Override
    public Collection<Concern> findByName(String name) {
        String sql = "SELECT * FROM T_PUBLISHER pub WHERE pub.PUB_NAME LIKE '%" + name + "%' ORDER BY pub.ID DESC";
        return getDatas(sql, new Vector<ValueObject>(), Concern.class);
    }
}
