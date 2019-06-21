package com.base.library.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView 的 基础适配器
 * @param <T>
 */
public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private int layoutId;
    private List<T> dataList = new ArrayList<>();

    public RecyclerViewBaseAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        bindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position);

    public Context getContext() {
        return context;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
