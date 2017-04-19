package org.crazyit.book.dao;

import org.crazyit.book.vo.BookSaleRecord;

import java.util.Collection;

/**
 * 书本销售记录DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public interface BookSaleRecordDao {

    /**
     * 根据销售记录id获取该销售记录下所有的书的销售记录
     * @param saleRecordId
     * @return
     */
    Collection<BookSaleRecord> findBySaleRecord(String saleRecordId);

    /**
     * 保存一条书的销售记录
     * @param record
     * @return
     */
    String saveBookSaleRecord(BookSaleRecord record);
}
