package rich.on.pay.fragment;

import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.BaseFragment;

public class PackageFragment extends BaseFragment {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.llJoin)
    LinearLayout llJoin;
    @BindView(R.id.tvJoinFee)
    TextView tvJoinFee;
    @BindView(R.id.tvNetworkCashback)
    TextView tvNetworkCashback;
    @BindView(R.id.tvIncomePotential)
    TextView tvIncomePotential;
    @BindView(R.id.tvBonus)
    TextView tvBonus;
    @BindView(R.id.tvDetail)
    TextView tvDetail;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_package;
    }

    @Override
    protected void onViewCreated() {
        llContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = llContent.getHeight();

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, height / 2);
                layoutParams.gravity = Gravity.CENTER;
                tvTitle.setLayoutParams(layoutParams);
            }
        });
    }
}
