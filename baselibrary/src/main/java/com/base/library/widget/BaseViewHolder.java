package com.base.library.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * RecyclerView 适配器的通用 ViewHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View itemView;
    private SparseArray<View> viewSparseArray;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        this.viewSparseArray = new SparseArray<View>();
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }
}
