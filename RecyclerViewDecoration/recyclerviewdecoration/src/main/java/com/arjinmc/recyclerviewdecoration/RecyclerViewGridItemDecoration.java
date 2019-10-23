package com.arjinmc.recyclerviewdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Grid mode ItemDecoration
 * Created by Eminem Lo on 2019-09-24.
 * email: arjinmc@hotmail.com
 */
public class RecyclerViewGridItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * default decoration color
     */
    private static final String DEFAULT_COLOR = "#bdbdbd";

    /**
     * the color for the diver
     */
    private int mColor;
    /**
     * horizontalSpacing
     */
    private int mHorizontalSpacing = 0;
    /**
     * VerticalSpacing
     */
    private int mVerticalSpacing = 0;

    /**
     * show border
     */
    private boolean mBorderVisible;

    private Paint mPaint;

    public void setParams(Param param) {

        mColor = param.color;
        mHorizontalSpacing = param.horizontalSpacing;
        mVerticalSpacing = param.verticalSpacing;
        mBorderVisible = param.borderVisible;

        initPaint();
    }

    private void initPaint() {

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        if (parent.getChildCount() == 0) {
            return;
        }
        if (parent.getLayoutManager() instanceof GridLayoutManager) {

            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            if (layoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                try {
                    throw new IllegalAccessException("RecyclerViewGridSpaceItemDecoration only support GridLayoutManager Vertical");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                }
            }
            int position = parent.getChildAdapterPosition(view);
            int spanCount = layoutManager.getSpanCount();
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            int column = lp.getSpanIndex();
            getGridItemOffsets(outRect, position, column, spanCount);
        } else {
            try {
                throw new IllegalAccessException("RecyclerViewGridSpaceItemDecoration only support GridLayoutManager Vertical");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getChildCount() == 0) {
            return;
        }
        drawGrid(c, parent);
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

        for (int i = 0; i < childrenCount; i++) {
            View childView = parent.getChildAt(i);
            int myT = childView.getTop();
            int myB = childView.getBottom();
            int myL = childView.getLeft();
            int myR = childView.getRight();
            int viewPosition = parent.getLayoutManager().getPosition(childView);

            //when columnSize/spanCount is One
            if (columnSize == 1) {


                if (isFirstGridRow(viewPosition, columnSize)) {

                    //draw left border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myL - mHorizontalSpacing / 2, myT);
                        path.lineTo(myL - mHorizontalSpacing / 2, myB);
                        c.drawPath(path, mPaint);
                    }
                    //draw top border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mVerticalSpacing);
                        Path path = new Path();
                        path.moveTo(myL - mHorizontalSpacing, myT - mVerticalSpacing / 2);
                        path.lineTo(myR + mHorizontalSpacing, myT - mVerticalSpacing / 2);
                        c.drawPath(path, mPaint);
                    }
                    //draw right border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myR + mHorizontalSpacing / 2, myT);
                        path.lineTo(myR + mHorizontalSpacing / 2, myB);
                        c.drawPath(path, mPaint);
                    }

                    //not first row
                } else {

                    //draw left border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myL - mHorizontalSpacing / 2
                                , myT - mVerticalSpacing);
                        path.lineTo(myL - mHorizontalSpacing / 2
                                , myB);
                        c.drawPath(path, mPaint);
                    }
                    //draw right border
                    if (mBorderVisible) {
                        Path path = new Path();
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        path.moveTo(myR + mHorizontalSpacing / 2
                                , myT);
                        path.lineTo(myR + mHorizontalSpacing / 2
                                , myB);
                        c.drawPath(path, mPaint);
                    }

                }

                if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                    //draw bottom border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mVerticalSpacing);
                        Path path = new Path();
                        path.moveTo(myL - mHorizontalSpacing
                                , myB + mVerticalSpacing / 2);
                        path.lineTo(myR + mHorizontalSpacing
                                , myB + mVerticalSpacing / 2);
                        c.drawPath(path, mPaint);
                    }
                } else {
                    mPaint.setStrokeWidth(mVerticalSpacing);
                    Path path = new Path();
                    path.moveTo(myL
                            , myB + mVerticalSpacing / 2);
                    path.lineTo(myR + mHorizontalSpacing
                            , myB + mVerticalSpacing / 2);
                    c.drawPath(path, mPaint);
                }

                //when columnSize/spanCount is Not One
            } else {
                if (isFirstGridColumn(viewPosition, columnSize) && isFirstGridRow(viewPosition, columnSize)) {

                    //draw left border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myL - mHorizontalSpacing / 2
                                , myT - mVerticalSpacing);
                        path.lineTo(myL - mHorizontalSpacing / 2
                                , myB);
                        c.drawPath(path, mPaint);
                    }

                    //draw top border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mVerticalSpacing);
                        Path path = new Path();
                        path.moveTo(myL
                                , myT - mVerticalSpacing / 2);
                        path.lineTo(myR + mHorizontalSpacing / 2
                                , myT - mVerticalSpacing / 2);
                        c.drawPath(path, mPaint);

                    }

                    if (itemSize == 1) {
                        //draw right border
                        if (mBorderVisible) {
                            mPaint.setStrokeWidth(mHorizontalSpacing);
                            Path path = new Path();
                            path.moveTo(myR + mHorizontalSpacing / 2
                                    , myT - mHorizontalSpacing);
                            path.lineTo(myR + mHorizontalSpacing / 2
                                    , myB);
                            c.drawPath(path, mPaint);
                        }
                    } else {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myR + mHorizontalSpacing / 2
                                , myT);
                        path.lineTo(myR + mHorizontalSpacing / 2
                                , myB);
                        c.drawPath(path, mPaint);
                    }

                } else if (isFirstGridRow(viewPosition, columnSize)) {

                    //draw top border
                    if (mBorderVisible) {

                        if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                            mPaint.setStrokeWidth(mVerticalSpacing);
                            Path path = new Path();
                            path.moveTo(myL - mHorizontalSpacing / 2
                                    , myT - mVerticalSpacing / 2);
                            path.lineTo(myR
                                    , myT - mVerticalSpacing / 2);
                            c.drawPath(path, mPaint);
                        } else {
                            mPaint.setStrokeWidth(mVerticalSpacing);
                            Path path = new Path();
                            path.moveTo(myL - mHorizontalSpacing / 2
                                    , myT - mVerticalSpacing / 2);
                            path.lineTo(myR + mHorizontalSpacing / 2
                                    , myT - mVerticalSpacing / 2);
                            c.drawPath(path, mPaint);
                        }

                    }

                    if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        //draw right border
                        if (mBorderVisible) {

                            int alterY = 0;
                            if (isLastSecondGridRowNotDivided(viewPosition, itemSize, columnSize)) {
                                alterY = mHorizontalSpacing;
                            }
                            Path path = new Path();
                            path.moveTo(myR + mHorizontalSpacing / 2
                                    , myT - mHorizontalSpacing - mVerticalSpacing);
                            path.lineTo(myR + mHorizontalSpacing / 2
                                    , myB + alterY + mVerticalSpacing / 2);
                            c.drawPath(path, mPaint);
                        }
                    } else {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myR + mHorizontalSpacing / 2
                                , myT);
                        path.lineTo(myR + mHorizontalSpacing / 2
                                , myB);
                        c.drawPath(path, mPaint);
                    }

                } else if (isFirstGridColumn(viewPosition, columnSize)) {

                    //draw left border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myL - mHorizontalSpacing / 2
                                , myT);
                        path.lineTo(myL - mHorizontalSpacing / 2
                                , myB);
                        c.drawPath(path, mPaint);
                    }

                    mPaint.setStrokeWidth(mHorizontalSpacing);
                    Path path = new Path();
                    path.moveTo(myR + mHorizontalSpacing / 2
                            , myT);
                    path.lineTo(myR + mHorizontalSpacing / 2
                            , myB);
                    c.drawPath(path, mPaint);

                } else {

                    mPaint.setStrokeWidth(mHorizontalSpacing);

                    if (isLastGridColumn(viewPosition, itemSize, columnSize)) {

                        //draw right border
                        if (mBorderVisible) {

                            int alterY = 0;
                            if (isLastSecondGridRowNotDivided(viewPosition, itemSize, columnSize)) {
                                alterY = mVerticalSpacing / 2;
                            }
                            Path path = new Path();
                            path.moveTo(myR + mHorizontalSpacing / 2
                                    , myT - mVerticalSpacing / 2);
                            path.lineTo(myR + mHorizontalSpacing / 2
                                    , myB + alterY + mVerticalSpacing / 2);
                            c.drawPath(path, mPaint);
                        }
                    } else {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        Path path = new Path();
                        path.moveTo(myR + mHorizontalSpacing / 2
                                , myT);
                        path.lineTo(myR + mHorizontalSpacing / 2
                                , myB);
                        c.drawPath(path, mPaint);
                    }
                }

                //bottom line
                if (isLastGridRow(viewPosition, itemSize, columnSize)) {
                    //draw bottom border
                    if (mBorderVisible) {
                        mPaint.setStrokeWidth(mHorizontalSpacing);
                        if (itemSize == 1) {
                            Path path = new Path();
                            path.moveTo(myL - mHorizontalSpacing
                                    , myB + mHorizontalSpacing / 2);
                            path.lineTo(myR + mHorizontalSpacing
                                    , myB + mHorizontalSpacing / 2);
                            c.drawPath(path, mPaint);
                        } else if (isLastGridColumn(viewPosition, itemSize, columnSize)) {
                            Path path = new Path();
                            path.moveTo(myL - mHorizontalSpacing
                                    , myB + mHorizontalSpacing / 2);
                            path.lineTo(myR + mHorizontalSpacing
                                    , myB + mHorizontalSpacing / 2);
                            c.drawPath(path, mPaint);
                        } else if (isFirstGridColumn(viewPosition, columnSize)) {
                            mPaint.setStrokeWidth(mVerticalSpacing);
                            Path path = new Path();
                            path.moveTo(myL - mHorizontalSpacing
                                    , myB + mVerticalSpacing / 2);
                            path.lineTo(myR + mHorizontalSpacing
                                    , myB + mVerticalSpacing / 2);
                            c.drawPath(path, mPaint);
                        } else {
                            mPaint.setStrokeWidth(mVerticalSpacing);
                            Path path = new Path();
                            path.moveTo(myL
                                    , myB + mVerticalSpacing / 2);
                            path.lineTo(myR + mHorizontalSpacing
                                    , myB + mVerticalSpacing / 2);
                            c.drawPath(path, mPaint);
                        }

                    }
                } else {
                    mPaint.setStrokeWidth(mVerticalSpacing);
                    Path path = new Path();
                    path.moveTo(myL - mHorizontalSpacing
                            , myB + mVerticalSpacing / 2);
                    path.lineTo(myR
                            , myB + mVerticalSpacing / 2);
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

    private void getGridItemOffsets(Rect outRect, int position, int column, int spanCount) {

        if (mBorderVisible) {
            outRect.left = mHorizontalSpacing * (spanCount - column) / spanCount;
            outRect.right = mHorizontalSpacing * (column + 1) / spanCount;
            if (position < spanCount) {
                outRect.top = mVerticalSpacing;
            }
            outRect.bottom = mVerticalSpacing;
        } else {
            outRect.left = mHorizontalSpacing * column / spanCount;
            outRect.right = mHorizontalSpacing * (spanCount - 1 - column) / spanCount;
            if (position >= spanCount) {
                outRect.top = mVerticalSpacing;
            }
        }
    }

    public static class Builder {

        private Param params;

        public Builder(Context context) {

            params = new Param();
        }

        public RecyclerViewGridItemDecoration create() {
            RecyclerViewGridItemDecoration recyclerViewItemDecoration = new RecyclerViewGridItemDecoration();
            recyclerViewItemDecoration.setParams(params);
            return recyclerViewItemDecoration;
        }


        public Builder color(@ColorInt int color) {
            params.color = color;
            return this;
        }

        public Builder color(String color) {
            if (RVItemDecorationUtil.isColorStringWithoutAlpha(color)) {
                params.color = Color.parseColor(color);
            }
            return this;
        }

        public Builder horizontalSpacing(int spacing) {
            if (spacing < 0) {
                spacing = 0;
            }
            if (spacing % 2 != 0) {
                spacing += 1;
            }
            params.horizontalSpacing = spacing;
            return this;
        }

        public Builder verticalSpacing(int spacing) {
            if (spacing < 0) {
                spacing = 0;
            }
            if (spacing % 2 != 0) {
                spacing += 1;
            }
            params.verticalSpacing = spacing;
            return this;
        }

        public Builder borderVisible(boolean borderVisible) {
            params.borderVisible = borderVisible;
            return this;
        }
    }

    private static class Param {

        public int color = Color.parseColor(DEFAULT_COLOR);
        public int horizontalSpacing = 0;
        public int verticalSpacing = 0;
        public boolean borderVisible;
    }
}
