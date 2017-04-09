package com.newbeeee.qt.parallax_library;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiuxiongding on 2017/4/2.
 */

public class BaseAdjustScrollFragment extends Fragment implements TabHolderScrollingContent {

    protected static final String ARG_POSITION = "position";
    protected int mPosition;
    protected HostView mHostView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPosition = getArguments().getInt(ARG_POSITION);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void adjustScroll(int scrollTranslation) {

    }
}
