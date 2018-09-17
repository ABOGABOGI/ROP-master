package rich.on.pay;

import android.content.Intent;
import android.os.Bundle;

import rich.on.pay.activity.SplashActivity;
import rich.on.pay.api.API;
import rich.on.pay.base.BaseActivity;

import static rich.on.pay.firebase.MyFirebaseMessagingService.NOTIFICATION_INTENT;

public class RootActivity extends BaseActivity {

    @Override
    protected int getContentViewResource() {
        return 0;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            if (API.currentUser() != null) {
                if (getIntent().getExtras() != null) {
                    String type = extra.getString(NOTIFICATION_INTENT, "");

                    if (type.length() > 0) {
                        Intent intent = new Intent(this, SplashActivity.class);
                        intent.putExtra(NOTIFICATION_INTENT, type);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, SplashActivity.class);
                        intent.putExtra(NOTIFICATION_INTENT, extra.getString("type"));
                        startActivity(intent);
                        finish();
                    }

                } else {
                    startActivity(new Intent(this, SplashActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(this, SplashActivity.class));
                finish();
            }
        } else {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }
}