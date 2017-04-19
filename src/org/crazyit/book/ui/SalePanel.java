package org.crazyit.book.ui;

import com.mysql.jdbc.JDBC4CommentClientInfoProvider;
import com.sun.xml.internal.bind.v2.schemagen.episode.Klass;
import org.crazyit.book.commons.BusinessException;
import org.crazyit.book.service.BookService;
import org.crazyit.book.service.SaleRecordService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.BookSaleRecord;
import org.crazyit.book.vo.SaleRecord;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

/**  \
 * 销售界面
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/17
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class SalePanel extends CommonPanel{

    private  BookService bookService;
    //销售记录列
    private Vector<String> columns;
    //书的销售记录列
    private Vector<String> bookSaleRecordColumns;
    //销售记录的业务接口
    private SaleRecordService saleRecordService;
    //书的交易记录列表
    private JTable bookSaleRecordTable;
    //书本选择的下拉框
    private JComboBox bookComboBox;
    //书的销售记录数据
    private Vector<BookSaleRecord> bookSaleRecordDatas;
    //销售记录的id文本框
    private JTextField saleRecordId;
    //销售记录总价
    private JTextField totalPrice;
    //销售日期
    private JTextField recordDate;
    //销售总数量
    private JTextField amount;
    //清空按钮
    private JButton clearButton;
    //数的单价
    private JLabel singlePrice;
    //购买数的数量
    private JTextField bookAmount;
    //书对应的库存
    private JLabel repertorySize;
    //添加书的按钮
    private JButton addBookButton;
    //删除书的按钮
    private JButton deleteBookButton;
    //成交按钮
    private JButton confirmButton;
    //查询按钮
    private JButton queryButton;
    //查询输入的日期
    private JTextField queryDate;
    //日期格式
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //时间格式
    private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    private void initColumns(){
        //初始化销售记录列表的列
        this.columns = new Vector<String>();
        this.columns.add("id");
        this.columns.add("购买书本");
        this.columns.add("总价");
        this.columns.add("交易日期");
        this.columns.add("总数量");
        //初始化销售记录中书列表的列
        this.bookSaleRecordColumns = new Vector<String>();
        this.bookSaleRecordColumns.add("id");
        this.bookSaleRecordColumns.add("书名");
        this.bookSaleRecordColumns.add("单价");
        this.bookSaleRecordColumns.add("数量");
        this.bookSaleRecordColumns.add("BookId");
    }

    public SalePanel(BookService bookService, SaleRecordService saleRecordService){
        this.bookService = bookService;
        this.saleRecordService = saleRecordService;
        //设置列表数据
        setViewDatas();
        //初始化列
        initColumns();
        //设置列表
        DefaultTableModel model = new DefaultTableModel(datas, columns);
        JTable table = new CommonJTable(model);
        setTable(table);
        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(1000, 350));
        //设置添加，修改的界面
        JPanel downPane = new JPanel();
        downPane.setLayout(new BoxLayout(downPane,BoxLayout.Y_AXIS));
        Box downBox1 = new Box(BoxLayout.X_AXIS);
        saleRecordId = new JTextField(10);
        downBox1.add(this.saleRecordId);
        saleRecordId.setVisible(false);
        //列表下面的Box
        downBox1.add(new JLabel("总价"));
        totalPrice = new JTextField(10);
        totalPrice.setEditable(false);
        downBox1.add(this.totalPrice);
        downBox1.add(Box.createHorizontalStrut(30));
        downBox1.add(new JLabel("交易日期"));
        recordDate = new JTextField(10);
        recordDate.setEditable(false);
        //设置当前交易时间
        setRecordDate();
        downBox1.add(this.recordDate);
        downBox1.add(Box.createHorizontalStrut(30));
        downBox1.add(new JLabel("总数量"));
        amount = new JTextField(10);
        amount.setEditable(false);
        downBox1.add(this.amount);
        downBox1.add(Box.createHorizontalStrut(30));
        //书列表
        Box downBox2 = new Box(BoxLayout.X_AXIS);
        bookSaleRecordDatas = new Vector<BookSaleRecord>();
        DefaultTableModel bookModel = new DefaultTableModel(bookSaleRecordDatas,bookSaleRecordColumns);
        //设置书本交易列标样式
        setBookSaleRecordTableFace();
        JScrollPane bookScrollPane = new JScrollPane(bookSaleRecordTable);
        bookScrollPane.setPreferredSize(new Dimension(1000,120));
        downBox2.add(bookScrollPane);

        Box downBox3 = new Box(BoxLayout.X_AXIS);
        downBox3.add(Box.createHorizontalStrut(100));
        downBox3.add(new JLabel("书本"));
        downBox3.add(Box.createHorizontalStrut(20));
        bookComboBox = new JComboBox();
        //为下拉框添加数据
        buildBooksComboBox();
        downBox3.add(this.bookComboBox);
        downBox3.add(Box.createHorizontalStrut(50));
        downBox3.add(new JLabel("数量"));
        downBox3.add(Box.createHorizontalStrut(20));
        bookAmount = new JTextField(10);
        downBox3.add(this.bookAmount);
        downBox3.add(Box.createHorizontalStrut(50));
        downBox3.add(new JLabel("单价"));
        downBox3.add(Box.createHorizontalStrut(20));
        singlePrice = new JLabel();
        downBox3.add(this.singlePrice);
        downBox3.add(Box.createHorizontalStrut(100));
        downBox3.add(new JLabel("库存"));
        downBox3.add(Box.createHorizontalStrut(20));
        repertorySize = new JLabel();
        downBox3.add(this.repertorySize);
        downBox3.add(Box.createHorizontalStrut(80));
        addBookButton = new JButton("添加");
        downBox3.add(this.addBookButton);
        downBox3.add(Box.createHorizontalStrut(30));
        deleteBookButton = new JButton("删除");
        downBox3.add(this.deleteBookButton);

        Box downBox4 = new Box(BoxLayout.X_AXIS);
        confirmButton = new JButton("成交");
        downBox4.add(Box.createHorizontalStrut(120));
        clearButton = new JButton("清空");
        downBox4.add(clearButton);

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
        queryBox.add(new JLabel("日期"));
        queryBox.add(Box.createHorizontalStrut(30));
        queryDate = new JTextField(20);
        queryBox.add(queryDate);
        queryBox.add(Box.createHorizontalStrut(30));
        queryButton = new JButton("查询");
        queryBox.add(queryButton);
        add(queryPanel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,upPane,downPane);
        split.setDividerSize(5);
         add(split);
         initListeners();
    }

    //初始化监听器
    private void initListeners(){
        //表格监听器
        getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    if(getTable().getSelectedRow() != 1)
                        return ;
                    view();
                }
            }
        });
        //清空按钮
        this.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        //书本选择下拉监听器
        this.bookComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBook();
            }
        });
        changeBook();
        //向列表添加一条书的销售记录的按钮
        this.addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendBook();
            }
        });
        //删除书的交易记录按钮
        this.deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });
        //成交按钮
        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sale();
            }
        });
        //查询
        this.queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query();
            }
        });

    }

    //查询书的方法
    private void query(){
       String date = this.queryDate.getText();
       Date d = null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            showWarn("请输入yyyy-MM-dd的格式日期");
            return;
        }
        //重新执行查询
        Vector<SaleRecord> records = (Vector<SaleRecord>) saleRecordService.getAll(d);
        Vector<Vector> datas = changeDatas(records);
        setDatas(datas);
        //刷新列表
        refreshTable();
    }

    //成交的方法
    private void sale(){
        if(!this.saleRecordId.getText().trim().equals("")){
            showWarn("请清空再进行操作!");
            return;
        }
        //如果没有成交任何书，返回
        if(this.bookSaleRecordDatas.size() == 0){
           showWarn("没有出售任何的书，不得交易！");
           return;
        }
        SaleRecord record = new SaleRecord();
        record.setRecord_date(this.recordDate.getText());
        record.setBookSaleRecords(this.bookSaleRecordDatas);
        try{
            saleRecordService.saveRecord(record);
        }catch (BusinessException e){
            showWarn(e.getMessage());
            return;
        }
        //重新读取数据
        setViewDatas();
        //清空页面
        clear();
    }

    //向列表添加一条书的销售记录
    private void appendBook(){
        if(!this.saleRecordId.getText().trim().equals("")){
            showWarn("请清空在进行操作!");
            return;
        }
        if(this.bookAmount.getText().trim().equals("")){
            showWarn("请输入书的数量");
            return;
        }
        Book book = (Book) bookComboBox.getSelectedItem();
        String amount = this.bookAmount.getText().trim();
        appendOrUpdate(book,amount);
        //刷新列表
        refreshBookSaleRecordTableData();
        //计算总价
        countTotalPrice();
        //计算总数量
        setTotalAmount();
    }

    //添加或者修改书本交易记录中的对象
    private void appendOrUpdate(Book book, String amount){
        BookSaleRecord r = getBookSaleRecordFromView(book);
        //如果为空，则为新添加的书，非空，则该书已经在列表中
        if(r == null){
            BookSaleRecord record = new BookSaleRecord();
            record.setBook(book);
            record.setTrade_num(amount);
            this.bookSaleRecordDatas.add(record);
        }else{
            int newAmount = Integer.parseInt(amount) + Integer.parseInt(r.getTrade_num());
            r.setTrade_num(String.valueOf(newAmount));
        }
    }

    //获取在列表中是否已经存在相同的书
    private BookSaleRecord getBookSaleRecordFromView(Book book){
        for(BookSaleRecord record:this.bookSaleRecordDatas){
            if(record.getBook().getId().equals(book.getId())){
                return record;
            }
        }
        return null;
    }

    //设置总数量
    private void setTotalAmount(){
        int amount = 0;
        for(BookSaleRecord record :this.bookSaleRecordDatas){
            amount += Integer.parseInt(record.getTrade_num());
        }
        this.amount.setText(String.valueOf(amount));
    }

    //计算总价
    private void countTotalPrice(){
        double totalPrice = 0 ;
        for(BookSaleRecord record : this.bookSaleRecordDatas){
            totalPrice += (Integer.valueOf(record.getTrade_num()))*((Double.valueOf(record.getBook().getBook_price())));
        }
        this.totalPrice.setText(String.valueOf(totalPrice));
    }

    //从列表中删除一条书的销售记录
    private void removeBook(){
        if(!this.saleRecordId.getText().trim().equals("")){
           showWarn("请清空再进行操作！");
           return;
        }
        if(bookSaleRecordTable.getSelectedRow() == -1){
            showWarn("请选择需要删除的行");
            return;
        }
        //在集合中删除对应的索引数据
        this.bookSaleRecordDatas.remove(bookSaleRecordTable.getSelectedRow());
        //刷新列表
        refreshBookSaleRecordTableData();
        //重新计算总价和总数量
        setTotalAmount();
        countTotalPrice();
    }

    //当书本选择下拉框发生变化时，执行该方法
    private void changeBook(){
        //获得选择的Bookd对象
        Book book = (Book) bookComboBox.getSelectedItem();
        if(book == null)
            return;
        //设置显示的书的价格
        this.singlePrice.setText(book.getBook_price());
        this.repertorySize.setText(book.getRepertory_size());
    }

    //将数据转换成为主列表的格式
    private Vector<Vector> changeDatas(Vector<SaleRecord> records){
        Vector<Vector> view = new Vector<Vector>();
        for(SaleRecord record:records){
            Vector vector = new Vector();
            vector.add(record.getId());
            vector.add(record.getBookNames());
            vector.add(record.getTotalPrice());
            vector.add(record.getRecord_date());
            vector.add(record.getAmount());
            view.add(vector);
        }
        return view;
    }

    //创建界面中选择书的下拉框
    private void buildBooksComboBox(){
        Collection<Book> books = bookService.getAll();
        for(Book book:books){
            this.bookComboBox.addItem(makeBook(book));
        }
    }

    //创建Book对象，用于添加到下拉框中，重写了equals和toString方法
    private Book makeBook(final Book source){
        Book book = new Book(){
            @Override
            public boolean equals(Object obj) {
                if(obj instanceof  Book){
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

    //查看一条销售记录
    private void view(){
        String saleRecordId = getSelectId(getTable());
        //得到书的交易记录
        SaleRecord record  = saleRecordService.get(saleRecordId);
        //设置当前书的销售数据
        this.bookSaleRecordDatas = record.getBookSaleRecords();
        //刷新书本销售列表
        refreshBookSaleRecordTableData();
        this.saleRecordId.setText(record.getId());
        this.totalPrice.setText(String.valueOf(record.getTotalPrice()));
        this.recordDate.setText(record.getRecord_date());
        this.amount.setText(String.valueOf(record.getAmount()));
    }

    //将书的销售记录转换为列表格式
    private Vector<Vector> changeBookSaleRecordData(Vector<BookSaleRecord> records){
        Vector<Vector> view = new Vector<Vector>();
        for (BookSaleRecord record: records){
            Vector vector = new Vector();
            vector.add(record.getId());
            vector.add(record.getBook().getBook_name());
            vector.add(record.getBook().getBook_price());
            vector.add(record.getTrade_num());
            vector.add(record.getBook().getId());
            view.add(vector);
        }
        return  view;
    }

    //刷新书本销售记录的列表
    private void refreshBookSaleRecordTableData(){
        Vector<Vector> view = changeBookSaleRecordData(this.bookSaleRecordDatas);
        DefaultTableModel tableModel = (DefaultTableModel) this.bookSaleRecordTable.getModel();
        //将数据设入表格Model中
        tableModel.setDataVector(view,this.bookSaleRecordColumns);
        //设置表格样式
        setBookSaleRecordTableFace();
    }

    //

    @Override
    public Vector<String> getColumns() {
        return this.columns;
    }

    //设置书本销售记录的样式
    private void setBookSaleRecordTableFace(){
        bookSaleRecordTable.setRowHeight(30);
        //隐藏销售记录id列
        bookSaleRecordTable.getColumn("id").setMinWidth(-1);
        bookSaleRecordTable.getColumn("id").setMaxWidth(-1);
        //隐藏对应的书id列
        bookSaleRecordTable.getColumn("bookId").setMinWidth(-1);
        bookSaleRecordTable.getColumn("bookId").setMaxWidth(-1) ;
    }

    //设置界面显示的交易时间
    private void setRecordDate(){
        Date now = new Date();
        this.recordDate.setText(timeFormat.format(now));
    }

    @Override
    public void setTableFace() {
        getTable().getColumn("id").setMinWidth(-1);
        getTable().getColumn("id").setMaxWidth(-1);
        getTable().getColumn("购买书本").setMinWidth(350);
        getTable().setRowHeight(30);
    }

    @Override
    public void setViewDatas() {
       Vector<SaleRecord> records = (Vector<SaleRecord>) saleRecordService.getAll(new Date());
       Vector<Vector> datas = changeDatas(records);
       setDatas(datas);
    }

    @Override
    public void clear() {
        //刷新主列表
        refreshTable();
        ;
        this.saleRecordId.setText("");
        this.totalPrice.setText("");
        //设置界面的交易时间为当前时间
        setRecordDate();
        this.amount.setText("");
        this.bookSaleRecordDatas.removeAll(this.bookSaleRecordDatas);
        refreshBookSaleRecordTableData();
        //刷新下拉
        this.bookComboBox.removeAllItems();
        buildBooksComboBox();
    }
}
