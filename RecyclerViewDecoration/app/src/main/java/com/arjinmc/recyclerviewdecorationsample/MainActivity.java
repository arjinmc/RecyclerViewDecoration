package com.arjinmc.recyclerviewdecorationsample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.arjinmc.expandrecyclerview.adapter.RecyclerViewAdapter;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewSingleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.recyclerviewdecoration.RecyclerViewItemDecoration;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private String[] mStyleStrs;
    private RecyclerView mRvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStyleStrs = getResources().getStringArray(R.array.style_list);

        mRvData = (RecyclerView) findViewById(R.id.rv_data);
        //set layout manager
        RecyclerViewStyleHelper.toLinearLayout(mRvData, LinearLayout.VERTICAL);
        //add itemdecoration
        mRvData.addItemDecoration(
                new RecyclerViewItemDecoration.Builder(this).color(Color.GRAY).thickness(1).create());
        mRvData.setAdapter(new RecyclerViewAdapter<>(this, Arrays.asList(mStyleStrs)
                , R.layout.item_main_list, new RecyclerViewSingleTypeProcessor<String>() {
            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, final int position, String data) {
                TextView tvTitle = holder.getView(R.id.tv_title);
                tvTitle.setText(data);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                jumpTo(CommonStyleActivity.class);
                                break;
                            case 1:
                                jumpTo(SpacingStyleActivity.class);
                                break;
                            case 2:
                                jumpTo(IgnoreStyleActivity.class);
                                break;
                            case 3:
                                jumpTo(StickyHeadActivity.class);
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        }));
    }


    private void jumpTo(Class clz) {
        startActivity(new Intent(this, clz));
    }
}
