package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.SaleRecordDao;
import org.crazyit.book.vo.SaleRecord;
import org.crazyit.book.vo.ValueObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class SaleRecordDaoImpl extends CommonDaoImpl implements SaleRecordDao {
    
    @Override
    public Collection<SaleRecord> findByDate(String begin, String end) {
        String sql = "SELECT * FROM T_SALE_RECORD r WHERE r.RECORD_DATE > '" + begin + "' AND r.RECORD_DATE < '" + end + "';";
        return getDatas(sql, new Vector<ValueObject>(), SaleRecord.class);
    }

    @Override
    public SaleRecord findById(String id) {
        String sql = "SELECT * FROM T_SALE_RECORD r WHERE r.ID = '" + id + "';";
        List<SaleRecord> list = (List<SaleRecord>) getDatas(sql, new ArrayList<ValueObject>(), SaleRecord.class);
        return (list.size() == 0) ? null : list.get(0);
    }

    @Override
    public String save(SaleRecord record) {
        String sql = "INSERT INTO T_SALE_RECORD VALUES(ID, '" + record.getRecord_date() + "');";
        return String.valueOf(getJDBCExecutor().executeUpdate(sql));
    }
}
