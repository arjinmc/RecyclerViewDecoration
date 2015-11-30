package com.arjinmc.recyclerviewdecoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.regex.Pattern;

/**
 * RecycleView item decoration
 * Created by Eminem Lu on 24/11/15.
 * Email arjinmc@hotmail.com
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    /**mode for direction*/
    public static final int MODE_HORIZONTAL = 0;
    public static final int MODE_VERTICAL = 1;

    /**default decoration thick size */
    private final int DEFAULT_SIZE = 1;
    /**default decoration color*/
    private final String DEFAULT_COLOR = "#bdbdbd";

    /**image resource id for R.java*/
    private int drawableRid = 0;
    /**decoration color*/
    private int color = Color.parseColor(DEFAULT_COLOR);
    /**decoration thick size*/
    private int thick;
    /**decoration dash with*/
    private int dashWidth = 0;
    /**decoration dash gap*/
    private int dashGap = 0;
    /**direction mode for decoration*/
    private int recyclerviewMode;

    private Paint paint;

    private Bitmap bmp;
    private NinePatch ninePatch;
    /**sign for if the resource image is a ninepatch image*/
    private Boolean hasNinePatch = false;

    public RecyclerViewItemDecoration(int recyclerviewMode, Context context, int drawableRid){
        this.recyclerviewMode = recyclerviewMode;
        this.drawableRid = drawableRid;

        this.bmp = BitmapFactory.decodeResource(context.getResources(), drawableRid);
        if(bmp.getNinePatchChunk()!=null){
            hasNinePatch = true;
            ninePatch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
        }
        initPaint();

    }

    public RecyclerViewItemDecoration(int recyclerviewMode, int color, int thick, int dashWidth, int dashGap){
        this.recyclerviewMode = recyclerviewMode;
        this.color = color;
        this.thick = thick;
        this.dashWidth = dashWidth;
        this.dashGap = dashGap;

        initPaint();

    }

    public RecyclerViewItemDecoration(int recyclerviewMode, String color, int thick, int dashWidth, int dashGap){
        this.recyclerviewMode = recyclerviewMode;
        if(isColorString(color)){
            this.color = Color.parseColor(color);
        }else{
            this.color = Color.parseColor(DEFAULT_COLOR);
        }
        this.thick = thick;
        this.dashWidth = dashWidth;
        this.dashGap = dashGap;

        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thick);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        paint.setColor(color);
        if(recyclerviewMode == MODE_HORIZONTAL){
            drawHorinzonal(c,parent);
        }else if(recyclerviewMode == MODE_VERTICAL){
            drawVertical(c,parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildLayoutPosition(view)!=parent.getAdapter().getItemCount()-1){
            if(recyclerviewMode == MODE_HORIZONTAL){
                if(drawableRid!=0){
                    outRect.set(0,0,0,bmp.getHeight());
                }else{
                    outRect.set(0,0,0,thick);
                }

            }else if(recyclerviewMode == MODE_VERTICAL){
                if(drawableRid!=0){
                    outRect.set(0,0,bmp.getWidth(),0);
                }else{
                    outRect.set(0,0,thick,0);
                }

            }
        }

    }

    /**
     * judge is a color string like #xxxxxx or #xxxxxxxx
     * @param colorStr
     * @return
     */
    private boolean isColorString(String colorStr){
        return Pattern.matches("^#([0-9a-fA-F]{6}||[0-9a-fA-F]{8})$",colorStr);
    }

    /**
     * draw horizonal decoration
     * @param c
     * @param parent
     */
    private void drawHorinzonal(Canvas c,RecyclerView parent){
        int childrentCount = parent.getChildCount();

        if(drawableRid!=0){
            if(hasNinePatch) {
                for(int i=0;i<childrentCount;i++) {
                    if (i != childrentCount - 1) {
                        View childView = parent.getChildAt(i);
                        int myY = childView.getBottom();

                        Rect rect = new Rect(0, myY, parent.getWidth(), myY+bmp.getHeight());
                        ninePatch.draw(c, rect);
                    }
                }
            }else {
                for(int i=0;i<childrentCount;i++) {
                    if (i != childrentCount - 1) {
                        View childView = parent.getChildAt(i);
                        int myY = childView.getBottom();

                        c.drawBitmap(bmp,0,myY,paint);
                    }
                }
            }

        }else if(dashWidth ==0 && dashGap==0){
            for(int i=0;i<childrentCount;i++) {
                if (i != childrentCount - 1) {
                    View childView = parent.getChildAt(i);
                    int myY = childView.getBottom() + thick / 2;
                    c.drawLine(0,myY,parent.getWidth(),myY,paint);
                }
            }
        }else{
            PathEffect effects = new DashPathEffect(new float[]{0,0,dashWidth,thick},dashGap);
            paint.setPathEffect(effects);

            for(int i=0;i<childrentCount;i++) {
                if (i != childrentCount - 1) {
                    View childView = parent.getChildAt(i);
                    int myY = childView.getBottom() + thick / 2;

                    Path path = new Path();
                    path.moveTo(0, myY);
                    path.lineTo(parent.getWidth(),myY);
                    c.drawPath(path, paint);
                }

            }
        }
    }

    /**
     * draw vertival decoration
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c,RecyclerView parent){
        int childrentCount = parent.getChildCount();
        if(drawableRid!=0){
            if(hasNinePatch){
                for(int i=0;i<childrentCount;i++) {
                    if (i != childrentCount-1) {
                        View childView = parent.getChildAt(i);
                        int myX = childView.getRight();
                        Rect rect = new Rect(myX, 0, myX+bmp.getWidth(), parent.getHeight());
                        ninePatch.draw(c, rect);
                    }
                }
            }else{

                for(int i=0;i<childrentCount;i++) {
                    if (i != childrentCount-1) {
                        View childView = parent.getChildAt(i);
                        int myX = childView.getRight();
                        c.drawBitmap(bmp,myX,0,paint);

                    }
                }
            }
        } else if(dashWidth ==0 && dashGap==0){

            for(int i=0;i<childrentCount;i++) {
                if (i != childrentCount-1) {
                    View childView = parent.getChildAt(i);
                    int myX = childView.getRight()+thick/2;
                    c.drawLine(myX,0,myX,parent.getHeight(),paint);
                }
            }


        }else{
            PathEffect effects = new DashPathEffect(new float[]{0,0,dashWidth,thick},dashGap);
            paint.setPathEffect(effects);
            for(int i=0;i<childrentCount;i++) {
                if (i != childrentCount-1) {
                    View childView = parent.getChildAt(i);
                    int myX = childView.getRight()+thick/2;

                    Path path = new Path();
                    path.moveTo(myX, 0);
                    path.lineTo(myX,parent.getHeight());
                    c.drawPath(path, paint);
                }
            }
        }
    }

}
