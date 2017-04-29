package cn.howl.JWM.AIsland;

import us.codecraft.webmagic.Spider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by wtnTUN on 2017/4/28.
 */
public class UI {
    private JPanel panel1;
    private JButton Button;
    private JTextArea commend;
    private JTextField urlPath;
    private JTextField localPath;
    private JCheckBox isPack;
    private JTextField MaxPage;
    private JTextField SPage;

    public static void main(String[] args) {
        JFrame frame = new JFrame("A岛图片收集者");
        frame.setContentPane(new UI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public UI() {
        //将控制台的信息输出到textArea
        PrintStream printStream = new PrintStream(new MyOutputStream(commend));
        System.setOut(printStream);
        System.setErr(printStream);

        Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = urlPath.getText();
                String local = localPath.getText();
                boolean pack = isPack.isSelected();
                int sPage = Integer.parseInt(SPage.getText());
                int maxPage = Integer.parseInt(MaxPage.getText());
                MySwingWorker sw = new MySwingWorker(url,local,pack,maxPage,sPage);
                sw.execute();
            }
        });
    }



}
