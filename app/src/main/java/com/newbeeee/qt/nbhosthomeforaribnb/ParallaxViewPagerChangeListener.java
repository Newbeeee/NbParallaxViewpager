package com.newbeeee.qt.nbhosthomeforaribnb;

import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by xiuxiongding on 2017/3/26.
 */

public class ParallaxViewPagerChangeListener implements ViewPager.OnPageChangeListener {

    protected ViewPager mViewPager;
    protected ParrllaxFragmentPagerAdapter mAdapter;

    protected View mHeader;

    protected int mNumFragments;

    public ParallaxViewPagerChangeListener(ViewPager viewPager, ParrllaxFragmentPagerAdapter adapter, View headerView) {
        mViewPager = viewPager;
        mAdapter = adapter;
        mNumFragments = mAdapter.getCount();
        mHeader = headerView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentItem = mViewPager.getCurrentItem();
        if (positionOffsetPixels > 0) {
            SparseArrayCompat<ScrollHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

            ScrollHolder fragmentContent;
            if (position < currentItem) {
                // Revealed the previous page
                fragmentContent = scrollTabHolders.valueAt(position);
            } else {
                // Revealed the next page
                fragmentContent = scrollTabHolders.valueAt(position + 1);
            }

            fragmentContent.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()),
                    mHeader.getHeight());
        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

        if (scrollTabHolders == null || scrollTabHolders.size() != mNumFragments) {
            return;
        }

        ScrollHolder currentHolder = scrollTabHolders.valueAt(position);
        currentHolder.adjustScroll(
                (int) (mHeader.getHeight() + mHeader.getTranslationY()),
                mHeader.getHeight());
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
