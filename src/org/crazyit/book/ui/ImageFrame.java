package org.crazyit.book.ui;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;

/**
 * 查看大图片的JFrame
 * Created with IntelliJ IDEA.
 * User: Wangxudong
 * Date: 2017/4/15
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class ImageFrame extends JFrame{
    JLabel imageLabel;

    public ImageFrame(ImageIcon image){
        JPanel mainPanel = new JPanel();
        this.imageLabel = new JLabel();
        this.imageLabel.setIcon(image);
        mainPanel.add(this.imageLabel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    public void refresh(ImageIcon image){
        this.imageLabel.setIcon(image);
        this.setVisible(true);
        super.repaint();
    }
}
