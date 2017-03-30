package com.newbeeee.qt.nbhosthomeforaribnb.listContent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.newbeeee.qt.nbhosthomeforaribnb.R;
import com.newbeeee.qt.nbhosthomeforaribnb.parallax.HostView;
import com.newbeeee.qt.nbhosthomeforaribnb.parallax.TabHolderScrollingContent;

/**
 * Created by xiuxiongding on 2017/3/30.
 */

public class NumberListFragment extends Fragment implements TabHolderScrollingContent{

    private static final String ARG_POSITION = "position";
    private ListView mListView;
    private int mPosition;
    private HostView mHostView;
    private int mHeaderHeight;
    private View mListHeaderView;

    public static Fragment newInstance(int position) {
        NumberListFragment fragment = new NumberListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mHostView = (HostView) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement TabHolderScrollingContent");
        }
    }

    @Override
    public void onDetach() {
        mHostView = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPosition = getArguments().getInt(ARG_POSITION);
        View view = inflater.inflate(R.layout.fragment_number_list, container, false);
        mListView = (ListView) view.findViewById(R.id.nb_listView);
        mListHeaderView = inflater.inflate(R.layout.scroll_content_header, mListView, false);
        mListView.addHeaderView(mListHeaderView);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        setListView();
        return view;
    }

    private void setListView() {

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mHostView.onScrollingContentScroll(-mListHeaderView.getTop(), mPosition);
            }
        });
        setAdapter();
    }

    private void setAdapter() {
        if (getActivity() == null) return;

        int size = 30;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = ""+i;
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringArray);

        mListView.setAdapter(adapter);
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
