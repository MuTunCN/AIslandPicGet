package cn.howl.JWM.AIsland;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;

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

    public MySwingWorker(String url, String local, boolean pack,int maxPage,int sPage) {
        this.url = url;
        this.local = local;
        this.pack = pack;
        this.maxPage = maxPage;
        this.sPage = sPage;
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
        logger.info("开始抓取"+url+"的前"+sPage+"页的内容");
        for (int i=1; i<=sPage; i++) {
            logger.info("=============================================");
            logger.info("开始抓取第"+i+"页的内容");
            Spider.create(new MyPageProcessor(maxPage))
                    .addPipeline(pl)
                    .thread(2)
                    .addUrl(url+"?page="+i)
                    .run();
//            System.out.printf("Page %d download success",i);
            publish(i);
            logger.info("=============================================");
        }
        return null;
    }

}
