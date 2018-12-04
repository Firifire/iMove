package com.a007inthecity.imove;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mDealsDesc = new ArrayList<>();
    private ArrayList<String> mDealsPrice = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView deals_img;
        TextView deals_desc;
        TextView deals_price;

        public ViewHolder(View itemView) {
            super(itemView);

            deals_img = itemView.findViewById(R.id.img_deals_banner);
            deals_desc = itemView.findViewById(R.id.text_deals_desc);
            deals_price = itemView.findViewById(R.id.text_deals_price);
        }
    }
}
