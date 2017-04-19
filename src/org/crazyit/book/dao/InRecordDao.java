package org.crazyit.book.dao;

import org.crazyit.book.vo.InRecord;

import java.util.Collection;

/**
 * 入库记录DAO接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public interface InRecordDao {

    /**
     * 根据日期区间查找入库记录
     * @param begin
     * @param end
     * @return
     */
    Collection<InRecord> findByDate(String begin, String end);

    /**
     * 根据入库记录id获得入库记录对象
     * @param id
     * @return
     */
    InRecord findById(String id);

    /**
     * 保存一个入库记录
     * @param record
     * @return
     */
    String save(InRecord record);
}
