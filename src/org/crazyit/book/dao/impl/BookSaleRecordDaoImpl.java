package org.crazyit.book.dao.impl;

import org.crazyit.book.dao.BookSaleRecordDao;
import org.crazyit.book.vo.BookSaleRecord;
import org.crazyit.book.vo.ValueObject;

import java.util.Collection;
import java.util.Vector;

/**
 * 书本销售记录DAO实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
public class BookSaleRecordDaoImpl extends CommonDaoImpl implements BookSaleRecordDao{

    @Override
    public Collection<BookSaleRecord> findBySaleRecord(String saleRecordId) {
        String sql = "SELECT * FROM T_BOOK_SALE_RECORD r WHERE r.T_SALE_RECORD_ID_FK = '" + saleRecordId + ",;";
        return getDatas(sql, new Vector<ValueObject>(), BookSaleRecord.class);
    }

    @Override
    public String saveBookSaleRecord(BookSaleRecord record) {
        String sql = "INSERT INTO T_BOOK_SALE_RECORD_VALUES(ID,'" + record.getBook().getId() + "','" + record.getT_sale_record_id_fk() + "','" + record.getTrade_num() + "');";
        return String.valueOf(getJDBCExecutor().executeUpdate(sql));
    }
}
