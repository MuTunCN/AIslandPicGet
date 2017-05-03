package cn.howl.JWM.AIsland;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wtnTUN on 2017/4/26.
 */
public class MyPipeline implements Pipeline {

    private String local;
    private boolean pack;

    public MyPipeline(String local, boolean pack) {
        if(local.endsWith("/")){
            local = local.substring(0,local.lastIndexOf("/"));
        }
        this.local = local;
        this.pack = pack;
    }


    public void process(ResultItems resultItems, Task task) {
        Logger logger = Logger.getLogger(MyPipeline.class);
        File dir = new File(local + "\\" );
        if (!dir.exists()) {
            dir.mkdirs();
        }
        List<String> imgs = resultItems.get("img");
        if (imgs == null||imgs.isEmpty()) {
            return;
        }

        if (pack) {
            String num = resultItems.get("ThreadNum");
            String name = resultItems.get(num);
            dir = new File(local + "\\" + name);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    logger.info("成功创建目录 " + local + "\\" + name);
                } else {
                    logger.info("目录" + local + "\\" + name + "创建失败 删除特殊符号");
                    String reg = "([^\\u4e00-\\u9fa5\\w\\(\\)（）])+?";
                    Pattern p = Pattern.compile(reg);
                    Matcher m = p.matcher(name);
                    name = m.replaceAll("");
                    dir = new File(local + "\\" + name);
                    dir.mkdirs();
                }
            }
        }
        int downNum = 0;
        for (String img : imgs) {
            downNum += new Download(img, dir.getPath() +"\\").call();

        }
        logger.info("下载了"+downNum+"张图片,跳过已下载的文件");
    }
}

    
    
