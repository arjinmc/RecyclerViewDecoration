package com.arjinmc.recyclerviewdecoration;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecycleView item decoration for only empty spacing
 * Created by Eminem Lo on 2017/9/7.
 * email: arjinmc@hotmail.com
 */

public class RecyclerViewLinearSpaceItemDecoration extends RecyclerView.ItemDecoration {

    public RecyclerViewLinearSpaceItemDecoration() {
        throw new RuntimeException("Use Builder to create!");
    }

    public static class Builder {

        private Context context;
        private Param param;

        public Builder(Context context) {
            this.context = context;
            param = new Param();
        }

        public Builder margin(int margin) {
            param.margin = margin;
            return this;
        }

        public RecyclerViewLinearItemDecoration create() {

            return new RecyclerViewLinearItemDecoration.Builder(context)
                    .thickness(param.margin)
                    .color(Color.TRANSPARENT)
                    .ignoreTypes(param.ignoreTypes)
                    .create();
        }

    }

    public static class Param {

        public int margin;
        public int[] ignoreTypes;

    }
}
