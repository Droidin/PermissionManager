package com.droidin.pmlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Created by bowen on 2016-11-03 0003.
 */

public class PermissionManager {

    static final String PARAM_RATIONALE = "reason";
    static final String PARAM_TIP = "tip";
    static final String PARAM_PERMISSIONS = "permissions";

    private static PermissionManager pmgr;

    public static PermissionManager get() {
        if (pmgr == null) {
            pmgr = new PermissionManager();
        }
        return pmgr;
    }

    OnPermissionResult onPermissionResult = null;

    private String reason;
    private String tip;

    private PermissionManager() {
        reason = "";
        tip = "";
    }

    public PermissionManager setReason(@NonNull String reason) {
        this.reason = reason;
        return this;
    }

    public PermissionManager setTip(String tip) {
        this.tip = tip;
        return this;
    }

    /**
     * request the permission
     *
     * @param context    for launch request activity
     * @param permission what permission you want to request
     */
    public PermissionManager request(@NonNull Context context, String permission) {
        Intent intent = new Intent(context, PermissionCheckActivity.class);
        intent.putExtra(PARAM_RATIONALE, pmgr.reason);
        intent.putExtra(PARAM_TIP, pmgr.tip);
        intent.putExtra(PARAM_PERMISSIONS, permission);
        context.startActivity(intent);
        return this;
    }

    /**
     * request the permission is authorized or not
     *
     * @param context
     * @param permission
     * @return
     */
    private boolean check(Context context, String permission) {
        int hasPermission = ContextCompat.checkSelfPermission(context, permission);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void onResult(OnPermissionResult onPermissionResult) {
        this.onPermissionResult = onPermissionResult;
    }
}
