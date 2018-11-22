package com.richonpay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.PaymentProduct;
import com.richonpay.utils.Extension;

import butterknife.BindView;

public class PaymentProductAdapter extends BaseAdapter<PaymentProduct, PaymentProductAdapter.ViewHolder> {
    private final PaymentProductAdapter.OnItemClickListener mListener;
    private Activity context;
    private int aspectWidth = 4;

    public PaymentProductAdapter(Activity context, PaymentProductAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected PaymentProductAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new PaymentProductAdapter.ViewHolder(inflater.inflate(R.layout.item_payment_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentProductAdapter.ViewHolder holder, int position) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        width -= (Math.round(context.getResources().getDimension(R.dimen.tiny)) * 2);
        float calculatedWidth = width / aspectWidth;
        holder.llParent.setLayoutParams(new LinearLayout.LayoutParams(Math.round(calculatedWidth), ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.ivPhoto.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (holder.ivPhoto != null) {
                    int width = holder.ivPhoto.getMeasuredWidth();
                    holder.ivPhoto.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    holder.ivPhoto.setLayoutParams(new LinearLayout.LayoutParams(width, width / 2));
                }
            }
        });

        final PaymentProduct item = getItem(position);
        holder.tvName.setText(item.getProduct());
        Extension.setImageFitCenter(context, holder.ivPhoto, item.getCoverUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, item.getId(), item.getProductCode());
            }
        });
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;
        @BindView(R.id.llParent)
        LinearLayout llParent;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int id, int category);
    }
}
