package com.newbeeee.qt.nbhosthomeforaribnb.listContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.newbeeee.qt.parallax_library.BaseAdjustScrollFragment;
import com.newbeeee.qt.nbhosthomeforaribnb.R;
import com.newbeeee.qt.nbhosthomeforaribnb.recyclerContent.Cheeses;

/**
 * Created by xiuxiongding on 2017/3/30.
 */

public class CheeseListFragment extends BaseAdjustScrollFragment {

    private ListView mListView;
    private int mHeaderHeight;
    private View mListHeaderView;

    public static Fragment newInstance(int position) {
        CheeseListFragment fragment = new CheeseListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cheese_list, container, false);
        mListView = (ListView) view.findViewById(R.id.nb_listView);
        mListHeaderView = inflater.inflate(R.layout.scroll_content_header, mListView, false);
        mListView.addHeaderView(mListHeaderView);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        setListView();
        return view;
    }

    private void setListView() {
        mListView.setAdapter(new CheeseListAdapter(this.getActivity(), Cheeses.getRandomSublist(Cheeses.sCheeseStrings, 30)));
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mHostView.onScrollingContentScroll(-mListHeaderView.getTop(), mPosition);
            }
        });
    }

    @Override
    public void adjustScroll(int scrollTranslation) {
        if (mListView == null) {
            return;
        }

        int scrollHeight = scrollTranslation + mHeaderHeight;
        if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        mListView.setSelectionFromTop(1, scrollHeight);
    }
}
