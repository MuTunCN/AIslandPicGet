package cn.howl.JWM.AIsland;

/**
 * Created by wtnTUN on 2017/5/3.
 */
public class UrlInfo {
    private String urlPath;
    private boolean isPack;
    private boolean isMultiThread;
    private String localPath;
    private int searchPage;
    private int threadMaxPage;
    private int multiThread = 5;

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public void setPack(boolean pack) {
        isPack = pack;
    }

    public void setmultiThread(boolean multiThread) {
        isMultiThread = multiThread;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public void setSearchPage(int searchPage) {
        this.searchPage = searchPage;
    }

    public void setThreadMaxPage(int threadMaxPage) {
        this.threadMaxPage = threadMaxPage;
    }

    public void setmultiThread(int multiThread) {
        multiThread = multiThread;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public boolean isPack() {
        return isPack;
    }

    public boolean isMultiThread() {
        return isMultiThread;
    }

    public String getLocalPath() {
        return localPath;
    }

    public int getSearchPage() {
        return searchPage;
    }

    public int getThreadMaxPage() {
        return threadMaxPage;
    }

    public int getmultiThread() {
        return multiThread;
    }

    public UrlInfo(String urlPath, boolean isPack, boolean isMultiThread, String localPath, int searchPage, int threadMaxPage) {
        this.urlPath = urlPath;
        this.isPack = isPack;
        this.isMultiThread = isMultiThread;
        this.localPath = localPath;
        this.searchPage = searchPage;
        this.threadMaxPage = threadMaxPage;
    }

    public UrlInfo(String urlPath, boolean isPack, boolean isMultiThread, String localPath, int searchPage, int threadMaxPage, int multiThread) {
        this.urlPath = urlPath;
        this.isPack = isPack;
        this.isMultiThread = isMultiThread;
        this.localPath = localPath;
        this.searchPage = searchPage;
        this.threadMaxPage = threadMaxPage;
        this.multiThread = multiThread;
    }
}
