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

import butterknife.BindView;
import com.richonpay.R;
import com.richonpay.base.BaseAdapter;
import com.richonpay.model.BankAccount;
import com.richonpay.utils.Extension;

public class BankLogoAdapter extends BaseAdapter<BankAccount, BankLogoAdapter.ViewHolder> {
    private final BankLogoAdapter.OnItemClickListener mListener;
    private Activity context;
    private Integer selectedPosition = 0;

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
            if (selectedPosition == holder.getAdapterPosition()) {
                holder.llBackground.setBackgroundResource(R.drawable.rounded_corner_selected);
            } else {
                holder.llBackground.setBackgroundResource(R.drawable.rounded_corner_white);
            }

            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            float calculatedSize = width / 5f;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Integer.parseInt(String.valueOf(Math.round(calculatedSize))));
            layoutParams.gravity = Gravity.CENTER;
            holder.ivImage.setLayoutParams(layoutParams);

            final BankAccount item = getItem(position);
            Extension.setImageFitCenter(context, holder.ivImage, item.getBank().getCoverUrl());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, item.getId(), item.getId());
                    if (selectedPosition != null) {
                        notifyItemChanged(selectedPosition);
                    }
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                }
            });
        } catch (Exception exception) {
            Log.e("BankLogoAdapter", "" + exception);
        }
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.llBackground)
        LinearLayout llBackground;
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