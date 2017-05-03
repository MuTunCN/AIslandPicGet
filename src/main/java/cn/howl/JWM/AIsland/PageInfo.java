package cn.howl.JWM.AIsland;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.util.List;

/**
 * Created by wtnTUN on 2017/5/3.
 * 获取最大页数
 */
public class PageInfo {
    private String url;

    public PageInfo(String url) {
        this.url = url;
    }

    public int getPageNum(){
        try {
            int ThreadMaxPage = 1;
            //获取分页数
            Document contents = Jsoup.connect(url).get();
            List<String> otherPageStatus = Xsoup.compile("//ul[@class='uk-pagination uk-pagination-left h-pagination']/li/a/text()")
                    .evaluate(contents).list();
            //检测有末页就提示 没有末页就去下一页的前面一个数字然后循环添加
            if (otherPageStatus.indexOf("末页") != -1) {
                String path = Xsoup.compile("//ul[@class='uk-pagination uk-pagination-left h-pagination']/li[14]/a/@href")
                        .evaluate(contents).get();
                ThreadMaxPage = Integer.parseInt(path.substring(path.lastIndexOf("=") + 1, path.length()));


            } else if (!otherPageStatus.isEmpty()) {
                //获得到最大页数
                ThreadMaxPage = Integer.parseInt(otherPageStatus.get(otherPageStatus.indexOf("下一页") - 1));


            }
            return ThreadMaxPage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
