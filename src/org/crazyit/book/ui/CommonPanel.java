package org.crazyit.book.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * 各个JPanel的基类
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class CommonPanel extends JPanel{
    //存放数据的table
    private JTable table;
    //列表数据
    protected Vector<Vector> datas;

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public Vector<Vector> getDatas() {
        return datas;
    }

    public void setDatas(Vector<Vector> datas) {
        this.datas = datas;
    }

    /**
     * 将数据设置进JTable中
     */
    public void initData(){
       if(this.table == null)
           return;
        DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
        //将数据设入表格Model中
        tableModel.setDataVector(getDatas(), getColumns());
        //设置表格样式
        setTableFace();
    }

    /**
     * 刷新列表的方法
     */
    public void refreshTable(){
        initData();
        getTable().repaint();
    }

    /**
     * 获取表列集合，由子类去实现
     * @return
     */
    public abstract Vector<String> getColumns();

    /**
     * 设置列表的样式，由子类去实现
      */
    public abstract void setTableFace();

    /**
     * 设置数据列表的方法，由子类去实现
     */
    public abstract void setViewDatas();

    /**
     * 清空界面下边的列表
     */
    public abstract void clear();

    /**
     * 分隔用的box
     * @return
     */
    public Box getAplitBox(){
        Box box= new Box(BoxLayout.X_AXIS);
        box.add(new JLabel("          "));
        return box;
    }

    /**
     * 给子类使用的方法，用于获取一个列表的id列值
     * @param table
     * @return
     */
    public String getSelectId(JTable table){
        int row = table.getSelectedRow();
        int column = table.getColumn("id").getModelIndex();
        String id = (String) table.getValueAt(row, column);
        return id;

    }
    

    /**
     * 显示警告
     * @param message
     * @return
     */
    protected int showWarn(String message){
        return JOptionPane.showConfirmDialog(this,message,"警告",JOptionPane.OK_CANCEL_OPTION);
    }
}
