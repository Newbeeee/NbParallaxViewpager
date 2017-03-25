package com.newbeeee.qt.nbhosthomeforaribnb;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private RecyclerView recyclerView;
    private ScrollHolder scrollHolder;
    private int scrollY;
    private LinearLayoutManager manager;


    public CheeseListFragment(ScrollHolder scrollHolder) {
        this.scrollHolder = scrollHolder;
    }

    @Override
    public void onDetach() {
        scrollHolder = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(
                R.layout.fragment_cheese_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.nb_recyclerView);
        manager = new LinearLayoutManager(getActivity());
        setRecyclerView(recyclerView);
        return view;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new CheeseListAdapter(getActivity(),
                getRandomSublist(Cheeses.sCheeseStrings, 30)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                Log.e("scrollY:", String.valueOf(scrollY));
                if (scrollHolder != null) {
                    scrollHolder.onRecyclerViewScroll(scrollY);
                }
            }
        });

    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    @Override
    public void onRecyclerViewScroll(int dy) {

    }

    @Override
    public void adjustScroll(int scrollY) {

    }
}
