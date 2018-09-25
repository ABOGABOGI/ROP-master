package rich.on.pay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.BaseAdapter;
import rich.on.pay.model.PaymentProduct;

public class OrderAdapter extends BaseAdapter<PaymentProduct, OrderAdapter.ViewHolder> {
    private final OrderAdapter.OnItemClickListener mListener;
    private Activity context;

    public OrderAdapter(Activity context, OrderAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected OrderAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(inflater.inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder holder, int position) {
        try {
            final PaymentProduct item = getItem(position);
//            holder.tvTitle.setText(item.getProduct());
//            holder.tvDate.setText(item.getProduct());
//            holder.tvMessage.setText(item.getProduct());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, item.getId(), item.getProductCode());
                }
            });
        } catch (Exception exception) {
            Log.e("OrderAdapter", "" + exception);
        }
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {

        @BindView(R.id.tvTransactionCode)
        TextView tvTransactionCode;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvDate)
        TextView tvDate;

        public ViewHolder(View view) {
            super(view);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int id, int category);
    }
}