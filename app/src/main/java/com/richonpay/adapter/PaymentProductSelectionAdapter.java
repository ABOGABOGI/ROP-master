package com.richonpay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.PaymentProduct;
import com.richonpay.utils.Extension;

import butterknife.BindView;

public class PaymentProductSelectionAdapter extends BaseAdapter<PaymentProduct, PaymentProductSelectionAdapter.ViewHolder> {
    private final PaymentProductSelectionAdapter.OnItemClickListener mListener;
    private Activity context;
    private int paymentProductType = 0;

    public PaymentProductSelectionAdapter(int paymentProductType, PaymentProductSelectionAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.paymentProductType = paymentProductType;
    }

    @Override
    protected PaymentProductSelectionAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new PaymentProductSelectionAdapter.ViewHolder(inflater.inflate(R.layout.item_payment_product_selection, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentProductSelectionAdapter.ViewHolder viewHolder, int position) {
        final PaymentProduct item = getItem(position);

        switch (paymentProductType) {
            case 1:  //  1 : Phone_Prepaid
                viewHolder.tvCategory.setText(R.string.balance_pulsa);
                break;
            case 2:  //  2 : Phone_Postpaid

                break;
            case 3:  //  3 : PLN_PREPAID
                viewHolder.tvCategory.setText(R.string.balance_pulsa);
                break;
            case 5:  //  4 : PLN_POSTPAID
                viewHolder.tvCategory.setText("");
                break;
            case 4:  //  4 : GAME
                viewHolder.tvCategory.setText(R.string.game_voucher);
                break;
        }

        viewHolder.tvPrice.setText(item.getName());
        viewHolder.tvTotalPayment.setText(Extension.priceFormat(item.getPrice()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, viewHolder.getAdapterPosition(), item.getProduct());
            }
        });
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        @BindView(R.id.tvCategory)
        TextView tvCategory;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvTotalPayment)
        TextView tvTotalPayment;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, String category);
    }
}