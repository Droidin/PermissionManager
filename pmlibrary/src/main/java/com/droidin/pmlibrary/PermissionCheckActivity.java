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
        implements
        MessageDialog.OnActionListener,
        DialogInterface.OnDismissListener,
        BottomDialog.OnDialogInvisible {

    private static final int CODE_PERMISSION_REQUEST = 0xff;

    private MessageDialog dialog;
    private String requestPerm;

    private void launchSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
        finish();
    }

    private boolean checkPermission(String permission) {
        int hasPermission = ContextCompat.checkSelfPermission(this, permission);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String reason = getIntent().getStringExtra(PermissionManager.PARAM_RATIONALE);
        String tip = getIntent().getStringExtra(PermissionManager.PARAM_TIP);

        dialog = new MessageDialog(this, reason, tip);
        dialog.setOnActionListener(this);
        dialog.setOnDismissListener(this);
        dialog.setOnDialogInvisibleListener(this);
        dialog.show();

        requestPerm = getIntent().getStringExtra(PermissionManager.PARAM_PERMISSIONS);
    }

    ///////////////////////////////////////////////////////////////////////////
    // callback
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODE_PERMISSION_REQUEST: {
                if (PermissionManager.get().onPermissionResult == null) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this.getApplicationContext(), R.string.on_denied_tip, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    PermissionManager.get().onPermissionResult.onResult(permissions[0], grantResults[0]);
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
            if (checkPermission(requestPerm)) {
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
    public void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    @Override
    public void onInvisible() {
        finish();
    }

    @Override
    public void finish() {
        dialog.dismiss();
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
