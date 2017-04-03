# NbParallaxViewpager
ViewPager with parallax imageView

## 简介

写这个项目是因为某个晚上在网上瞎逛，无意间看到 ***Aribnb*** 工程师的 [这篇博客](http://nerds.airbnb.com/host-experience-android/) 。虽然年代久远，但觉得里面的交互很有意思，于是就学习一下，并决定自己动手实现出来。n(*≧▽≦*)n

首先看效果图：

![](https://github.com/Newbeeee/NbParallaxViewpager/blob/master/app/src/main/res/raw/parallax.gif)

***ParallaxViewPager*** 包括一个可折叠的顶部图片，一个固定在折叠图片底部的 *tab* 栏，和图片下方的 *viewpager*。*viewpager* 向上滑动时，顶部图片折叠，直到 *tab* 栏固定在 *toolbar* 下方。*viewpager* 下滑时，顶部图片展开。  

本项目的难点有二：

* 让图片跟随 *viewpager* 的上滑折叠
* 让 *viewpager* 中的内容（*scrollview* 、*listview* 或 *recyclerview*）在左右滑动或切换 *tab* 中保持最上方第一个 *item* 与 *viewpager* 顶部契合。

下面就为大家分析一下如何解决这两点，实现所需滑动效果。

## 折叠图片

### 布局

布局上采用 *relativeLayout* 为根布局，一个 *relativeLayout* 嵌套顶部图片和 *tab* 栏，*viewpager* 占满屏幕。

这里让 *viewpager* 占满屏幕是为了配合实现折叠图片的效果。我们为每个 *fragment* 的布局中添加一个和顶部图片等高的空白 *header*， 这样 *viewpager* 每个 *fragment* 包含的 *scrollview* ， *listview* 或 *recyclerview* ， 在 *viewpager* 上滑时可以跟随顶部图片一起滑动。

主界面布局如下：

```java
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/nb_mainView"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <android.support.v4.view.ViewPager
        android:id="@+id/nb_viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <RelativeLayout
        android:id="@+id/nb_header"
        android:layout_width="match_parent"
        android:layout_height="200dp">

        <ImageView
            android:id="@+id/nb_imageView"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:scaleType="centerCrop"
            android:src="@drawable/nb_bg" />

        <android.support.design.widget.TabLayout
            android:id="@+id/nb_tab_layout"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:background="@color/bgGray"
            android:alpha="0.5"
            android:layout_alignParentBottom="true" />
    </RelativeLayout>
</RelativeLayout>
```
在包含 *scrollview* 的 *fragment* 里我们在 *scrollview* 内的 *LinearLayout* 中添加一个与顶部图片等高(本例中为 200dp) 的 *header*。在 *listview* 中用 `addHeaderView` 添加头部， 在 *recyclerview* 中则把 *position* 为 0 的 *item* 设置为头部。


### 折叠

要实现折叠效果，需要监听每个 *fragment* 中内容的滑动，得到滑动距离，并把距离值传到主界面中，让顶部图片滑动相应的距离。

首先我们创建一个接口 **HostView**

```java
public interface HostView {
    void onScrollingContentScroll(int scrollY, int pagePosition);
}
```

在每个 *fragment* 监听滑动的方法中调用，把滑动距离传入。

#### scrollContent

这里我们写了一个 *ResponseScrollView* 继承自 *ScrollView* ， 重写其中的 `onScrollChanged` 方法，并暴露出一个接口。

```java
public class ResponseScrollView extends ScrollView {

    public interface OnScrollChangedListener {
        void onScrollChanged(View view, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;


    public ResponseScrollView(Context context) {
        super(context);
    }

    public ResponseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResponseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.mOnScrollChangedListener = onScrollChangedListener;
    }
}
```

*mScrollView* 通过自定义的 *listener* 监听滑动，并把自身滑动的距离传给 *mHostView* `onScrollingContentScroll` 的第一个参数

~~~java
mScrollView.setOnScrollChangedListener(new ResponseScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(View view, int l, int t, int oldl, int oldt) {
                mHostView.onScrollingContentScroll(view.getScrollY(), mPosition);
            }
        });
~~~

#### recyclerContent

这里 *mScrollY* 即 *recyclerview* 的滑动距离

~~~java
mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;
                if (mHostView != null) {
                    mHostView.onScrollingContentScroll(mScrollY, mPosition);
                }
            }
        });
~~~

#### listContent

这里 *listview* 占满屏幕，所以 *listview* 的 *headerview* 的顶部与屏幕顶部齐平， `getTop()` 即 *listview* 滑动距离

~~~java
mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mHostView.onScrollingContentScroll(-mListHeaderView.getTop(), mPosition);
            }
        });
