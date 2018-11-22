package com.richonpay.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;

import butterknife.BindView;

/**
 * Created by Winardi on 10/12/2017.
 */

public class StringAdapter extends BaseAdapter<String, StringAdapter.ViewHolder> {
    private final StringAdapter.OnItemClickListener mListener;
    private boolean centerText = false;

    public StringAdapter(StringAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public StringAdapter(boolean centerText, StringAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.centerText = centerText;
    }

    @Override
    protected StringAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_string_adapter, parent, false);
        return new StringAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StringAdapter.ViewHolder viewHolder, int i) {
        viewHolder.llCategory.setVisibility(View.GONE);
        viewHolder.tvString.setText(String.valueOf(getItem(viewHolder.getAdapterPosition())));

        if (centerText) {
            viewHolder.tvString.setGravity(Gravity.CENTER);
        } else {
            viewHolder.tvString.setGravity(Gravity.CENTER | Gravity.START);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, viewHolder.getAdapterPosition());
            }
        });

    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.tvString)
        TextView tvString;
        @BindView(R.id.llCategory)
        LinearLayout llCategory;

        public ViewHolder(View view) {
            super(view);
        }
    }
}