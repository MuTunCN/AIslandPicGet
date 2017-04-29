package cn.howl.JWM.AIsland;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by wtnTUN on 2017/4/26.
 * 获取一个串下的所有图片，所有页数的图片
 */
public class MyPageProcessor implements PageProcessor {
    //初始化日志
    Logger logger = Logger.getLogger(MyPageProcessor.class);
    //站点本身的配置信息
    Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    //最大图片数
    private int maxPage = 50;

    public MyPageProcessor() {
    }

    public MyPageProcessor(int maxPage) {
        this.maxPage = maxPage;
    }

    public synchronized void process(Page page) {
        //去除页面后面的?r=
        if(page.getUrl().regex("\\?r=").match()){
            page.setUrl(page.getUrl().replace("\\?r=\\w*",""));
        }
        //添加串号里的剩余页码页面地址
        if (page.getUrl().regex("/t/[0-9]*").match()) {
            //获取第一个串的文字
            String ThreadName = page.getHtml().xpath("//div[@class='h-threads-item-main']/div[@class='h-threads-content']/text()").toString().trim();
            String ThreadNum = page.getHtml().xpath("//div[@class='h-threads-list']/div/@data-threads-id").toString();
            if(ThreadName!=null) {
                page.putField(ThreadNum, ThreadName);
            }
            page.putField("ThreadNum",ThreadNum);
            logger.info("otherPage " + page.getUrl());
            //判断是否需要页面处理
            if (!page.getUrl().regex("\\?page=").match()) {
                //获取分页数
                List<String> otherPageStatus = page.getHtml().xpath("//ul[@class='uk-pagination uk-pagination-left h-pagination']/li/a/text()").all();
                //检测有末页就提示 没有末页就去下一页的前面一个数字然后循环添加
                if (otherPageStatus.indexOf("末页") != -1) {
                    String path = page.getHtml().xpath("//ul[@class='uk-pagination uk-pagination-left h-pagination']/li[14]/a/@href").toString();
                    int ThreadMaxPage = Integer.parseInt(path.substring(path.lastIndexOf("=") + 1, path.length()));
                    logger.info("这个串居然有" + ThreadMaxPage + "页!!所以最大只取了前" + maxPage + "页");
                    maxPage = (maxPage > ThreadMaxPage) ? ThreadMaxPage : maxPage;
                    //循环添加
                    for (int i = 1; i <= maxPage; i++) {
                        page.addTargetRequest(page.getUrl() + "?page=" + i);
                    }
                } else if (!otherPageStatus.isEmpty()) {
                    //获得到最大页数
                    int ThreadMaxPage = Integer.parseInt(otherPageStatus.get(otherPageStatus.indexOf("下一页") - 1));
                    logger.info("这个串有" + ThreadMaxPage + "页");
                    //循环添加
                    for (int i = 1; i <= ThreadMaxPage; i++) {
                        page.addTargetRequest(page.getUrl() + "?page=" + i);
                    }
                }
            }
//            page.putField("ThreadHrefOtherPage", page.getHtml().xpath("//*[@id=\"h-content\"]/div[1]/ul/li/a").links().all());
//            page.addTargetRequests((List<String>) page.getResultItems().get("ThreadHrefOtherPage"));
        } else if(!page.getUrl().regex("/t/[0-9]*\\?page=").match()){
            //获得板块页的串号地址
            page.putField("ThreadsHref", page.getHtml().xpath(
                    "//div[@class='h-threads-item-main']/div[@class='h-threads-info']/a[@class='h-threads-info-id']").links().all());
            List<String> Threads = page.getResultItems().get("ThreadsHref");
            page.addTargetRequests(Threads);
            return;
        }
        page.putField("img", page.getHtml().xpath("//a[@class='h-threads-img-a']/@href").all());
    }

    public Site getSite() {
        return site;
    }
}
