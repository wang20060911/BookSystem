package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.InRecordDao;
import org.crazyit.book.vo.InRecord;
import org.crazyit.book.vo.ValueObject;
import org.omg.CORBA.INTF_REPOS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * 入库记录DAO实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */
public class InRecordDaoImpl extends CommonDaoImpl implements InRecordDao{

    @Override
    public Collection<InRecord> findByDate(String begin, String end) {
        String sql = "SELECT * FROM T_IN_RECORD r WHERE r.RECORD_DATE > '" + begin + "' AND r.RECORD_DATE < '" + end + "';";
        return getDatas(sql, new Vector<ValueObject>(),InRecord.class);
    }

    @Override
    public InRecord findById(String id) {
        String sql ="SELECT * FROM T_IN_RECORD r WHERE r.ID = '" + id + "';";
        List<InRecord> datas = (List<InRecord>) getDatas(sql, new ArrayList<ValueObject>(), InRecord.class);
        return (datas.size() == 1) ? datas.get(1) : null;
    }

    @Override
    public String save(InRecord record) {
        String sql = "INSERT INTO T_IN_RECORD VALUES( ID,'" + record.getRecord_date() + "');";
        return String.valueOf(getJDBCExecutor().executeUpdate(sql));
    }
}
