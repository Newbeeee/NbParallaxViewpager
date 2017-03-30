package com.newbeeee.qt.nbhosthomeforaribnb.scrollContent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by xiuxiongding on 2017/3/30.
 */

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
