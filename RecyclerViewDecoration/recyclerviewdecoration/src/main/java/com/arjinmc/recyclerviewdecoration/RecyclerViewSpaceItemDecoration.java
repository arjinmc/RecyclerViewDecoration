package com.arjinmc.recyclerviewdecoration;

import android.content.Context;
import android.graphics.Color;

/**
 * RecycleView item decoration for only empty spacing
 * Created by Eminem Lo on 2017/9/7.
 * email: arjinmc@hotmail.com
 */

public class RecyclerViewSpaceItemDecoration {

    public RecyclerViewSpaceItemDecoration() {
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

        public Builder marginHorizontal(int margin) {
            param.marginHorizontal = margin;
            return this;
        }

        public Builder marginVertical(int margin) {
            param.marginVertical = margin;
            return this;
        }

        public Builder ignoreTypes(int[] ignoreTypes){
            param.ignoreTypes = ignoreTypes;
            return this;
        }

        public RecyclerViewItemDecoration create() {

            return new RecyclerViewItemDecoration.Builder(context)
                    .thickness(param.margin)
                    .gridHorizontalSpacing(param.marginHorizontal)
                    .gridVerticalSpacing(param.marginVertical)
                    .color(Color.TRANSPARENT)
                    .ignoreTypes(param.ignoreTypes)
                    .create();
        }

    }

    public static class Param {

        public int margin;
        public int marginHorizontal;
        public int marginVertical;
        public int[] ignoreTypes;

    }
}
