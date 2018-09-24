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

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.BaseAdapter;
import rich.on.pay.model.PaymentProduct;
import rich.on.pay.utils.Extension;

public class BankLogoAdapter extends BaseAdapter<PaymentProduct, BankLogoAdapter.ViewHolder> {
    private final BankLogoAdapter.OnItemClickListener mListener;
    private Activity context;

    public BankLogoAdapter(Activity context, BankLogoAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected BankLogoAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new BankLogoAdapter.ViewHolder(inflater.inflate(R.layout.item_bank_logo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BankLogoAdapter.ViewHolder holder, int position) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            float calculatedSize = width / 4f;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Integer.parseInt(String.valueOf(Math.round(calculatedSize))));
            layoutParams.gravity = Gravity.CENTER;
            holder.ivImage.setLayoutParams(layoutParams);

            final PaymentProduct item = getItem(position);
            Extension.setImageFitCenter(context, holder.ivImage, item.getCoverUrl());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, item.getId(), item.getProductCode());
                }
            });
        } catch (Exception exception) {
            Log.e("BankLogoAdapter", "" + exception);
        }
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int id, int category);
    }
}