package com.zxk.myapplication.download;

public interface DownloadListener {
    void onProgressUpdate(int progress);
    void onSuccess(String file);
    void onFailure(String reason);
}
