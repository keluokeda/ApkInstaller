package com.ke.apkinstaller;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.File;

public class ApkInstallReceiver extends BroadcastReceiver {


    public static long downLoadId;

    @Override
    public void onReceive(Context context, Intent intent) {
        //下载成功收到的广播
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadApkId == downLoadId) {
                installApk(context, downloadApkId);
            }
        }
    }


    /**
     * 安装apk
     *
     * @param context       上下文
     * @param downloadApkId 下载的id
     */
    private void installApk(@NonNull Context context, long downloadApkId) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager == null) {
            return;
        }
        Uri downloadFileUri = getDownloadFileUri(downloadManager, downloadApkId);
        if (downloadFileUri != null) {
            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //7.0 add
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            try {
                context.startActivity(install);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 获取下载文件的uri,6.0以下返回的是file:///格式，不用做处理。6.0及以上返回的是content:///，只有6.0版本需要做处理
     *
     * @param downloadManager 下载管理器
     * @param downLoadId      下载id
     * @return apk的uri
     */
    @Nullable
    private Uri getDownloadFileUri(@NonNull DownloadManager downloadManager, long downLoadId) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            File file = getApkFile(downLoadId, downloadManager);
            if (file != null) {
                return Uri.fromFile(file);
            }
        } else {
            return downloadManager.getUriForDownloadedFile(downLoadId);
        }

        return null;
    }




    @Nullable
    private File getApkFile(long downloadId, DownloadManager downloadManager) {
        File targetApkFile = null;
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloadManager.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }



}
