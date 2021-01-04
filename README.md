# ApkInstaller
#### 1.引入
1. 在你的项目级的build.gradle中添加
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2. 添加依赖
```
implementation 'com.github.keluokeda:ApkInstaller:1.0.0'
```

#### 2.使用
```
 ApkInstaller().start(applicationContext,
                       "https://imtt.dd.qq.com/16891/apk/27C906E3454BF479B0C8E2A44B7366D7.apk?fsname=com.tencent.mobileqq_8.4.18_1558.apk&csr=1bbd",
                       "4_2_7.apk",
                       "正在下载更新文件")
```
