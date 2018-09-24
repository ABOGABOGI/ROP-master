package rich.on.pay.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.utils.Extension;

public class ChangePhoneEmailActivity extends ToolbarActivity {

    public static int PHONE = 10020;
    public static int EMAIL = 10021;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etPrevious)
    TextInputEditText etPrevious;
    @BindView(R.id.etUpdated)
    TextInputEditText etUpdated;
    @BindView(R.id.btnSave)
    Button btnSave;

    private int type = 0;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_change_phone_email;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getInt("TYPE", 0);
        }
//        etUpdated.setInputType((type == 0 ? InputType.TYPE_CLASS_PHONE : InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS));
        tvTitle.setText((type == 0 ? getString(R.string.phone_number) : getString(R.string.email)));
        etPrevious.setHint((type == 0 ? getString(R.string.previous_phone_number) : getString(R.string.previous_email)));
        etUpdated.setHint((type == 0 ? getString(R.string.new_phone_number) : getString(R.string.new_email)));
//        etUpdated.requestFocus();
    }

    @OnClick(R.id.btnSave)
    void submitChanges() {
        Intent previousScreen = new Intent(getApplicationContext(), AccountSettingActivity.class);
        previousScreen.putExtra("UPDATE", (type == 0 ? Extension.validatePhoneNumber(etUpdated.getText().toString()) : etUpdated.getText().toString()));
        setResult((type == 0 ? PHONE : EMAIL), previousScreen);
        finish();
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//            if (inputMethodManager.isActive()) {
//                if (getCurrentFocus() != null) {
//                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                }
//            }
////            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////            imm.hideSoftInputFromWindow(etPrevious.getWindowToken(), 0);
////            imm.hideSoftInputFromWindow(etUpdated.getWindowToken(), 0);
//        } catch (Exception exception) {
//            Log.e("ONDESTROY", "" + exception);
//        }
//    }
}
