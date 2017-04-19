package org.crazyit.book.vo;

/**
 * 书本对象
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */
public class Book extends ValueObject {
//    书本名称
    private String book_name;
//    简介
    private String book_intro;
//    书的单价
    private String book_price;
//    种类的外键
    private String type_id_fk;
//    出版社外键
    private String pub_id_fk;
//    存储量
    private String repertory_size;
//    略缩图url
    private String image_url;
//    书本种类，从数据库中查询出来的时候，这个属性为null,在通过本类的type_id_fk去设置这个属性
    private Type type;
//    书对应的出版社，与type相同
    private Concern concern;
    private String author;

    public Book(){}

    public Book(String id, String book_name,String book_intro, String book_price, String type_id_fk, String pub_id_fk, String  repertory_size, String image_url, String author){
        setId(id);
        this.book_name = book_name;
        this.book_intro = book_intro;
        this.book_price = book_price;
        this.type_id_fk = type_id_fk;
        this.pub_id_fk = pub_id_fk;
        this.repertory_size = repertory_size;
        this.image_url = image_url;
        this.author = author;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_intro(String book_intro) {
        this.book_intro = book_intro;
    }

    public String getBook_intro() {
        return book_intro;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }

    public String getBook_price() {
        return book_price;
    }

    public String getType_id_fk() {
        return type_id_fk;
    }

    public void setType_id_fk(String type_id_fk) {
        this.type_id_fk = type_id_fk;
    }

    public String getPub_id_fk() {
        return pub_id_fk;
    }

    public void setPub_id_fk(String pub_id_fk) {
        this.pub_id_fk = pub_id_fk;
    }

    public String getRepertory_size() {
        return repertory_size;
    }

    public void setRepertory_size(String repertory_size) {
        this.repertory_size = repertory_size;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Concern getConcern() {
        return concern;
    }

    public void setConcern(Concern concern) {
        this.concern = concern;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
