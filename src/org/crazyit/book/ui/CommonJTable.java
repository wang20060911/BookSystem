package org.crazyit.book.ui;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * 列表的基类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class CommonJTable extends JTable{

    public CommonJTable(TableModel tableModel){
        super(tableModel);
        //设置表格只能选择一行
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * 重写父类的方法，使所有的单元格不可编辑
     * @param row
     * @param column
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
