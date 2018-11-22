package com.richonpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.PaymentProduct;

import butterknife.BindView;

public class WaterBillLocationAdapter extends BaseAdapter<PaymentProduct, WaterBillLocationAdapter.ViewHolder> {
    private final WaterBillLocationAdapter.OnItemClickListener mListener;
    private final Context context;

    public WaterBillLocationAdapter(Context context, WaterBillLocationAdapter.OnItemClickListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected WaterBillLocationAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new WaterBillLocationAdapter.ViewHolder(inflater.inflate(R.layout.item_waterbill_location, parent, false));
    }

    @Override
    public void onBindViewHolder(final WaterBillLocationAdapter.ViewHolder holder, int position) {
        final PaymentProduct item = getItem(holder.getAdapterPosition());

        holder.tvLocation.setText(String.valueOf(item.getName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, holder.getAdapterPosition(), item);
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, PaymentProduct paymentProduct);
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.tvLocation)
        TextView tvLocation;

        public ViewHolder(View view) {
            super(view);
        }
    }
}