package com.richonpay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.TransactionOrderDetail;
import com.richonpay.utils.Extension;

import java.util.Date;

import butterknife.BindView;

public class MutationAdapter extends BaseAdapter<TransactionOrderDetail, MutationAdapter.ViewHolder> {
    private final MutationAdapter.OnItemClickListener mListener;
    private Context context;
    private boolean isBalance = false;

    public MutationAdapter(Context context, boolean isBalance, MutationAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.isBalance = isBalance;
        this.mListener = onItemClickListener;
    }

    @Override
    protected MutationAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new MutationAdapter.ViewHolder(inflater.inflate(R.layout.item_mutation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MutationAdapter.ViewHolder holder, int position) {
        if (isBalance) {
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.tvTransactionType.setVisibility(View.GONE);
        } else {
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.statusSuccess));
        }

        final TransactionOrderDetail item = getItem(position);
        if (position > 0) {
            String itemDate = Extension.voucherDateFormat.format(item.getCreatedAt());
            TransactionOrderDetail prevOrder = getItem(position - 1);
            String prevItemDate = Extension.voucherDateFormat.format(prevOrder.getCreatedAt());
            if (itemDate.equals(prevItemDate)) {
                holder.tvHeaderDate.setVisibility(View.GONE);
            } else {
                holder.tvHeaderDate.setVisibility(View.VISIBLE);
                holder.tvHeaderDate.setText(Extension.voucherDateFormat.format(item.getCreatedAt()));
            }

        } else {
            holder.tvHeaderDate.setVisibility(View.VISIBLE);
            holder.tvHeaderDate.setText(Extension.voucherDateFormat.format(item.getCreatedAt()));
            String today = Extension.voucherDateFormat.format(new Date());
            String date = Extension.voucherDateFormat.format(item.getCreatedAt());
            if (date.matches(today)) {
                holder.tvHeaderDate.setVisibility(View.VISIBLE);
            } else {
                holder.tvHeaderDate.setVisibility(View.GONE);
            }
        }

        if (item.getTransactionType() == 0) {
            holder.tvAmount.setText(String.valueOf("+" + Extension.priceFormat(item.getTotalAmount())));
        } else {
            holder.tvAmount.setText(String.valueOf("-" + Extension.priceFormat(item.getTotalAmount())));
        }

//        switch (item.getType()) {
//            case 0: // 0 Pay
//                holder.tvReference.setText(R.string.payment);
//                Extension.setCircleProfileImage(context, holder.ivReferable, R.drawable.pay_circle);
//                break;
//            case 1: // 0 Split Bill
//                holder.tvReference.setText(R.string.payment);
//                Extension.setCircleProfileImage(context, holder.ivReferable, R.drawable.pay_circle);
//                break;
//            case 3: // 0 Topup
//                holder.tvReference.setText(R.string.top_up_user);
//                Extension.setCircleProfileImage(context, holder.ivReferable, R.drawable.icon_topup);
//                break;
//            case 4: // 0 Topup Credit
//                holder.tvReference.setText(R.string.topup_credit);
//                Extension.setCircleProfileImage(context, holder.ivReferable, R.drawable.icon_topup);
//                break;
//            case 7: // 0 Refund
//                holder.tvReference.setText(R.string.refund);
//                Extension.setCircleProfileImage(context, holder.ivReferable, R.drawable.icon_refund);
//                break;
//        }
        holder.tvTransactionDate.setText(Extension.sdfAMPMwithSecond.format(item.getCreatedAt()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, holder.getAdapterPosition(), item);
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, TransactionOrderDetail transaction);
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        @BindView(R.id.tvHeaderDate)
        TextView tvHeaderDate;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tvTransactionFor)
        TextView tvTransactionFor;
        @BindView(R.id.tvTransactionType)
        TextView tvTransactionType;
        @BindView(R.id.tvTransactionDate)
        TextView tvTransactionDate;

        public ViewHolder(View view) {
            super(view);
        }
    }
}