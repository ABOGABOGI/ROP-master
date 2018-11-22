package com.richonpay.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.ExploreCategory;
import com.richonpay.model.PaymentProduct;

import butterknife.BindView;

public class WaterBIllParentAdapter extends BaseAdapter<ExploreCategory, WaterBIllParentAdapter.ViewHolder> {
    private final WaterBIllParentAdapter.OnItemClickListener mListener;
    private final Context context;

    public WaterBIllParentAdapter(Context context, WaterBIllParentAdapter.OnItemClickListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected WaterBIllParentAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new WaterBIllParentAdapter.ViewHolder(inflater.inflate(R.layout.item_waterbill_location_header, parent, false));
    }

    @Override
    public void onBindViewHolder(final WaterBIllParentAdapter.ViewHolder holder, int position) {
        final ExploreCategory item = getItem(holder.getAdapterPosition());

        holder.tvHeader.setText(item.getCategory());

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        WaterBillLocationAdapter mAdapter = new WaterBillLocationAdapter(context, new WaterBillLocationAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, final PaymentProduct selectedItem) {
                mListener.onItemSelected(selectedItem);
            }
        });

        holder.recyclerView.setAdapter(mAdapter);
        mAdapter.setItems(item.getPaymentProduct());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, PaymentProduct paymentProduct);

        void onItemSelected(PaymentProduct paymentProduct);
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.tvHeader)
        TextView tvHeader;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
        }
    }
}