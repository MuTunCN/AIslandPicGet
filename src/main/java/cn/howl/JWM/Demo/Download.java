package cn.howl.JWM.Demo;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wtnTUN on 2017/4/26.
 */
public class Download extends Thread {
    private String url;
    private String path;
    Logger logger = Logger.getLogger(Download.class);
    public Download (String url,String path){
        this.path = path;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            URL URL = new URL(url);
            URLConnection con = URL.openConnection();
            InputStream in = con.getInputStream();
            File file = new File(path+url.substring(url.lastIndexOf('/')+1));
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len = 0;
            logger.info("start download image "+ url.substring(url.lastIndexOf('/')+1));
            while((len = in.read(buff)) != -1) {
                fos.write(buff,0,len);
            }
            fos.close();
            logger.info("end download image "+url.substring(url.lastIndexOf('/')+1));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
