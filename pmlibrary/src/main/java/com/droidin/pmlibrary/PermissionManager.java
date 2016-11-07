package com.droidin.pmlibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

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
     * check the permission
     *
     * @param context    for launch check activity
     * @param permission what permission you want to check
     */
    public PermissionManager check(@NonNull Context context, String permission) {
        Intent intent = new Intent(context, PermissionCheckActivity.class);
        intent.putExtra(PARAM_RATIONALE, pmgr.reason);
        intent.putExtra(PARAM_TIP, pmgr.tip);
        intent.putExtra(PARAM_PERMISSIONS, permission);
        context.startActivity(intent);
        return this;
    }

    public void onResult(OnPermissionResult onPermissionResult) {
        this.onPermissionResult = onPermissionResult;
    }
}
