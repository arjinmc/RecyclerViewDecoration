package com.arjinmc.recyclerviewdecorationsample;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.arjinmc.expandrecyclerview.adapter.RecyclerViewAdapter;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewMultipleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.recyclerviewdecoration.RecyclerViewLinearItemDecoration;
import com.arjinmc.recyclerviewdecoration.RecyclerViewStickyHeadItemDecoration;
import com.arjinmc.recyclerviewdecorationsample.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample for group list
 * Created by Eminem Lo on 2017/9/11.
 * email: arjinmc@hotmail.com
 */

public class StickyHeadActivity extends AppCompatActivity {

    private EditText mEtPosition;
    private Button mBtnAdd;
    private CheckBox mCbIsGroup;
    private int mAddCount = -1;

    //you can use any adapter of RecyclerView
    private RecyclerViewAdapter mAdapter;
    private List<Car> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_head);
        getSupportActionBar().setSubtitle("StickyHead");

        mEtPosition = findViewById(R.id.et_position);
        mBtnAdd = findViewById(R.id.btn_add);
        mCbIsGroup = findViewById(R.id.cb_isGroup);

        RecyclerView rvList = findViewById(R.id.rv_list);
        RecyclerViewStyleHelper.toLinearLayout(rvList, LinearLayout.VERTICAL);
        rvList.addItemDecoration(new RecyclerViewLinearItemDecoration.Builder(this)
                .color(Color.GRAY)
                .thickness(3)
                .create());
        //default group view type is zero
        rvList.addItemDecoration(new RecyclerViewStickyHeadItemDecoration.Builder().groupViewType(0).create());
        mDataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Car car = new Car();
            car.setBrand("Car Band" + i);
            car.setType("Car Type" + i * i);
            if (i % 10 == 0) {
                car.setGroup("Group " + (i / 10));
            }
            mDataList.add(car);
        }

        mAdapter = new RecyclerViewAdapter<>(this, mDataList
                , new int[]{R.layout.item_rv_group, R.layout.item_rv_data}
                , new RecyclerViewMultipleTypeProcessor<Car>() {

            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, final int position, Car car) {
                if (getItemViewType(position) == 0) {
                    TextView tvGroup = holder.getView(R.id.tv_group);
                    tvGroup.setText(car.getGroup());
                } else {
                    TextView tvContent = holder.getView(R.id.tv_brand);
                    tvContent.setText("Car brand:" + car.getBrand() + " / type: " + car.getBrand());

                    tvContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(StickyHeadActivity.this
                                    , "position: " + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (mDataList.get(position).getGroup() != null) {
                    return 0;
                }
                return 1;
            }
        });

        rvList.setAdapter(mAdapter);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = mEtPosition.getText().toString();
                if (TextUtils.isEmpty(inputString)) {
                    return;
                }
                try {
                    Integer position = Integer.valueOf(inputString);
                    if (position >= 0) {
                        mAddCount++;
                        Car car = new Car();
                        car.setBrand("Car Band add " + mAddCount);
                        car.setType("Car Type add" + mAddCount);
                        if (mCbIsGroup.isChecked()) {
                            car.setGroup("Group Add " + mAddCount);
                        }
                        mDataList.add(position, car);
                        mAdapter.notifyDataChanged(mDataList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
