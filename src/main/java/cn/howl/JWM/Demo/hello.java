package cn.howl.JWM.Demo;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by wtnTUN on 2017/4/25.
 */
public class hello implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private int startPage = 1;
    private int endPage = 10;
    private List<String> imgs ;
    public void process(Page page) {
        for (int i = startPage; i < endPage; i++) {
//            page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
            page.addTargetRequest("https://h.nimingban.com/t/5847239?page="+i);
        }
//        page.putField("author",page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("img",page.getHtml().xpath("//a[@class='h-threads-img-a']/@href").all());
//        page.putField("readme",page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider MySpider = Spider.create(new hello())
//                .addUrl("https://h.nimingban.com/t/5847239")
//                .addPipeline(new ImgPipeline())
//                .thread(3);
//        MySpider.start();
        try {
            URL url = new URL("https://h.nimingban.com/Api/getForumList");
            InputStream in = url.openConnection().getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
class ImgPipeline implements Pipeline {
    public void process(ResultItems resultItems, Task task) {
        List<String> imgs = resultItems.get("img");
        for (String img:
                imgs) {
            new Download(img,"e:\\test\\imgs\\").start();
        }
    }
}