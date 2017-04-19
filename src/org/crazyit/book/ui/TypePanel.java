package org.crazyit.book.ui;

import org.crazyit.book.service.TypeService;
import org.crazyit.book.vo.Type;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * 种类管理界面
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class TypePanel extends CommonPanel{

    //业务对象
    private TypeService service;
    //列的集合
    private Vector<String> columns;
    //种类介绍的文本域
    private JTextArea intro;
    //种类名称的文本框
    private JTextField typeName;
    //隐藏的种类id
    private JTextField typeId;
    //保存的按钮
    private JButton saveButton;
    //清空的按钮
    private JButton clearButton;
    //根据名字查询的输入框
    private JTextField queryByNameTextField;
    //查询按钮
    private JButton queryButton;

     //初始化列
    public void initColumns(){
        this.columns = new Vector();
        this.columns.add("id");
        this.columns.add("种类名称");
        this.columns.add("简介");
    }

    public TypePanel(TypeService serviec){
        this.service = serviec;
        //设置数据
        setViewDatas();
        //初始化列
        initColumns();
        //设置列表
        DefaultTableModel model = new DefaultTableModel(null,this.columns);
        JTable table = new CommonJTable(model);
        //设置父类的JTable对象
        setTable(table);
        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(1000, 350));

        //设置添加，修改的页面
        JPanel downPane = new JPanel();
        downPane.setLayout(new BoxLayout(downPane,BoxLayout.Y_AXIS));

        Box downBox1 = new Box(BoxLayout.X_AXIS);
        //存放id的文本框
        typeId = new JTextField();
        typeId.setVisible(false);
        downBox1.add(typeId);
        //列表下面的box
        downBox1.add(new JLabel("种类名称:"));
        downBox1.add(Box.createHorizontalStrut(10));
        typeName = new JTextField(10);
        downBox1.add(typeName);
        downBox1.add(Box.createHorizontalStrut(100));

        Box downBox2 = new Box(BoxLayout.X_AXIS);
        downBox2.add(new JLabel("种类简介:"));
        downBox2.add(Box.createHorizontalStrut(10));
        intro = new JTextArea("",5,5);
        JScrollPane introScrollPane = new JScrollPane(intro);
        intro.setLineWrap(true);
        downBox2.add(introScrollPane);
        downBox2.add(Box.createHorizontalStrut(100));

        Box downBox3 = new Box(BoxLayout.X_AXIS);
        saveButton = new JButton("保存");
        downBox3.add(saveButton);
        downBox3.add(Box.createHorizontalStrut(30));
        clearButton = new JButton("清空");
        downBox3.add(clearButton);
        downBox3.add(Box.createHorizontalStrut(30));

        downPane.add(getAplitBox());
        downPane.add(downBox1);
        downPane.add(getAplitBox());
        downPane.add(downBox2);
        downPane.add(getAplitBox());
        downPane.add(downBox3);

        //查询的JPanel
        JPanel queryPanel = new JPanel();
        Box queryBox = new Box(BoxLayout.X_AXIS);
        queryBox.add(new JLabel("名称:"));
        queryBox.add(Box.createHorizontalStrut(30));
        queryByNameTextField = new JTextField(20);
        queryBox.add(queryByNameTextField);
        queryBox.add(Box.createHorizontalStrut(30));
        queryButton = new JButton("查询");
        queryBox.add(queryButton);
        queryPanel.add(queryBox);

        this.add(queryPanel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,upPane,downPane);
        split.setDividerLocation(5);
        this.add(split);
        //添加监听器
        initListeners();
    }

    //添加监听器
    private void initListeners(){
        //表格选择监听器
        getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //当选择行时鼠标释放时才执行
                if(!e.getValueIsAdjusting()){
                    //如果没有选中任意一行，则返回
                    if(getTable().getSelectedRowCount() != 1)
                        return;
                    //调用父类的方法获得选择行的id
                    String id = getSelectId(getTable());
                    view(id);
                }
            }
        });
        
        //清空按钮监听器
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        //保存按钮监听器
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //验证空值
                if(typeName.getText().trim().equals("")){
                    showWarn("请输入名称!");
                    return;
                }
                //判断种类id是否有值
                if(typeId.getText().trim().equals("")){
                    add();
                }else{
                    update();
                }
            }
        });
    }

    //查询方法
    private void query(String name){
        //通过service方法查询结果
        Vector<Type> types = (Vector<Type>) service.query(name);
        //转换数据格式
        Vector<Vector> datas = changeDatas(types);
        //设置数据
        setDatas(datas);
        //刷新列表
        refreshTable();
    }

    //新增一个种类
    private void add(){
        String typeName = this.typeName.getText();
        String intro = this.intro.getText();
        Type type = new Type(null,typeName,typeName);
        //调用业务方法新增一个种类
        type = service.add(type);
        //重新读取数据
        setViewDatas();
        //刷新列表
        clear();
    }

    //修改一个种类
    private void update(){
        String typeId = this.typeId.getText();
        String typeName = this.typeName.getText();
        String intro = this.intro.getText();
        Type type = new Type(typeId,typeName,intro);
        service.update(type);
        //重新读取数据
        setViewDatas();
        //刷新列表
        refreshTable();
    }

    //查看一个种类
    public void view(String id){
        Type data = service.get(id);
        typeId.setText(data.getId());
        typeName.setText(data.getType_name());
        intro.setText(data.getType_intro());
    }

    @Override
    public Vector<String> getColumns() {
        return this.columns;
    }

    @Override
    public void setTableFace() {
        //隐藏id列表
        getTable().getColumn("id").setMinWidth(-1);
        getTable().getColumn("id").setMaxWidth(-1);
        //设置简介列的最小宽度
        getTable().getColumn("简介").setMinWidth(350);
        //设置表格的行宽
        getTable().setRowHeight(30);
    }

    @Override
    public void setViewDatas() {
       Vector<Type> types = (Vector<Type>) service.getAll();
       Vector<Vector> datas = changeDatas(types);
       setDatas(datas);
    }

    @Override
    public void clear() {
         refreshTable();
         typeId.setText("");
         typeName.setText("");
         intro.setText("");
    }
    

    /**
     * 将数据转换成仕途表格的格式
     * @param datas
     * @return
     */
    private Vector<Vector> changeDatas(Vector<Type> datas){
        Vector<Vector> view = new Vector<Vector>();
        for(Type type : datas){
            Vector v = new Vector();
            v.add(type.getId());
            v.add(type.getType_name());
            v.add(type.getType_intro());
            view.add(v);
        }
        return view;
    }

}
