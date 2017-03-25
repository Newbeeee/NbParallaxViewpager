package com.newbeeee.qt.nbhosthomeforaribnb;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ScrollHolder {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private RelativeLayout mHeader;
    private RelativeLayout mMainView;
    private MFragmentAdapter adapter;

    private int tabHeight = 50;
    private int mHeaderHeight = 200;
    private int mMinHeaderTranslation = -450;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.nb_toolBar);
        mToolbar.setTitle("Newbeeee");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.textPrimary));

        mImageView = (ImageView) findViewById(R.id.nb_imageView);
        mHeader = (RelativeLayout) findViewById(R.id.nb_header);
        mMainView = (RelativeLayout) findViewById(R.id.nb_mainView);
        mViewPager = (ViewPager) findViewById(R.id.nb_viewPager);
        if (mViewPager != null) {
            setViewPager(mViewPager);
        }

        mTabLayout = (TabLayout) findViewById(R.id.nb_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        setMainView();

    }

    private void setViewPager(final ViewPager viewPager) {
        adapter = new MFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(this), "Category 1");
        adapter.addFragment(new CheeseListFragment(this), "Category 2");
        adapter.addFragment(new CheeseListFragment(this), "Category 3");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = viewPager.getCurrentItem();
                if (positionOffsetPixels > 0) {
                    SparseArrayCompat<ScrollHolder> scrollHolders = adapter.getScrollHolders();
                    ScrollHolder fragmentContent;
                    if (position < currentItem) {
                        fragmentContent = scrollHolders.valueAt(position);
                    } else {
                        fragmentContent = scrollHolders.valueAt(position + 1);
                    }

                    fragmentContent.adjustScroll((int) mHeader.getTranslationY());
                }
            }

            @Override
            public void onPageSelected(int position) {
                SparseArrayCompat<ScrollHolder> scrollHolders = adapter.getScrollHolders();
                if (scrollHolders == null || scrollHolders.size() != 3) {
                    return;
                }

                ScrollHolder currentHolder = scrollHolders.valueAt(position);
                currentHolder.adjustScroll((int) mHeader.getTranslationY());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setMainView() {
        mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

            }
        });
    }

    @Override
    public void onRecyclerViewScroll(int dy) {
        float translationY = Math.max(-dy, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
        mImageView.setTranslationY(- translationY / 3);
//        SparseArrayCompat<ScrollHolder> scrollHolders = adapter.getScrollHolders();
//        ScrollHolder content = scrollHolders.valueAt(mViewPager.getCurrentItem());
//        content.adjustScroll((int) translationY);
    }

    @Override
    public void adjustScroll(int scrollY) {

    }


    static class MFragmentAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollHolder> scrollHolders;
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MFragmentAdapter(FragmentManager fm) {
            super(fm);
            scrollHolders = new SparseArrayCompat<>();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);
            scrollHolders.put(position, (ScrollHolder) object);
            return object;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        public SparseArrayCompat<ScrollHolder> getScrollHolders() {
            return scrollHolders;
        }
     }
}
