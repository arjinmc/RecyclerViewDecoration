package com.arjinmc.recyclerviewdecoration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.NoCopySpan;

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

        public Builder mode(@RecyclerViewItemDecoration.Mode int mode) {
            param.mode = mode;
            return this;
        }

        public Builder parent(RecyclerView parent) {
            param.parent = parent;
            return this;
        }

        public RecyclerViewItemDecoration create() {

            return new RecyclerViewItemDecoration.Builder(context)
                            .thickness(param.margin)
                            .gridHorizontalSpacing(param.marginHorizontal)
                            .gridVerticalSpacing(param.marginVertical)
                            .mode(param.mode)
                            .parent(param.parent)
                            .color(Color.TRANSPARENT)
                            .create();
        }

    }

    public static class Param {

        public int margin;
        public int marginHorizontal;
        public int marginVertical;
        public int mode = RecyclerViewItemDecoration.MODE_HORIZONTAL;
        ;
        public RecyclerView parent;

    }
}
