package rich.on.pay.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import rich.on.pay.R;
import rich.on.pay.base.ToolbarActivity;

public class VerifyUserAccountActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnVerifyAccount)
    Button btnVerifyAccount;

    public static File selectedIC = null;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_verify_user;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.verify_user_data);
        btnVerifyAccount.setText(R.string.verify_now);
//        btnVerifyAccount.setText(R.string.ongoing);
//        btnVerifyAccount.setText(R.string.successful);
    }

    @OnClick(R.id.btnVerifyAccount)
    void takeICPhoto() {
        if (Build.VERSION.SDK_INT > 22) {
            Dexter.withActivity(VerifyUserAccountActivity.this).withPermissions(Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        EasyImage.openCamera(VerifyUserAccountActivity.this, 0);
                    } else {
                        DialogOnDeniedPermissionListener.Builder
                                .withContext(VerifyUserAccountActivity.this)
                                .withTitle(R.string.camera_permission)
                                .withMessage(R.string.camera_permission_message)
                                .withButtonText(android.R.string.ok)
                                .withIcon(R.drawable.camera)
                                .build();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        } else {
            EasyImage.openCamera(VerifyUserAccountActivity.this, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imagesFiles, EasyImage.ImageSource source, int cameraType) {
                if (imagesFiles.size() >= 1) {
                    try {
                        VerifyUserAccountActivity.selectedIC = new Compressor(VerifyUserAccountActivity.this).compressToFile(imagesFiles.get(0));
                        startActivity(new Intent(VerifyUserAccountActivity.this, CameraResultActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        VerifyUserAccountActivity.selectedIC = null;
    }
}
