package com.arjinmc.recyclerviewdecorationsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.arjinmc.expandrecyclerview.adapter.RecyclerViewAdapter;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewSingleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.recyclerviewdecoration.RecyclerViewItemDecoration;
import com.arjinmc.recyclerviewdecorationsample.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eminem Lo on 2017/9/7.
 * email: arjinmc@hotmail.com
 */

public class CommonStyleActivity extends AppCompatActivity {

    private final int TITLE_COUNT = 100;
    private RecyclerView mRecyclerView;
    private RadioGroup mRgMode;
    private RecyclerView.ItemDecoration mCurrentItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_style);
        getSupportActionBar().setSubtitle("Common Style");

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_data);

        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < TITLE_COUNT; i++) {
            Car car = new Car();
            car.setUuid(UUID.randomUUID().toString());
            car.setBrand("brand" + i);
            car.setType("type" + i * 2);
            carList.add(car);
        }
        mRecyclerView.setAdapter(new RecyclerViewAdapter(this, carList, R.layout.item_rv_data
                , new RecyclerViewSingleTypeProcessor<Car>() {
            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, int position, Car car) {
                TextView tvBrand = holder.getView(R.id.tv_brand);
                TextView tvType = holder.getView(R.id.tv_type);
                TextView tvUUID = holder.getView(R.id.tv_uuid);
                tvBrand.setText(car.getBrand());
                tvType.setText(car.getType());
                tvUUID.setText(car.getUuid());
            }
        }));

        mRgMode = (RadioGroup) findViewById(R.id.rg_mode);
        mRgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_horizontal:
                        setHorzizonalMode();
                        break;
                    case R.id.rb_vertical:
                        setVerticalMode();
                        break;
                    case R.id.rb_grid:
                        setGridMode();
                        break;
                }
            }
        });

        setHorzizonalMode();

    }

    private void setHorzizonalMode() {
        if (mCurrentItemDecoration != null)
            mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        RecyclerViewStyleHelper.toLinearLayout(mRecyclerView, LinearLayout.VERTICAL);
        mCurrentItemDecoration = new RecyclerViewItemDecoration.Builder(this)
                .color(Color.RED)
//                .color("#ff0000")
//                .dashWidth(8)
//                .dashGap(5)
                .thickness(6)
//                .drawableID(R.drawable.diver)
//                .drawableID(R.drawable.diver_color_no)
                .paddingStart(20)
                .paddingEnd(10)
                .firstLineVisible(true)
                .lastLineVisible(true)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

    private void setVerticalMode() {
        if (mCurrentItemDecoration != null)
            mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        RecyclerViewStyleHelper.toLinearLayout(mRecyclerView, LinearLayout.HORIZONTAL);
        mCurrentItemDecoration = new RecyclerViewItemDecoration.Builder(this)
                .color(Color.RED)
                .color("#ff0000")
//                .dashWidth(8)
//                .dashGap(5)
//                .thickness(6)
//                .drawableID(R.drawable.diver_vertical)
                .drawableID(R.drawable.diver_v)
//                .paddingStart(20)
//                .paddingEnd(10)
                .firstLineVisible(true)
                .lastLineVisible(true)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

    private void setGridMode() {
        if (mCurrentItemDecoration != null)
            mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        RecyclerViewStyleHelper.toGridView(mRecyclerView, 4);
        mCurrentItemDecoration = new RecyclerViewItemDecoration.Builder(this)
//                .color(Color.RED)
                .color("#b0ff0000")
//                .dashWidth(8)
//                .dashGap(2)
                .thickness(6)
//                .drawableID(R.drawable.diver_color_no)
//                .drawableID(R.drawable.diver_color)
                .gridBottomVisible(true)
                .gridTopVisible(true)
                .gridLeftVisible(true)
                .gridRightVisible(true)
                .gridHorizontalSpacing(20)
                .gridVerticalSpacing(10)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

}
