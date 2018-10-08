package rich.on.pay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.BaseAdapter;
import rich.on.pay.model.TopUpRequest;
import rich.on.pay.utils.Extension;

public class TopUpAmountAdapter extends BaseAdapter<TopUpRequest, TopUpAmountAdapter.ViewHolder> {
    private final TopUpAmountAdapter.OnItemClickListener mListener;
    private Activity context;
    private int selectedPosition = -1;

    public TopUpAmountAdapter(Activity topupAmountSelectionActivity, OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
        this.context = topupAmountSelectionActivity;
    }

    @Override
    protected TopUpAmountAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new TopUpAmountAdapter.ViewHolder(inflater.inflate(R.layout.item_topup_amount, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (position == selectedPosition) {
            holder.llBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_rounded_corner_gradient_dark));
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.llBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_rounded_corner_dark));
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.gradient_dark));
        }
        final TopUpRequest object = getItem(holder.getAdapterPosition());
        holder.tvAmount.setText(Extension.priceFormat(object.getAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position, object);
                int prevPosition = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(position);
                notifyItemChanged(prevPosition);
            }
        });
    }

    public void selectItem(int position) {
        int prevPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(position);
        notifyItemChanged(prevPosition);
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.llBackground)
        LinearLayout llBackground;
        @BindView(R.id.tvAmount)
        TextView tvAmount;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, TopUpRequest selectedItem);
    }
}
