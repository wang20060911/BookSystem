package org.crazyit.book.ui;

import org.crazyit.book.service.BookService;
import org.crazyit.book.service.InRecordService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.BookInRecord;
import org.crazyit.book.vo.InRecord;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

/**
 * 库存页面
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/18
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class RepertoryPanel extends  CommonPanel{

    private InRecordService inRecordService;
    //入库记录列表的列集合
    Vector<String> columns;
    //书的入库记录的列集合
    Vector<String> bookInColumns;
    //数的入库列表JTable
    JTable bookInTable;
    //数的入库记录列表记录
    Vector<BookInRecord> bookInRecords;
    BookService bookService;
    JComboBox bookComboBox;
    JTextField amount;
    JTextField inDate;
    JTextField inRecordId;
    JButton clearButton;
    JButton addBookButton;
    JButton deleteBookButton;
    JTextField bookAmount;
    JLabel repertorySize;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    JButton inButton;

    private void initColumns(){
        columns = new Vector<String>();
        columns.add("id");
        columns.add("入库书本");
        columns.add("入库日期");
        columns.add("入库数量");
        bookInColumns = new Vector<String>();
        bookInColumns.add("id");
        bookInColumns.add("书名");
        bookInColumns.add("单价");
        bookInColumns.add("数量");
        bookInColumns.add("bookId");
    }

    public RepertoryPanel(InRecordService inRecordService, BookService bookService) {
        this.inRecordService = inRecordService;
        this.bookService = bookService;
        initColumns();
        setViewDatas();
        //设置列表
        DefaultTableModel model = new DefaultTableModel(getDatas(), this.columns);
        JTable table = new CommonJTable(model);
        setTable(table);

        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(1000, 350));


        //设置添加, 修改的界面
        JPanel downPane = new JPanel();
        downPane.setLayout(new BoxLayout(downPane, BoxLayout.Y_AXIS));


        /*******************************************************/
        //
        Box downBox1 = new Box(BoxLayout.X_AXIS);
        //保存入库记录的隐藏域
        this.inRecordId = new JTextField();
        downBox1.add(this.inRecordId);
        inRecordId.setVisible(false);

        downBox1.add(new JLabel("入库日期："));
        this.inDate = new JTextField(10);
        this.inDate.setEditable(false);
        setInDate();
        downBox1.add(this.inDate);
        downBox1.add(new JLabel("      "));

        downBox1.add(new JLabel("总数量："));
        this.amount = new JTextField(10);
        this.amount.setEditable(false);
        downBox1.add(this.amount);
        downBox1.add(new JLabel("      "));

        /*******************************************************/
        //书列表
        Box downBox2 = new Box(BoxLayout.X_AXIS);

        this.bookInRecords = new Vector<BookInRecord>();
        DefaultTableModel bookModel = new DefaultTableModel(this.bookInRecords,
                this.bookInColumns);
        this.bookInTable = new CommonJTable(bookModel);
        setBookInRecordFace();

        JScrollPane bookScrollPane = new JScrollPane(this.bookInTable);
        bookScrollPane.setPreferredSize(new Dimension(1000, 120));
        downBox2.add(bookScrollPane);

        /*******************************************************/
        Box downBox3 = new Box(BoxLayout.X_AXIS);
        downBox3.add(Box.createHorizontalStrut(300));

        downBox3.add(new JLabel("书本："));
        downBox3.add(Box.createHorizontalStrut(20));
        this.bookComboBox = new JComboBox();
        buildBooksComboBox();
        downBox3.add(bookComboBox);

        downBox3.add(Box.createHorizontalStrut(50));

        downBox3.add(new JLabel("数量："));
        downBox3.add(Box.createHorizontalStrut(20));
        this.bookAmount = new JTextField(10);
        downBox3.add(this.bookAmount);
        downBox3.add(Box.createHorizontalStrut(50));

        downBox3.add(new JLabel("现有："));
        downBox3.add(Box.createHorizontalStrut(20));
        this.repertorySize = new JLabel();
        downBox3.add(this.repertorySize);
        downBox3.add(Box.createHorizontalStrut(50));

        this.addBookButton = new JButton("添加");
        downBox3.add(this.addBookButton);

        downBox3.add(Box.createHorizontalStrut(30));

        this.deleteBookButton = new JButton("删除");
        downBox3.add(this.deleteBookButton);

        /*******************************************************/
        Box downBox4 = new Box(BoxLayout.X_AXIS);

        this.inButton = new JButton("入库");
        downBox4.add(this.inButton);

        downBox4.add(Box.createHorizontalStrut(130));

        this.clearButton = new JButton("清空");
        downBox4.add(this.clearButton);

        /*******************************************************/
        downPane.add(getAplitBox());
        downPane.add(downBox1);
        downPane.add(getAplitBox());
        downPane.add(downBox2);
        downPane.add(getAplitBox());
        downPane.add(downBox3);
        downPane.add(getAplitBox());
        downPane.add(downBox4);

        /*******************查询******************/
        JPanel queryPanel = new JPanel();
        Box queryBox = new Box(BoxLayout.X_AXIS);
        queryBox.add(new JLabel("日期："));
        queryBox.add(Box.createHorizontalStrut(30));
        queryBox.add(new JTextField(20));
        queryBox.add(Box.createHorizontalStrut(30));
        queryBox.add(new JButton("查询"));
        queryPanel.add(queryBox);
        this.add(queryPanel);

        //列表为添加界面
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, downPane);
        split.setDividerSize(5);
        this.add(split);
        //初始化监听器
        initListeners();
    }

    //初始化监听器
    private void initListeners(){
        getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    if(getTable().getSelectedRowCount() != 1)
                        return;
                    view();
                }
            }
        });
        bookComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBook();
            }
        });

        changeBook();

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendBook();
            }
        });

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });

        inButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                in();
            }
        });
    }

    //入库的方法
    private void in(){
        if(!inRecordId.getText().trim().equals("")){
            showWarn("请清空陪你过再进行操作");
            return;
        }
        if(bookInRecords.size() == 0){
            showWarn("没有任何书入库");
            return;
        }
        InRecord record = new InRecord();
        record.setRecord_date(inDate.getText().trim());
        record.setBookInRecords(bookInRecords);
        inRecordService.save(record);
        //重新读取数据
        setViewDatas();;
        //清空界面
        clear();
    }

    //从列表中移除一条书的入库记录
    private void removeBook(){
        if(!inRecordId.getText().trim().equals("")){
            showWarn("请清空在进行操作");
            return;
        }
        if(bookInTable.getSelectedRow() == -1){
            showWarn("请选择需要删除的行");
            return;
        }
        //在集合中删除对应索引的数据
        bookInRecords.remove(bookInTable.getSelectedRow());
        //刷新列表
        refreshBookInRecordTableData();
        //重新计算总数量
        countAmount();
    }

    //向书的入库记录列表中添加一本书
    private void appendBook(){
        if(!inRecordId.getText().trim().equals("")){
            showWarn("请清空再进行操作");
            return;
        }
        if(bookAmount.getText().trim().equals("")){
            showWarn("请输入书的数量");
            return;
        }
        //h获得选中的对象
        Book book = (Book) bookComboBox.getSelectedItem();
        String amount = bookAmount.getText().trim();
        appendOrUpdate(book,amount);
        refreshBookInRecordTableData();
        countAmount();
    }

    //当书本选择下拉框发生变化时，执行该方法
    private void changeBook(){
        Book book = (Book) bookComboBox.getSelectedItem();
        if(book == null)
            return;
        repertorySize.setText(book.getRepertory_size());
    }

    //计算一次入库书的总数
    private void countAmount(){
        int amount = 0;
        for(BookInRecord record : bookInRecords){
            amount += Integer.valueOf(record.getIn_num());
        }
        this.amount.setText(String.valueOf(amount));
    }

    //添加或者修改书本交易中的对象
    private void appendOrUpdate(Book book, String amount){
        BookInRecord r = getBookInRecordFromView(book);
        if(r == null){
            BookInRecord record = new BookInRecord();
            record.setBook(book);
            record.setIn_num(amount);
            bookInRecords.add(record);
        }else{
            int newAmount = Integer.valueOf(amount) + Integer.valueOf(r.getIn_num()) ;
            r.setIn_num(String.valueOf(newAmount));
        }
    }

    //获取在列表中是否已经存在相同的书
    private BookInRecord getBookInRecordFromView(Book book){
        for(BookInRecord record : bookInRecords){
            if(record.getId().equals(book.getId())){
               return record;
            }
        }
        return null;
    }

    //查看入库记录
    private void view(){
        String id = getSelectId(getTable());
        InRecord record = inRecordService.get(id);
        bookInRecords = record.getBookInRecords();
        refreshBookInRecordTableData();
        inRecordId.setText(record.getId());
        amount.setText(String.valueOf(record.getAmount()));
        inDate.setText(record.getRecord_date());
    }

    //设置书的入库记录的列表
    private void setBookInRecordFace(){
        bookInTable.setRowHeight(30);
        bookInTable.getColumn("id").setMinWidth(-1);
        bookInTable.getColumn("id").setMaxWidth(-1);
        bookInTable.getColumn("bookId").setMinWidth(-1);
        bookInTable.getColumn("bookId").setMaxWidth(-1);

    }

    //将数据转换成主列表的数据格式
    private Vector<Vector> changeDatas(Vector<InRecord> records){
        Vector<Vector> view = new Vector<Vector>();
        for(InRecord record : records){
            Vector vector = new Vector();
            vector.add(record.getId());
            vector.add(record.getBookNames());
            vector.add(record.getRecord_date());
            vector.add(record.getAmount());
            view.add(vector);
        }
        return view;
    }

    //刷新书本入库记录的列表
    private void refreshBookInRecordTableData(){
        Vector<Vector> view = changeBookInRecordDate(bookInRecords);
        DefaultTableModel tableModel = (DefaultTableModel) bookInTable.getModel();
        tableModel.setDataVector(view,bookInColumns);
        setBookInRecordFace();
    }

    //将书的入库记录转换成列表合适
    private Vector<Vector> changeBookInRecordDate(Vector<BookInRecord> records){
        Vector<Vector> view = new Vector<Vector>();
        for(BookInRecord record : records){
            Vector vector = new Vector();
            vector.add(record.getId());
            vector.add(record.getBook().getBook_name());
            vector.add(record.getBook().getBook_price());
            vector.add(record.getIn_num());
            vector.add(record.getBook().getId());
            view.add(vector);
        }
        return view;
    }

    //创建界面中选择书的下拉框
    private void buildBooksComboBox(){
        Collection<Book> books = bookService.getAll();
        for (Book book : books){
            bookComboBox.addItem(makeBook(book));
        }
    }

    //创建Book对象，用于添加到下拉框中，重写了equals和toString方法
    private Book makeBook(final Book source){
        Book book = new Book(){
            @Override
            public boolean equals(Object obj) {
                if(obj instanceof Book){
                   Book b = (Book) obj;
                   if(getId().equals(b.getId()))
                       return true;
                }
                return false;
            }

            @Override
            public String toString() {
                return getBook_name();
            }
        };
        book.setBook_name(source.getBook_name());
        book.setBook_price(source.getBook_price());
        book.setRepertory_size(source.getRepertory_size());
        book.setId(source.getId());
        return book;
    }

    //设置界面显示的家交易日期
    private void setInDate(){
        Date now = new Date();
        inDate.setText(timeFormat.format(now));
    }

    @Override
    public Vector<String> getColumns() {
        return columns;
    }

    @Override
    public void setTableFace() {
       getTable().getColumn("入库书本").setMinWidth(350);
       getTable().setRowHeight(35);
       getTable().getColumn("id").setMinWidth(-1);
       getTable().getColumn("id").setMaxWidth(-1);
    }

    @Override
    public void setViewDatas() {
        Vector<InRecord> records = (Vector<InRecord>) inRecordService.getAll(new Date());
        Vector<Vector> datas = changeDatas(records);
        setDatas(datas);
    }

    @Override
    public void clear() {
       //刷新住列表
        refreshTable();
        inRecordId.setText("");
        amount.setText("");
        inDate.setText("");
        bookInRecords.removeAll(bookInRecords);
        refreshBookInRecordTableData();
        bookComboBox.removeAllItems();
        buildBooksComboBox();
        setInDate();
    }
}
