# PermissionManager

### A simple library for Android application to mange runtime permission

> minSdkVersion 14

#### And how to use ?

Like this ,at where you want to request permission

```java
 PermissionManager
      //get instance of PermissionManager and  add a callback for request
     .getInstance(
     new OnPermissionResult() {
         @Override
         public void onPermissionRequestResult(String permission, int result) {
             Toast.makeText(getApplicationContext()
                     , result == PackageManager.PERMISSION_GRANTED ? "Granted" : "Denied"
                     , Toast.LENGTH_SHORT)
                     .show();
         }
     })
     //to describe why you need this permission
     .setReason("为了拍摄照片，需要使用您设备上的的摄像头")
     //a tip to tell user how or where change the authorization
     .setTip("您可以前往“设置-权限管理“页面配置相关权限")
     //set up what permission you want to request
     .request(this, Manifest.permission.CAMERA);
```