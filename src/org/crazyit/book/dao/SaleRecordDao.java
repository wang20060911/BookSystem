package org.crazyit.book.dao;

import org.crazyit.book.vo.SaleRecord;

import java.util.Collection;

/**
 * 销售记录DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public interface SaleRecordDao {

    /**
     * 根据两个日期，查找两个日期之间的交易记录
     * @param begin
     * @param end
     * @return
     */
    Collection<SaleRecord> findByDate(String begin, String end);

    /**
     * 根据id查找销售记录
     * @param id
     * @return
     */
    SaleRecord findById(String id);

    /**
     * 保存一条销售纪录
     * @param record
     * @return
     */
    String save(SaleRecord record);
}
