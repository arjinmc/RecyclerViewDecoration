# RecyclerViewDecoration
a common tool class for decoration of RecyclerView,support ninepatch image.

![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-12-02-111504.png)  
 
## Horizonal Mode
1.pure line
``` java
    rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    rvData.addItemDecoration(new RecyclerViewItemDecoration(
      RecyclerViewItemDecoration.MODE_HORIZONTAL, Color.DKGRAY,5,0,0));
      //or
      //RecyclerViewItemDecoration.MODE_HORIZONTAL, "#ff0000",5,0,0));
``` 
2.pure line with gap
``` java
    rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    rvData.addItemDecoration(new RecyclerViewItemDecoration(
      RecyclerViewItemDecoration.MODE_HORIZONTAL, Color.DKGRAY,5,20,10));
```
3.use image resource(including .9.png)
``` java 
    rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    rvData.addItemDecoration(new RecyclerViewItemDecoration(
      RecyclerViewItemDecoration.MODE_HORIZONTAL,this, R.drawable.diver));
``` 

## Vertical Mode
the same as the Horizonal Mode
``` java
    rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
    rvData.addItemDecoration(new RecyclerViewItemDecoration(
      RecyclerViewItemDecoration.MODE_VERTICAL,this, R.drawable.diver_vertical));
``` 

## Grid Mode
smililar to other modes
``` java
    rvData.setLayoutManager(new GridLayoutManager(this, 6));
    rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.RED,10,20,10));
//  rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID,this, R.drawable.diver_color));
``` 

 
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155050.png) 
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-154937.png)  
![image](https://github.com/arjinmc/RecyclerViewDecoration/blob/master/images/device-2015-11-30-155157.png)  
