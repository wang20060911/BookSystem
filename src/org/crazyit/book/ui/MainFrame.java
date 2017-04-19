package org.crazyit.book.ui;

import oracle.jrockit.jfr.JFR;
import org.crazyit.book.dao.*;
import org.crazyit.book.dao.impl.*;
import org.crazyit.book.service.*;
import org.crazyit.book.service.impl.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

/**
 * 主界面的Frame
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame{
    private SalePanel salePanel;
    private RepertoryPanel repertoryPanel;
    private BookPanel bookPanel;
    private ConcernPanel concernPanel;
    private TypePanel typePanel;
    private CommonPanel currentPanel;
    //业务接口
    private TypeService typeService;
    private ConcernService concernService;
    private BookService bookService;
    private SaleRecordService saleRecordService;
    private InRecordService inRecordService;

    private Action sale = new AbstractAction("销售管理",new ImageIcon("images/sale.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(salePanel);
        }
    } ;

    private Action repertory = new AbstractAction("库存管理",new ImageIcon("images/repertory.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(repertoryPanel);
        }
    };

    private Action book = new AbstractAction("书本管理",new ImageIcon("images/book.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
             changePanel(bookPanel);
             bookPanel.initImage();
             bookPanel.repaint();
        }
    } ;

    private Action type = new AbstractAction("种类管理",new ImageIcon("images/type.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(typePanel);
        }
    };

    private Action concern = new AbstractAction("出版社管理",new ImageIcon("images/concern.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(concernPanel);
        }
    } ;

    public MainFrame(){
        TypeDao typeDao = new TypeDaoImpl();
        ConcernDao concernDao = new ConcernDaoImpl();
        BookDao bookDao = new BookDaoImpl();
        SaleRecordDao saleRecordDao = new SaleRecordDaoImpl();
        BookSaleRecordDao bookSaleRecordDao = new BookSaleRecordDaoImpl();
        InRecordDao inRecordDao = new InRecordDaoImpl();
        BookInRecordDao bookInRecordDao = new BookInRecordDaoImpl();

        typeService = new TypeServiceImpl(typeDao);
        concernService = new ConcernServiceImpl(concernDao);
        bookService = new BookServiceImpl(bookDao,typeDao,concernDao);
        saleRecordService = new SaleRecordServiceImpl(saleRecordDao,bookSaleRecordDao,bookDao);
        inRecordService = new InRecordServiceImpl(inRecordDao,bookInRecordDao,bookDao);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("系统");
        menuBar.add(menu);

        menu.add(sale).setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
        menu.add(repertory).setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK));
        menu.add(book).setAccelerator(KeyStroke.getKeyStroke('B',InputEvent.CTRL_MASK));
        menu.add(type).setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));
        menu.add(concern).setAccelerator(KeyStroke.getKeyStroke('C',InputEvent.CTRL_MASK));

        salePanel = new SalePanel(bookService,saleRecordService);
        add(salePanel);
        currentPanel = salePanel;
        salePanel.initData();

        repertoryPanel = new RepertoryPanel(inRecordService,bookService);
        bookPanel = new BookPanel(bookService,typeService,concernService);
        concernPanel = new ConcernPanel(concernService);
        typePanel = new TypePanel(typeService);

        setJMenuBar(menuBar);
        setTitle("图书进存销管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void changePanel(CommonPanel commonPanel){
        remove(currentPanel);
        add(commonPanel);
        currentPanel = commonPanel;
        repaint();
        setVisible(true);
        commonPanel.setViewDatas();
        commonPanel.clear();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
