package org.crazyit.book.vo;

import java.util.Vector;

/**
 * 销售记录
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午5:17
 * To change this template use File | Settings | File Templates.
 */
public class SaleRecord extends ValueObject {
//    交易日期
    private String record_date;
//    销售的总数量
    private int amount;
//    销售的总价钱
    private double totalPrice;
//    书的销售记录
    private Vector<BookSaleRecord> bookSaleRecords;
//    记录中对应所有书的名称
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Vector<BookSaleRecord> getBookSaleRecords() {
        return bookSaleRecords;
    }

    public void setBookSaleRecords(Vector<BookSaleRecord> bookSaleRecords) {
        this.bookSaleRecords = bookSaleRecords;
    }

    public String getBookNames() {
        return bookNames;
    }

    public void setBookNames(String bookNames) {
        this.bookNames = bookNames;
    }
}
