package com.richonpay.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseAdapter<M, VH extends BaseAdapter.BaseViewHolder> extends RecyclerView.Adapter<VH> {
    private List<M> items;

    public BaseAdapter() {
        setHasStableIds(true);
    }

    public void add(M object) {
        items.add(object);
        notifyItemInserted(items.size() - 1);
    }

    public void add(int index, M object) {
        items.add(index, object);
        notifyItemInserted(index);
    }

    public void addItems(List<M> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        items.remove(position);
        if (position == 0)
            notifyDataSetChanged();
        else
            notifyItemRemoved(position);
    }

    public List<M> getItems() {
        return items;
    }

    public void setItems(List<M> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public M getItem(int position) {
        return items.get(position);
    }

    protected abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    @Deprecated
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}