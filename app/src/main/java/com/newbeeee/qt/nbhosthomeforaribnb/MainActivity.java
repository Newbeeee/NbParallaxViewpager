package com.newbeeee.qt.nbhosthomeforaribnb;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.newbeeee.qt.nbhosthomeforaribnb.listContent.NumberListFragment;
import com.newbeeee.qt.nbhosthomeforaribnb.parallax.HostView;
import com.newbeeee.qt.nbhosthomeforaribnb.parallax.ParallaxFragmentPagerAdapter;
import com.newbeeee.qt.nbhosthomeforaribnb.parallax.ParallaxViewPagerChangeListener;
import com.newbeeee.qt.nbhosthomeforaribnb.recyclerContent.CheeseListFragment;
import com.newbeeee.qt.nbhosthomeforaribnb.scrollContent.CardScrollViewFragment;


public class MainActivity extends AppCompatActivity implements HostView {

    protected static final String IMAGE_TRANSLATION_Y = "image_translation_y";
    protected static final String HEADER_TRANSLATION_Y = "header_translation_y";

    private TabLayout mTabLayout;
    private ImageView mImageView;


    private View mHeader;
    private ViewPager mViewPager;
    private ParallaxFragmentPagerAdapter mAdapter;

    private int tabHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private int mNumFragments = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + tabHeight;
        mImageView = (ImageView) findViewById(R.id.nb_imageView);
        mHeader = findViewById(R.id.nb_header);
        mViewPager = (ViewPager) findViewById(R.id.nb_viewPager);


        mTabLayout = (TabLayout) findViewById(R.id.nb_tab_layout);

        if (savedInstanceState != null) {
            mImageView.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            mHeader.setTranslationY(savedInstanceState.getFloat(HEADER_TRANSLATION_Y));
        }

        setAdapter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat(IMAGE_TRANSLATION_Y, mImageView.getTranslationY());
        outState.putFloat(HEADER_TRANSLATION_Y, mHeader.getTranslationY());
        super.onSaveInstanceState(outState);
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new MFragmentAdapter(getSupportFragmentManager(), mNumFragments);
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mNumFragments);
        mViewPager.addOnPageChangeListener(new ParallaxViewPagerChangeListener(mViewPager, mAdapter, mHeader));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onScrollingContentScroll(int scrollY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            float translationY = Math.max(-scrollY, mMinHeaderTranslation);
            mHeader.setTranslationY(translationY);
            mImageView.setTranslationY(-translationY / 3);
            Log.e("mHeader.getTranslationY:", String.valueOf(mHeader.getTranslationY()));
        }
    }

    static class MFragmentAdapter extends ParallaxFragmentPagerAdapter {


        public MFragmentAdapter(FragmentManager fm, int numFragments) {
            super(fm, numFragments);

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = CheeseListFragment.newInstance(0);
                    break;

                case 1:
                    fragment = CardScrollViewFragment.newInstance(1);
                    break;

                case 2:
                    fragment = NumberListFragment.newInstance(2);
                    break;

                default:
                    throw new IllegalArgumentException("Wrong page given " + position);
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Category 1";

                case 1:
                    return "Category 2";

                case 2:
                    return "Category 3";


                default:
                    throw new IllegalArgumentException("wrong position for the fragment in vehicle page");
            }
        }
    }
}
