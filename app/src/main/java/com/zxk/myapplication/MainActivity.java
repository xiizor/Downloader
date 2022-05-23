package com.zxk.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zxk.myapplication.model.DownloadApp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv;

    private ArrayList<DownloadApp> mDatas = new ArrayList<>();

    //mock测试数据
    private void generateDatas() {
        for (int i = 1; i <= 10; i++) {
//            mDatas.add("第 " + i + " 个item");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(linearLayoutManager);

        RvAdapter adapter = new RvAdapter(this, mDatas);
        mRv.setAdapter(adapter);

        //requestDownloadList
    }
}