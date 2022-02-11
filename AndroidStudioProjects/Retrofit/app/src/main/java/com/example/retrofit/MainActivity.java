package com.example.retrofit;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<Bean.DataBean> arrayList=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String url="http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lcj","start initView");
        initView();
        Log.d("lcj","end initView");
        Log.d("lcj","start initData");
        initData();
        Log.d("lcj","end initData");
    }

    private void initView() {
        //查找recyclerview控件
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //创建线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //添加垂直布局
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //将线性布局管理器添加到recyclerview中
        recyclerView.setLayoutManager(manager);
        //实例化适配器
        recyclerViewAdapter=new RecyclerViewAdapter(getApplicationContext(),arrayList);
        //添加适配器
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitService retrofitService=retrofit.create(IRetrofitService.class);
        Call<Bean> call = retrofitService.getUrl(url);
        call.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Bean bean = response.body();
                arrayList.addAll(bean.getData());
                recyclerViewAdapter.refresh(arrayList);
            }
            @Override
            public void onFailure(Call<Bean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
