# RecyclerViewDecoration
一个通用的RecyclerView分割线，支持.9图片.

你可以直接导入这个库，因为它已经在JCenter.

更多扩展用法将会在[ExpandRecyclerView](https://github.com/arjinmc/ExpandRecyclerView)这个库中体现，<strong>此库也会合并到ExpandRecyclerView库中</strong>。  
如果你不想使用ExpandRecyclerView库的内容，别担心，依然可以继续用这个库。

[更新日志](NEWS_CN.md)

#### gradle
```code
compile 'com.arjinmc.android:recyclerviewdecoration:3.1'
```
#### maven
```code
<dependency>
  <groupId>com.arjinmc.android</groupId>
  <artifactId>recyclerviewdecoration</artifactId>
  <version>3.1</version>
  <type>pom</type>
</dependency>
```

# RecyclerViewItemDecoration.Builder
thickness的值最好是偶数，2的倍数。

## Horizonal Mode（横向模式）
``` java

rvData.setLayoutManager(new LinearLayoutManager(context
  , LinearLayoutManager.VERTICAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(context)
        .color(Color.RED)
        .color("#ff0000")
        .dashWidth(8)
        .dashGap(5)
        .thickness(6)
        .drawableID(R.drawable.diver)
        .drawableID(R.drawable.diver_color_no)
        .paddingStart(20)
        .paddingEnd(10)
        .firstLineVisible(true)
        .lastLineVisible(true)
        .ignoreTypes(int[])
        .create());

```
## Vertical Mode（纵向模式）
``` java

rvData.setLayoutManager(new LinearLayoutManager(context
  , LinearLayoutManager.HORIZONTAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .color(Color.RED)
       .color("#ff0000")
       .dashWidth(8)
       .dashGap(5)
       .thickness(6)
       .drawableID(R.drawable.diver_vertical)
       .drawableID(R.drawable.diver_v)
       .paddingStart(20)
       .paddingEnd(10)
       .firstLineVisible(true)
       .lastLineVisible(true)
       .ignoreTypes(int[])
       .create());
```

## Grid Mode（网格模式）
``` java

rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .color(Color.RED)
       .color("#ff0000")
       .dashWidth(8)
       .dashGap(5)
       .thickness(6)
       .drawableID(R.drawable.diver_color_no)
       .gridBottomVisible(true) //控制下面边框
       .gridTopVisible(true) //控制上面边框
       .gridLeftVisible(true) //控制左边边框
       .gridRightVisible(true) //控制右边边框
       .create());

```

当你使用gridHorizontalSpacing 或者 gridVerticalSpacing 或者两个都使用时,如果你需要显示边框时thickness是必须的属性。目前而言，这两个属性还不支持图片和.9图片。下面是一个例子。

```java
rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .color("#ff0000")
       .dashWidth(8)
       .dashGap(5)
       .thickness(6)
       .gridBottomVisible(true) //控制下面边框
       .gridTopVisible(true) //控制上面边框
       .gridLeftVisible(true) //控制左边边框
       .gridRightVisible(true) //控制右边边框
       .gridHorizontalSpacing(20)
       .gridVerticalSpacing(10)
       .create());

```

## RecyclerViewSpaceItemDecoration

```java
rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewSpaceItemDecoration.Builder(this)
      //如果纵横间距是一样的，使用margin(int size)
      //如果RecyclerView是线性布局，使用margin(int size)
//      .margin(10)
        .marginHorizontal(10)
        .marginVertical(20)
        .ignoreTypes(int[])
        .create());
```

## RecyclerViewStickyHeadItemDecoration
```code
//默认分组viewtpe是0，你可以修改成自己的。支持任意RecyclerView Adapter。
rvList.addItemDecoration(new RecyclerViewStickyHeadItemDecoration.Builder().groupViewType(0).create());
```
RecyclerViewStickyHeadItemDecoration参考自[https://github.com/chenpengfei88/StickyItemDecoration](https://github.com/chenpengfei88/StickyItemDecoration)

## 效果图
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-12-02-111504.png)  
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155050.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-154937.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155157.png)

