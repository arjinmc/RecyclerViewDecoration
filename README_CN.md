# RecyclerViewDecoration
一个通用的Recyclerview分割线，支持.9图片.

## 注意！
有人问我用了LinearLayout的方向属性对于RecyclerViewItemDecoration不起作用这个问题，是的，确实不起作用。因为这个RecyclerViewItemDecoration有自己的方向，就是绘画的方向，跟LinearLayout的方向是不一样的。

# 更新日志

<b>2017/7/3rd</b>

* 优化网格模式的画线方式
* 删除旧的过时方法
* 新增网格模式两个属性gridHorizontalSpacing 和 gridVerticalSpacing，可以画纯色线和虚线。

<b>2017/5/27th</b>

* 加入适配v7包的LayoutManager的支持.  

如果你搞不清楚RecyclerViewItemDecoration的画线防线，使用 <b>builder.parent(recycelerView)</b> 替代builder.mode(orientaion), 它可以自动适配RecyclerView的实际方向。

<b>2017/5/23rd</b>

* 网格模式新增属性gridBottomVisible,gridTopVisible,gridLeftVisible,gridRightVisible。你可以控制网格模式的边框是否显示，默认是隐藏。
* 优化算法。

<b>2017/4/15th</b>

* 新增Builder模式创建RecyclerViewItemDecoration。
* 纵/横模式新增paddingStart 和 paddingEnd。
* 纵/横模式新增firstLineVisible and lastLineVisible 控制第一行和最后一行是否显示分割线。

# RecyclerViewItemDecoration.Builder
thickness的值最好是偶数，2的倍数。

## Horizonal Mode（横向模式）
``` java

rvData.setLayoutManager(new LinearLayoutManager(context
  , LinearLayoutManager.VERTICAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(context)
        //默认是RecyclerViewItemDecoration.HORIZONTAL
        .mode(RecyclerViewItemDecoration.HORIZONTAL) //或者 parent(rvData)
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
        .create());

```
## Vertical Mode（纵向模式）
``` java

rvData.setLayoutManager(new LinearLayoutManager(context
  , LinearLayoutManager.HORIZONTAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .mode(RecyclerViewItemDecoration.MODE_VERTICAL) //或者 parent(rvData)
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
       .create());
```

## Grid Mode（网格模式）
``` java

rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .mode(RecyclerViewItemDecoration.MODE_GRID) //或者 parent(rvData)
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
       .mode(RecyclerViewItemDecoration.MODE_GRID) //或者 parent(rvData)
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

## 效果图
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-12-02-111504.png)  
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155050.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-154937.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155157.png)

