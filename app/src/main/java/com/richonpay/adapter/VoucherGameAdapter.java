package com.richonpay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.ExploreCategory;
import com.richonpay.utils.Extension;

import butterknife.BindView;

public class VoucherGameAdapter extends BaseAdapter<ExploreCategory, VoucherGameAdapter.ViewHolder> {

    private VoucherGameAdapter.OnItemClickListener mListener;
    private Context context;

    public VoucherGameAdapter(Context context, VoucherGameAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected VoucherGameAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new VoucherGameAdapter.ViewHolder(inflater.inflate(R.layout.item_voucher_game, parent, false));
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, ExploreCategory exploreCategory);
    }

    @Override
    public void onBindViewHolder(@NonNull final VoucherGameAdapter.ViewHolder holder, final int position) {
        final ExploreCategory item = getItem(position);
        holder.tvVoucherName.setText(item.getCategory());
        Extension.setImageFitCenter(context, holder.ivVoucherLogo, item.getPictureUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position, item);
            }
        });
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.ivVoucherLogo)
        ImageView ivVoucherLogo;
        @BindView(R.id.tvVoucherName)
        TextView tvVoucherName;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