~~~

如上三种情况，把滑动的距离传入。然后在主界面中实现 *HostView* 接口，让顶部图片滑动。

```java
public class MainActivity extends AppCompatActivity implements HostView {

    tabHeight = 50dp
    mHeaderHeight = 200dp
    mMaxHeaderTranslation = -mHeaderHeight + tabHeight;

	...
	
	@Override
    public void onScrollingContentScroll(int scrollY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            float translationY = Math.max(-scrollY, mMaxHeaderTranslation);
            mHeader.setTranslationY(translationY);
            mImageView.setTranslationY(-translationY / 3);
        }
    }
}

```

其中 *mMaxHeaderTranslation* 是顶部图片可折叠的最大值， 即图片高度和 *tab* 高度的差值。当滑动距离超过最大值后， 顶部图片不再滑动。

上文说到，用 *relativeLayout* 包裹顶部图片和 *tab* 栏， 上面的代码中，让此 *relativeLayout* (即 *mHeader*) 上滑， 而其中的图片下滑， 就可实现图片折叠的视差效果。


## 左右滑动

在 *viewpager* 上滑过程中， 切换 *viewpager* ，需要让切换到的 *fragment* 中顶部内容和 *fragment* 高度相契合。为实现此效果，我们进行以下操作。

##### 1. 创建 *TabHolerScrollingContent* 接口供 *fragment* 实现

```java
public interface TabHolderScrollingContent {
    void adjustScroll(int scrollTranslation);
}
```

其中的 *scrollTranslation* 记录主界面中顶部图片上滑的距离。

接着在每个不同的 *fragement* 中实现该接口。

##### 2. *scrollContent* 实现该接口 

切换到包含 *scrollview* 的 *fragment* 时，*scrollview* 进行相应滑动，和顶部图片滑动的距离保持一致

```java
public class ScrollViewFragment implements TabHolderScrollingContent {

	...
	
	@Override
    public void adjustScroll(int scrollTranslation) {
        if (mScrollView == null) {
            return;
        }
        mScrollView.scrollTo(0, -scrollTranslation);
    }
}

```

##### 3. *listContent* 实现该接口

这里 *scrollTranslation* 为负数， *mHeaderHeight* 为 *listview* 的 *headerview* 高度，本例为 200dp。以下代码中把高度减去移动距离，算出的是 *listview* 中 *position* 为 1 的 *item*（即第一个有效数据）所需要移动到的位置。

```java
public class ListViewFragment implements TabHolderScrollingContent {

	...
	
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

```

##### 4. *recyclerContent* 实现该接口

*recyclerview* 滑动相应距离

```java
public class RecyclerViewFragment implements TabHolderScrollingContent {

	private LinearLayoutManager mLayoutMgr;
	
	private void setRecyclerView() {
        mLayoutMgr = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutMgr);
        
        ...
    }

	...
	
	@Override
    public void adjustScroll(int scrollTranslation) {
        if (mRecyclerView == null) {
            return;
        }
        mScrollY = -scrollTranslation;
        mLayoutMgr.scrollToPositionWithOffset(0, scrollTranslation);
    }
}
```

##### 5. 在 viewpager 的左右滑动和点击 *tab* 切换中调用该接口的方法

```java
mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentItem = mViewPager.getCurrentItem();
        if (positionOffsetPixels > 0) {
            SparseArrayCompat<TabHolderScrollingContent> scrollTabHolders = mAdapter.getScrollTabHolders();

            TabHolderScrollingContent fragmentContent;
            if (position < currentItem) {
                fragmentContent = scrollTabHolders.valueAt(position);
            } else {
                fragmentContent = scrollTabHolders.valueAt(position + 1);
            }

            fragmentContent.adjustScroll((int) mHeader.getTranslationY());
        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<TabHolderScrollingContent> scrollTabHolders = mAdapter.getScrollTabHolders();

        if (scrollTabHolders == null || scrollTabHolders.size() != mNumFragments) {
            return;
        }

        TabHolderScrollingContent currentHolder = scrollTabHolders.valueAt(position);
        currentHolder.adjustScroll((int) mHeader.getTranslationY());
    }    
});
```

如此，大功告成。

## 总结

通过完成这个项目，加深了对listview、scrollview、recyclerview滑动的理解，更意识到 `技术是复杂`。希望将来还能不断努力，学到更复杂的技术知识，写出更好的代码。

另外，这是本人第一次写技术博客，欢迎大家多多吐槽、交流。

最后，附上源码地址：<https://github.com/Newbeeee/NbParallaxViewpager>

看官若看的顺眼，不吝 *star* ，n(*≧▽≦*)n






