package com.droidin.permissionmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.droidin.pmlibrary.OnPermissionResult;
import com.droidin.pmlibrary.PermissionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button test_btn_camera = (Button) findViewById(R.id.test_btn_camera);
        test_btn_camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_btn_camera: {
                PermissionManager.get()
                        .setReason("为了拍摄照片，需要使用您设备上的的摄像头")
                        .setTip("您可以前往“设置-权限管理“页面配置相关权限")
                        .check(this, Manifest.permission.CAMERA)
                        .onResult(new OnPermissionResult() {
                            @Override
                            public void onResult(String permission, int result) {

                            }
                        });
                break;
            }
        }
    }
}