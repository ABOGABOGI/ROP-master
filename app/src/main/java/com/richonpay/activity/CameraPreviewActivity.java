package com.richonpay.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import com.richonpay.R;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.utils.AspectRatioFragment;

public class CameraPreviewActivity extends ToolbarActivity implements AspectRatioFragment.Listener {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.cameraView)
    CameraView cameraView;
    @BindView(R.id.tvCategoryTitle)
    TextView tvCategoryTitle;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.fabCapture)
    FloatingActionButton fabCapture;

    private static final String FRAGMENT_DIALOG = "dialog";
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private Handler mBackgroundHandler;
    private int type = CameraResultActivity.IDENTITY;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_camera_preview;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getInt("TYPE", CameraResultActivity.IDENTITY);
        }

        tvTitle.setText((type == CameraResultActivity.IDENTITY) ? getString(R.string.verify_nric) : getString(R.string.selfie_with_nric));
        tvCategoryTitle.setText((type == CameraResultActivity.IDENTITY) ? getString(R.string.foto_ktp) : getString(R.string.selfie_with_nric));
        tvDescription.setText((type == CameraResultActivity.IDENTITY) ? getString(R.string.verify_nric_description) : getString(R.string.verify_selfie_description));

        if (cameraView != null) {
            cameraView.addCallback(mCallback);
        }
    }

    @Override
    public void onAspectRatioSelected(@NonNull AspectRatio ratio) {

    }

    @OnClick(R.id.fabCapture)
    void captureImage() {
        fabCapture.setEnabled(false);
        cameraView.takePicture();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (type == CameraResultActivity.SELFIE) {
                int facing = cameraView.getFacing();
                cameraView.setFacing(facing == CameraView.FACING_FRONT ?
                        CameraView.FACING_BACK : CameraView.FACING_FRONT);
            }
            cameraView.setFlash(CameraView.FLASH_OFF);
            cameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ConfirmationDialogFragment
                    .newInstance(R.string.camera_permission_message,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION,
                            R.string.camera_permission)
                    .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
        fabCapture.setEnabled(true);
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            mBackgroundHandler.getLooper().quit();
            mBackgroundHandler = null;
        }
    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private CameraView.Callback mCallback
            = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.e("asdf", "onCameraOpened");
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.e("asdf", "onCameraClosed");
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Log.e("asdf", "onPictureTaken " + data.length);
            Toast.makeText(cameraView.getContext(), R.string.picture_taken, Toast.LENGTH_SHORT)
                    .show();
            getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {
                    File file;
                    if (type == CameraResultActivity.IDENTITY) {
                        file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), String.valueOf(new Date().getTime()) + "nric.jpg");
                        VerifyUserAccountActivity.selectedNRIC = file;
                    } else {
                        file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), String.valueOf(new Date().getTime()) + "selfie.jpg");
                        VerifyUserAccountActivity.selectedSelfie = file;
                    }

                    OutputStream os = null;
                    try {
                        os = new FileOutputStream(file);
                        os.write(data);
                        os.close();
                    } catch (IOException e) {
                        Log.e("asdf try", "Cannot write to " + file, e);
                    } finally {
                        if (os != null) {
                            try {
                                os.close();

                                Intent intent = new Intent(CameraPreviewActivity.this, CameraResultActivity.class);
                                intent.putExtra("TYPE", type);
                                startActivityForResult(intent, VerifyUserAccountActivity.VERIFY_ACCOUNT);

                            } catch (IOException e) {
                                Log.e("asdf finally", "Cannot write to " + file, e);
                            }
                        }
                    }
                }
            });
        }
    };

    public static class ConfirmationDialogFragment extends DialogFragment {

        private static final String ARG_MESSAGE = "message";
        private static final String ARG_PERMISSIONS = "permissions";
        private static final String ARG_REQUEST_CODE = "request_code";
        private static final String ARG_NOT_GRANTED_MESSAGE = "not_granted_message";

        public static ConfirmationDialogFragment newInstance(@StringRes int message,
                                                             String[] permissions, int requestCode, @StringRes int notGrantedMessage) {
            ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_MESSAGE, message);
            args.putStringArray(ARG_PERMISSIONS, permissions);
            args.putInt(ARG_REQUEST_CODE, requestCode);
            args.putInt(ARG_NOT_GRANTED_MESSAGE, notGrantedMessage);
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Bundle args = getArguments();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(args.getInt(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String[] permissions = args.getStringArray(ARG_PERMISSIONS);
                                    if (permissions == null) {
                                        throw new IllegalArgumentException();
                                    }
                                    ActivityCompat.requestPermissions(getActivity(),
                                            permissions, args.getInt(ARG_REQUEST_CODE));
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(),
                                            args.getInt(ARG_NOT_GRANTED_MESSAGE),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .create();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == VerifyUserAccountActivity.VERIFY_ACCOUNT) {
                setResult(VerifyUserAccountActivity.VERIFY_ACCOUNT, new Intent(CameraPreviewActivity.this, VerifyUserAccountActivity.class));
                finish();
            }
        } catch (Exception exception) {
            Log.e("onActivityResult ", "" + exception);
        }
    }
}
