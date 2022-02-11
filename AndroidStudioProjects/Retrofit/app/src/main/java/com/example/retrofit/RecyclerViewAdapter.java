package com.example.retrofit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>{
    private Context context;
    private List<Bean.DataBean> data= new ArrayList<>();
    //构造中传入上下文和带有数据的集合
    public RecyclerViewAdapter(Context context, List<Bean.DataBean> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("lcj","onCreateViewHolder");
        //这个方法主要是找的我们刚刚所写的item布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new MyHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Log.d("lcj","onBindViewHolder");
        //将集合中的具体数据拿到相应的item项中展示
        Picasso.get().load(data.get(i).getPic()).into(myHolder.im);
        myHolder.title.setText(data.get(i).getTitle());
        myHolder.food_str.setText(data.get(i).getFood_str());
    }
    @Override
    public int getItemCount() {
        //集合的长度
        return data.size();
    }
    public void refresh(List<Bean.DataBean> list){
        //这个方法是我们自己手写的，主要是对适配器的一个刷新
        this.data.addAll(list);
        notifyDataSetChanged();
        Log.d("lcj","notifyDataSetChanged");
    }
    class MyHolder extends RecyclerView.ViewHolder {
        //ViewHolder的作用主要是 性能的优化，在每个子item项中的子控件都是一样的情况下，达到控件的复用从而达到节约系统资源的目的
        ImageView im;
        TextView title;
        TextView food_str;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            im=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.title);
            food_str=itemView.findViewById(R.id.food_str);
        }
    }
}


