package com.richonpay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.PaymentProduct;

import butterknife.BindView;

public class NotificationAdapter extends BaseAdapter<PaymentProduct, NotificationAdapter.ViewHolder> {
    private final NotificationAdapter.OnItemClickListener mListener;
    private Activity context;

    public NotificationAdapter(Activity context, NotificationAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected NotificationAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new NotificationAdapter.ViewHolder(inflater.inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder holder, int position) {
        try {
            final PaymentProduct item = getItem(position);
//            holder.tvTitle.setText(item.getProduct());
//            holder.tvDate.setText(item.getProduct());
//            holder.tvMessage.setText(item.getProduct());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, item.getId(), item.getProductCode());
                }
            });
        } catch (Exception exception) {
            Log.e("NotificationAdapter", "" + exception);
        }
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDate)
        ImageView tvDate;
        @BindView(R.id.tvMessage)
        ImageView tvMessage;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int id, int category);
    }
}