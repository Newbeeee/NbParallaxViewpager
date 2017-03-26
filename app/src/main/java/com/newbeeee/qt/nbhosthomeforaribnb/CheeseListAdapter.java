package com.newbeeee.qt.nbhosthomeforaribnb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by xiuxiongding on 2017/3/25.
 */

public class CheeseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mValues;

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;

    public CheeseListAdapter(List<String> items) {
        super();
        mValues = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cheese_list, parent, false);
            return new RecyclerItemViewHolder(view);
        }
        throw new RuntimeException("Invalid view type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > 0) {
            RecyclerItemViewHolder mHolder = (RecyclerItemViewHolder) holder;
            mHolder.mTextView.setText(mValues.get(position - 1));

            Glide.with(mHolder.mImageView.getContext())
                    .load(Cheeses.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(mHolder.mImageView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }


    private static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.avatar);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    private static class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {

        public RecyclerHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
