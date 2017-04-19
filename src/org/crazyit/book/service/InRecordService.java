package org.crazyit.book.service;

import org.crazyit.book.vo.InRecord;

import java.util.Collection;
import java.util.Date;

/**
 * 入库记录业务接口
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
public interface InRecordService {

    /**
     * 保存一条入库记录
     * @param record
     */
    void save(InRecord record);

    /**
     * 根据日期查找对应的入库记录
     * @param date
     * @return
     */
    Collection<InRecord> getAll(Date date);

    /**
     * 根据id获得入库记录
     * @param id
     * @return
     */
    InRecord get(String id);
}
