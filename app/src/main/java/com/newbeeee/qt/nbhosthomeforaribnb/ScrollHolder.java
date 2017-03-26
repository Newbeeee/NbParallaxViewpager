package com.newbeeee.qt.nbhosthomeforaribnb;

import android.support.v7.widget.RecyclerView;

/**
 * Created by xiuxiongding on 2017/3/25.
 */

public interface ScrollHolder {
    void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition);

    void adjustScroll(int scrollHeight, int headerHeight);
}
