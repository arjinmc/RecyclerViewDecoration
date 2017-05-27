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
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

/**
 * RecycleView item decoration
 * Created by Eminem Lu on 24/11/15.
 * Email arjinmc@hotmail.com
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * mode for direction
     * draw the itemdrecoration orientation
     */
    public static final int MODE_HORIZONTAL = 0;
    public static final int MODE_VERTICAL = 1;
    public static final int MODE_GRID = 2;

    /**
     * default decoration color
     */
    private static final String DEFAULT_COLOR = "#bdbdbd";

    /**
     * image resource id for R.java
     */
    private int mDrawableRid = 0;
    /**
     * decoration color
     */
    private int mColor = Color.parseColor(DEFAULT_COLOR);
    /**
     * decoration thickness
     */
    private int mThickness;
    /**
     * decoration dash with
     */
    private int mDashWidth = 0;
    /**
     * decoration dash gap
     */
    private int mDashGap = 0;
    private boolean mFirstLineVisible;
    private boolean mLastLineVisible;
    private int mPaddingStart = 0;
    private int mPaddingEnd = 0;
    /**
     * border line for grid mode
     */
    private boolean mGridLeftVisible;
    private boolean mGridRightVisible;
    private boolean mGridTopVisible;
    private boolean mGridBottomVisible;
    /**
     * direction mode for decoration
     */
    private int mMode;
    private RecyclerView mParent;

    private Paint mPaint;

    private Bitmap mBmp;
    private NinePatch mNinePatch;
    /**
     * choose the real thickness for image or thickness
     */
    private int mCurrentThickness;
    /**
     * sign for if the resource image is a ninepatch image
     */
    private Boolean hasNinePatch = false;

    public RecyclerViewItemDecoration() {
    }

    @Deprecated
    public RecyclerViewItemDecoration(int recyclerviewMode, Context context, int drawableRid) {
        this.mMode = recyclerviewMode;
        this.mDrawableRid = drawableRid;

        this.mBmp = BitmapFactory.decodeResource(context.getResources(), drawableRid);
        if (mBmp.getNinePatchChunk() != null) {
            hasNinePatch = true;
            mNinePatch = new NinePatch(mBmp, mBmp.getNinePatchChunk(), null);
        }
        initPaint();

    }

    @Deprecated
    public RecyclerViewItemDecoration(int recyclerviewMode, int color, int thick, int dashWidth, int dashGap) {
        this.mMode = recyclerviewMode;
        this.mColor = color;
        this.mThickness = thick;
        this.mDashWidth = dashWidth;
        this.mDashGap = dashGap;

        initPaint();

    }

    @Deprecated
    public RecyclerViewItemDecoration(int recyclerviewMode, String color, int thick, int dashWidth, int dashGap) {
        this.mMode = recyclerviewMode;
        if (isColorString(color)) {
            this.mColor = Color.parseColor(color);
        } else {
            this.mColor = Color.parseColor(DEFAULT_COLOR);
        }
        this.mThickness = thick;
        this.mDashWidth = dashWidth;
        this.mDashGap = dashGap;

        initPaint();
    }

    public void setParams(Context context, Param params) {

        this.mMode = params.mode;
        this.mDrawableRid = params.drawableRid;
        this.mColor = params.color;
        this.mThickness = params.thickness;
        this.mDashGap = params.dashGap;
        this.mDashWidth = params.dashWidth;
        this.mPaddingStart = params.paddingStart;
        this.mPaddingEnd = params.paddingEnd;
        this.mFirstLineVisible = params.firstLineVisible;
        this.mLastLineVisible = params.lastLineVisible;
        this.mGridLeftVisible = params.gridLeftVisible;
        this.mGridRightVisible = params.gridRightVisible;
        this.mGridTopVisible = params.gridTopVisible;
        this.mGridBottomVisible = params.gridBottomVisible;
        this.mParent = params.parent;

        if (mParent != null) compatibleWithLayoutManager(mParent);

        this.mBmp = BitmapFactory.decodeResource(context.getResources(), mDrawableRid);
        if (mBmp != null) {

            if (mBmp.getNinePatchChunk() != null) {
                hasNinePatch = true;
                mNinePatch = new NinePatch(mBmp, mBmp.getNinePatchChunk(), null);
            }

            if (mMode == MODE_HORIZONTAL)
                mCurrentThickness = mThickness == 0 ? mBmp.getHeight() : mThickness;
            if (mMode == MODE_VERTICAL)
                mCurrentThickness = mThickness == 0 ? mBmp.getWidth() : mThickness;
        }

        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mThickness);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        mPaint.setColor(mColor);
        if (mMode == MODE_HORIZONTAL) {
            drawHorinzonal(c, parent);
        } else if (mMode == MODE_VERTICAL) {
            drawVertical(c, parent);
        } else if (mMode == MODE_GRID) {
            drawGrid(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int viewPosition = parent.getChildLayoutPosition(view);

        if (mMode == MODE_HORIZONTAL) {

            if (!(!mLastLineVisible &&
                    viewPosition == parent.getAdapter().getItemCount() - 1)) {
                if (mDrawableRid != 0) {
                    outRect.set(0, 0, 0, mCurrentThickness);
                } else {
                    outRect.set(0, 0, 0, mThickness);
                }
            }

            if (mFirstLineVisible && viewPosition == 0) {
                if (mDrawableRid != 0) {
                    outRect.set(0, mCurrentThickness, 0, mCurrentThickness);
                } else {
                    outRect.set(0, mThickness, 0, mThickness);
                }
            }

        } else if (mMode == MODE_VERTICAL) {
            if (!(!mLastLineVisible &&
                    viewPosition == parent.getAdapter().getItemCount() - 1)) {
                if (mDrawableRid != 0) {
                    outRect.set(0, 0, mCurrentThickness, 0);
                } else {
                    outRect.set(0, 0, mThickness, 0);
                }
            }
            if (mFirstLineVisible && viewPosition == 0) {
                if (mDrawableRid != 0) {
                    outRect.set(mCurrentThickness, 0, mCurrentThickness, 0);
                } else {
                    outRect.set(mThickness, 0, mThickness, 0);
                }
            }

        } else if (mMode == MODE_GRID) {
            int columnSize = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            int itemSize = parent.getAdapter().getItemCount();

            if (mDrawableRid != 0) {
                setGridOffsets(outRect, viewPosition, columnSize, itemSize, 0);
            } else {
                setGridOffsets(outRect, viewPosition, columnSize, itemSize, 1);
            }
        }

    }

    /**
     * judge is a color string like #xxxxxx or #xxxxxxxx
     *
     * @param colorStr
     * @return
     */
    public static boolean isColorString(String colorStr) {
        return Pattern.matches("^#([0-9a-fA-F]{6}||[0-9a-fA-F]{8})$", colorStr);
    }

    private boolean isPureLine() {
        if (mDashGap == 0 && mDashWidth == 0)
            return true;
        return false;
    }

    /**
     * draw horizonal decoration
     *
     * @param c
     * @param parent
     */
    private void drawHorinzonal(Canvas c, RecyclerView parent) {
        int childrentCount = parent.getChildCount();

        if (mDrawableRid != 0) {

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);
                int myY = childView.getTop();

                if (hasNinePatch) {
                    Rect rect = new Rect(mPaddingStart, myY - mCurrentThickness
                            , parent.getWidth() - mPaddingEnd, myY);
                    mNinePatch.draw(c, rect);
                } else {
                    c.drawBitmap(mBmp, mPaddingStart, myY - mCurrentThickness, mPaint);
                }
            }

            for (int i = 0; i < childrentCount; i++) {
                if (!mLastLineVisible && i == childrentCount - 1)
                    break;
                View childView = parent.getChildAt(i);
                int myY = childView.getBottom();

                if (hasNinePatch) {
                    Rect rect = new Rect(mPaddingStart, myY
                            , parent.getWidth() - mPaddingEnd, myY + mCurrentThickness);
                    mNinePatch.draw(c, rect);
                } else {
                    c.drawBitmap(mBmp, mPaddingStart, myY, mPaint);
                }

            }

        } else {

            boolean isPureLine = isPureLine();
            if (!isPureLine) {
                PathEffect effects = new DashPathEffect(new float[]{0, 0, mDashWidth, mThickness}, mDashGap);
                mPaint.setPathEffect(effects);
            }

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);
                int myY = childView.getTop() - mThickness / 2;

                if (isPureLine) {
                    c.drawLine(mPaddingStart, myY, parent.getWidth() - mPaddingEnd, myY, mPaint);
                } else {
                    Path path = new Path();
                    path.moveTo(mPaddingStart, myY);
                    path.lineTo(parent.getWidth() - mPaddingEnd, myY);
                    c.drawPath(path, mPaint);
                }
            }

            for (int i = 0; i < childrentCount; i++) {
                if (!mLastLineVisible && i == childrentCount - 1)
                    break;
                View childView = parent.getChildAt(i);
                int myY = childView.getBottom() + mThickness / 2;

                if (isPureLine) {
                    c.drawLine(mPaddingStart, myY, parent.getWidth() - mPaddingEnd, myY, mPaint);
                } else {
                    Path path = new Path();
                    path.moveTo(mPaddingStart, myY);
                    path.lineTo(parent.getWidth() - mPaddingEnd, myY);
                    c.drawPath(path, mPaint);
                }

            }

        }
    }

    /**
     * draw vertival decoration
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childrentCount = parent.getChildCount();
        if (mDrawableRid != 0) {

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);
                int myX = childView.getLeft();
                if (hasNinePatch) {
                    Rect rect = new Rect(myX - mCurrentThickness, mPaddingStart
                            , myX, parent.getHeight() - mPaddingEnd);
                    mNinePatch.draw(c, rect);
                } else {
                    c.drawBitmap(mBmp, myX - mCurrentThickness, mPaddingStart, mPaint);
                }
            }
            for (int i = 0; i < childrentCount; i++) {
                if (!mLastLineVisible && i == childrentCount - 1)
                    break;
                View childView = parent.getChildAt(i);
                int myX = childView.getRight();
                if (hasNinePatch) {
                    Rect rect = new Rect(myX, mPaddingStart, myX + mCurrentThickness
                            , parent.getHeight() - mPaddingEnd);
                    mNinePatch.draw(c, rect);
                } else {
                    c.drawBitmap(mBmp, myX, mPaddingStart, mPaint);
                }
            }

        } else {

            boolean isPureLine = isPureLine();
            if (!isPureLine) {
                PathEffect effects = new DashPathEffect(new float[]{0, 0, mDashWidth, mThickness}, mDashGap);
                mPaint.setPathEffect(effects);
            }

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);
                int myX = childView.getLeft() - mThickness / 2;
                if (isPureLine) {
                    c.drawLine(myX, mPaddingStart, myX, parent.getHeight() - mPaddingEnd, mPaint);
                } else {
                    Path path = new Path();
                    path.moveTo(myX, mPaddingStart);
                    path.lineTo(myX, parent.getHeight() - mPaddingEnd);
                    c.drawPath(path, mPaint);
                }
            }

            for (int i = 0; i < childrentCount; i++) {
                if (!mLastLineVisible && i == childrentCount - 1)
                    break;
                View childView = parent.getChildAt(i);
                int myX = childView.getRight() + mThickness / 2;
                if (isPureLine) {
                    c.drawLine(myX, mPaddingStart, myX, parent.getHeight() - mPaddingEnd, mPaint);
                } else {
                    Path path = new Path();
                    path.moveTo(myX, mPaddingStart);
                    path.lineTo(myX, parent.getHeight() - mPaddingEnd);
                    c.drawPath(path, mPaint);
                }

            }
        }
    }

    /**
     * draw grid decoration
     *
     * @param c
     * @param parent
     */
    private void drawGrid(Canvas c, RecyclerView parent) {

        int childrentCount = parent.getChildCount();
        int columnSize = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int adapterChildrenCount = parent.getAdapter().getItemCount();

        if (mDrawableRid != 0) {
            if (hasNinePatch) {
                for (int i = 0; i < childrentCount; i++) {
                    View childView = parent.getChildAt(i);

                    //horizonal
                    if (mGridBottomVisible && isLastGridRow(i, adapterChildrenCount, columnSize)) {
                        Rect rect = new Rect(0
                                , childView.getBottom()
                                , childView.getRight() + mBmp.getHeight()
                                , childView.getBottom() + mBmp.getHeight());
                        mNinePatch.draw(c, rect);
                    }

                    if (mGridTopVisible && isFirstGridRow(i, columnSize)) {
                        Rect rect = new Rect(0
                                , 0
                                , childView.getRight() + mBmp.getWidth()
                                , childView.getBottom() + mBmp.getHeight());
                        mNinePatch.draw(c, rect);
                    } else if (!isLastGridRow(i, adapterChildrenCount, columnSize)) {
                        Rect rect = new Rect(
                                childView.getLeft()
                                , childView.getBottom()
                                , childView.getRight()
                                , childView.getBottom() + mBmp.getHeight());
                        mNinePatch.draw(c, rect);

                    }


                    //vertical
                    if (isLastGridRow(i, adapterChildrenCount, columnSize)
                            && !isLastGridColumn(i, childrentCount, columnSize)) {
                        Rect rect = new Rect(
                                childView.getRight()
                                , childView.getTop()
                                , childView.getRight() + mBmp.getWidth()
                                , childView.getBottom());
                        mNinePatch.draw(c, rect);
                    } else if (!isLastGridColumn(i, childrentCount, columnSize)) {
                        Rect rect = new Rect(
                                childView.getRight()
                                , childView.getTop()
                                , childView.getRight() + mBmp.getWidth()
                                , childView.getBottom() + mBmp.getHeight());
                        mNinePatch.draw(c, rect);
                    }

                    if (mGridLeftVisible && isFirstGridColumn(i, columnSize)) {
                        Rect rect = new Rect(
                                childView.getLeft() - mBmp.getWidth()
                                , childView.getTop()
                                , childView.getRight() + mBmp.getWidth()
                                , childView.getBottom() + mBmp.getHeight());
                        mNinePatch.draw(c, rect);
                    }

                    if (mGridRightVisible && isLastGridColumn(i, childrentCount, columnSize)) {
                        Rect rect = new Rect(
                                childView.getRight()
                                , childView.getTop() - mBmp.getHeight()
                                , childView.getRight() + mBmp.getWidth()
                                , childView.getBottom() + mBmp.getHeight());
                        mNinePatch.draw(c, rect);
                    }


                }
            } else {

                for (int i = 0; i < childrentCount; i++) {
                    View childView = parent.getChildAt(i);
                    int myX = childView.getRight();
                    int myY = childView.getBottom();

                    //horizonal
                    if (mGridBottomVisible && isLastGridRow(i, adapterChildrenCount, columnSize)) {
                        c.drawBitmap(mBmp, childView.getLeft(), myY, mPaint);
                    } else if (!isLastGridRow(i, adapterChildrenCount, columnSize)) {
                        c.drawBitmap(mBmp, childView.getLeft(), myY, mPaint);
                    }

                    if (mGridTopVisible && isFirstGridRow(i, columnSize)) {
                        c.drawBitmap(mBmp, childView.getLeft(), childView.getTop() - mBmp.getHeight(), mPaint);
                    }

                    //vertical
                    if (!isLastGridColumn(i, childrentCount, columnSize)) {
                        c.drawBitmap(mBmp, myX, childView.getTop(), mPaint);
                    }

                    if (mGridLeftVisible && isFirstGridColumn(i, columnSize)) {
                        c.drawBitmap(mBmp, childView.getLeft() - mBmp.getWidth(), childView.getTop(), mPaint);
                    }

                    if (mGridRightVisible && isLastGridColumn(i, childrentCount, columnSize)) {
                        c.drawBitmap(mBmp, childView.getRight(), childView.getTop(), mPaint);
                    }


                }
            }
        } else if (mDashWidth == 0 && mDashGap == 0) {

            for (int i = 0; i < childrentCount; i++) {
                View childView = parent.getChildAt(i);
                int myX = childView.getRight() + mThickness / 2;
                int myY = childView.getBottom() + mThickness / 2;

                //horizonal
                if (mGridBottomVisible && isLastGridRow(i, adapterChildrenCount, columnSize)) {
                    c.drawLine(childView.getLeft(), myY, childView.getRight() + mThickness, myY, mPaint);
                } else if (!isLastGridRow(i, adapterChildrenCount, columnSize)) {
                    c.drawLine(childView.getLeft(), myY, childView.getRight() + mThickness, myY, mPaint);
                }

                if (mGridTopVisible && isFirstGridRow(i, columnSize)) {
                    c.drawLine(childView.getLeft(), childView.getTop() - mThickness / 2
                            , childView.getRight() + mThickness, childView.getTop() - mThickness / 2, mPaint);
                }

                //vertical
                if (isLastGridRow(i, adapterChildrenCount, columnSize)
                        && !isLastGridColumn(i, childrentCount, columnSize)) {
                    c.drawLine(myX, childView.getTop(), myX, childView.getBottom(), mPaint);
                } else if (!isLastGridColumn(i, childrentCount, columnSize)) {
                    c.drawLine(myX, childView.getTop(), myX, myY, mPaint);
                }

                if (mGridLeftVisible && isFirstGridColumn(i, columnSize)) {
                    c.drawLine(childView.getLeft() - mThickness / 2
                            , childView.getTop() - mThickness
                            , childView.getLeft() - mThickness / 2
                            , childView.getBottom() + mThickness, mPaint);
                }

                if (mGridRightVisible && isLastGridColumn(i, childrentCount, columnSize)) {
                    c.drawLine(childView.getRight() + mThickness / 2
                            , childView.getTop()
                            , childView.getRight() + mThickness / 2
                            , childView.getBottom(), mPaint);
                }


            }


        } else {
            PathEffect effects = new DashPathEffect(new float[]{0, 0, mDashWidth, mThickness}, mDashGap);
            mPaint.setPathEffect(effects);
            for (int i = 0; i < childrentCount; i++) {
                View childView = parent.getChildAt(i);
                int myX = childView.getRight() + mThickness / 2;
                int myY = childView.getBottom() + mThickness / 2;

                //horizonal
                if (mGridBottomVisible && isLastGridRow(i, adapterChildrenCount, columnSize)) {
                    Path path = new Path();
                    path.moveTo(0, myY);
                    path.lineTo(myX, myY);
                    c.drawPath(path, mPaint);
                } else if (!isLastGridRow(i, adapterChildrenCount, columnSize)) {
                    Path path = new Path();
                    path.moveTo(0, myY);
                    path.lineTo(myX, myY);
                    c.drawPath(path, mPaint);
                }

                if (mGridTopVisible && isFirstGridRow(i, columnSize)) {

                    Path path = new Path();
                    path.moveTo(childView.getLeft(), childView.getTop() - mThickness / 2);
                    path.lineTo(childView.getRight() + mThickness, childView.getTop() - mThickness / 2);
                    c.drawPath(path, mPaint);

                }

                //vertical
                if (isLastGridRow(i, adapterChildrenCount, columnSize)
                        && !isLastGridColumn(i, childrentCount, columnSize)) {
                    Path path = new Path();
                    path.moveTo(myX, childView.getTop());
                    path.lineTo(myX, childView.getBottom());
                    c.drawPath(path, mPaint);
                } else if (!isLastGridColumn(i, childrentCount, columnSize)) {
                    Path path = new Path();
                    path.moveTo(myX, childView.getTop());
                    path.lineTo(myX, childView.getBottom());
                    c.drawPath(path, mPaint);
                }

                if (mGridLeftVisible && isFirstGridColumn(i, columnSize)) {
                    Path path = new Path();
                    path.moveTo(childView.getLeft() - mThickness / 2, childView.getTop() - mThickness);
                    path.lineTo(childView.getLeft() - mThickness / 2, childView.getBottom() + mThickness);
                    c.drawPath(path, mPaint);
                }

                if (mGridRightVisible && isLastGridColumn(i, childrentCount, columnSize)) {
                    Path path = new Path();
                    path.moveTo(childView.getRight() + mThickness / 2, childView.getTop());
                    path.lineTo(childView.getRight() + mThickness / 2, childView.getBottom());
                    c.drawPath(path, mPaint);
                }

            }
        }
    }

    /**
     * set offsets for grid mode
     *
     * @param outRect
     * @param viewPosition
     * @param columnSize
     * @param itemSize
     * @param tag          0 for drawable
     */
    public void setGridOffsets(Rect outRect, int viewPosition, int columnSize
            , int itemSize, int tag) {

        int x = 0;
        int y = 0;
        if (tag == 0) {
            x = mBmp.getWidth();
            y = mBmp.getHeight();
        } else {
            x = mThickness;
            y = mThickness;
        }
        if (isFirstGridColumn(viewPosition, columnSize)
                && isFirstGridRow(viewPosition, columnSize)) {

            if (mGridTopVisible && mGridLeftVisible) {
                outRect.set(x, y, 0, 0);
            } else if (mGridTopVisible) {
                outRect.set(0, y, 0, 0);
            } else if (mGridLeftVisible) {
                outRect.set(x, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, 0);
                outRect.set(0, 0, 0, 0);
            }
        } else if (isFirstGridRow(viewPosition, columnSize)
                && isLastGridColumn(viewPosition, itemSize, columnSize)) {
            if (mGridTopVisible && mGridRightVisible) {
                outRect.set(x, y, x, 0);
            } else if (mGridTopVisible) {
                outRect.set(x, y, 0, 0);
            } else if (mGridRightVisible) {
                outRect.set(x, 0, x, 0);
            } else {
                outRect.set(x, 0, 0, 0);
            }
        } else if (isFirstGridColumn(viewPosition, columnSize)
                && isLastGridRow(viewPosition, itemSize, columnSize)) {
            if (mGridLeftVisible && mGridBottomVisible) {
                outRect.set(x, y, 0, y);
            } else if (mGridLeftVisible) {
                outRect.set(x, y, 0, 0);
            } else if (mGridBottomVisible) {
                outRect.set(0, y, 0, y);
            } else {
                outRect.set(0, y, 0, 0);
            }
        } else if (isLastGridColumn(viewPosition, itemSize, columnSize)
                && isLastGridRow(viewPosition, itemSize, columnSize)) {
            if (mGridRightVisible && mGridBottomVisible) {
                outRect.set(x, y, x, y);
            } else if (mGridRightVisible) {
                outRect.set(x, y, x, 0);
            } else if (mGridBottomVisible) {
                outRect.set(x, y, 0, y);
            } else {
                outRect.set(x, y, 0, 0);
            }
        } else if (isFirstGridRow(viewPosition, columnSize)) {
            if (mGridTopVisible) {
                outRect.set(x, y, 0, 0);
            } else {
                outRect.set(x, 0, 0, 0);
            }
        } else if (isFirstGridColumn(viewPosition, columnSize)) {

            if (mGridLeftVisible) {
                outRect.set(x, y, 0, 0);
            } else {
                outRect.set(0, y, 0, 0);
            }
        } else if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
            if (mGridRightVisible) {
                outRect.set(x, y, x, 0);
            } else {
                outRect.set(x, y, 0, 0);
            }
        } else if (isLastGridRow(viewPosition, itemSize, columnSize)) {

            if (mGridBottomVisible) {
                outRect.set(x, y, 0, y);
            } else {
                outRect.set(x, y, 0, 0);
            }
        } else {
            outRect.set(x, y, 0, 0);
        }
    }

    /**
     * check if is one of the first columns
     *
     * @param position
     * @param columnSize
     * @return
     */
    private boolean isFirstGridColumn(int position, int columnSize) {

        return position % columnSize == 0;
    }

    /**
     * check if is one of the last columns
     *
     * @param position
     * @param columnSize
     * @return
     */
    private boolean isLastGridColumn(int position, int itemSize, int columnSize) {
        boolean isLast = false;
        if (((position + 1) % columnSize == 0) || (itemSize <= columnSize && position == itemSize - 1)) {
            isLast = true;
        }
        return isLast;
    }

    /**
     * check if is the first row of th grid
     *
     * @param position
     * @param columnSize
     * @return
     */
    private boolean isFirstGridRow(int position, int columnSize) {
        return position < columnSize;
    }

    /**
     * check if is the last row of the grid
     *
     * @param position
     * @param itemSize
     * @param columnSize
     * @return
     */
    private boolean isLastGridRow(int position, int itemSize, int columnSize) {
        return position / columnSize == (itemSize - 1) / columnSize;
    }

    /**
     * compatible with recyclerview layoutmanager
     *
     * @param parent
     */
    private void compatibleWithLayoutManager(RecyclerView parent) {

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            mMode = MODE_GRID;
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayout.HORIZONTAL) {
                mMode = MODE_VERTICAL;
            } else {
                mMode = MODE_HORIZONTAL;
            }
        }
    }

    public static class Builder {

        private Param params;
        private Context context;

        public Builder(Context context) {

            params = new Param();
            this.context = context;

        }

        public RecyclerViewItemDecoration create() {
            RecyclerViewItemDecoration recyclerViewItemDecoration = new RecyclerViewItemDecoration();
            recyclerViewItemDecoration.setParams(context, params);
            return recyclerViewItemDecoration;
        }

        public Builder mode(int mode) {
            params.mode = mode;
            return this;
        }

        public Builder drawableID(int drawableID) {
            params.drawableRid = drawableID;
            return this;
        }

        public Builder color(@ColorInt int color) {
            params.color = color;
            return this;
        }

        public Builder color(String color) {
            if (isColorString(color)) {
                params.color = Color.parseColor(color);
            }
            return this;
        }

        public Builder thickness(int thickness) {
            params.thickness = thickness;
            return this;
        }

        public Builder dashWidth(int dashWidth) {
            params.dashWidth = dashWidth;
            return this;
        }

        public Builder dashGap(int dashGap) {
            params.dashGap = dashGap;
            return this;
        }

        public Builder lastLineVisible(boolean visible) {
            params.lastLineVisible = visible;
            return this;
        }

        public Builder firstLineVisible(boolean visible) {
            params.firstLineVisible = visible;
            return this;
        }

        public Builder paddingStart(int padding) {
            params.paddingStart = padding;
            return this;
        }

        public Builder paddingEnd(int padding) {
            params.paddingEnd = padding;
            return this;
        }

        public Builder gridLeftVisible(boolean visible) {
            params.gridLeftVisible = visible;
            return this;
        }

        public Builder gridRightVisible(boolean visible) {
            params.gridRightVisible = visible;
            return this;
        }

        public Builder gridTopVisible(boolean visible) {
            params.gridTopVisible = visible;
            return this;
        }

        public Builder gridBottomVisible(boolean visible) {
            params.gridBottomVisible = visible;
            return this;
        }

        public Builder parent(RecyclerView recyclerView) {
            params.parent = recyclerView;
            return this;
        }


    }

    private static class Param {

        public int mode = MODE_HORIZONTAL;
        public int drawableRid = 0;
        public int color = Color.parseColor(DEFAULT_COLOR);
        public int thickness;
        public int dashWidth = 0;
        public int dashGap = 0;
        public boolean lastLineVisible;
        public boolean firstLineVisible;
        public int paddingStart;
        public int paddingEnd;
        public boolean gridLeftVisible;
        public boolean gridRightVisible;
        public boolean gridTopVisible;
        public boolean gridBottomVisible;
        public RecyclerView parent;
    }

}