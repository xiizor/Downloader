package com.zxk.myapplication.download;

public interface IDownloadService {
    public void download(String downloadUrl,DownloadListener listener);
    public void stop();
}
