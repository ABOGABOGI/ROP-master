package com.richonpay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.PaymentProduct;
import com.richonpay.utils.Extension;

import butterknife.BindView;

public class ProductPaymentAdapter extends BaseAdapter<PaymentProduct, ProductPaymentAdapter.ViewHolder> {
    private final ProductPaymentAdapter.OnItemClickListener mListener;
    private Activity context;

    public ProductPaymentAdapter(Activity context, ProductPaymentAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected ProductPaymentAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ProductPaymentAdapter.ViewHolder(inflater.inflate(R.layout.item_payment_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductPaymentAdapter.ViewHolder holder, final int position) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            float calculatedSize = width / 6f;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Integer.parseInt(String.valueOf(Math.round(calculatedSize))), Integer.parseInt(String.valueOf(Math.round(calculatedSize))));
            layoutParams.gravity = Gravity.CENTER;
            holder.ivPhoto.setLayoutParams(layoutParams);

            final PaymentProduct item = getItem(position);
            holder.tvName.setText(item.getProduct());

            switch (item.getProduct()) {
                case "Pulsa":
                    Extension.setImageFitCenter(context, holder.ivPhoto, R.drawable.pulsa);
                    break;
                case "Listrik":
                    Extension.setImageFitCenter(context, holder.ivPhoto, R.drawable.listrik);
                    break;
                case "Voucher Game":
                    Extension.setImageFitCenter(context, holder.ivPhoto, R.drawable.voucher_game);
                    break;
                default:
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, item.getId(), item.getProductCode(), holder.getAdapterPosition());
                }
            });
        } catch (Exception exception) {
            Log.e("ProductPaymentAdapter", "" + exception);
        }
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int id, int category, int position);
    }
}