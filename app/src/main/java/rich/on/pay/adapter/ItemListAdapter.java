package rich.on.pay.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
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
import rich.on.pay.model.ItemList;
import rich.on.pay.utils.Extension;

public class ItemListAdapter extends BaseAdapter<ItemList, ItemListAdapter.ViewHolder> {

    private ItemListAdapter.OnItemClickListener mListener;
    private Activity context;

    public ItemListAdapter(Activity context, ItemListAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        this.context = context;
    }

    @Override
    protected ItemListAdapter.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ItemListAdapter.ViewHolder(inflater.inflate(R.layout.item_list_with_pointer, parent, false));
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, ItemList exploreCategory);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListAdapter.ViewHolder holder, final int position) {
        final ItemList item = getItem(position);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        float calculatedSize = width / 14f;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Integer.parseInt(String.valueOf(Math.round(calculatedSize))), Integer.parseInt(String.valueOf(Math.round(calculatedSize))));
        layoutParams.gravity = Gravity.CENTER;
        holder.ivImage.setLayoutParams(layoutParams);

        Extension.setImage(context, holder.ivImage, item.getImage());

        holder.tvName.setText(item.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position, item);
            }
        });
    }

    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvName)
        TextView tvName;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
