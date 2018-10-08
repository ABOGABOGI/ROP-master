package rich.on.pay.fragment;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.BaseFragment;
import rich.on.pay.utils.Extension;

public class PackageFragment extends BaseFragment {

    @BindView(R.id.flBackground)
    FrameLayout flBackground;
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

        try {
            int packageType = getArguments().getInt("PACKAGE", 0);
            double fee = getArguments().getDouble("FEE", 0);
            int cashback = getArguments().getInt("CASHBACK", 0);
            double income = getArguments().getDouble("INCOME", 0);
            String bonus = getArguments().getString("BONUS", "");

            switch (packageType) {
                case 0:
                    tvTitle.setText(R.string.silver_member);
                    flBackground.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.packageSilverBackground));
                    break;
                case 1:
                    tvTitle.setText(R.string.gold_member);
                    flBackground.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.packageGoldBackground));
                    break;
                case 2:
                    tvTitle.setText(R.string.platinum_member);
                    flBackground.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.packagePlatinumBackground));
                    break;
            }

            tvJoinFee.setText(Extension.priceFormat(fee));
            tvNetworkCashback.setText(String.valueOf(cashback + "%"));
            tvIncomePotential.setText(Extension.priceFormat(income));
            tvBonus.setText(String.valueOf(bonus));

        } catch (Exception exception) {
            Log.e("onViewCreated", "" + exception);
        }
    }
}
