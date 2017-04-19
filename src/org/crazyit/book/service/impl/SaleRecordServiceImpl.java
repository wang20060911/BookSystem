package org.crazyit.book.service.impl;

import org.crazyit.book.commons.BusinessException;
import org.crazyit.book.commons.DateUtil;
import org.crazyit.book.dao.BookDao;
import org.crazyit.book.dao.BookSaleRecordDao;
import org.crazyit.book.dao.SaleRecordDao;
import org.crazyit.book.service.SaleRecordService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.BookSaleRecord;
import org.crazyit.book.vo.SaleRecord;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

/**
 * 销售记录业务实现类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/14
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public class SaleRecordServiceImpl implements SaleRecordService{

    private SaleRecordDao saleRecordDao;

    private BookSaleRecordDao bookSaleRecordDao;

    private BookDao bookDao;

    public SaleRecordServiceImpl(SaleRecordDao saleRecordDao, BookSaleRecordDao bookSaleRecordDao, BookDao bookDao){
        this.saleRecordDao = saleRecordDao;
        this.bookSaleRecordDao = bookSaleRecordDao;
        this.bookDao = bookDao;
    }

    @Override
    public void saveRecord(SaleRecord record) {
        //遍历判断书的库存是否不够
        for (BookSaleRecord r : record.getBookSaleRecords()) {
            String bookId = r.getBook().getId();
            Book book = bookDao.find(bookId);
            //当库存不够时，抛出异常
            if(Integer.valueOf(r.getTrade_num()) > Integer.valueOf(book.getRepertory_size())){
                throw new BusinessException(book.getBook_name() + "的库存不够");
            }
        }
        //先保存交易记录
        String id = saleRecordDao.save(record);
        //在保存书的交易记录
        for(BookSaleRecord r : record.getBookSaleRecords()){
            //设置销售纪录id
            r.setT_sale_record_id_fk(id);
            bookSaleRecordDao.saveBookSaleRecord(r);
            //修改书的库存
            String bookId = r.getBook().getId();
            Book book = bookDao.find(bookId);
            //计算剩余的库存
            int level = Integer.valueOf(book.getRepertory_size()) - Integer.valueOf(r.getTrade_num());
            //设置库存并将库存书保存到数据库
            book.setRepertory_size(String.valueOf(level));
            bookDao.updateRepertory(book);

        }
    }

    @Override
    public Collection<SaleRecord> getAll(Date date) {
        //得到下一天
        Date nextDate = DateUtil.getNextDate(date);
        //得到今天的日期，格式为yyyy-MM-dd
        String today = DateUtil.getDateString(date);
        //得到明天的日期，格式为yyyy-MM-dd
        String tomorrow = DateUtil.getDateString(nextDate);
        Collection<SaleRecord> records = saleRecordDao.findByDate(today,tomorrow);
        for (SaleRecord record : records) {
            procesDatas(record);
        }
        return records;
    }

    @Override
    public SaleRecord get(String id) {
        SaleRecord record = saleRecordDao.findById(id);
        return procesDatas(record);
    }

    /**
     * 处理一个SaleRecord,设置它的书本销售记录属性和书本名字属性
     * @param record
     * @return
     */
    private SaleRecord procesDatas(SaleRecord record){
        //查找该记录所对应的书的销售记录
        Collection<BookSaleRecord> bookSaleRecords = bookSaleRecordDao.findBySaleRecord(record.getId());
        //设置结果集中的每一个book属性
        setBook(bookSaleRecords);
        //设置SaleRecord对象中的书的销售记录集合
        record.setBookSaleRecords((Vector<BookSaleRecord>) bookSaleRecords);
        //设置SaleRecord的书名集合
        record.setBookNames(getBookNames(bookSaleRecords));
        //设置数量和总价
        record.setAmount(getAmount(bookSaleRecords));
        record.setTotalPrice(getTotalPrice(bookSaleRecords));
        return record;
    }

    /**
     * 获取一次交易中涉及的总价
      * @param bookSaleRecords
     * @return
     */
    private double getTotalPrice(Collection<BookSaleRecord> bookSaleRecords){
        double result = 0;
        for (BookSaleRecord bookSaleRecord : bookSaleRecords) {
            //书本的交易量
            int tradeSum = Integer.valueOf(bookSaleRecord.getTrade_num());
            //书的单价
            double bookPrice = Double.valueOf(bookSaleRecord.getBook().getBook_price());
            result += (bookPrice * tradeSum);
        }
         return result;
    }

    /**
     * 获取一次交易中所有书本的交易量
     * @param bookSaleRecords
     * @return
     */
    private int getAmount(Collection<BookSaleRecord> bookSaleRecords){
        int result = 0;
        for (BookSaleRecord bookSaleRecord : bookSaleRecords) {
            result += Integer.valueOf(bookSaleRecord.getTrade_num());
        }
        return result;
    }

    /**
     * 设置参数中的每一个BookSaleRecord的book属性
     * @param bookSaleRecords
     */
    private void setBook(Collection<BookSaleRecord> bookSaleRecords){
        for (BookSaleRecord bookSaleRecord : bookSaleRecords) {
            Book book = bookDao.find(bookSaleRecord.getBook_id_fk());
            bookSaleRecord.setBook(book);
        }
    }

    /**
     * 获取一次交易中所有书本的名字，以逗号隔开
     * @param bookSaleRecords
     * @return
     */
    private String getBookNames(Collection<BookSaleRecord> bookSaleRecords){
       if(bookSaleRecords.size() == 0)
           return "";
       StringBuffer result = new StringBuffer();
        for (BookSaleRecord bookSaleRecord : bookSaleRecords) {
            Book book = bookSaleRecord.getBook();
            result.append(book.getBook_name() + ",");
        }
        return result.substring(0, result.lastIndexOf(","));
    }
}
