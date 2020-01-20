# 更新日志
<b>v4.1 2019/12/27</b>
1. 修复 RecyclerViewStickyHeadItemDecoration bug. 
2. RecyclerViewLinearSpaceItemDecoration.Builder 加入 ignoreTypes.

<b>v4.0 2019/10/23</b>
* RecyclerViewItemDecoration 变成 RecyclerViewLinearItemDecoration 和 RecyclerViewGridItemDecoration (不支持带透明通道的颜色值).  
* RecyclerViewSpaceDecoration 变成 RecyclerViewLinearSpaceItemDecoration 和 RecyclerViewGridSpaceItemDecoration  ( 支持 StaggeredGridLayoutManager) .

<b>v3.1 2019/9/20</b>
修复适配LinearLayoutManger模式的RecyclerView内边距padding。

<b>v3.0 2019/7/2nd</b>
转成androidx libs。

<b>2018/11/27th</b>
加入RecyclerViewStickyHeadItemDecoration, 自动将分组viewtype改成sticky head模式。

<b>2018/4/4th</b>
* 给LinearLayoutManager布局模式加入新属性“ignoreType”来过滤不需要画分割线的viewtype类型（int数组)，同时，如果是RecyclerView的第一个child是属于忽略的viewtype类型，那么firstLineVisible属性会不起作用，同样，如果ReycyclerView的最后一个child是属于忽略的viewtype类型，那么lastLineVisible属性也不会起作用。

<b>2018/3/15th</b>
* 移除StickyHeadItemDecoration。

<b>2017/12/20th</b>
* 更新算法，不包括StickyHeadItemDecoration。

<b>2017/10/25th</b>
* 加入固定头部样式，可以自动获取group作为header，你只需要告诉它你的分组的viewtype类型。

它可以自动匹配的group的内容作为固定的header。只支持这种布局LinearLayoutManager.VERTICAL。

<b>2017/9/8th</b>

* 优化绘制和适配LayoutManager。
* 删除两个属性mode和parent。
* 新增RecyclerViewSpaceItemDecoration用来创建空白的分割线。
* 更新示例代码。

现在可以完全自动适配LayoutManager的方向。

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
* 纵/横模式新增firstLineVisible 和 lastLineVisible 控制第一行和最后一行是否显示分割线。