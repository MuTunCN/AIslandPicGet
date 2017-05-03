package cn.howl.JWM.AIsland;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

/**
 * Created by wtnTUN on 2017/4/26.
 */
public class Download implements Callable<Integer> {
    private String url;
    private String path;
    Logger logger = Logger.getLogger(Download.class);
    public Download(String url, String path){
        this.path = path;
        this.url = url;
    }


    public Integer call() {
        try {
            URL URL = new URL(url);
            URLConnection con = URL.openConnection();
            InputStream in = con.getInputStream();
            File file = new File(path+url.substring(url.lastIndexOf('/')+1));
            //目录不存在跳过
            if(!new File(path).exists()){
//                System.out.println(path+"目录不存在");
                return 0;
            }
            //跳过重复文件
            if(file.exists()){
                //logger.info("file exists skip");
                return 0;
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len = 0;
//            logger.info("image "+ url.substring(url.lastIndexOf('/')+1));
            while((len = in.read(buff)) != -1) {
                fos.write(buff,0,len);
            }
            fos.close();
            return 1;
        } catch (MalformedURLException e) {
            logger.error("格式错误");
        } catch (IOException e) {
            logger.error("下载失败，稍后自动重试");
        }
        return 0;
    }
}
