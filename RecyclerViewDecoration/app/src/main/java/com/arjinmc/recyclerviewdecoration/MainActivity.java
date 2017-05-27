package com.arjinmc.recyclerviewdecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String[] titles = null;
    //init title  count for test
    private final int TITLE_COUNT = 100;

    private RecyclerView rvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles = new String[TITLE_COUNT];
        for(int i=0;i<TITLE_COUNT;i++){
            titles[i] = "title"+i;
        }

        rvData = (RecyclerView) findViewById(R.id.rv_data);
        rvData.setAdapter(new DataAdapater());

        //horizonal mode
//        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//        rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
//                .color(Color.RED)
////                .color("#ff0000")
////                .dashWidth(8)
////                .dashGap(5)
//                .thickness(6)
////                .drawableID(R.drawable.diver)
////                .drawableID(R.drawable.diver_color_no)
//                .paddingStart(20)
//                .paddingEnd(10)
//                .firstLineVisible(true)
//                .lastLineVisible(true)
//                .create());

        //vertical mode
//        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
//        rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
//                .mode(RecyclerViewItemDecoration.MODE_VERTICAL)
////                .parent(rvData)
//                .color(Color.RED)
//                .color("#ff0000")
////                .dashWidth(8)
////                .dashGap(5)
////                .thickness(6)
////                .drawableID(R.drawable.diver_vertical)
//                .drawableID(R.drawable.diver_v)
////                .paddingStart(20)
////                .paddingEnd(10)
//                .firstLineVisible(true)
//                .lastLineVisible(true)
//                .create());

        //grid
        rvData.setLayoutManager(new GridLayoutManager(this, 4));
        rvData.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
//                .mode(RecyclerViewItemDecoration.MODE_GRID)
                .parent(rvData)
//                .color(Color.RED)
//                .color("#ff0000")
//                .dashWidth(8)
//                .dashGap(2)
//                .thickness(6)
//                .drawableID(R.drawable.diver_color_no)
                .drawableID(R.drawable.diver_color)
                .gridBottomVisible(true)
                .gridTopVisible(true)
                .gridLeftVisible(true)
                .gridRightVisible(true)
                .create());


    }

    private class DataAdapater extends RecyclerView.Adapter<DataViewHolder>{


        @Override
        public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rv_data, parent, false);
            DataViewHolder holder = new DataViewHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(DataViewHolder holder, final int position) {

            holder.tvTitle.setText(titles[position]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

    private class DataViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;

        public DataViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }


}
