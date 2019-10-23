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
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecycleView item decoration
 * Created by Eminem Lo on 24/11/15.
 * Email arjinmc@hotmail.com
 */
public class RecyclerViewLinearItemDecoration extends RecyclerView.ItemDecoration {

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

    public RecyclerViewLinearItemDecoration() {
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

            if (mMode == RVItemDecorationConst.MODE_HORIZONTAL) {
                mCurrentThickness = mThickness == 0 ? mBmp.getHeight() : mThickness;
            }
            if (mMode == RVItemDecorationConst.MODE_VERTICAL) {
                mCurrentThickness = mThickness == 0 ? mBmp.getWidth() : mThickness;
            }
        } else {
            mCurrentThickness = mThickness;
        }

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCurrentThickness);
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
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (!hasGetParentLayoutMode) {
            compatibleWithLayoutManager(parent);
            hasGetParentLayoutMode = true;
        }
        int viewPosition = parent.getLayoutManager().getPosition(view);

        if (mMode == RVItemDecorationConst.MODE_HORIZONTAL) {

            if (!isIgnoreType(parent.getAdapter().getItemViewType(viewPosition))) {
                if (!(!mLastLineVisible &&
                        viewPosition == parent.getAdapter().getItemCount() - 1)) {
                    outRect.set(0, 0, 0, mCurrentThickness);
                }

                if (mFirstLineVisible && viewPosition == 0) {
                    outRect.set(0, mCurrentThickness, 0, mCurrentThickness);
                }
            } else {
                outRect.set(0, 0, 0, 0);
            }

        } else if (mMode == RVItemDecorationConst.MODE_VERTICAL) {
            if (!isIgnoreType(parent.getAdapter().getItemViewType(viewPosition))) {
                if (!(!mLastLineVisible &&
                        viewPosition == parent.getAdapter().getItemCount() - 1)) {
                    outRect.set(0, 0, mCurrentThickness, 0);
                }
                if (mFirstLineVisible && viewPosition == 0) {
                    outRect.set(mCurrentThickness, 0, mCurrentThickness, 0);
                }

            } else {
                outRect.set(0, 0, 0, 0);
            }
        }

    }

    private boolean isPureLine() {
        if (mDashGap == 0 && mDashWidth == 0) {
            return true;
        }
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

        if (parent.getClipToPadding()) {
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();
            c.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        }

        if (mDrawableRid != 0) {

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);
                int myY = childView.getTop();

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {

                    if (hasNinePatch) {
                        Rect rect = new Rect(mPaddingStart + parent.getPaddingLeft(), myY - mCurrentThickness
                                , parent.getWidth() - parent.getPaddingRight() - mPaddingEnd, myY);
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, mPaddingStart + parent.getPaddingLeft(), myY - mCurrentThickness, mPaint);
                    }
                }
            }


            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1) {
                    break;
                }
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {

                    int myY = childView.getBottom();

                    if (hasNinePatch) {
                        Rect rect = new Rect(mPaddingStart + parent.getPaddingLeft(), myY
                                , parent.getWidth() - parent.getPaddingRight() - mPaddingEnd, myY + mCurrentThickness);
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, mPaddingStart + parent.getPaddingLeft(), myY, mPaint);
                    }
                }

            }

        } else {

            boolean isPureLine = isPureLine();
            if (!isPureLine) {
                PathEffect effects = new DashPathEffect(new float[]{0, 0, mDashWidth, mCurrentThickness}, mDashGap);
                mPaint.setPathEffect(effects);
            }

            if (mFirstLineVisible) {

                View childView = parent.getChildAt(0);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {

                    int myY = childView.getTop() - mCurrentThickness / 2;

                    Path path = new Path();
                    path.moveTo(mPaddingStart + parent.getPaddingLeft(), myY);
                    path.lineTo(parent.getWidth() - mPaddingEnd - parent.getPaddingRight(), myY);
                    c.drawPath(path, mPaint);
                }
            }

            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1) {
                    break;
                }
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {

                    int myY = childView.getBottom() + mCurrentThickness / 2;
                    Path path = new Path();
                    path.moveTo(mPaddingStart + parent.getPaddingLeft(), myY);
                    path.lineTo(parent.getWidth() - mPaddingEnd - parent.getPaddingRight(), myY);
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

        if (parent.getClipToPadding()) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            c.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        }

        if (mDrawableRid != 0) {

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {
                    int myX = childView.getLeft();
                    if (hasNinePatch) {
                        Rect rect = new Rect(myX - mCurrentThickness, mPaddingStart + parent.getPaddingLeft()
                                , myX, parent.getHeight() - mPaddingEnd - parent.getPaddingRight());
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, myX - mCurrentThickness, mPaddingStart + parent.getPaddingLeft(), mPaint);
                    }
                }
            }
            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1) {
                    break;
                }
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {

                    int myX = childView.getRight();
                    if (hasNinePatch) {
                        Rect rect = new Rect(myX, mPaddingStart + parent.getPaddingLeft(), myX + mCurrentThickness
                                , parent.getHeight() - mPaddingEnd - parent.getPaddingRight());
                        mNinePatch.draw(c, rect);
                    } else {
                        c.drawBitmap(mBmp, myX, mPaddingStart + parent.getPaddingLeft(), mPaint);
                    }
                }
            }

        } else {

            boolean isPureLine = isPureLine();
            if (!isPureLine) {
                PathEffect effects = new DashPathEffect(new float[]{0, 0, mDashWidth, mCurrentThickness}, mDashGap);
                mPaint.setPathEffect(effects);
            }

            if (mFirstLineVisible) {
                View childView = parent.getChildAt(0);
                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {
                    int myX = childView.getLeft() - mCurrentThickness / 2;
                    Path path = new Path();
                    path.moveTo(myX, mPaddingStart + parent.getPaddingLeft());
                    path.lineTo(myX, parent.getHeight() - mPaddingEnd - parent.getPaddingRight());
                    c.drawPath(path, mPaint);
                }
            }

            for (int i = 0; i < childrenCount; i++) {
                if (!mLastLineVisible && i == childrenCount - 1) {
                    break;
                }
                View childView = parent.getChildAt(i);

                if (!isIgnoreType(parent.getAdapter().getItemViewType(
                        parent.getLayoutManager().getPosition(childView)))) {

                    int myX = childView.getRight() + mCurrentThickness / 2;
                    Path path = new Path();
                    path.moveTo(myX, mPaddingStart + parent.getPaddingLeft());
                    path.lineTo(myX, parent.getHeight() - mPaddingEnd - parent.getPaddingRight());
                    c.drawPath(path, mPaint);
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
            if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.HORIZONTAL) {
                    mMode = RVItemDecorationConst.MODE_VERTICAL;
                } else {
                    mMode = RVItemDecorationConst.MODE_HORIZONTAL;
                }
            } else {
                try {
                    throw new IllegalAccessException("RecyclerViewLinearItemDecoration only support LinearLayoutManager");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                }
            }

        } else {
            try {
                throw new IllegalAccessException("RecyclerViewLinearItemDecoration only support LinearLayoutManager");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
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

        public RecyclerViewLinearItemDecoration create() {
            RecyclerViewLinearItemDecoration recyclerViewLinearItemDecoration = new RecyclerViewLinearItemDecoration();
            recyclerViewLinearItemDecoration.setParams(context, params);
            return recyclerViewLinearItemDecoration;
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
            if (RVItemDecorationUtil.isColorString(color)) {
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
        public int[] ignoreTypes;
    }

}