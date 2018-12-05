package com.a007inthecity.imove;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    public static final String TAG = "MainActivity";

    private View accountFragmentView;
    private ArrayList<String> mDealsDesc = new ArrayList<>();
    private ArrayList<String> mDealsPrice = new ArrayList<>();
    private ArrayList<Drawable> mDealsImg = new ArrayList<>();

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accountFragmentView = inflater.inflate(R.layout.fragment_account, container, false);
        initCards();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(accountFragmentView.getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = accountFragmentView.findViewById(R.id.recycler_deals);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(accountFragmentView.getContext(),mDealsDesc,mDealsPrice,mDealsImg);
        recyclerView.setAdapter(recyclerViewAdapter);
        // Inflate the layout for this fragment
        return accountFragmentView;
    }

    private void initCards(){
        mDealsDesc.add("Get RM 3 off any Grande size drink");
        mDealsPrice.add("700 points");
        mDealsImg.add(getResources().getDrawable(R.drawable.starbucks,null));


        mDealsDesc.add("Free Regular-size drink");
        mDealsPrice.add("1200 points");
        mDealsImg.add(getResources().getDrawable(R.drawable.tealive,null));


        mDealsDesc.add("Free upgrade to Large-size meals");
        mDealsPrice.add("400 points");
        mDealsImg.add(getResources().getDrawable(R.drawable.mcd,null));


        mDealsDesc.add("200 points for 500 points");
        mDealsPrice.add("200 points");
        mDealsImg.add(getResources().getDrawable(R.drawable.rapidpenang,null));

//        initRecyclerView();
    }

}
