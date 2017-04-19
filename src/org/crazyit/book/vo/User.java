package org.crazyit.book.vo;

/**
 * 用户对象
 * Created with IntelliJ IDEA.
 * User: Ydz
 * Date: 17-4-11
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public class User extends ValueObject {
    private String user_name;
    private String user_password;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_password() {
        return user_password;
    }


}
