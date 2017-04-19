package org.crazyit.book.vo;

/**
 * 各个数据库对象的父类
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */
public class ValueObject {
//   ID字段，对应数据库中的ID列
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
