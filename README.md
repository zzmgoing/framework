# 使用JetPack框架搭建的基础库(MVVM)，用于快速构建项目

- kotlin-version: 1.3.72
- gradle-tools: 4.0.1
- compileSdkVersion: 30
- minSdkVersion: 16
- targetSdkVersion: 30

本库会依赖常用的第三方库，一般为项目基本会用到的，比如网络请求，权限，刷新加载等，尽量不会依赖多余的库。

依赖库：

//ViewModel KTX
api "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
//Lifecycle KTX
api "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
//LiveData KTX
api "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
//APP Startup
api "androidx.startup:startup-runtime:1.0.0-alpha01"
//retrofit
api 'com.squareup.retrofit2:retrofit:2.9.0'
api 'com.squareup.retrofit2:converter-gson:2.9.0'
api 'com.squareup.retrofit2:converter-scalars:2.9.0'
//Glide
api 'com.github.bumptech.glide:glide:4.11.0'
kapt 'com.github.bumptech.glide:compiler:4.11.0'
//BaseRecyclerViewAdapterHelper
api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4'
//smart:refresh-layout
api 'com.scwang.smart:refresh-layout-kernel:2.0.1'
api 'com.scwang.smart:refresh-header-classics:2.0.1'
//immersionbar
api 'com.gyf.immersionbar:immersionbar:3.0.0'
api 'com.gyf.immersionbar:immersionbar-components:3.0.0'
api 'com.gyf.immersionbar:immersionbar-ktx:3.0.0'
//permissionx
api 'com.permissionx.guolindev:permissionx:1.2.2'
//autosize
implementation 'me.jessyan:autosize:1.2.1'