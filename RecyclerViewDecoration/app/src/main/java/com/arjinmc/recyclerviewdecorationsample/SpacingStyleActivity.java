package com.arjinmc.recyclerviewdecorationsample;

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
import com.arjinmc.recyclerviewdecoration.RecyclerViewSpaceItemDecoration;
import com.arjinmc.recyclerviewdecorationsample.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eminem Lo on 2017/9/7.
 * email: arjinmc@hotmail.com
 */

public class SpacingStyleActivity extends AppCompatActivity {

    private final int TITLE_COUNT = 100;
    private RecyclerView mRecyclerView;
    private RadioGroup mRgMode;
    private RecyclerView.ItemDecoration mCurrentItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_style);
        getSupportActionBar().setSubtitle("Spacing Style");

        mRecyclerView = findViewById(R.id.rv_data);

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

        mRgMode = findViewById(R.id.rg_mode);
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
                    default:
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
        mCurrentItemDecoration = new RecyclerViewSpaceItemDecoration.Builder(this)
                .margin(10)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

    private void setVerticalMode() {
        if (mCurrentItemDecoration != null)
            mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        RecyclerViewStyleHelper.toLinearLayout(mRecyclerView, LinearLayout.HORIZONTAL);
        mCurrentItemDecoration = new RecyclerViewSpaceItemDecoration.Builder(this)
                .margin(20)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

    private void setGridMode() {
        if (mCurrentItemDecoration != null)
            mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        RecyclerViewStyleHelper.toGridView(mRecyclerView, 4);
        mCurrentItemDecoration = new RecyclerViewSpaceItemDecoration.Builder(this)
                //if horizontal and vertical spacing is the same size,just use margin(int size)
//                .margin(10)
                .marginHorizontal(10)
                .marginVertical(20)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

}
