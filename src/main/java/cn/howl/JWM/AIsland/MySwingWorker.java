package cn.howl.JWM.AIsland;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.xsoup.Xsoup;

import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by wtnTUN on 2017/4/26.
 */
public class MySwingWorker extends SwingWorker<Integer,Integer> {
    private String url;
    private String local;
    private boolean pack;
    private int maxPage;
    private int sPage;
    private boolean mulitThread;
    private int threadNum ;

    public MySwingWorker(UrlInfo urlInfo) {
        this.url = urlInfo.getUrlPath();
        this.local = urlInfo.getLocalPath();
        this.pack = urlInfo.isPack();
        this.maxPage = urlInfo.getThreadMaxPage();
        this.sPage = urlInfo.getSearchPage();
        this.mulitThread = urlInfo.isMultiThread();
        this.threadNum = urlInfo.getmultiThread();
    }
    @Override
    protected void process(List<Integer> chunks) {
        for (int chunk : chunks) {
            int progress = (int)(chunk*1.0/sPage*1.0*100);
            this.setProgress(progress);
        }
    }

    protected Integer doInBackground() throws Exception {
        MyPipeline pl = new MyPipeline(local,pack);
        Logger logger = Logger.getLogger(MySwingWorker.class);
        //对比页数
        int urlMaxPage = new PageInfo(url).getPageNum();
        sPage = (sPage > urlMaxPage) ? urlMaxPage : sPage;
        logger.info("开始抓取"+url+"的前"+sPage+"页的内容");
        for (int i=1; i<=sPage; i++) {
            logger.info("=============================================");
            logger.info("开始抓取第"+i+"页的内容");
            if (mulitThread) {
                Spider.create(new MyPageProcessor(maxPage))
                        .addPipeline(pl)
                        .thread(threadNum)
                        .addUrl(url+"?page="+i)
                        .start();
            } else {
                Spider.create(new MyPageProcessor(maxPage))
                        .addPipeline(pl)
                        .thread(2)
                        .addUrl(url+"?page="+i)
                        .run();
            }
//            System.out.printf("Page %d download success",i);
            publish(i);
            logger.info("=============================================");
        }
        return null;
    }

}
