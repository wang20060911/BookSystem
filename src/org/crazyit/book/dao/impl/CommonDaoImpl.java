package org.crazyit.book.dao.impl;

import org.crazyit.book.commons.DataUtil;
import org.crazyit.book.jdbc.JDBCExecutor;
import org.crazyit.book.vo.ValueObject;

import java.sql.ResultSet;
import java.util.Collection;

/**
 * 各个DAO的基类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/13
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
public class CommonDaoImpl {

    /**
     * 返回JDBCExecutor对象
     * @return
     */
    public JDBCExecutor getJDBCExecutor() {
        return JDBCExecutor.getJdbcExecutor();
    }

    /**
     * 根据参数的SQL,存放结果的集合对象和具体的数据库映射对象返回一个集合
     * @param sql
     * @param result
     * @param clazz
     * @return
     */
    public Collection getDatas(String sql, Collection<ValueObject> result, Class clazz){
//        执行SQL返回ResultSet对象
        ResultSet rs = getJDBCExecutor().executeQuery(sql);
//        对ResultSet进行封装并返回结果集
        return DataUtil.getDatas(result, rs, clazz);
        
    }
}
