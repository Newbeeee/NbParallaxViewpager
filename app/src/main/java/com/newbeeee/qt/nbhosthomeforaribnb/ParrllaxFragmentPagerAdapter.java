package com.newbeeee.qt.nbhosthomeforaribnb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * Created by xiuxiongding on 2017/3/26.
 */

public abstract class ParrllaxFragmentPagerAdapter extends FragmentPagerAdapter {
    private SparseArrayCompat<ScrollHolder> mScrollTabHolders;
    private int mNumFragments;

    public ParrllaxFragmentPagerAdapter(FragmentManager fm, int numFragments) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<>();
        mNumFragments = numFragments;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);

        mScrollTabHolders.put(position, (ScrollHolder) object);

        return object;
    }

    @Override
    public int getCount() {
        return mNumFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public SparseArrayCompat<ScrollHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}
