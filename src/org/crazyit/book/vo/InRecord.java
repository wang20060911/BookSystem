package org.crazyit.book.vo;

import java.util.Vector;

/**
 * 入库记录
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
public class InRecord extends ValueObject {
//    入库时间
    private String record_date;
//    入库的总数量
    private int amount;
//    该入库记录所对应书的入库记录
    private Vector<BookInRecord> bookInRecords;
//    入库书的名称，以逗号隔开
    private String bookNames;

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Vector<BookInRecord> getBookInRecords() {
        return bookInRecords;
    }

    public void setBookInRecords(Vector<BookInRecord> bookInRecords) {
        this.bookInRecords = bookInRecords;
    }

    public String getBookNames() {
        return bookNames;
    }

    public void setBookNames(String bookNames) {
        this.bookNames = bookNames;
    }
}
