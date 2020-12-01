package com.ke.apkinstaller.demo

import android.Manifest
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ke.apkinstaller.ApkInstaller
import com.tbruyelle.rxpermissions2.RxPermissions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        findViewById<Button>(R.id.download).setOnClickListener {

            RxPermissions(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe {
                        if (it) {
                            ApkInstaller().start(applicationContext,
                                    "https://imtt.dd.qq.com/16891/apk/27C906E3454BF479B0C8E2A44B7366D7.apk?fsname=com.tencent.mobileqq_8.4.18_1558.apk&csr=1bbd",
                                    "4_2_7.apk",
                                    "正在下载更新文件")
                        }
                    }


        }
    }
}