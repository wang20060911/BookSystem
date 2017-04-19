package org.crazyit.book.vo;

/**
 * 书的销售记录
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午5:17
 * To change this template use File | Settings | File Templates.
 */
public class BookSaleRecord extends ValueObject{
//    该记录对应的书的外键
    private String book_id_fk;
//    该记录对应的销售记录外键
    private String t_sale_record_id_fk;
//    该记录对应的书的销售数量
    private String trade_num;
//    该记录对应的书的对象，
    private Book book;
//    该记录对应的销售记录对象，当从数据库查找到BookSaleRecord时，该属性为null
    private SaleRecord saleRecord;

    public String getBook_id_fk() {
        return book_id_fk;
    }

    public void setBook_id_fk(String book_id_fk) {
        this.book_id_fk = book_id_fk;
    }

    public String getT_sale_record_id_fk() {
        return t_sale_record_id_fk;
    }

    public void setT_sale_record_id_fk(String t_sale_record_id_fk) {
        this.t_sale_record_id_fk = t_sale_record_id_fk;
    }

    public String getTrade_num() {
        return trade_num;
    }

    public void setTrade_num(String trade_num) {
        this.trade_num = trade_num;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public SaleRecord getSaleRecord() {
        return saleRecord;
    }

    public void setSaleRecord(SaleRecord saleRecord) {
        this.saleRecord = saleRecord;
    }
}
