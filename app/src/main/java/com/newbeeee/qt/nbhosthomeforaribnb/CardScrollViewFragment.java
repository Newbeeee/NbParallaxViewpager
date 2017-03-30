package com.newbeeee.qt.nbhosthomeforaribnb;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiuxiongding on 2017/3/30.
 */

public class CardScrollViewFragment extends Fragment implements TabHolderScrollingContent{

    private static final String ARG_POSITION = "position";
    private ResponseScrollView mScrollView;
    private int mPosition;
    private HostView mHostView;

    public static Fragment newInstance(int position) {
        CardScrollViewFragment fragment = new CardScrollViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_scroll_card_list, container, false);
        mScrollView = (ResponseScrollView) view.findViewById(R.id.nb_scrollView);
        setScrollView();
        return view;
    }

    private void setScrollView() {
        mScrollView.setOnScrollChangedListener(new ResponseScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(View view, int l, int t, int oldl, int oldt) {
                mHostView.onScrollingContentScroll(view.getScrollY(), mPosition);
            }
        });
    }

    @Override
    public void adjustScroll(int scrollTranslation) {
        if (mScrollView == null) {
            return;
        }
        mScrollView.scrollTo(0, -scrollTranslation);
    }
}
