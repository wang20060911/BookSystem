package org.crazyit.book.ui;

import org.crazyit.book.service.ConcernService;
import org.crazyit.book.vo.Concern;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ConvolveOp;
import java.util.Vector;

/**
 * 出版社的JPanel对象
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/17
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class ConcernPanel extends CommonPanel{
    private Vector<String> columns;
    private ConcernService service;
    //清空按钮
    private JButton clearButton;
    //保存按钮
    private JButton saveButton;
    //id（隐藏）
    private JTextField concernId;
    //出版社名称
    private JTextField concernName;
    //联系人
    private JTextField pubLinkMan;
    //联系电话
    private JTextField pubTel;
    //简介
    private JTextArea pubIntro;
    //查询按钮
    private JButton queryButton;
    //根据名称查询的输入框
    private JTextField queryName;

    //初始化列
    private void initColumns(){
        this.columns = new Vector<String>();
        this.columns.add("id");
        this.columns.add("出版社名称");
        this.columns.add("联系人");
        this.columns.add("联系电话");
        this.columns.add("简介");
    }

    public ConcernPanel(ConcernService service){
        this.service = service;
        //初始化列集合
        initColumns();
        //设置列表数据
        setViewDatas();
        //设置列表
        DefaultTableModel model = new DefaultTableModel(null,this.columns);
        JTable table = new CommonJTable(model);
        setTable(table);

        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(1000,350));

        JPanel downPane = new JPanel();
        downPane.setLayout(new BoxLayout(downPane,BoxLayout.Y_AXIS));

        Box downBox1 = new Box(BoxLayout.X_AXIS);
        concernId = new JTextField();
        //设置为不可见
        concernId.setVisible(false);
        downBox1.add(concernId);
        downBox1.add(new JLabel("出版社名称"));
        downBox1.add(Box.createHorizontalStrut(10));
        concernName = new JTextField();
        downBox1.add(concernName);
        downBox1.add(Box.createHorizontalStrut(400));

        Box downBox2 = new Box(BoxLayout.X_AXIS);
        downBox2.add(new JLabel("联系人"));
        downBox2.add(Box.createHorizontalStrut(10));
        pubLinkMan = new JTextField();
        downBox2.add(pubLinkMan);
        downBox2.add(Box.createHorizontalStrut(50));
        downBox2.add(new JLabel("联系电话"));
        downBox2.add(Box.createHorizontalStrut(10));
        pubTel = new JTextField();
        downBox2.add(pubTel);

         Box downBox3 = new Box(BoxLayout.X_AXIS);
         downBox3.add(new JLabel("简介"));
         downBox3.add(Box.createHorizontalStrut(35));
         pubIntro = new JTextArea("",5,3);
         JScrollPane introScrollPane = new JScrollPane(pubIntro);
         pubIntro.setLineWrap(true);
         downBox3.add(introScrollPane);

         Box downBox4 = new Box(BoxLayout.X_AXIS);
         saveButton = new JButton("保存");
         downBox4.add(saveButton);
         downBox4.add(Box.createHorizontalStrut(30));
         clearButton = new JButton("清空");
         downBox4.add(clearButton);
         downBox4.add(Box.createHorizontalStrut(30));

         downPane.add(getAplitBox());
         downPane.add(downBox1);
         downPane.add(getAplitBox());
         downPane.add(downBox2);
         downPane.add(getAplitBox());
         downPane.add(downBox3);
         downPane.add(getAplitBox());
         downPane.add(downBox4);

         JPanel queryPanel = new JPanel();
         Box queryBox = new Box(BoxLayout.X_AXIS);
         queryBox.add(new JLabel("名称"));
         queryBox.add(Box.createHorizontalStrut(30));
         queryName = new JTextField(20);
         queryBox.add(queryName);
         queryBox.add(Box.createHorizontalStrut(30));
         queryButton = new JButton("查询");
         queryBox.add(queryButton);
         queryPanel.add(queryBox);
         //列表为添加页面
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,upPane,downPane);
        split.setDividerLocation(5);
        this.add(split);
        //初始化监听器
        initListeners();
    }

    //初始化监听器
    private void initListeners(){
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
                save();
            }
        });

        //查询按钮监听器
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query();
            }
        });
    }

    //按名字模糊查询
    private void query(){
        String name = this.queryName.getText().trim();
        Vector<Concern> concerns = (Vector<Concern>) service.query(name);
        //转换数据格式
        Vector<Vector> datas = changeDatas(concerns);
        //设置数据
        setDatas(datas);
        //刷新列表
        refreshTable();
    }

    //查看一个出版社
    private void view(String id){
        Concern concern = service.find(id);
        this.concernId.setText(concern.getId());
        this.concernName.setText(concern.getPub_name());
        this.pubLinkMan.setText(concern.getPub_link_man());
        this.pubTel.setText(concern.getPub_tel());
        this.pubIntro.setText(concern.getPub_intro());
    }

    //保存方法
    private void save(){
        //调用父类的方法，弹出错误提示
        if(this.concernName.getText().trim().equals("")){
            showWarn("请输入出版社名称");
            return;
        }
        //如果id为空，则为新增，不为空则为修改操作
        if(this.concernId.getText().trim().equals("")){
            add();
        }else{
            update();
        }

    }

    //新增方法
    private void add(){
        //获得界面中的值并封装成对象
        Concern concern = getConcern();
        service.add(concern);
        setViewDatas();
        clear();
    }

    //修改方法
    private void update(){
        //取得当前修改记录的ID
        String id = this.concernId.getText();
        //根据界面数据获得Concern对象
        Concern concern = getConcern();
        concern.setId(id);
        service.update(concern);
        setViewDatas();
        refreshTable();
    }

    //从表单中获取数据并封装成Concern对象，没有id
    private Concern getConcern(){
        String concernName = this.concernName.getText().trim();
        String pubTel = this.pubTel.getText().trim();
        String pubLinkMan = this.pubLinkMan.getText().trim();
        String pubIntro = this.pubIntro.getText().trim();
        return new Concern(null,concernName,pubTel,pubLinkMan,pubIntro);
    }

    /**
     * 将数据转换成视图表格的格式
     * @param datas
     * @return
     */
    private Vector<Vector> changeDatas(Vector<Concern> datas){
        Vector<Vector> view = new Vector<Vector>();
        for(Concern concern:datas){
            Vector<String> v = new Vector();
            v.add(concern.getId());
            v.add(concern.getPub_name());
            v.add(concern.getPub_link_man());
            v.add(concern.getPub_tel());
            v.add(concern.getPub_intro());
            view.add(v);
        }
        return view;
    }


    @Override
    public Vector<String> getColumns() {
        return this.columns;
    }

    @Override
    public void setTableFace() {
       getTable().getColumn("id").setMinWidth(-1);
       getTable().getColumn("id").setMaxWidth(-1);
       getTable().getColumn("简介").setMinWidth(400);
       getTable().setRowHeight(30);
    }

    @Override
    public void setViewDatas() {
       Vector<Concern> concerns = (Vector<Concern>) service.getAll();
       Vector<Vector> datas = changeDatas(concerns);
       setDatas(datas);
    }

    @Override
    public void clear() {
       refreshTable();
       this.concernId.setText("");
       this.concernName.setText("");
       this.pubLinkMan.setText("");
       this.pubTel.setText("");
       this.pubIntro.setText("");
    }
}
