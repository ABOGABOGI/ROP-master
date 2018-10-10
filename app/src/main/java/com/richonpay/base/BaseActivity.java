package com.richonpay.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private boolean isForeground = false;

    // CONNECTION STATUS
    public static int connectionStatus = 0;
    public static BaseActivity.ConnectionListener connectionListener;

    public static void setConnectionListener(BaseActivity.ConnectionListener valueChangeListener) {
        BaseActivity.connectionListener = valueChangeListener;
    }

    public interface ConnectionListener {
        void onConnectionChangedListener(int receivedInvitation);
    }

    public static void ConnectionChanged() {
        if (connectionListener != null) {
            BaseActivity.connectionListener.onConnectionChangedListener(connectionStatus);
        }
    }

    public static Dialog inviteDialog;

    //CHECK SPLITBILL INVITATION
    public static int receivedInvitation = 0;
    public static String receivedInvitationUsername = "";
    public static BaseActivity.InvitationListener invitationListener;

    public static void invitationListener(BaseActivity.InvitationListener valueChangeListener) {
        BaseActivity.invitationListener = valueChangeListener;
    }

    public interface InvitationListener {
        void onReceivedInvitationListener(int receivedInvitation);
    }

    public static void InvitationReceived() {
        if (invitationListener != null) {
            BaseActivity.invitationListener.onReceivedInvitationListener(receivedInvitation);
        }
    }

    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
        int contentViewResource = getContentViewResource();
        if (contentViewResource != 0) setContentView(getContentViewResource());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        onBindView();
        onViewCreated();
    }

    protected void onBindView() {
        ButterKnife.bind(this);
    }

    @LayoutRes
    protected abstract int getContentViewResource();

    protected abstract void onViewCreated();
}
