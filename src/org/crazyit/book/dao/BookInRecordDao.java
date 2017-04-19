package org.crazyit.book.dao;

import org.crazyit.book.vo.BookInRecord;

import java.util.Collection;

/**
 * 书本入库记录DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public interface BookInRecordDao {

    /**
     * 根据入库记录查找全部的书的入库记录
     * @return
     */
    Collection<BookInRecord> findByInRecord(String inRecordId);

    /**
     * 保存一条书的入库记录，并返回该记录的id
     * @param record
     * @return
     */
    String save(BookInRecord record);
}
