package org.crazyit.book.vo;

/**
 * 书本种类对象
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class Type extends ValueObject {
//    名称
    private String type_name;
//    简介
    private String type_intro;

    public Type(){}

    public Type(String id, String type_name, String type_intro){
        setId(id);
        this.type_name = type_name;
        this.type_intro = type_intro;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_intro() {
        return type_intro;
    }

    public void setType_intro(String type_intro) {
        this.type_intro = type_intro;
    }


}
