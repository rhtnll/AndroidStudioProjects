package com.example.recyclerview;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    BaseAdapter mAdapter;
    int loadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        final MyAdapter myadapter = new MyAdapter(this);

        mAdapter = new LoadMoreAdapterWrapper(myadapter, new OnLoad() {
            @Override
            public void load(int pagePosition, int pageSize, final ILoadCallback callback) {
                //此处模拟做网络操作，2s延迟，将拉取的数据更新到adpter中
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> dataSet = new ArrayList();
                        for (int i = 0; i < 10; i++) {
                            dataSet.add("我是第"+i+"条数据");
                        }
                        //数据的处理最终还是交给被装饰的adapter来处理
                        myadapter.appendData(dataSet);
                        callback.onSuccess();
                        //模拟加载到没有更多数据的情况，触发onFailure
                        if (loadCount++ == 3) {
                            callback.onFailure();
                        }
                    }
                }, 2000);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

}