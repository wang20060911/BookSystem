package org.crazyit.book.ui;

import org.crazyit.book.commons.ImageUtil;
import org.crazyit.book.commons.UploadException;
import org.crazyit.book.service.BookService;
import org.crazyit.book.service.ConcernService;
import org.crazyit.book.service.TypeService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.Concern;
import org.crazyit.book.vo.Type;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IntrospectionException;
import java.io.File;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 书本界面
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/18
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class BookPanel extends CommonPanel{

    private Vector<String> columns;

    private BookService bookService;

    private TypeService typeService;

    private ConcernService concernService;

    private JComboBox typeComboBox;

    private JComboBox concernComboBox;

    private JTextField bookId;

    private JTextField bookName;

    private JTextField price;

    private JTextArea intro;

    private JButton clearButton;

    private JButton saveButton;

    private JTextField nameQueryTextField;

    private JButton queryButton;

    private JButton imageButton;

    private FileChooser chooser;

    private ImageIcon currentImage;

    private String currentImagepath;

    private JLabel imageLabel;

    private JTextField author;

    private final static String DEFAULT_FILE_PATH = "upload/no_pic.gif";

    private ImageFrame imageFrame;

    private void initColumns(){
        columns = new Vector<String>();
        columns.add("id");
        columns.add("书本名称");
        columns.add("简介");
        columns.add("作者");
        columns.add("所属种类");
        columns.add("出版社");
        columns.add("库存");
        columns.add("价格");

    }

    public void initImage(){
        currentImage = new ImageIcon(DEFAULT_FILE_PATH);
        currentImagepath =DEFAULT_FILE_PATH;
        refreshImage();
    }

    public BookPanel(BookService bookService, TypeService typeService,
                     ConcernService concernService) {
        this.bookService = bookService;
        this.typeService = typeService;
        this.concernService = concernService;
        setViewDatas();
        initColumns();

        //设置列表
        DefaultTableModel model = new DefaultTableModel(getDatas(), this.columns);
        JTable table = new CommonJTable(model);
        setTable(table);

        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(1000, 350));

        //设置添加, 修改的界面
        JPanel downPane = new JPanel();
        downPane.setLayout(new BoxLayout(downPane, BoxLayout.X_AXIS));

        Box downBox1 = new Box(BoxLayout.X_AXIS);
        //添加id隐藏域
        bookId = new JTextField(10);
        bookId.setVisible(false);
        downBox1.add(bookId);
        //列表下面的box
        downBox1.add(new JLabel("书本名称："));
        downBox1.add(Box.createHorizontalStrut(10));
        bookName = new JTextField(10);
        downBox1.add(bookName);
        downBox1.add(Box.createHorizontalStrut(30));

        downBox1.add(new JLabel("价格："));
        downBox1.add(Box.createHorizontalStrut(10));
        price = new JTextField(10);
        downBox1.add(price);
        downBox1.add(Box.createHorizontalStrut(30));

        downBox1.add(new JLabel("作者："));
        downBox1.add(Box.createHorizontalStrut(10));
        author = new JTextField(10);
        downBox1.add(author);
        downBox1.add(Box.createHorizontalStrut(30));

        /***************************************************/
        Box downBox4 = new Box(BoxLayout.X_AXIS);

        downBox4.add(new JLabel("所属种类："));
        downBox4.add(Box.createHorizontalStrut(10));
        typeComboBox = new JComboBox();
        addTypes();
        downBox4.add(typeComboBox);
        downBox4.add(Box.createHorizontalStrut(30));

        downBox4.add(new JLabel("出版社："));
        concernComboBox = new JComboBox();
        addConcerns();
        downBox4.add(concernComboBox);
        downBox4.add(Box.createHorizontalStrut(30));

        downBox4.add(new JLabel("书本图片："));
        this.chooser = new FileChooser(this);
        this.imageButton = new JButton("请选择文件");
        downBox4.add(this.imageButton);
        downBox4.add(Box.createHorizontalStrut(30));

        /*******************************************************/
        Box downBox2 = new Box(BoxLayout.X_AXIS);
        downBox2.add(new JLabel("书本简介："));
        downBox2.add(Box.createHorizontalStrut(10));

        intro = new JTextArea("", 5, 5);
        JScrollPane introScrollPane = new JScrollPane(intro);
        intro.setLineWrap(true);
        downBox2.add(introScrollPane);
        downBox2.add(Box.createHorizontalStrut(30));
        /*******************************************************/
        Box downBox3 = new Box(BoxLayout.X_AXIS);

        saveButton = new JButton("保存");
        downBox3.add(saveButton);
        downBox3.add(Box.createHorizontalStrut(30));

        clearButton = new JButton("清空");
        downBox3.add(clearButton);
        downBox3.add(Box.createHorizontalStrut(30));

        /*******************************************************/
        Box downLeftBox = new Box(BoxLayout.Y_AXIS);

        downLeftBox.add(getAplitBox());
        downLeftBox.add(downBox1);
        downLeftBox.add(getAplitBox());
        downLeftBox.add(downBox4);
        downLeftBox.add(getAplitBox());
        downLeftBox.add(downBox2);
        downLeftBox.add(getAplitBox());
        downLeftBox.add(downBox3);

        Box downRightBox = new Box(BoxLayout.Y_AXIS);
        this.imageLabel = new JLabel();
        this.imageLabel.setPreferredSize(new Dimension(200, 200));
        this.currentImage = new ImageIcon(DEFAULT_FILE_PATH);
        this.currentImagepath = DEFAULT_FILE_PATH;
        this.imageLabel.setIcon(this.currentImage);
        JScrollPane p = new JScrollPane(this.imageLabel);
        downRightBox.add(p);

        downPane.add(downLeftBox);
        downPane.add(downRightBox);

        /*******************查询******************/
        JPanel queryPanel = new JPanel();
        Box queryBox = new Box(BoxLayout.X_AXIS);
        queryBox.add(new JLabel("书名："));
        queryBox.add(Box.createHorizontalStrut(30));
        nameQueryTextField = new JTextField(20);
        queryBox.add(nameQueryTextField);
        queryBox.add(Box.createHorizontalStrut(30));
        queryButton = new JButton("查询");
        queryBox.add(queryButton);
        queryPanel.add(queryBox);
        this.add(queryPanel);

        //列表为添加界面
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, downPane);
        split.setDividerSize(5);
        this.add(split);
        //添加监听器
        initListeners();
    }

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

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bookName.getText().trim().equals("")){
                    showWarn("请输入书的名称");
                    return;
                }
                save();
            }
        });

        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query();
            }
        });

        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addImage();
            }
        });
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                showImageFrame();
            }
        });
    }

    //打开显示图片的JFrame
    private void showImageFrame(){
        if(imageFrame == null){
            imageFrame = new ImageFrame(currentImage);
        }
        imageFrame.refresh(getBigImage());
    }

    //获取大图片的路径
    private ImageIcon getBigImage(){
        String smallImagePath = currentImagepath;
        if(smallImagePath.equals(DEFAULT_FILE_PATH)){
            return currentImage;
        }
        //获取无后缀文件名
        String temp = smallImagePath.substring(0,smallImagePath.lastIndexOf("."));
        //拼装大图路径全文件名
        String bigImagePath = temp + "-big" + smallImagePath.substring(smallImagePath.lastIndexOf("."),smallImagePath.length());
        return new ImageIcon(bigImagePath);
    }

    //添加文件的方法
    private void addImage(){
        chooser.showOpenDialog(this);
    }

    //上传图片
    public void upload(File selectFile){
        try{
            //使用uuid生成文件名，保证文件名唯一
            String uuid = ImageUtil.getUUID();
            //略缩图的url
            String smallFilePath = "upload" + File.separator + uuid + ".jpg";
            //原图的url
            String bigFilePath = "upload" + File.separator + uuid + "-big.jpg";
            //生成略缩图
            File file = ImageUtil.makeImage(selectFile, smallFilePath,"jpg",true);
            //生成原图
            File source = ImageUtil.makeImage(selectFile, bigFilePath, "jpg", false);
            //设置界面显示的图片对象
            currentImage = new ImageIcon(file.getAbsolutePath());
            //设置界面显示的图片url
            currentImagepath = smallFilePath;
            //刷新图片显示区
            refreshImage();
        }catch (UploadException e){
            e.printStackTrace();
            showWarn(e.getMessage());
        }
    }

    //刷新图片显示的JLabel
    private void refreshImage(){
        imageLabel.setIcon(currentImage);
    }

    //根据书名查询
    private void query(){
        String name = nameQueryTextField.getText().trim();
        Vector<Book> books = (Vector<Book>) bookService.find(name);
        Vector<Vector> datas = changeDatas(books);
        setDatas(datas);
        refreshTable();
    }

    //保存
    private void save(){
        if(bookId.getText().trim().equals("")){
            add();
        }else{
            update();
        }
    }

    //新增书本
    private void add(){
        if(!validatePrice()){
            showWarn("请输入正确的价格");
        }
        Book book = getBook();
        bookService.add(book);
        setViewDatas();
        clear();
    }

    //验证输入
    private boolean validatePrice(){
        String price = this.price.getText();
        try{
            Integer p = Integer.parseInt(price);
            if(p>0)
                return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //修改书本，修改时不需要修改库存，因为库存取决于销售与入货
    private void update(){
        Book book = getBook();
        book.setId(bookId.getText());
        bookService.update(book);
        setViewDatas();
        clear();
    }

    //从界面中获取数据并封装成Book对象
    private Book getBook(){
        String bookName = this.bookName.getText().trim();
        String price = this.price.getText().trim();
        String intro = this.intro.getText().trim();
        String author = this.author.getText().trim();
        Type type = (Type)typeComboBox.getSelectedItem();
        Concern concern = (Concern) concernComboBox.getSelectedItem();
        return new Book(null,bookName,intro,price,type.getId(),concern.getId(),"0",currentImagepath,author);

    }

    //查看书本
    private void view(){
        String id = getSelectId(getTable());
        Book book = bookService.get(id);
        bookId.setText(book.getId());
        bookName.setText(book.getBook_name());
        price.setText(book.getBook_price());
        intro.setText(book.getBook_intro());
        author.setText(book.getAuthor());
        typeComboBox.setSelectedItem(makeType(book.getType()));
        concernComboBox.setSelectedItem(makeConcern(book.getConcern()));
        currentImage = new ImageIcon(book.getImage_url());
        currentImagepath = book.getImage_url();
        refreshImage();
    }

    private Vector<Vector> changeDatas(Vector<Book> datas){
        Vector<Vector> view = new Vector<Vector>();
        for(Book book : datas){
            Vector vector = new Vector();
            vector.add(book.getId());
            vector.add(book.getBook_name());
            vector.add(book.getBook_intro());
            vector.add(book.getAuthor());
            vector.add(book.getType().getType_name());
            vector.add(book.getConcern().getPub_name());
            vector.add(book.getRepertory_size());
            vector.add(book.getBook_price());
            view.add(vector);
        }
        return  view;
    }

    //获取全部的种类并添加到下拉框中
    private void addTypes(){
        Collection<Type> types = typeService.getAll();
        for(Type type : types){
            typeComboBox.addItem(makeType(type));
        }
    }

    //获取全部的出版社并添加到下拉框中
    private void addConcerns(){
        Collection<Concern> concerns = concernService.getAll();
        for(Concern concern:concerns){
            concernComboBox.addItem(makeConcern(concern));
        }
    }

    //创建Type对象添加到下拉框中
    private Type makeType(final Type source){
        final Type type = new Type(){
            @Override
            public String toString() {
                return source.getType_name();
            }

            @Override
            public boolean equals(Object obj) {
                if(obj instanceof  Type){
                    Type t = (Type) obj;
                    if(getId().equals(t.getId()))
                        return true;
                }
                return false;
            }
        };
        type.setId(source.getId());
        return type;
    }

    private Concern makeConcern(final Concern source){
        Concern concern = new Concern(){
            @Override
            public String toString() {
                return source.getPub_name();
            }

            @Override
            public boolean equals(Object obj) {
                if(obj instanceof Concern){
                   Concern c = (Concern) obj;
                   if(getId().equals(c.getId()))
                       return true;
                }
                return  false;
            }
        };
        concern.setId(source.getId());
        return concern;
    }

    @Override
    public Vector<String> getColumns() {
        return columns;
    }

    @Override
    public void setTableFace() {
       getTable().getColumn("id").setMinWidth(-1);
       getTable().getColumn("id").setMaxWidth(-1);
       getTable().getColumn("简介").setMinWidth(350);
       getTable().setRowHeight(30);
    }

    @Override
    public void setViewDatas() {
       Vector<Book> books = (Vector<Book>) bookService.getAll();
       Vector<Vector> datas = changeDatas(books);
       setDatas(datas);
    }

    @Override
    public void clear() {
       refreshTable();
       bookId.setText("");
       bookName.setText("");
       price.setText("");
       intro.setText("");
       author.setText("");
       typeComboBox.removeAllItems();
       concernComboBox.removeAllItems();
       addTypes();
       addConcerns();
       currentImage = new ImageIcon(DEFAULT_FILE_PATH);
       refreshImage();
       currentImagepath = DEFAULT_FILE_PATH;
    }
}

class FileChooser extends  JFileChooser{
    BookPanel bookPanel;

    public FileChooser(BookPanel bookPanel){
        this.bookPanel = bookPanel;
    }

    @Override
    public void approveSelection() {
        File file = getSelectedFile();
        bookPanel.upload(file);
        super.approveSelection();
    }
}