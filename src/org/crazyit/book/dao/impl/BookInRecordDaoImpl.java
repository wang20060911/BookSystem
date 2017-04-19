package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.BookInRecordDao;
import org.crazyit.book.vo.BookInRecord;
import org.crazyit.book.vo.ValueObject;

import java.util.Collection;
import java.util.Vector;

/**
 * 书本入库DAO实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class BookInRecordDaoImpl extends CommonDaoImpl implements BookInRecordDao{

    @Override
    public Collection<BookInRecord> findByInRecord(String inRecordId) {
        String sql = "SELECT * FROM T_BOOK_IN_RECORD r WHERE r.T_IN_RECORD_ID_FK = '" + inRecordId + "';";
        return getDatas(sql, new Vector<ValueObject>(), BookInRecord.class);
    }

    @Override
    public String save(BookInRecord record) {
        String sql = "INSERT INTO T_BOOK_IN_RECORD VALUES( ID, '" + record.getBook().getId() + "','" + record.getT_in_record_id_fk() + "','" + record.getIn_num() + "');";
        return String.valueOf(getJDBCExecutor().executeUpdate(sql));
    }
}
