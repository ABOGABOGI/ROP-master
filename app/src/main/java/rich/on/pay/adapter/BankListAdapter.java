package rich.on.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.BaseAdapter;
import rich.on.pay.model.BankAccount;
import rich.on.pay.utils.Extension;

public class BankListAdapter extends BaseAdapter<BankAccount, BankListAdapter.ViewHolder> {
    private final BankListAdapter.OnItemClickListener mListener;
    private Context context;
    private boolean showCheckmark = false;

    public BankListAdapter(Context context, boolean showCheckmark, BankListAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
        this.showCheckmark = showCheckmark;
    }

    @Override
    protected BankListAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new BankListAdapter.ViewHolder(inflater.inflate(R.layout.item_bank_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final BankListAdapter.ViewHolder viewHolder, int i) {
        final BankAccount item = getItem(viewHolder.getAdapterPosition());
        viewHolder.tvName.setText(String.valueOf(item.getAccountName()));
        viewHolder.tvDetail.setText(String.valueOf(item.getAccountNo() + " / " + item.getBank().getAbbr()));

        if (showCheckmark) {
            if (item.getIsPrimary() == 1) {
                viewHolder.ivSelected.setBackgroundResource(R.drawable.checkmark);
            } else {
                viewHolder.ivSelected.setBackgroundResource(0);
            }
        } else {
            viewHolder.ivSelected.setVisibility(View.GONE);
        }

        if (item.getBank() != null) {
            if (item.getBank().getCoverUrl() != null) {
                Extension.setImageFitCenter(context, viewHolder.ivPhoto, item.getBank().getCoverUrl());
            }
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, viewHolder.getAdapterPosition(), item);
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, BankAccount bankAccount);
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDetail)
        TextView tvDetail;
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;
        @BindView(R.id.ivSelected)
        ImageView ivSelected;

        public ViewHolder(View view) {
            super(view);
        }
    }
}