package com.newbeeee.qt.nbhosthomeforaribnb.recyclerContent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newbeeee.qt.parallax_library.BaseAdjustScrollFragment;
import com.newbeeee.qt.nbhosthomeforaribnb.R;

/**
 * Created by xiuxiongding on 2017/3/25.
 */

public class CheeseRvFragment extends BaseAdjustScrollFragment {

    private RecyclerView mRecyclerView;
    private int mScrollY;
    private LinearLayoutManager mLayoutMgr;


    public static Fragment newInstance(int position) {
        CheeseRvFragment fragment = new CheeseRvFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cheese_rv, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.nb_recyclerView);
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        mLayoutMgr = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutMgr);
        mRecyclerView.setAdapter(new CheeseRvAdapter(Cheeses.getRandomSublist(Cheeses.sCheeseStrings, 30)));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;
                if (mHostView != null) {
                    mHostView.onScrollingContentScroll(mScrollY, mPosition);
                }
            }
        });
    }

    @Override
    public void adjustScroll(int scrollTranslation) {
        if (mRecyclerView == null) {
            return;
        }
        mScrollY = -scrollTranslation;
        mLayoutMgr.scrollToPositionWithOffset(0, scrollTranslation);
    }
}
