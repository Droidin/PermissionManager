package com.droidin.pmlibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class PermissionCheckActivity
        extends AppCompatActivity
        implements MessageDialog.OnActionListener, BottomDialog.OnDialogInvisible {

    private static final int CODE_PERMISSION_REQUEST = 0xff;

    private MessageDialog dialog;
    private String requestPerm;

    private boolean isAppSettingPageLaunched = false;

    private void launchSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
        isAppSettingPageLaunched = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.style.AlphaAnim);

        String reason = getIntent().getStringExtra(PermissionManager.PARAM_RATIONALE);
        String tip = getIntent().getStringExtra(PermissionManager.PARAM_TIP);

        dialog = new MessageDialog(this, reason, tip);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        dialog.setOnActionListener(this);
        dialog.setOnDialogInvisibleListener(this);
        dialog.show();

        requestPerm = getIntent().getStringExtra(PermissionManager.PARAM_PERMISSIONS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAppSettingPageLaunched) {
            finish();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // callback
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODE_PERMISSION_REQUEST: {
                if (PermissionManager.getInstance(null).onPermissionResult == null) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this.getApplicationContext(), R.string.on_denied_tip, Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onAction(boolean accept) {
        if (accept) {
            if (PermissionManager.check(this, requestPerm)) {
                finish();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, requestPerm)) {
                    launchSetting();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{requestPerm}, CODE_PERMISSION_REQUEST);
                }
            }
        } else {
            finish();
        }
    }

    @Override
    public void onDialogDismiss() {
        finish();
    }

    @Override
    public void finish() {
        dialog.dismiss();
        if (PermissionManager.getInstance(null).onPermissionResult != null) {
            PermissionManager.getInstance(null)
                    .onPermissionResult
                    .onPermissionRequestResult(requestPerm, ContextCompat.checkSelfPermission(this, requestPerm));
        }
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
