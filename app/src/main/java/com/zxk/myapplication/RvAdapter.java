package com.zxk.myapplication;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zxk.myapplication.download.DownloadListener;
import com.zxk.myapplication.download.DownloadServiceImpl;
import com.zxk.myapplication.download.IDownloadService;
import com.zxk.myapplication.model.DownloadApp;
import com.zxk.myapplication.model.DownloadState;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<DownloadApp> mDatas;

    public static final int MAX_DOWNLOADING = 2;

    public RvAdapter(Context context, ArrayList<DownloadApp> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new RvHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RvHolder rvHolder = (RvHolder) holder;
        rvHolder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class RvHolder extends RecyclerView.ViewHolder {

        public ImageView mImg;
        public TextView mTvName;
        public TextView mTvState;
        public Button mBt;

        public RvHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) itemView.findViewById(R.id.item_img);
//            Glide
//            Glide.with(mContext).loadUrl(mDatas.get(position).icon).into(mImg);
            mTvName = (TextView) itemView.findViewById(R.id.item_tv_name);
            mTvName.setText(mDatas.get(position).name);

            mTvName = (TextView) itemView.findViewById(R.id.item_tv_state);
            mTvName.setText(mDatas.get(position).state);

            mBt = (Button) itemView.findViewById(R.id.item_bt_download);
            mBt.setText(getBtText(mDatas.get(position).state));
            mBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    procBtClick();
                }
            });
        }

        private String getBtText(String state) {
            switch (state) {
                case DownloadState.NOT_DOWNLOAD:
                    return "download";
                case DownloadState.DOWNLOADING:
                    return "cancel";
                case DownloadState.FINISH_DOWNLOAD:
                    return "done";
                case DownloadState.FAILED_DOWNLOAD:
                    return "retry";
            }

            return "download";
        }

        private void procBtClick() {
            if (getDownloadCnt() >= MAX_DOWNLOADING) {
                Toast.makeText(mContext,"超出下载限制", Toast.LENGTH_LONG);
            } else {
                switch (mDatas.get(position).state) {
                    case DownloadState.DOWNLOADING:
                        cancelDownload();
                        mDatas.get(position).state = DownloadState.NOT_DOWNLOAD;
                        //更新按钮文案
                        break;
                    case DownloadState.NOT_DOWNLOAD:
                        executeDownload();
                        mDatas.get(position).state = DownloadState.DOWNLOADING;
                        //更新按钮文案
                        break;
                    case DownloadState.FAILED_DOWNLOAD:
                        executeDownload();
                        mDatas.get(position).state = DownloadState.DOWNLOADING;
                        //更新按钮文案
                        mTvState.setText("downloading");
                        break;
                }
            }
        }

        private void cancelDownload() {
            IDownloadService service = new DownloadServiceImpl();
            service.stop();
        }

        private void executeDownload() {
            IDownloadService service = new DownloadServiceImpl();
            //
            service.download("http://xxx.xxx.xxx", new DownloadListener() {
                @Override
                public void onProgressUpdate(int progress) {
                    // 更新状态
                    mDatas.get(position).state = DownloadState.DOWNLOADING;
                    mTvState.setText("Waiting");
                    mBt.setText("cancel");
                    // 更新progresssBar
                }

                @Override
                public void onSuccess(String file) {
                    // 更新状态
                    mDatas.get(position).state = DownloadState.FINISH_DOWNLOAD;
                    // notify
                    mTvState.setText("Download SuccessFully");
                    mBt.setText("done");
                }

                @Override
                public void onFailure(String reason) {
                    // 更新状态
                    mDatas.get(position).state = DownloadState.FAILED_DOWNLOAD;
                    // notify
                    mTvState.setText("Download Failed");
                    mBt.setText("retry");
                }
            });
        }


        private int getDownloadCnt() {
            int count = 0;
            for (DownloadApp item :mDatas) {
                if (TextUtils.equals(item.state, DownloadState.DOWNLOADING)) {
                    count++;
                }
            }
            return count;
        }

        private int position;
        public void setPosition(int position) {
            this.position = position;
        }
    }

}
