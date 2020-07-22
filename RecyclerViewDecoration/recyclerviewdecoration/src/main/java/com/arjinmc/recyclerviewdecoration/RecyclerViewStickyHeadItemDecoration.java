package com.arjinmc.recyclerviewdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

    private View mStickyView;
    private RecyclerView mParent;
    /**
     * mark the group view's width and height
     */
    private int mStickyViewHeight;
    private int mStickyViewMarginTop;

    /**
     * default group view type is zero.
     */
    private int mGroupViewType = 0;

    /**
     * position list
     */
    private List<Integer> mStickyPositionList = new ArrayList<>();

    /**
     * bind position of sticky view
     */
    private int mBindStickyViewPosition = -1;
    /**
     * current StickyView Holder
     */
    private RecyclerView.ViewHolder mCurrentStickyViewHolder;

    public void setParam(Param param) {
        mGroupViewType = param.groupViewType;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (parent == null || parent.getAdapter() == null || parent.getAdapter().getItemCount() == 0
                || parent.getLayoutManager() == null) {
            return;
        }

        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();

            boolean findStickView = false;

            int itemCount = parent.getChildCount();
            for (int i = 0; i < itemCount; i++) {
                View view = parent.getChildAt(i);

                if (isGroupViewType(parent.getChildAdapterPosition(view))) {
                    findStickView = true;
                    if (mCurrentStickyViewHolder == null) {
                        mCurrentStickyViewHolder = parent.getAdapter().onCreateViewHolder(parent, mGroupViewType);
                        mStickyView = mCurrentStickyViewHolder.itemView;
                    }
                    if (view.getTop() <= 0) {
                        bindDataForStickyView(layoutManager.findFirstVisibleItemPosition());
                    } else {
                        if (mStickyPositionList.size() > 0) {
                            if (mStickyPositionList.size() == 1) {
                                bindDataForStickyView(mStickyPositionList.get(0));
                            } else {
                                int currentPosition = layoutManager.findFirstVisibleItemPosition() + i;
                                int indexOfCurrentPosition = mStickyPositionList.lastIndexOf(currentPosition);
                                bindDataForStickyView(mStickyPositionList.get(indexOfCurrentPosition - 1));
                            }
                        }
                    }

                    if (view.getTop() > 0 && view.getTop() <= mStickyViewHeight) {
                        mStickyViewMarginTop = mStickyViewHeight - view.getTop();
                    } else {
                        mStickyViewMarginTop = 0;

                        View nextStickyView = getNextStickyView();
                        if (nextStickyView != null && nextStickyView.getTop() <= mStickyViewHeight) {
                            mStickyViewMarginTop = mStickyViewHeight - nextStickyView.getTop();
                        }

                    }

                    drawStickyView(c);
                    break;
                }
            }

            if (!findStickView) {
                mStickyViewMarginTop = 0;
                if (layoutManager.findFirstVisibleItemPosition() + parent.getChildCount() == parent.getAdapter().getItemCount()
                        && mStickyPositionList.size() > 0) {
                    bindDataForStickyView(mStickyPositionList.get(mStickyPositionList.size() - 1));
                }
                drawStickyView(c);
            }
        } else {
            try {
                throw new IllegalAccessException("Only support RecyclerView LinearLayoutManager.VERTICAL");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (mParent == null) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation()
                        != RecyclerView.VERTICAL) {
                    throw new IllegalArgumentException("Only support LinearLayoutManager.VERTICAL");
                }
            }
            mParent = parent;
            initListener();
        }
    }

    /**
     * add listener to RecyclerView
     */
    private void initListener() {

        // update sticky position list if data of RecyclerView has changed
        mParent.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                initStickyPositionList();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                initStickyPositionList();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                initStickyPositionList();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                initStickyPositionList();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                initStickyPositionList();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                initStickyPositionList();
            }
        });
        initStickyPositionList();
    }

    private void initStickyPositionList() {
        if (mParent == null || mParent.getAdapter() == null) {
            return;
        }
        if (mStickyPositionList == null) {
            mStickyPositionList = new ArrayList<>();
        } else {
            mStickyPositionList.clear();
        }
        int itemCount = mParent.getAdapter().getItemCount();
        if (itemCount > 0) {
            for (int i = 0; i < itemCount; i++) {
                if (isGroupViewType(i)) {
                    mStickyPositionList.add(i);
                }
            }
            View lGroupView = mParent.getChildAt(mStickyPositionList.get(0));
            if (lGroupView != null) {
                lGroupView.measure(0, 0);
                mStickyViewHeight = lGroupView.getMeasuredHeight();
            }
        }
    }

    /**
     * draw sticky view
     *
     * @param canvas
     */
    private void drawStickyView(Canvas canvas) {
        if (mStickyView == null) {
            return;
        }
        int saveCount = canvas.save();
        canvas.translate(0, -mStickyViewMarginTop);
        mStickyView.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /**
     * bind data for sticky view
     *
     * @param position
     */
    private void bindDataForStickyView(int position) {
        if (mBindStickyViewPosition == position || mCurrentStickyViewHolder == null) {
            return;
        }
        mBindStickyViewPosition = position;
        mParent.getAdapter().onBindViewHolder(mCurrentStickyViewHolder, mBindStickyViewPosition);
        mStickyView = mCurrentStickyViewHolder.itemView;
        measureStickyView();
        mStickyViewHeight = mCurrentStickyViewHolder.itemView.getBottom() - mCurrentStickyViewHolder.itemView.getTop();
    }

    /**
     * get the next sticky view
     *
     * @return view
     */
    private View getNextStickyView() {
        if (mParent == null) {
            return null;
        }
        int num = 0;
        View nextStickyView = null;
        int size = mParent.getChildCount();
        for (int i = 0; i < size; i++) {
            View view = mParent.getChildAt(i);
            if (isGroupViewType(mParent.getChildAdapterPosition(view))) {
                nextStickyView = view;
                num++;
            }
            if (num == 2) {
                break;
            }
        }
        return num >= 2 ? nextStickyView : null;
    }

    /**
     * measure sticky view
     */
    private void measureStickyView() {
        if (mParent == null || mStickyView == null || !mStickyView.isLayoutRequested()) {
            return;
        }

        int parentWidth = mParent.getMeasuredWidth();
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY);
        int heightSpec;

        ViewGroup.LayoutParams layoutParams = mStickyView.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }

        mStickyView.measure(widthSpec, heightSpec);
        mStickyView.layout(0, 0, mStickyView.getMeasuredWidth(), mStickyView.getMeasuredHeight());
    }

    /**
     * chech the view of target position is GroupView type
     *
     * @param position
     * @return
     */
    private boolean isGroupViewType(int position) {
        if (mParent == null || mParent.getAdapter() == null || position < 0) {
            return false;
        }
        if (mParent.getAdapter().getItemCount() != 0
                && mParent.getAdapter().getItemCount() > position
                && mParent.getAdapter().getItemViewType(position) == mGroupViewType) {

            return true;
        }
        return false;
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

        public RecyclerViewStickyHeadItemDecoration create() {
            RecyclerViewStickyHeadItemDecoration recyclerViewStickyHeadItemDecoration = new RecyclerViewStickyHeadItemDecoration();
            recyclerViewStickyHeadItemDecoration.setParam(param);
            return recyclerViewStickyHeadItemDecoration;
        }
    }

    private static class Param {

        public int groupViewType;
    }
}
