package com.richonpay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.BankInfo;

import butterknife.BindView;

public class StringBankListAdapter extends BaseAdapter<BankInfo, StringBankListAdapter.ViewHolder> {
    private final StringBankListAdapter.OnItemClickListener mListener;

    public StringBankListAdapter(StringBankListAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    protected StringBankListAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new StringBankListAdapter.ViewHolder(inflater.inflate(R.layout.item_string_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(final StringBankListAdapter.ViewHolder viewHolder, int i) {
        final BankInfo item = getItem(viewHolder.getAdapterPosition());
        viewHolder.llCategory.setVisibility(View.GONE);
        viewHolder.tvString.setText(String.valueOf(item.getName() + " (" + item.getAbbr() + ")"));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, viewHolder.getAdapterPosition(), item);
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, BankInfo bankResponse);
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