package com.arjinmc.recyclerviewdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ItemDecoration for Group Style
 * Created by Eminem Lo on 2017/9/8.
 * email: arjinmc@hotmail.com
 */

public class RecyclerViewGroupItemDecoration extends RecyclerView.ItemDecoration {

    public static final int MODE_HORIZONTAL = 0;
    public static final int MODE_VERTICAL = 1;

    @IntDef(value = {MODE_HORIZONTAL, MODE_VERTICAL})
    public @interface Mode {
    }

    private int mParentMode;
    private boolean hasGetParentLayoutMode = false;
    private int mMode;
    private int mGroupType;

    public void setParam(Param param) {
        this.mMode = param.mode;
        this.mGroupType = param.groupType;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (parent.getChildCount() == 0)
            return;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);



    }

    private int getParentLayoutMode(RecyclerView parent) {

        if (parent.getLayoutManager() != null) {
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                mParentMode = RVItemDecorationConst.MODE_GRID;
            } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayout.HORIZONTAL) {
                    mParentMode = RVItemDecorationConst.MODE_VERTICAL;
                } else {
                    mParentMode = RVItemDecorationConst.MODE_HORIZONTAL;
                }
            }
            return mParentMode;
        }
        return RVItemDecorationConst.MODE_UNKNOWN;
    }

    public static class Builder {

        private Context context;
        private Param param;

        public Builder(Context context) {
            this.context = context;
            this.param = new Param();
        }

        public Builder mode(@Mode int mode) {
            param.mode = mode;
            return this;
        }

        public Builder groupType(int groupType) {
            param.groupType = groupType;
            return this;
        }

        public RecyclerViewGroupItemDecoration create() {
            RecyclerViewGroupItemDecoration recyclerviewGroupItemDecoration =
                    new RecyclerViewGroupItemDecoration();
            recyclerviewGroupItemDecoration.setParam(param);
            return recyclerviewGroupItemDecoration;
        }

    }

    public static class Param {
        public int mode;
        public int groupType;
    }
}
