package com.arjinmc.recyclerviewdecoration;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Sticky Head for RecyclerView
 * Created by Eminem Lo on 2017/9/12.
 * email: arjinmc@hotmail.com
 * <p>
 * <p> Attention!</p>
 * RecyclerViewStickyHeadItemDecoration
 * only support of the RecyclerView with LinearLayoutManager.VERTICAL
 */

public class RecyclerViewStickyHeadItemDecoration extends RecyclerView.ItemDecoration {

    private Bitmap mHeadBitmap, mLastHeadBitmap;
    /**
     * cache the view converted to bitmap for the group views
     */
    private ArrayMap<Integer, Bitmap> mGroupViewsMap;
    private RecyclerView mParent;
    /**
     * mark the group view's width and height
     */
    private int mViewWidth, mViewHeight;
    private Paint mPaint;
    /**
     * mark for smooth move y axis for head
     */
    private int mMoveY;
    /**
     * mark for the smooth move if scrolling up
     */
    private boolean isScrollUp;
    /**
     * mark the current group view index on the top
     */
    private int mCurrentIndex = -1;

    /**
     * default group view type is zero.
     */
    private int mGroupViewType = 0;
    /**
     * mark for smooth mode
     */
    private boolean isSmooth = false;

    public void setParam(Param param) {
        mGroupViewType = param.groupViewType;
        isSmooth = param.isSmooth;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (mPaint == null) mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {

            if (mHeadBitmap == null) {
                View view = mParent.findChildViewUnder(0, mViewHeight + 1);
                int viewPosition = mParent.getChildAdapterPosition(view);
                mGroupViewsMap.put(viewPosition, convertViewToBimap(view));

                mHeadBitmap = mGroupViewsMap.get(viewPosition);
                mLastHeadBitmap = mGroupViewsMap.get(viewPosition);

                if (mViewWidth == 0) {
                    mViewWidth = view.getMeasuredWidth();
                    mViewHeight = view.getMeasuredHeight();
                }

            }

            if (isSmooth) {

                if (mMoveY == 0) {
                    c.drawBitmap(mHeadBitmap, 0, 0, mPaint);
                } else {
                    int lastKey;
                    if (isScrollUp) {
                        lastKey = mCurrentIndex;
                    } else {
                        lastKey = mCurrentIndex - 1;
                    }
                    if (lastKey >= 0) {
                        mLastHeadBitmap = mGroupViewsMap.get(mGroupViewsMap.keyAt(lastKey));
                    }
                    c.drawBitmap(mLastHeadBitmap, 0, -(mViewHeight - mMoveY), mPaint);
                }
            } else {
                c.drawBitmap(mHeadBitmap, 0, 0, mPaint);
            }

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (mParent == null) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation()
                        != LinearLayoutManager.VERTICAL) {
                    throw new IllegalArgumentException("Only support LinearLayoutManager.VERTICAL");
                }
            }
            mParent = parent;
            mGroupViewsMap = new ArrayMap<>();
            setListener();
        }

    }

    /**
     * add listener to RecyclerView
     */
    private void setListener() {

        mParent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isScrollToTop()) {
                        mHeadBitmap = mGroupViewsMap.get(mGroupViewsMap.keyAt(0));
                    } else if (isScrollToBottom()) {
                        mHeadBitmap = mGroupViewsMap.get(mGroupViewsMap.keyAt(mGroupViewsMap.size() - 1));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    updateHead(dy);
                }
            }
        });

    }

    /**
     * update sticky head
     *
     * @param moveY
     */
    private void updateHead(int moveY) {

        View temp = mParent.findChildViewUnder(0, mViewHeight + 1);
        int tempPosition = mParent.getChildAdapterPosition(temp);
        if (tempPosition != -1
                && mParent.getAdapter().getItemViewType(tempPosition) == mGroupViewType) {

            //add the view converted to bitmap for group views
            if (mGroupViewsMap.get(tempPosition) == null) {
                mGroupViewsMap.put(tempPosition, convertViewToBimap(temp));
            }
            mMoveY = temp.getTop();
            if (moveY > 0) {
                isScrollUp = false;
                if (mGroupViewsMap.indexOfKey(tempPosition) != mCurrentIndex) {
                    mCurrentIndex = mGroupViewsMap.indexOfKey(tempPosition);
                    mHeadBitmap = mGroupViewsMap.get(tempPosition);
                }
            } else if (moveY < 0) {
                isScrollUp = true;
                int index = mGroupViewsMap.indexOfKey(tempPosition) - 1;
                if (index >= 0 && mCurrentIndex != index) {
                    mCurrentIndex = index;
                    mHeadBitmap = mGroupViewsMap.get(
                            mGroupViewsMap.keyAt(mCurrentIndex));
                }
            }
        } else {
            mMoveY = 0;
        }
    }

    /**
     * if it is scroll to RecyclerView top
     *
     * @return true/false
     */
    private boolean isScrollToTop() {
        if (mParent == null) return false;
        LinearLayoutManager layoutManager = (LinearLayoutManager) mParent.getLayoutManager();
        if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0)
            return true;
        return false;
    }

    /**
     * if it is scroll to RecyclerView bottom
     *
     * @return
     */
    private boolean isScrollToBottom() {
        if (mParent == null || mParent.getAdapter() == null) return false;
        LinearLayoutManager layoutManager = (LinearLayoutManager) mParent.getLayoutManager();
        if (layoutManager.findLastCompletelyVisibleItemPosition() == mParent.getAdapter().getItemCount() - 1)
            return true;
        return false;
    }

    /**
     * convert view to bitmap
     *
     * @param view
     * @return
     */
    private Bitmap convertViewToBimap(View view) {
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.draw(c);
        return b;
    }

    /**
     * Builder to create Sticky Head
     */
    public static class Builder {

        private Param param;

        public Builder() {
            param = new Param();
        }

        public Builder groupViewType(int groupViewType) {
            param.groupViewType = groupViewType;
            return this;
        }

        public Builder isSmooth(boolean isSmooth) {
            param.isSmooth = isSmooth;
            return this;
        }

        public RecyclerViewStickyHeadItemDecoration create() {
            RecyclerViewStickyHeadItemDecoration recyclerViewStickyHeadItemDecoration = new RecyclerViewStickyHeadItemDecoration();
            recyclerViewStickyHeadItemDecoration.setParam(param);
            return recyclerViewStickyHeadItemDecoration;
        }
    }

    private static class Param {

        public int groupViewType;
        public boolean isSmooth;
    }
}
