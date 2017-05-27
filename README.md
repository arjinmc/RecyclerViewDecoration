# RecyclerViewDecoration
a common tool class for decoration of RecyclerView,support ninepatch image.

## Attention please!
Some people ask me for RecyclerViewDecoration doesnot work for the orientation attributes of LinearLayout. No,it doesn't. Because RecyclerViewDecoration has its own orientation attributes as itself drawn orientation, not the same as orientation attributes of LinearLayout.

![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-12-02-111504.png)  

# Update News
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
        //default mode is RecyclerViewItemDecoration.HORIZONTAL
        .mode(RecyclerViewItemDecoration.HORIZONTAL) //or parent(rvData)
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
## Vertical Mode
``` java

rvData.setLayoutManager(new LinearLayoutManager(context
  , LinearLayoutManager.HORIZONTAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .mode(RecyclerViewItemDecoration.MODE_VERTICAL) //or parent(rvData)
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

## Grid Mode
``` java

rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
       .mode(RecyclerViewItemDecoration.MODE_GRID) //or parent(rvData)
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

![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155050.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-154937.png)
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155157.png)

# Old Methods Deprecated
I don't suggest you guys use old methods.
## Horizonal Mode
1.pure line
``` java
rvData.setLayoutManager(new LinearLayoutManager(this
  , LinearLayoutManager.VERTICAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration(
  RecyclerViewItemDecoration.MODE_HORIZONTAL, Color.DKGRAY,5,0,0));
  //or
  //RecyclerViewItemDecoration.MODE_HORIZONTAL, "#ff0000",5,0,0));
``` 
2.pure line with gap
``` java
rvData.setLayoutManager(new LinearLayoutManager(this
  , LinearLayoutManager.VERTICAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration(
  RecyclerViewItemDecoration.MODE_HORIZONTAL, Color.DKGRAY,5,20,10));
```
3.use image resource(including .9.png)
``` java 
rvData.setLayoutManager(new LinearLayoutManager(this
  , LinearLayoutManager.VERTICAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration(
  RecyclerViewItemDecoration.MODE_HORIZONTAL,this, R.drawable.diver));
``` 

## Vertical Mode
the same as the Horizonal Mode
``` java
rvData.setLayoutManager(new LinearLayoutManager(this
  , LinearLayoutManager.HORIZONTAL,false));
rvData.addItemDecoration(new RecyclerViewItemDecoration(
  RecyclerViewItemDecoration.MODE_VERTICAL,this, R.drawable.diver_vertical));
``` 

## Grid Mode
smililar to other modes
``` java
rvData.setLayoutManager(new GridLayoutManager(this, 6));
rvData.addItemDecoration(new RecyclerViewItemDecoration(
  RecyclerViewItemDecoration.MODE_GRID, Color.RED,10,20,10));
//rvData.addItemDecoration(new RecyclerViewItemDecoration(
//RecyclerViewItemDecoration.MODE_GRID,this, R.drawable.diver_color));
``` 

 

