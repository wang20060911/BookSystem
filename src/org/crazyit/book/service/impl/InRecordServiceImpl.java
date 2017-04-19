package org.crazyit.book.service.impl;

import org.crazyit.book.commons.DateUtil;
import org.crazyit.book.dao.BookDao;
import org.crazyit.book.dao.BookInRecordDao;
import org.crazyit.book.dao.InRecordDao;
import org.crazyit.book.service.InRecordService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.BookInRecord;
import org.crazyit.book.vo.InRecord;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

/**
 * 入库记录业务实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public class InRecordServiceImpl implements InRecordService{

    private InRecordDao inRecordDao;

    private BookInRecordDao bookInRecordDao;

    private BookDao bookDao;

    public InRecordServiceImpl(InRecordDao inRecordDao, BookInRecordDao bookInRecordDao, BookDao bookDao){
        this.inRecordDao = inRecordDao;
        this.bookInRecordDao = bookInRecordDao;
        this.bookDao = bookDao;
    }

    @Override
    public void save(InRecord record) {
        String id = inRecordDao.save(record);
        for(BookInRecord bookInRecord : record.getBookInRecords()){
            bookInRecord.setT_in_record_id_fk(id);
            bookInRecordDao.save(bookInRecord);
            //修改书的库存
            String bookId = bookInRecord.getBook().getId();
            Book book = bookDao.find(bookId);
            book.setRepertory_size(String.valueOf(Integer.valueOf(book.getRepertory_size()) + Integer.valueOf(bookInRecord.getIn_num())));
            bookDao.updateRepertory(book);
        }
    }

    @Override
    public Collection<InRecord> getAll(Date date) {
        //得到下一天
        Date nextDate = DateUtil.getNextDate(date);
        //得到今天的日期，格式为yyyy-MM-dd
        String today = DateUtil.getDateString(date);
        //得到明天的日期，格式为yyyy-MM-dd
        String tomorrow = DateUtil.getDateString(nextDate);
        Collection<InRecord> records = inRecordDao.findByDate(today, tomorrow);
        for (InRecord record : records) {
            processDatas(record);
        }
        return records;
    }

    @Override
    public InRecord get(String id) {
        InRecord record = inRecordDao.findById(id);
        return processDatas(record);
    }

    private InRecord processDatas(InRecord record){
        Collection<BookInRecord> bookInRecords = bookInRecordDao.findByInRecord(record.getId());
        //设置记录中的每一本书
        setBook(bookInRecords);
        //设置入库记录中的书的入库记录
        record.setBookInRecords((Vector<BookInRecord>) bookInRecords);
        //设置书名
        record.setBookNames(getBookNames(bookInRecords));
        //设置书总数
        record.setAmount(getAmount(bookInRecords));
        return record;
    }

    /**
     * 获取一次入库记录中所有书本的交易量
     * @param bookInRecords
     * @return
     */
    private int getAmount(Collection<BookInRecord> bookInRecords){
        int result = 0;
        for (BookInRecord bookInRecord : bookInRecords) {
            result += Integer.valueOf(bookInRecord.getIn_num());
        }
        return  result;
    }

    /**
     * 设置参数中的每一个BookInRecord的book属性
     * @param bookInRecords
     */
    private void setBook(Collection<BookInRecord> bookInRecords){
        for (BookInRecord bookInRecord : bookInRecords) {
            Book book = bookDao.find(bookInRecord.getBook_id_fk());
            bookInRecord.setBook(book);
        }
    }

    /**
     * 获取一次入库记录中所育书本的名字，以逗号隔开
     * @param bookInRecords
     * @return
     */
    private String getBookNames(Collection<BookInRecord> bookInRecords){
        if(bookInRecords.size() == 0)
            return "";
        StringBuffer result = new StringBuffer();
        for (BookInRecord bookInRecord : bookInRecords) {
            Book book = bookInRecord.getBook();
            result.append((book.getBook_name()) + ",");
        }
        return result.substring(0,result.lastIndexOf(","));
    }
}
