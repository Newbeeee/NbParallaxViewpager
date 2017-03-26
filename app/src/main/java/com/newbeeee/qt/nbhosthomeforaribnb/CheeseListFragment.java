package com.newbeeee.qt.nbhosthomeforaribnb;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xiuxiongding on 2017/3/25.
 */

public class CheeseListFragment extends Fragment implements ScrollHolder {

    protected static final String ARG_POSITION = "position";
    private RecyclerView mRecyclerView;
    private ScrollHolder mScrollHolder;
    private int mScrollY;
    private LinearLayoutManager mLayoutMgr;
    private int mPosition;

    public static Fragment newInstance(int position) {
        CheeseListFragment fragment = new CheeseListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public CheeseListFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mScrollHolder = (ScrollHolder) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement ScrollHolder");
        }
    }

    @Override
    public void onDetach() {
        mScrollHolder = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPosition = getArguments().getInt(ARG_POSITION);
        View view = inflater.inflate(R.layout.fragment_cheese_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.nb_recyclerView);
        setRecyclerView();
        return view;
    }

    private void setRecyclerVIewOnScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;

                if (mScrollHolder != null) {
                    mScrollHolder.onRecyclerViewScroll(mRecyclerView, dx, dy, mScrollY, mPosition);
                }
            }
        });
    }

    private void setRecyclerView() {
        mLayoutMgr = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutMgr);
        mRecyclerView.setAdapter(new CheeseListAdapter(getRandomSublist(Cheeses.sCheeseStrings, 30)));
        setRecyclerVIewOnScrollListener();
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    private void setScrollOnLayoutManager(int scrollY) {
        mLayoutMgr.scrollToPositionWithOffset(0, -scrollY);
    }

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition) {

    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mRecyclerView == null) {
            return;
        }
        mScrollY = headerHeight - scrollHeight;
        setScrollOnLayoutManager(mScrollY);
    }
}
