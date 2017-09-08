package com.arjinmc.recyclerviewdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    private int mMode;

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

}
