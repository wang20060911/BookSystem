package org.crazyit.book.service;

import org.crazyit.book.vo.SaleRecord;

import java.util.Collection;
import java.util.Date;

/**
 * 销售业务接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public interface SaleRecordService{

    /**
     * 新增一条销售记录
     * @param record
     */
    void saveRecord(SaleRecord record);

    /**
     * 根据日期获取该日期对应的销售记录
     * @param date
     * @return
     */
    Collection<SaleRecord> getAll(Date date);

    /**
     * 根据id获得销售记录
     * @param id
     * @return
     */
    SaleRecord get(String id);
}
