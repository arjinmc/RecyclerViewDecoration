package com.arjinmc.recyclerviewdecoration;

import android.graphics.Color;
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
    private final int TITLE_COUNT = 22;

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

        //horizonal mode,line
//        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//        rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, Color.DKGRAY,5,0,0));

        //horizonal mode, image resouce
//        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//        rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL,this, R.drawable.diver));

        //vertical mode, line
//        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
//        rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_VERTICAL, Color.RED,10,20,10));

        //vertical mode, image resouce
//        rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
//        rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_VERTICAL,this, R.drawable.diver_vertical));
        //this image has no ninepatch
//        rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_VERTICAL,this, R.drawable.diver_v));

        //grid
        rvData.setLayoutManager(new GridLayoutManager(this, 6));
        rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.RED,10,20,10));
//        rvData.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID,this, R.drawable.diver_color));


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
