package com.a007inthecity.imove;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mDealsDesc = new ArrayList<>();
    private ArrayList<String> mDealsPrice = new ArrayList<>();
    private ArrayList<Drawable> mDealImage = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapter(Context mContext, ArrayList<String> mDealsDesc, ArrayList<String> mDealsPrice, ArrayList<Drawable> mDealImage) {
        this.mDealsDesc = mDealsDesc;
        this.mDealsPrice = mDealsPrice;
        this.mDealImage = mDealImage;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.deals_img.setImageDrawable(mDealImage.get(position));
        holder.deals_desc.setText(mDealsDesc.get(position));
        holder.deals_price.setText(mDealsPrice.get(position));

    }

    @Override
    public int getItemCount() {
        return mDealsDesc.size();
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
