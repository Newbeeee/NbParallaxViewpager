package com.newbeeee.qt.nbhosthomeforaribnb.listContent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newbeeee.qt.nbhosthomeforaribnb.R;
import com.newbeeee.qt.nbhosthomeforaribnb.recyclerContent.Cheeses;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xiuxiongding on 2017/4/2.
 */

public class CheeseListAdapter extends BaseAdapter {

    private List<String> mValues;
    private LayoutInflater mInflater;

    public CheeseListAdapter(Context context, List<String> values) {
        this.mValues = values;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_cheese_list, null);
        CircleImageView imageView = (CircleImageView) view.findViewById(R.id.nb_img);
        TextView textView = (TextView) view.findViewById(R.id.nb_tv);
        textView.setText(mValues.get(position));
        Glide.with(view.getContext())
                .load(Cheeses.getRandomCheeseDrawable())
                .fitCenter()
                .into(imageView);
        return view;
    }
}
