package cn.howl.JWM.AIsland;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.List;

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
    private JProgressBar progressBar;
    private JComboBox FrontPages;
    private JCheckBox FrontPageSearch;
    private JCheckBox isMultiThread;
    private JTextField ThreadNum;

    public static void main(String[] args) {
        JFrame frame = new JFrame("A岛图片收集者");
        frame.setContentPane(new UI().panel1);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public UI() {
        urlPath.setText("https://h.nimingban.com/t/117617");
        localPath.setText("e:/AIslandPicGet");
        SPage.setText("5");
        MaxPage.setText("4");
        //设置板块列表
        List<String> names = getFrontPages("names");
        for (String name : names) {
            FrontPages.addItem(name);
        }

        //将控制台的信息输出到textArea
        PrintStream printStream = new PrintStream(new MyOutputStream(commend));
        System.setOut(printStream);
        System.setErr(printStream);

        Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String local = localPath.getText();
                boolean pack = isPack.isSelected();
                boolean isMuliThread = isMultiThread.isSelected();
                String url;
                if (FrontPageSearch.isSelected()) {
                    int index = FrontPages.getSelectedIndex();
                    url = "https://h.nimingban.com" + getFrontPages("urls").get(index);
                } else {
                    //获取属性
                    url = urlPath.getText();
                }
                int sPage = Integer.parseInt(SPage.getText());
                int maxPage = Integer.parseInt(MaxPage.getText());
                int threadNum = (ThreadNum.getText().equals("")) ? 5 : Integer.parseInt(ThreadNum.getText());
                UrlInfo urlInfo = new UrlInfo(url, pack,isMuliThread ,local, sPage, maxPage,threadNum);
                //创建异步
                MySwingWorker sw = new MySwingWorker(urlInfo);
                Button.setEnabled(false);
                //绑定监听器
                sw.addPropertyChangeListener(new MyPCListener());
                sw.execute();
            }
        });
    }

    class MyPCListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if("progress"==evt.getPropertyName()){
                int progress = (Integer)evt.getNewValue();
                progressBar.setValue(progress);
                if (progress == 100) {
                    Button.setEnabled(true);
                }
            }
        }

    }
    //获取板块名
    private List<String> getFrontPages(String type) {
        String url = "https://h.nimingban.com/Forum";
        Document contents = null;
        try {
            contents = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> names = Xsoup.compile("//ul[@class='uk-nav-sub']/li/a/text()").evaluate(contents).list();
        List<String> urls = Xsoup.compile("//ul[@class='uk-nav-sub']/li/a/@href").evaluate(contents).list();
        return (type.equals("names")) ? names : urls;
    }
}
