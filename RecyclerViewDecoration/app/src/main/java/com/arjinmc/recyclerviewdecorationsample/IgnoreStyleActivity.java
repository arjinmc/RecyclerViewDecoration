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
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewMultipleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.recyclerviewdecoration.RecyclerViewItemDecoration;
import com.arjinmc.recyclerviewdecorationsample.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Ignore some view type that won't be drew itemdecoration
 * Created by Eminem Lo on 2018/4/4.
 * email: arjinmc@hotmail.com
 */
public class IgnoreStyleActivity extends AppCompatActivity {

    private final int TITLE_COUNT = 100;
    private RecyclerView mRecyclerView;
    private RadioGroup mRgMode;

    RecyclerViewItemDecoration mCurrentItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ignore);

        getSupportActionBar().setSubtitle("Ignore Style");

        mRecyclerView = findViewById(R.id.rv_data);

        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < TITLE_COUNT; i++) {
            Car car = new Car();
            car.setUuid(UUID.randomUUID().toString());
            car.setBrand("brand" + i);
            car.setType("type" + i * 2);
            carList.add(car);
        }


        mRecyclerView.setAdapter(new RecyclerViewAdapter(this, carList
                , new int[]{R.layout.item_rv_group, R.layout.item_rv_data}
                , new RecyclerViewMultipleTypeProcessor<Car>() {
            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, int position, Car car) {

                //if it's group type
                if (getItemViewType(position) == 0) {
                    TextView tvBrand = holder.getView(R.id.tv_group);
                    tvBrand.setText(car.getBrand());
                    //if it's item type
                } else {
                    TextView tvBrand = holder.getView(R.id.tv_brand);
                    TextView tvType = holder.getView(R.id.tv_type);
                    TextView tvUUID = holder.getView(R.id.tv_uuid);
                    tvBrand.setText(car.getBrand());
                    tvType.setText(car.getType());
                    tvUUID.setText(car.getUuid());
                }

            }

            @Override
            public int getItemViewType(int position) {
                //mark as group type
                if (position % 5 == 0) {
                    return 0;
                }
                //mark as item type
                return 1;
            }
        }));


        mRgMode = findViewById(R.id.rg_mode);
        mRgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_horizontal:
                        setHorizontalMode();
                        break;
                    case R.id.rb_vertical:
                        setVerticalMode();
                        break;
                    default:
                        break;
                }
            }
        });

        setHorizontalMode();
    }


    private void setHorizontalMode() {
        mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        RecyclerViewStyleHelper.toLinearLayout(mRecyclerView, LinearLayout.VERTICAL);
        mCurrentItemDecoration = new RecyclerViewItemDecoration.Builder(this)
//                .color(Color.RED)
                .color("#ff0000")
                .dashWidth(8)
                .dashGap(5)
                .thickness(6)
//                .drawableID(R.drawable.diver)
//                .drawableID(R.drawable.diver_color_no)
                .paddingStart(20)
                .paddingEnd(10)
                .ignoreTypes(new int[]{0})
                .lastLineVisible(true)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

    private void setVerticalMode() {
        mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        RecyclerViewStyleHelper.toLinearLayout(mRecyclerView, LinearLayout.HORIZONTAL);
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
                .ignoreTypes(new int[]{0})
                .lastLineVisible(true)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }


}
