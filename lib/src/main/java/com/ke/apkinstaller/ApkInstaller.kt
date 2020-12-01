package com.ke.apkinstaller

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment

class ApkInstaller() {



    fun start(context: Context, apkUrl: String, apkFileName: String, title: String) {
        val downloadManager =
            context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager? ?: return
        val req = DownloadManager.Request(Uri.parse(apkUrl))
        req.setAllowedOverRoaming(false) //加这个，防止在8.0上没反应


        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
//        req.setAllowedOverRoaming(true);

        //        req.setAllowedOverRoaming(true);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

        //设置文件的保存的位置[三种方式]
        //第一种
        //file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk

        //设置文件的保存的位置[三种方式]
        //第一种
        //file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkFileName)
        //第二种
        //file:///storage/emulated/0/Download/update.apk
//        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第三种 自定义文件路径
//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"update.apk");
//        if (file.exists()){
//            file.delete();
//        }
//
//        req.setDestinationUri(Uri.fromFile(file));


        //第二种
        //file:///storage/emulated/0/Download/update.apk
//        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第三种 自定义文件路径
//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"update.apk");
//        if (file.exists()){
//            file.delete();
//        }
//
//        req.setDestinationUri(Uri.fromFile(file));
        req.setTitle(title)
        req.setMimeType("application/vnd.android.package-archive")


        val downLoadId = downloadManager.enqueue(req)
        ApkInstallReceiver.downLoadId = downLoadId
    }
}