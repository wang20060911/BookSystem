package org.crazyit.book.ui;

import org.crazyit.book.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录的JFrame
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class LoginFrame extends JFrame{

    //定义"帐号"和"密码"的标签
    private JLabel account_label = new JLabel("帐号");
    private JLabel password_label = new JLabel("密码");

    //定义存放用户帐号和密码的文本项
    private JTextField account_field = new JTextField();
    private JPasswordField password_field = new JPasswordField();

    //定义登录界面的Box容器，以便使用BoxLayout布局器
    private Box up = Box.createHorizontalBox();
    private Box center = Box.createHorizontalBox();
    private Box upCenter = Box.createVerticalBox();
    private Box down = Box.createHorizontalBox();

    UserService userService;

    //定义登录按钮
    private JButton login = new JButton("登录");

    public LoginFrame(UserService userService){
        this.userService = userService;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //布局各容器，并设置各容器的水平和垂直间距
        up.add(Box.createHorizontalStrut(50));
        up.add(account_label);
        up.add(Box.createHorizontalStrut(10));
        up.add(account_field);
        up.add(Box.createHorizontalStrut(100));

        center.add(Box.createHorizontalStrut(50));
        center.add(password_label);
        center.add(Box.createHorizontalStrut(10));
        center.add(password_field);
        center.add(Box.createHorizontalStrut(100));

        upCenter.add(Box.createVerticalStrut(20));
        upCenter.add(up);
        upCenter.add(Box.createVerticalStrut(20));
        upCenter.add(center);
        upCenter.add(Box.createVerticalStrut(20));

        down.add(Box.createHorizontalStrut(270));
        down.add(login, BorderLayout.EAST);
        down.add(Box.createHorizontalStrut(30));
        down.add(Box.createVerticalStrut(50));

        this.add(upCenter, BorderLayout.CENTER);
        this.add(down,BorderLayout.SOUTH);
        this.setBounds(300, 250, 350, 200);
        this.pack();
        this.setVisible(true);
        initListeners();
    }

    /**
     * 初始化监听器
     */
    public void initListeners(){
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    /**
     * 登录操作
     */
    public void login(){
        String name = this.account_field.getText().trim();
        char[] passwords = this.password_field.getPassword();
        StringBuffer password = new StringBuffer();
        for(char c: passwords){
            password.append(c);
        }
        try{
            userService.login(name,password.toString());
            new MainFrame();
            this.setVisible(true);
        }catch (Exception e){
             e.printStackTrace();
        }
    }

    /**
     * 显示警告
     * @param message
     * @return
     */
    protected  int showWran(String message){
        return JOptionPane.showConfirmDialog(this,message,"警告",JOptionPane.OK_CANCEL_OPTION);
    }
}
