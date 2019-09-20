# RecyclerViewDecoration
a common tool class for decoration of RecyclerView,support ninepatch image.

[中文版](README_CN.md)
[Release Note](NEWS.md)

You can import this lib with gradle or maven because it exists on JCenter.

More expandable useful methods will be shown in [ExpandRecyclerView](https://github.com/arjinmc/ExpandRecyclerView) lib. <strong>This lib will be combine into ExpandRecyclerView lib</strong>.   
If you don't want to use ExpandRecyclerView lib, no worry, you can still use this lib.

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
Param thickness would be better when it's an even number which can be divided by 2.
## Horizonal Mode
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
## Vertical Mode
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

## Grid Mode
``` java

rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .color(Color.RED)
       .color("#ff0000")
       .dashWidth(8)
       .dashGap(5)
       .thickness(6)
       .drawableID(R.drawable.diver_color_no)
       .gridBottomVisible(true) //control bottom border
       .gridTopVisible(true) //control top border
       .gridLeftVisible(true) //control left border
       .gridRightVisible(true) //control right border
       .create());

```
When you use gridHorizontalSpacing or gridVerticalSpacing or both,thickness is required especially if need to show borders.Currently these two new attributes donot support image or ninepatch.There is a sample.
```java
rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .color("#ff0000")
       .dashWidth(8)
       .dashGap(5)
       .thickness(6)
       .gridBottomVisible(true) //control bottom border
       .gridTopVisible(true) //control top border
       .gridLeftVisible(true) //control left border
       .gridRightVisible(true) //control right border
       .gridHorizontalSpacing(20)
       .gridVerticalSpacing(10)
       .create());

```

## RecyclerViewSpaceItemDecoration

```java
rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewSpaceItemDecoration.Builder(this)
      //if horizontal and vertical spacing is the same size,just use margin(int size)
      //if recyclerview is LinearLayout,use margin(int size)
//      .margin(10)
        .marginHorizontal(10)
        .marginVertical(20)
        .ignoreTypes(int[])
        .create());
```

## RecyclerViewStickyHeadItemDecoration
```code
//Default group view type is zero, you can change it. Support any RecyclerView Adapter.
rvList.addItemDecoration(new RecyclerViewStickyHeadItemDecoration.Builder().groupViewType(0).create());
```
RecyclerViewStickyHeadItemDecoration reference to [https://github.com/chenpengfei88/StickyItemDecoration](https://github.com/chenpengfei88/StickyItemDecoration)

## sample images

![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-12-02-111504.png)  
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155050.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-154937.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155157.png)

