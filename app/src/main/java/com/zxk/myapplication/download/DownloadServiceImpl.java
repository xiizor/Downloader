package com.zxk.myapplication.download;

public class DownloadServiceImpl implements IDownloadService {

    //stop后断点续传
    private int progress;

    @Override
    public void download(String downloadUrl, DownloadListener listener) {
        // RXJava + Retrofit + OKHttpClient
//        Api.getInstance().basUrl("https://www.111.com").
//                addConverterFactory(GsonConverterFactory.create(date_gson)).
//                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
//                getService(IDownloadService.class).
//                manageWithLoading().
//                subScribeOn(listener);
    }

    @Override
    public void stop() {

    }
}
