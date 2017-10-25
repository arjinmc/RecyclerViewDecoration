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
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewMultipleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewSingleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.recyclerviewdecoration.RecyclerViewSpaceItemDecoration;
import com.arjinmc.recyclerviewdecoration.RecyclerViewStickyHeadItemDecoration;
import com.arjinmc.recyclerviewdecorationsample.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eminem Lo on 2017/10/25.
 * email: arjinmc@hotmail.com
 */

public class StickyHeadStyleActivity extends AppCompatActivity {

    private final int TITLE_COUNT = 100;
    private RecyclerView mRecyclerView;
    private RadioGroup mRgMode;
    private RecyclerView.ItemDecoration mCurrentItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_head_style);
        getSupportActionBar().setSubtitle("Spacing Style");

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_data);
        RecyclerViewStyleHelper.toLinearLayout(mRecyclerView, LinearLayout.VERTICAL);
        mRecyclerView.addItemDecoration(new RecyclerViewSpaceItemDecoration.Builder(this)
                .margin(2)
                .create());

        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < TITLE_COUNT; i++) {
            Car car = new Car();
            car.setUuid(UUID.randomUUID().toString());
            car.setBrand("brand" + i);
            car.setType("type" + i * 2);
            carList.add(car);
        }
        mRecyclerView.setAdapter(new RecyclerViewAdapter(this, carList, new int[]{R.layout.item_rv_group
                , R.layout.item_rv_data}
                , new RecyclerViewMultipleTypeProcessor<Car>() {
            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, int position, Car car) {
                if (getItemViewType(position) != 0) {
                    TextView tvBrand = holder.getView(R.id.tv_brand);
                    TextView tvType = holder.getView(R.id.tv_type);
                    TextView tvUUID = holder.getView(R.id.tv_uuid);
                    tvBrand.setText(car.getBrand());
                    tvType.setText(car.getType());
                    tvUUID.setText(car.getUuid());
                } else {
                    TextView tvGroup = holder.getView(R.id.tv_group);
                    tvGroup.setText(car.getBrand());
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position % 10 == 0)
                    return 0;
                return 1;
            }
        }));

        mRgMode = (RadioGroup) findViewById(R.id.rg_mode);
        mRgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_normail:
                        setNormalMode();
                        break;
                    case R.id.rb_smooth:
                        setSmoothMode();
                        break;
                }
            }
        });

        setNormalMode();

    }

    private void setNormalMode() {
        if (mCurrentItemDecoration != null)
            mRecyclerView.removeItemDecoration(mCurrentItemDecoration);

        mCurrentItemDecoration = new RecyclerViewStickyHeadItemDecoration.Builder()
//                .groupViewType(0)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

    private void setSmoothMode() {
        if (mCurrentItemDecoration != null)
            mRecyclerView.removeItemDecoration(mCurrentItemDecoration);
        mCurrentItemDecoration = new RecyclerViewStickyHeadItemDecoration.Builder()
//                .groupViewType(0)
                .isSmooth(true)
                .create();
        mRecyclerView.addItemDecoration(mCurrentItemDecoration);
    }

}
