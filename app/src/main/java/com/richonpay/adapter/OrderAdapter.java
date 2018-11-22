package com.richonpay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.UpgradeRequest;
import com.richonpay.utils.Extension;

import butterknife.BindView;

public class OrderAdapter extends BaseAdapter<UpgradeRequest, OrderAdapter.ViewHolder> {
    private final OrderAdapter.OnItemClickListener mListener;
    private Activity context;

    public OrderAdapter(Activity context, OrderAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected OrderAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(inflater.inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder holder, int position) {
        try {
            final UpgradeRequest item = getItem(position);
            holder.tvTransactionCode.setText(String.valueOf(context.getString(R.string.transaction_code) + " " + item.getCode()));
            holder.tvAmount.setText(Extension.priceFormat(item.getPrice()));
            holder.tvDate.setText(Extension.receiptDetailDateFormat.format(item.getCreatedAt()));

            switch (item.getStatus()) {
                case UpgradeRequest.PENDING:
                    holder.tvStatus.setText(context.getString(R.string.waiting));
                    holder.tvStatus.setBackgroundResource(R.drawable.status_waiting);
                    break;

                case UpgradeRequest.WAITING_PAYMENT:
                    holder.tvStatus.setText(context.getString(R.string.waiting));
                    holder.tvStatus.setBackgroundResource(R.drawable.status_waiting);
                    break;

                case UpgradeRequest.ON_PROCESS:
                    holder.tvStatus.setText(context.getString(R.string.processing_allcaps));
                    holder.tvStatus.setBackgroundResource(R.drawable.status_process);
                    break;

                case UpgradeRequest.ACCEPTED:
                    holder.tvStatus.setText(context.getString(R.string.successful));
                    holder.tvStatus.setBackgroundResource(R.drawable.status_success);
                    break;

                case UpgradeRequest.DECLINED:
                    holder.tvStatus.setText(context.getString(R.string.declined_allcaps));
                    holder.tvStatus.setBackgroundResource(R.drawable.status_cancelled);
                    break;

                case UpgradeRequest.CANCELLED:
                    holder.tvStatus.setText(context.getString(R.string.cancel_allcaps));
                    holder.tvStatus.setBackgroundResource(R.drawable.status_cancelled);
                    break;

                case UpgradeRequest.REFUNDED:
                    holder.tvStatus.setText(context.getString(R.string.refund_allcaps));
                    holder.tvStatus.setBackgroundResource(R.drawable.status_refund);
                    break;
                default:
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, item.getId(), item);
                }
            });
        } catch (Exception exception) {
            Log.e("OrderAdapter", "" + exception);
        }
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        @BindView(R.id.tvTransactionCode)
        TextView tvTransactionCode;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvDate)
        TextView tvDate;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int id, UpgradeRequest upgradeRequest);
    }
}