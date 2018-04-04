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
import android.support.annotation.DrawableRes;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

/**
 * RecycleView item decoration
 * Created by Eminem Lo on 24/11/15.
 * Email arjinmc@hotmail.com
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

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
     * spacing for grid mode
     */
    public int mGridHorizontalSpacing;
    public int mGridVerticalSpacing;

    /***
     * ignore the item types which won't be drew item decoration
     * only for mode horizontal and vertical
     */
    private ArrayMap<Integer, Integer> mIgnoreTypes;

    /**
     * direction mode for decoration
     */
    private int mMode;

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
    private boolean hasNinePatch = false;
    /**
     * sign for if has get the parent RecyclerView LayoutManager mode
     */
    private boolean hasGetParentLayoutMode = false;
    private Context mContext;

    public RecyclerViewItemDecoration() {
    }

    public void setParams(Context context, Param params) {

        this.mContext = context;

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
        this.mGridHorizontalSpacing = params.gridHorizontalSpacing;
        this.mGridVerticalSpacing = params.gridVerticalSpacing;
        if (params.ignoreTypes != null && params.ignoreTypes.length != 0) {
            this.mIgnoreTypes = new ArrayMap<>();
            int ignoreTypeSize = params.ignoreTypes.length;
            for (int i = 0; i < ignoreTypeSize; i++) {
                this.mIgnoreTypes.put(params.ignoreTypes[i], params.ignoreTypes[i]);
            }
        }

    }

    private void initPaint(Context context) {

        this.mBmp = BitmapFactory.decodeResource(context.getResources(), mDrawableRid);
        if (mBmp != null) {

            if (mBmp.getNinePatchChunk() != null) {
                hasNinePatch = true;
                mNinePatch = new NinePatch(mBmp, mBmp.getNinePatchChunk(), null);
            }

            if (mMode == RVItemDecorationConst.MODE_HORIZONTAL)
                mCurrentThickness = mThickness == 0 ? mBmp.getHeight() : mThickness;
            if (mMode == RVItemDecorationConst.MODE_VERTICAL)
                mCurrentThickness = mThickness == 0 ? mBmp.getWidth() : mThickness;
        }

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mThickness);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (parent.getAdapter() == null || parent.getChildCount() == 0) {
            return;
        }
        mPaint.setColor(mColor);
        if (mMode == RVItemDecorationConst.MODE_HORIZONTAL) {
            drawHorizontal(c, parent);
        } else if (mMode == RVItemDecorationConst.MODE_VERTICAL) {
            drawVertical(c, parent);
        } else if (mMode == RVItemDecorationConst.MODE_GRID) {
            drawGrid(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (!hasGetParentLayoutMode) {
            compatibleWithLayoutManager(parent);
            hasGetParentLayoutMode = true;
        }
        int viewPosition = parent.getChildLayoutPosition(view);

        if (mMode == RVItemDecorationConst.MODE_HORIZONTAL) {

            if (!isIgnoreType(parent.getAdapter().getItemViewType(viewPosition))) {
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
            } else {
                outRect.set(0, 0, 0, 0);
            }

        } else if (mMode == RVItemDecorationConst.MODE_VERTICAL) {
            if (!isIgnoreType(parent.getAdapter().getItemViewType(viewPosition))) {
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

            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else if (mMode == RVItemDecorationConst.MODE_GRID) {
            int columnSize = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            int itemSize = parent.getAdapter().getItemCount();

            if (mDrawableRid != 0) {
                if (hasNinePatch) {
                    setGridOffsets(outRect, viewPosition, columnSize, itemSize, 0);
                } else {
                    setGridOffsets(outRect, viewPosition, columnSize, itemSize, 1);
                }
            } else {
                setGridOffsets(outRect, viewPosition, columnSize, itemSize, -1);
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
     * draw horizontal decoration
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {

        int childrenCount = parent.getChildCount();
        if (mDrawableRid != 0) {

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);
                int myY = childView.getTop();

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {
                    if (hasNinePatch) {
                        Rect rect = new Rect(mPaddingStart, myY - mCurrentThickness
                                , parent.getWidth() - mPaddingEnd, myY);
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, mPaddingStart, myY - mCurrentThickness, mPaint);
                    }
                }
            }


            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1)
                    break;
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {

                    int myY = childView.getBottom();

                    if (hasNinePatch) {
                        Rect rect = new Rect(mPaddingStart, myY
                                , parent.getWidth() - mPaddingEnd, myY + mCurrentThickness);
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, mPaddingStart, myY, mPaint);
                    }
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

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {
                    int myY = childView.getTop() - mThickness / 2;

                    Path path = new Path();
                    path.moveTo(mPaddingStart, myY);
                    path.lineTo(parent.getWidth() - mPaddingEnd, myY);
                    c.drawPath(path, mPaint);
                }
            }

            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1)
                    break;
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {
                    int myY = childView.getBottom() + mThickness / 2;

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
        int childrenCount = parent.getChildCount();
        if (mDrawableRid != 0) {

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {
                    int myX = childView.getLeft();
                    if (hasNinePatch) {
                        Rect rect = new Rect(myX - mCurrentThickness, mPaddingStart
                                , myX, parent.getHeight() - mPaddingEnd);
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, myX - mCurrentThickness, mPaddingStart, mPaint);
                    }
                }
            }
            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1)
                    break;
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {

                    int myX = childView.getRight();
                    if (hasNinePatch) {
                        Rect rect = new Rect(myX, mPaddingStart, myX + mCurrentThickness
                                , parent.getHeight() - mPaddingEnd);
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, myX, mPaddingStart, mPaint);
                    }
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
                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {
                    int myX = childView.getLeft() - mThickness / 2;
                    Path path = new Path();
                    path.moveTo(myX, mPaddingStart);
                    path.lineTo(myX, parent.getHeight() - mPaddingEnd);
                    c.drawPath(path, mPaint);
                }
            }

            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1)
                    break;
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getChildLayoutPosition(childView)))) {

                    int myX = childView.getRight() + mThickness / 2;
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

        int childrenCount = parent.getChildCount();
        int columnSize = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int itemSize = parent.getAdapter().getItemCount();

        if (mDrawableRid != 0) {

            mPaint.setStrokeWidth(mThickness);

            for (int i = 0; i < childrenCount; i++) {
                View childView = parent.getChildAt(i);
                int myT = childView.getTop();
                int myB = childView.getBottom();
                int myL = childView.getLeft();
                int myR = childView.getRight();
                int viewPosition = parent.getChildLayoutPosition(childView);

                //when columnSize/spanCount is One
                if (columnSize == 1) {
                    if (isFirstGridRow(viewPosition, columnSize)) {

                        if (mGridLeftVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL - mThickness
                                        , myT
                                        , myL
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myL - mThickness, myT, mPaint);
                            }
                        }
                        if (mGridTopVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL - mThickness
                                        , myT - mThickness
                                        , myR + mThickness
                                        , myT);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myL - mThickness, myT - mThickness, mPaint);
                            }
                        }
                        if (mGridRightVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myR
                                        , myT
                                        , myR + mThickness
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myR, myT, mPaint);
                            }
                        }

                        //not first row
                    } else {

                        if (mGridLeftVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL - mThickness
                                        , myT - mThickness
                                        , myL
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp
                                        , myL - mThickness
                                        , myT - mThickness
                                        , mPaint);
                            }
                        }

                        if (mGridRightVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myR
                                        , myT - mThickness
                                        , myR + mThickness
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myR, myT - mThickness, mPaint);
                            }
                        }

                    }

                    if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                        if (mGridBottomVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL - mThickness
                                        , myB
                                        , myR + mThickness
                                        , myB + mThickness);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myL - mThickness, myB, mPaint);
                            }
                        }
                    } else {
                        if (hasNinePatch) {
                            Rect rect = new Rect(myL
                                    , myB
                                    , myR + mThickness
                                    , myB + mThickness);
                            mNinePatch.draw(c, rect);
                        } else {
                            c.drawBitmap(mBmp
                                    , myL
                                    , myB
                                    , mPaint);
                        }
                    }

                    //when columnSize/spanCount is Not One
                } else {
                    if (isFirstGridColumn(viewPosition, columnSize) && isFirstGridRow(viewPosition, columnSize)) {

                        if (mGridLeftVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL - mThickness
                                        , myT - mThickness
                                        , myL
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myL - mThickness, myT - mThickness, mPaint);
                            }
                        }

                        if (mGridTopVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL
                                        , myT - mThickness
                                        , myR
                                        , myT);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myL, myT - mThickness, mPaint);
                            }
                        }

                        if (itemSize == 1) {
                            if (mGridRightVisible) {
                                if (hasNinePatch) {
                                    Rect rect = new Rect(myR
                                            , myT - mThickness
                                            , myR + mThickness
                                            , myB);
                                    mNinePatch.draw(c, rect);
                                } else {
                                    c.drawBitmap(mBmp, myR, myT - mThickness, mPaint);
                                }
                            }
                        } else {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myR
                                        , myT - mThickness
                                        , myR + mThickness
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp
                                        , myR
                                        , myT - mThickness
                                        , mPaint);
                            }
                        }

                    } else if (isFirstGridRow(viewPosition, columnSize)) {

                        if (mGridTopVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL
                                        , myT - mThickness
                                        , myR
                                        , myT);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myL, myT - mThickness, mPaint);
                            }
                        }

                        if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                            if (mGridRightVisible) {
                                if (hasNinePatch) {
                                    Rect rect = new Rect(myR
                                            , myT - mThickness
                                            , myR + mThickness
                                            , myB);
                                    mNinePatch.draw(c, rect);
                                } else {
                                    c.drawBitmap(mBmp, myR, myT - mThickness, mPaint);
                                }
                            }
                        } else {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myR
                                        , myT - mThickness
                                        , myR + mThickness
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp
                                        , myR
                                        , myT - mBmp.getHeight()
                                        , mPaint);
                            }
                        }

                    } else if (isFirstGridColumn(viewPosition, columnSize)) {
                        if (mGridLeftVisible) {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myL - mThickness
                                        , myT
                                        , myL
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp, myL - mThickness, myT, mPaint);
                            }
                        }

                        if (hasNinePatch) {
                            Rect rect = new Rect(myR
                                    , myT
                                    , myR + mThickness
                                    , myB);
                            mNinePatch.draw(c, rect);
                        } else {
                            c.drawBitmap(mBmp
                                    , myR
                                    , myT
                                    , mPaint);
                        }
                    } else {
                        if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                            if (mGridRightVisible) {
                                if (hasNinePatch) {
                                    Rect rect = new Rect(myR
                                            , myT - mThickness
                                            , myR + mThickness
                                            , myB);
                                    mNinePatch.draw(c, rect);
                                } else {
                                    c.drawBitmap(mBmp
                                            , myR
                                            , myT - mBmp.getHeight()
                                            , mPaint);
                                }
                            }
                        } else {
                            if (hasNinePatch) {
                                Rect rect = new Rect(myR
                                        , myT
                                        , myR + mThickness
                                        , myB);
                                mNinePatch.draw(c, rect);
                            } else {
                                c.drawBitmap(mBmp
                                        , myR
                                        , myT
                                        , mPaint);
                            }
                        }
                    }

                    //bottom line
                    if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                        if (mGridBottomVisible) {
                            if (itemSize == 1) {
                                if (hasNinePatch) {
                                    Rect rect = new Rect(myL - mThickness
                                            , myB
                                            , myR + mThickness
                                            , myB + mThickness);
                                    mNinePatch.draw(c, rect);
                                } else {
                                    c.drawBitmap(mBmp
                                            , myL - mThickness
                                            , myB
                                            , mPaint);
                                }
                            } else if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                                if (hasNinePatch) {
                                    Rect rect = new Rect(myL - mThickness
                                            , myB
                                            , myR + mThickness
                                            , myB + mThickness);
                                    mNinePatch.draw(c, rect);
                                } else {
                                    c.drawBitmap(mBmp
                                            , myL - mThickness
                                            , myB + mThickness / 2
                                            , mPaint);
                                }
                            } else {
                                if (hasNinePatch) {
                                    Rect rect = new Rect(myL - mThickness
                                            , myB
                                            , myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing)
                                            , myB + mThickness);
                                    mNinePatch.draw(c, rect);
                                } else {
                                    c.drawBitmap(mBmp
                                            , myL - mThickness
                                            , myB, mPaint);
                                }
                            }

                        }
                    } else {
                        if (hasNinePatch) {
                            Rect rect = new Rect(myL - mThickness
                                    , myB
                                    , myR
                                    , myB + (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing));
                            mNinePatch.draw(c, rect);
                        } else {
                            c.drawBitmap(mBmp
                                    , myL - mBmp.getWidth()
                                    , myB
                                    , mPaint);
                        }
                    }
                }

            }

        } else {
            if (!isPureLine()) {
                PathEffect effects = new DashPathEffect(new float[]{0, 0, mDashWidth, mThickness}, mDashGap);
                mPaint.setPathEffect(effects);
            }
            for (int i = 0; i < childrenCount; i++) {
                View childView = parent.getChildAt(i);
                int myT = childView.getTop();
                int myB = childView.getBottom();
                int myL = childView.getLeft();
                int myR = childView.getRight();
                int viewPosition = parent.getChildLayoutPosition(childView);

                //when columnSize/spanCount is One
                if (columnSize == 1) {
                    if (isFirstGridRow(viewPosition, columnSize)) {

                        if (mGridLeftVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL - mThickness / 2, myT);
                            path.lineTo(myL - mThickness / 2, myB);
                            c.drawPath(path, mPaint);
                        }
                        if (mGridTopVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL - mThickness, myT - mThickness / 2);
                            path.lineTo(myR + mThickness, myT - mThickness / 2);
                            c.drawPath(path, mPaint);
                        }
                        if (mGridRightVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myR + mThickness / 2, myT);
                            path.lineTo(myR + mThickness / 2, myB);
                            c.drawPath(path, mPaint);
                        }

                        //not first row
                    } else {

                        if (mGridLeftVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL - mThickness / 2
                                    , myT - (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing));
                            path.lineTo(myL - mThickness / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }

                        if (mGridRightVisible) {
                            Path path = new Path();
                            mPaint.setStrokeWidth(mThickness);
                            path.moveTo(myR + mThickness / 2
                                    , myT);
                            path.lineTo(myR + mThickness / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }

                    }

                    if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                        if (mGridBottomVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL - mThickness
                                    , myB + mThickness / 2);
                            path.lineTo(myR + mThickness
                                    , myB + mThickness / 2);
                            c.drawPath(path, mPaint);
                        }
                    } else {
                        mPaint.setStrokeWidth(mThickness);
                        if (mGridVerticalSpacing != 0) {
                            mPaint.setStrokeWidth(mGridVerticalSpacing);
                        }
                        Path path = new Path();
                        path.moveTo(myL
                                , myB + (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing) / 2);
                        path.lineTo(myR + mThickness
                                , myB + (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing) / 2);
                        c.drawPath(path, mPaint);
                    }

                    //when columnSize/spanCount is Not One
                } else {
                    if (isFirstGridColumn(viewPosition, columnSize) && isFirstGridRow(viewPosition, columnSize)) {

                        if (mGridLeftVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL - mThickness / 2
                                    , myT - mThickness);
                            path.lineTo(myL - mThickness / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }

                        if (mGridTopVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL
                                    , myT - mThickness / 2);
                            path.lineTo(myR
                                    , myT - mThickness / 2);
                            c.drawPath(path, mPaint);

                        }

                        if (itemSize == 1) {
                            if (mGridRightVisible) {
                                mPaint.setStrokeWidth(mThickness);
                                Path path = new Path();
                                path.moveTo(myR + mThickness / 2
                                        , myT - mThickness);
                                path.lineTo(myR + mThickness / 2
                                        , myB);
                                c.drawPath(path, mPaint);
                            }
                        } else {
                            mPaint.setStrokeWidth(mThickness);
                            if (mGridHorizontalSpacing != 0) {
                                mPaint.setStrokeWidth(mGridHorizontalSpacing);
                            }
                            Path path = new Path();
                            path.moveTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                    , myT - mThickness);
                            path.lineTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }

                    } else if (isFirstGridRow(viewPosition, columnSize)) {

                        if (mGridTopVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL
                                    , myT - mThickness / 2);
                            path.lineTo(myR
                                    , myT - mThickness / 2);
                            c.drawPath(path, mPaint);

                        }

                        if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                            mPaint.setStrokeWidth(mThickness);
                            if (mGridRightVisible) {

                                int alterY = 0;
                                if (isLastSecondGridRowNotDivided(viewPosition, itemSize, columnSize)) {
                                    alterY = (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing);
                                }
                                Path path = new Path();
                                path.moveTo(myR + mThickness / 2
                                        , myT - mThickness);
                                path.lineTo(myR + mThickness / 2
                                        , myB + alterY);
                                c.drawPath(path, mPaint);
                            }
                        } else {
                            if (mGridHorizontalSpacing != 0) {
                                mPaint.setStrokeWidth(mGridHorizontalSpacing);
                            }
                            Path path = new Path();
                            path.moveTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                    , myT - mThickness);
                            path.lineTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }

                    } else if (isFirstGridColumn(viewPosition, columnSize)) {

                        if (mGridLeftVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            Path path = new Path();
                            path.moveTo(myL - mThickness / 2
                                    , myT);
                            path.lineTo(myL - mThickness / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }

                        mPaint.setStrokeWidth(mThickness);
                        if (mGridHorizontalSpacing != 0) {
                            mPaint.setStrokeWidth(mGridHorizontalSpacing);
                        }
                        Path path = new Path();
                        path.moveTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                , myT);
                        path.lineTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                , myB);
                        c.drawPath(path, mPaint);

                    } else {

                        mPaint.setStrokeWidth(mThickness);

                        if (isLastGridColumn(viewPosition, itemSize, columnSize)) {

                            if (mGridRightVisible) {

                                int alterY = 0;
                                if (isLastSecondGridRowNotDivided(viewPosition, itemSize, columnSize)) {
                                    alterY = (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing);
                                }
                                Path path = new Path();
                                path.moveTo(myR + mThickness / 2
                                        , myT - (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing));
                                path.lineTo(myR + mThickness / 2
                                        , myB + alterY);
                                c.drawPath(path, mPaint);
                            }
                        } else {
                            if (mGridHorizontalSpacing != 0) {
                                mPaint.setStrokeWidth(mGridHorizontalSpacing);
                            }
                            Path path = new Path();
                            path.moveTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                    , myT);
                            path.lineTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing) / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }
                    }

                    //bottom line
                    if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                        if (mGridBottomVisible) {
                            mPaint.setStrokeWidth(mThickness);
                            if (itemSize == 1) {
                                Path path = new Path();
                                path.moveTo(myL - mThickness
                                        , myB + mThickness / 2);
                                path.lineTo(myR + mThickness
                                        , myB + mThickness / 2);
                                c.drawPath(path, mPaint);
                            } else if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                                Path path = new Path();
                                path.moveTo(myL - (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing)
                                        , myB + mThickness / 2);
                                path.lineTo(myR + mThickness
                                        , myB + mThickness / 2);
                                c.drawPath(path, mPaint);
                            } else {
                                Path path = new Path();
                                path.moveTo(myL - (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing)
                                        , myB + mThickness / 2);
                                path.lineTo(myR + (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing)
                                        , myB + mThickness / 2);
                                c.drawPath(path, mPaint);
                            }

                        }
                    } else {
                        mPaint.setStrokeWidth(mThickness);
                        if (mGridVerticalSpacing != 0) {
                            mPaint.setStrokeWidth(mGridVerticalSpacing);
                        }
                        Path path = new Path();
                        path.moveTo(myL - (mGridHorizontalSpacing == 0 ? mThickness : mGridHorizontalSpacing)
                                , myB + (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing) / 2);
                        path.lineTo(myR
                                , myB + (mGridVerticalSpacing == 0 ? mThickness : mGridVerticalSpacing) / 2);
                        c.drawPath(path, mPaint);
                    }
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
     * @param tag          0 for ninepatch,1 for drawable bitmap
     */
    public void setGridOffsets(Rect outRect, int viewPosition, int columnSize
            , int itemSize, int tag) {

        int x;
        int y;
        int borderThickness = mThickness;
        if (tag == 0) {
            x = y = mThickness;
            mGridVerticalSpacing = mGridHorizontalSpacing = 0;
        } else if (tag == 1) {
            x = mBmp.getWidth();
            y = mBmp.getHeight();
            mGridVerticalSpacing = mGridHorizontalSpacing = 0;
        } else {

            if (mGridHorizontalSpacing != 0)
                x = mGridHorizontalSpacing;
            else
                x = mThickness;

            if (mGridVerticalSpacing != 0)
                y = mGridVerticalSpacing;
            else
                y = mThickness;

        }

        //when columnSize/spanCount is One
        if (columnSize == 1) {
            if (isFirstGridRow(viewPosition, columnSize)) {
                if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                    outRect.set((mGridLeftVisible ? borderThickness : 0)
                            , (mGridTopVisible ? borderThickness : 0)
                            , (mGridRightVisible ? borderThickness : 0)
                            , (mGridBottomVisible ? borderThickness : y));
                } else {
                    outRect.set((mGridLeftVisible ? borderThickness : 0)
                            , (mGridTopVisible ? borderThickness : 0)
                            , (mGridRightVisible ? borderThickness : 0)
                            , y);
                }

            } else {
                if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                    outRect.set((mGridLeftVisible ? borderThickness : 0)
                            , 0
                            , (mGridRightVisible ? borderThickness : 0)
                            , (mGridBottomVisible ? borderThickness : 0));
                } else {
                    outRect.set((mGridLeftVisible ? borderThickness : 0)
                            , 0
                            , (mGridRightVisible ? borderThickness : 0)
                            , y);
                }
            }
        } else {
            if (isFirstGridColumn(viewPosition, columnSize)
                    && isFirstGridRow(viewPosition, columnSize)) {

                outRect.set((mGridLeftVisible ? borderThickness : 0)
                        , (mGridTopVisible ? borderThickness : 0)
                        , (itemSize == 1 ? borderThickness : x)
                        , (isLastGridRow(viewPosition, itemSize, columnSize) ? borderThickness : y));

            } else if (isFirstGridRow(viewPosition, columnSize)) {

                if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                    outRect.set(0
                            , (mGridTopVisible ? borderThickness : 0)
                            , (mGridRightVisible ? borderThickness : 0)
                            , (isLastGridRow(viewPosition, itemSize, columnSize) ? borderThickness : y));
                } else {
                    outRect.set(0
                            , (mGridTopVisible ? borderThickness : 0)
                            , x
                            , (isLastGridRow(viewPosition, itemSize, columnSize) ? borderThickness : y));
                }


            } else if (isFirstGridColumn(viewPosition, columnSize)) {

                if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                    outRect.set((mGridLeftVisible ? borderThickness : 0)
                            , 0
                            , (isLastGridColumn(viewPosition, itemSize, columnSize) ? borderThickness : x)
                            , (mGridBottomVisible ? borderThickness : 0));
                } else {
                    outRect.set((mGridLeftVisible ? borderThickness : 0)
                            , 0
                            , (isLastGridColumn(viewPosition, itemSize, columnSize) ? borderThickness : x)
                            , y);
                }

            } else {

                if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                    if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                        outRect.set(0
                                , 0
                                , (mGridRightVisible ? borderThickness : 0)
                                , (mGridBottomVisible ? borderThickness : 0));
                    } else {
                        outRect.set(0
                                , 0
                                , (mGridRightVisible ? borderThickness : 0)
                                , y);
                    }
                } else {
                    if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                        outRect.set(0
                                , 0
                                , x
                                , (mGridBottomVisible ? borderThickness : 0));
                    } else {
                        outRect.set(0
                                , 0
                                , x
                                , y);
                    }

                }
            }
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
        if ((position + 1) % columnSize == 0) {
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
        int temp = itemSize % columnSize;
        if (temp == 0 && position >= itemSize - columnSize) {
            return true;
        } else if (position >= itemSize / columnSize * columnSize) {
            return true;
        }
        return false;
    }

    /**
     * check if is the last second row of the grid when the itemSize cannot be divided by columnSize
     *
     * @param position
     * @param itemSize
     * @param columnSize
     * @return
     */
    private boolean isLastSecondGridRowNotDivided(int position, int itemSize, int columnSize) {
        int temp = itemSize % columnSize;
        if (temp != 0 && itemSize - 1 - temp == position) {
            return true;
        }
        return false;
    }

    /**
     * compatible with recyclerview layoutmanager
     *
     * @param parent
     */
    private void compatibleWithLayoutManager(RecyclerView parent) {

        if (parent.getLayoutManager() != null) {
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                mMode = RVItemDecorationConst.MODE_GRID;
            } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayout.HORIZONTAL) {
                    mMode = RVItemDecorationConst.MODE_VERTICAL;
                } else {
                    mMode = RVItemDecorationConst.MODE_HORIZONTAL;
                }
            }

        } else {
            mMode = RVItemDecorationConst.MODE_UNKNOWN;
        }

        initPaint(mContext);

    }

    /**
     * check current item is ignore type
     *
     * @return
     */
    private boolean isIgnoreType(int viewType) {

        if (mIgnoreTypes == null || mIgnoreTypes.isEmpty()) {
            return false;
        }

        if (mIgnoreTypes.containsKey(viewType)) {
            return true;
        }

        return false;
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

        public Builder drawableID(@DrawableRes int drawableID) {
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
            if (thickness % 2 != 0) {
                thickness += 1;
            }
            if (thickness <= 2) {
                thickness = 2;
            }
            params.thickness = thickness;
            return this;
        }

        public Builder dashWidth(int dashWidth) {
            if (dashWidth < 0) {
                dashWidth = 0;
            }
            params.dashWidth = dashWidth;
            return this;
        }

        public Builder dashGap(int dashGap) {
            if (dashGap < 0) {
                dashGap = 0;
            }
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
            if (padding < 0) {
                padding = 0;
            }
            params.paddingStart = padding;
            return this;
        }

        public Builder paddingEnd(int padding) {
            if (padding < 0) {
                padding = 0;
            }
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

        public Builder gridHorizontalSpacing(int spacing) {
            if (spacing < 0) {
                spacing = 0;
            }
            params.gridHorizontalSpacing = spacing;
            return this;
        }

        public Builder gridVerticalSpacing(int spacing) {
            if (spacing < 0) {
                spacing = 0;
            }
            params.gridVerticalSpacing = spacing;
            return this;
        }

        public Builder ignoreTypes(int[] ignoreTypes) {
            params.ignoreTypes = ignoreTypes;
            return this;
        }

    }

    private static class Param {

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
        public int gridHorizontalSpacing = 0;
        public int gridVerticalSpacing = 0;
        public int[] ignoreTypes;
    }

}