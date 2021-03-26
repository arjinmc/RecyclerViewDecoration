# RecyclerViewDecoration
一个通用的RecyclerView分割线，支持.9图片.

你可以直接导入这个库，因为它已经在JCenter.

更多扩展用法将会在[ExpandRecyclerView](https://github.com/arjinmc/ExpandRecyclerView)这个库中体现，<strong>此库也会合并到ExpandRecyclerView库中</strong>。  
如果你不想使用ExpandRecyclerView库的内容，别担心，依然可以继续用这个库。

[更新日志](NEWS_CN.md)

文档在这里 [Wiki](https://github.com/arjinmc/RecyclerViewDecoration/wiki)  

## 引用

### JitPack
```code 
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
     implementation 'com.github.arjinmc:RecyclerViewDecoration:4.1.1'
}
```

#### gradle
```code
compile 'com.arjinmc.android:recyclerviewdecoration:4.1.1'
```
#### maven
```code
<dependency>
  <groupId>com.arjinmc.android</groupId>
  <artifactId>recyclerviewdecoration</artifactId>
  <version>4.1.1</version>
  <type>pom</type>
</dependency>
```

## 功能

全部都用Builder模式创建。

* RecyclerViewLinearItemDecoration 线性分割线
* RecyclerViewLinearSpaceItemDecoration 线性空白分割线
* RecyclerViewGridItemDecoration 网格分割线
* RecyclerViewGridSpaceItemDecoration 网格空白分割线
* RecyclerViewStickyHeadItemDecoration 黏体header分割线，参考自 [https://github.com/chenpengfei88/StickyItemDecoration](https://github.com/chenpengfei88/StickyItemDecoration)

## 效果图
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-12-02-111504.png)  
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155050.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-154937.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155157.png)

