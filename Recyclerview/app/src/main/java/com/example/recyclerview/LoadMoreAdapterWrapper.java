package com.example.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoadMoreAdapterWrapper extends BaseAdapter<String> {
 private BaseAdapter bAdapter;
 private static final int mPageSize = 10;
 private int mPagePosition = 0;
 private boolean hasMoreData = true;
 private OnLoad mOnLoad;

 public LoadMoreAdapterWrapper(BaseAdapter adapter, OnLoad onLoad) {
 bAdapter = adapter;
 mOnLoad = onLoad;
 }

 @Override
 public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 if (viewType == R.layout.list_item_no_more) {
 View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
 return new NoMoreItemVH(view);
 } else if (viewType == R.layout.list_item_loading) {
 View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
 return new LoadingItemVH(view);
 } else {
 return bAdapter.onCreateViewHolder(parent, viewType);
 }
 }

 @Override
 public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
 if (holder instanceof LoadingItemVH) {
 requestData(mPagePosition, mPageSize);
 } else if (holder instanceof NoMoreItemVH) {

 } else {
 bAdapter.onBindViewHolder(holder, position);
 }
 }

 private void requestData(int pagePosition, int pageSize) {

 //网络请求,如果是异步请求，则在成功之后的回调中添加数据，并且调用notifyDataSetChanged方法，hasMoreData为true
 //如果没有数据了，则hasMoreData为false，然后通知变化，更新recylerview

 if (mOnLoad != null) {
 mOnLoad.load(pagePosition, pageSize, new ILoadCallback() {
 @Override
 public void onSuccess() {
 notifyDataSetChanged();
 mPagePosition = (mPagePosition + 1) * mPageSize;
 hasMoreData = true;
 }

 @Override
 public void onFailure() {
 hasMoreData = false;
 }
 });
 }
 }

 @Override
 public int getItemViewType(int position) {
 if (position == getItemCount() - 1) {
 if (hasMoreData) {
 return R.layout.list_item_loading;
 } else {
 return R.layout.list_item_no_more;
 }
 } else {
 return bAdapter.getItemViewType(position);
 }
 }

 @Override
 public int getItemCount() {
 return bAdapter.getItemCount() + 1;
 }

 static class LoadingItemVH extends RecyclerView.ViewHolder {

 public LoadingItemVH(View itemView) {
 super(itemView);
 }

 }

 static class NoMoreItemVH extends RecyclerView.ViewHolder {

 public NoMoreItemVH(View itemView) {
 super(itemView);
 }
 }

 }

 interface OnLoad {
 void load(int pagePosition, int pageSize, ILoadCallback callback);
 }


 interface ILoadCallback {
 void onSuccess();
 void onFailure();
 }