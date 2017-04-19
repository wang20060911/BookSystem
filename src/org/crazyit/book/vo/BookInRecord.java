package org.crazyit.book.vo;

/**
 * 书本入库记录对象
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
public class BookInRecord extends ValueObject{
//    对应书的外键，从数据库查出来时有值
    private String book_id_fk;
//    对应的入库记录外键
    private String t_in_record_id_fk;
//    入库数量
    private String in_num;
//   该记录所对应的书，从数据库查出来时为null
    private Book book;
//    该记录所对应的库记录，从数据库查出来时为null
    private InRecord inRecord;

    public String getBook_id_fk() {
        return book_id_fk;
    }

    public void setBook_id_fk(String book_id_fk) {
        this.book_id_fk = book_id_fk;
    }

    public String getT_in_record_id_fk() {
        return t_in_record_id_fk;
    }

    public void setT_in_record_id_fk(String t_in_record_id_fk) {
        this.t_in_record_id_fk = t_in_record_id_fk;
    }

    public String getIn_num() {
        return in_num;
    }

    public void setIn_num(String in_num) {
        this.in_num = in_num;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public InRecord getInRecord() {
        return inRecord;
    }

    public void setInRecord(InRecord inRecord) {
        this.inRecord = inRecord;
    }
}
