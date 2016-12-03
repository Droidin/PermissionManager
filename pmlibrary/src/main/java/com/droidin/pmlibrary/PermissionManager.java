package com.droidin.pmlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Created by bowen on 2016-11-03 0003.
 */
public class PermissionManager {

    static final String PARAM_RATIONALE = "reason";
    static final String PARAM_TIP = "tip";
    static final String PARAM_PERMISSIONS = "permissions";

    private String reason;
    private String tip;
    OnPermissionResult onPermissionResult = null;

    private static PermissionManager pmgr;

    /**
     * getInstance the instance and set up a callback
     *
     * @param onPermissionResult callback if null please call
     *                           {@link PermissionManager#onResult(OnPermissionResult)} to setup callback
     *                           before {@link PermissionManager#request(Context, String)} called
     */
    public static PermissionManager getInstance(OnPermissionResult onPermissionResult) {
        if (pmgr == null) {
            pmgr = new PermissionManager();
        }
        pmgr.onResult(onPermissionResult);
        return pmgr;
    }

    /**
     * getInstance the instance
     * please call
     * {@link PermissionManager#onResult(OnPermissionResult)} to set up callback
     * before {@link PermissionManager#request(Context, String)} called
     */
    private PermissionManager() {
        reason = "";
        tip = "";
    }

    /**
     * tell user why you need the permission
     *
     * @param reason
     * @return
     */
    public PermissionManager setReason(@NonNull String reason) {
        this.reason = reason;
        return this;
    }

    /**
     * a tip to tell user how or where change the authorization
     *
     * @param tip
     * @return
     */
    public PermissionManager setTip(String tip) {
        this.tip = tip;
        return this;
    }

    /**
     * call this method to getInstance callback
     * before {@link PermissionManager#request(Context, String)} called
     *
     * @param onPermissionResult callback
     */
    public PermissionManager onResult(OnPermissionResult onPermissionResult) {
        if (onPermissionResult != null) {
            this.onPermissionResult = onPermissionResult;
        }
        return this;
    }

    /**
     * do the request
     *
     * @param context    for launch request activity
     * @param permission the permission you want to request
     */
    public void request(@NonNull Context context, String permission) {
        if (check(context, permission)) {
            if (onPermissionResult != null) {
                onPermissionResult.onPermissionRequestResult(permission, PackageManager.PERMISSION_GRANTED);
            }
        } else {
            Intent intent = new Intent(context, PermissionCheckActivity.class);
            intent.putExtra(PARAM_RATIONALE, pmgr.reason);
            intent.putExtra(PARAM_TIP, pmgr.tip);
            intent.putExtra(PARAM_PERMISSIONS, permission);
            context.startActivity(intent);
        }
    }

    /**
     * A method to
     * check the permission is authorized or not
     *
     * @param context    Context
     * @param permission the permission you want to check
     * @return
     */
    public static boolean check(Context context, String permission) {
        int hasPermission = ContextCompat.checkSelfPermission(context, permission);
        return hasPermission == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}
