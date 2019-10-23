package com.arjinmc.recyclerviewdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView Grid Space Decoration
 * Created by Eminem Lo on 2019-09-24.
 * email: arjinmc@hotmail.com
 */
public class RecyclerViewGridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontalSpacing;
    private int mVerticalSpacing;

    /**
     * show edge
     */
    private boolean mBorderVisible;

    public RecyclerViewGridSpaceItemDecoration() {

    }

    public RecyclerViewGridSpaceItemDecoration(int spacing, boolean borderVisible) {
        mHorizontalSpacing = spacing;
        mVerticalSpacing = spacing;
        mBorderVisible = borderVisible;
    }

    public RecyclerViewGridSpaceItemDecoration(int horizontalSpacing, int verticalSpacing, boolean borderVisible) {
        mHorizontalSpacing = horizontalSpacing;
        mVerticalSpacing = verticalSpacing;
        mBorderVisible = borderVisible;
    }

    public RecyclerViewGridSpaceItemDecoration(int spacing) {
        mHorizontalSpacing = spacing;
        mVerticalSpacing = spacing;
    }

    public RecyclerViewGridSpaceItemDecoration(int horizontalSpacing, int verticalSpacing) {
        mHorizontalSpacing = horizontalSpacing;
        mVerticalSpacing = verticalSpacing;
    }

    public void setParams(Param params) {
        if (params.margin > 0) {
            mHorizontalSpacing = params.margin;
            mVerticalSpacing = params.margin;
        } else {
            mHorizontalSpacing = params.marginHorizontal;
            mVerticalSpacing = params.marginVertical;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildCount() == 0) {
            return;
        }

        if (parent.getLayoutManager() instanceof GridLayoutManager) {

            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            if (layoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                try {
                    throw new IllegalAccessException("RecyclerViewGridSpaceItemDecoration only support GridLayoutManager/StaggeredGridLayoutManager Vertical");
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
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
            if (layoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                try {
                    throw new IllegalAccessException("RecyclerViewGridSpaceItemDecoration only support GridLayoutManager/StaggeredGridLayoutManager Vertical");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                }
            }

            int position = parent.getChildAdapterPosition(view);
            int spanCount = layoutManager.getSpanCount();
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            int column = lp.getSpanIndex();
            getGridItemOffsets(outRect, position, column, spanCount);
        } else {
            try {
                throw new IllegalAccessException("RecyclerViewGridSpaceItemDecoration only support GridLayoutManager/StaggeredGridLayoutManager");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
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

        private Context context;
        private Param param;

        public Builder(Context context) {
            this.context = context;
            param = new Param();
        }

        public Builder margin(int margin) {
            if (margin < 0) {
                margin = 0;
            }
            param.margin = margin;
            return this;
        }

        public Builder marginHorizontal(int margin) {
            if (margin < 0) {
                margin = 0;
            }
            param.marginHorizontal = margin;
            return this;
        }

        public Builder marginVertical(int margin) {
            if (margin < 0) {
                margin = 0;
            }
            param.marginVertical = margin;
            return this;
        }

        public Builder borderVisible(boolean visible) {
            param.borderVisible = visible;
            return this;
        }

        public RecyclerViewGridSpaceItemDecoration create() {
            RecyclerViewGridSpaceItemDecoration recyclerViewGridSpaceItemDecoration =
                    new RecyclerViewGridSpaceItemDecoration();
            recyclerViewGridSpaceItemDecoration.setParams(param);
            return recyclerViewGridSpaceItemDecoration;

        }

    }

    public static class Param {

        public int margin;
        public int marginHorizontal;
        public int marginVertical;
        public boolean borderVisible;
    }
}