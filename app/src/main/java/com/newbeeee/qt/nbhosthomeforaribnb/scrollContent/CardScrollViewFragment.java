package com.newbeeee.qt.nbhosthomeforaribnb.scrollContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newbeeee.qt.parallax_library.BaseAdjustScrollFragment;
import com.newbeeee.qt.nbhosthomeforaribnb.R;
import com.newbeeee.qt.parallax_library.ResponseScrollView;

/**
 * Created by xiuxiongding on 2017/3/30.
 */

public class CardScrollViewFragment extends BaseAdjustScrollFragment {

    private ResponseScrollView mScrollView;

    public static Fragment newInstance(int position) {
        CardScrollViewFragment fragment = new CardScrollViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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
