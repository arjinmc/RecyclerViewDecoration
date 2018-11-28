# RecyclerViewDecoration
a common tool class for decoration of RecyclerView,support ninepatch image.

[中文版](README_CN.md)

You can import this lib with gradle or maven because it exists on JCenter.

More expandable useful methods will be shown in [ExpandRecyclerView](https://github.com/arjinmc/ExpandRecyclerView) lib. <strong>This lib will be combine into ExpandRecyclerView lib</strong>.   
If you don't want to use ExpandRecyclerView lib, no worry, you can still use this lib.

#### gradle
```code
compile 'com.arjinmc.android:recyclerviewdecoration:2.6'
```
#### maven
```code
<dependency>
  <groupId>com.arjinmc.android</groupId>
  <artifactId>recyclerviewdecoration</artifactId>
  <version>2.6</version>
  <type>pom</type>
</dependency>
```

# Update News
<b>2018/11/27th</b>
add RecyclerViewStickyHeadItemDecoration, auto make group view type to sticky head mode.

<b>2018/4/4th</b>
* add a new attribute "ignoreType" to filter the viewTypes(int array) which are no need to draw ItemDecoration, meanwhile if the first child of ReyclerView is the ignore viewType,the "firstLineVisible" attributes won't work, the lastest child of ReyclerView with "lastLineVisible" attributes as well.

<b>2018/3/15th</b>
* remove StickyHeadItemDecoration.

<b>2017/12/20th</b>
* Upgrate algorithm,not including StickyHeadItemDecoration.

<b>2017/10/25th</b>
* add sticky head style that it can auto get your group view if you tell it the viewtype of group series.

It can completely auto compatible the group view that need to be shown on the top stickily.Only support this layout orientation -- LinearLayoutManager.VERTICAL.

<b>2017/9/8th</b>

* optimize draw and compatible LayoutManager.
* remove two attributes mode and parent.
* add RecyclerViewSpaceItemDecoration to build spacing diver.
* update sample.

It can completely auto compatible the orientation of LayoutManager.

<b>2017/7/3rd</b>

* optimize draw lines for grid mode.
* remove old methods that deprecated.
* add two new attributes gridHorizontalSpacing and gridVerticalSpacing for grid mode with pure or gap lines.

<b>2017/5/27th</b>

* add compatible method to compatible with support v7 LayoutManager.  

If you don't understand the orientation of this RecyclerViewItemDecoration common tool class,just use <b>builder.parent(recycelerView)</b> instead of builder.mode(orientaion), it can automatic compatible with the layoutmanager of RecyclerView.

<b>2017/5/23rd</b>

* add gridBottomVisible,gridTopVisible,gridLeftVisible,gridRightVisible params for MODE_GRID. You can control the borders' visibility of grid mode,it's up to you make them shown or hidden(default is hidden).
* optimizate algorithm.

<b>2017/4/15th</b>

* add Builder for RecyclerViewItemDecoration to create.
* add params paddingStart and paddingEnd for MODE_HORIZONTAL and MODE_VERTICAL.
* add params firstLineVisible and lastLineVisible for MODE_HORIZONTAL and MODE_VERTICAL.

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

## sample images

![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-12-02-111504.png)  
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155050.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-154937.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155157.png)

