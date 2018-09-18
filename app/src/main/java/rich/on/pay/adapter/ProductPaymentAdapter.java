package rich.on.pay.adapter;

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

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.BaseAdapter;
import rich.on.pay.model.PaymentProduct;
import rich.on.pay.utils.Extension;

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
    public void onBindViewHolder(@NonNull final ProductPaymentAdapter.ViewHolder holder, int position) {
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
            Extension.setImageFitCenter(context, holder.ivPhoto, R.drawable.account_inactive);
            Extension.setImageFitCenter(context, holder.ivPhoto, item.getCoverUrl());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, item.getId(), item.getProductCode());
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
        void onClick(View view, int id, int category);
    }
}