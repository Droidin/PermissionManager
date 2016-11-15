# PermissionManager

### A simple library for Android application to mange runtime permission

> minSdkVersion 14

#### And how to use ?

Like this ,at where you want to request permission

```java
 PermissionManager
    //get instance of PermissionManager 
    .get()
    //about why you need it
    .setReason("为了拍摄照片，需要使用您设备上的的摄像头")
    //set a tip sentence on Didalog
    .setTip("您可以前往“设置-权限管理“页面配置相关权限")
    //set callback for request
    .onResult(new OnPermissionResult() {
        @Override
        public void onPermissionRequestResult(String permission, int result) {
            Toast.makeText(getApplicationContext()
                    , result == PackageManager.PERMISSION_GRANTED ? "Granted" : "Denied"
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    })
    //set up what permission you want to request
    .request(this, Manifest.permission.CAMERA);
```