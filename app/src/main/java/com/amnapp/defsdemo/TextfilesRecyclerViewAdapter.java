package com.amnapp.defsdemo;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class TextfilesRecyclerViewAdapter extends RecyclerView.Adapter<TextfilesRecyclerViewAdapter.ViewHolder> {


    public static final String TAG = "TextfilesRVAdapter";

    public TextfilesRecyclerViewAdapter(RecentList recentList) {
        Log.d(TAG,"AD constructor");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreate");

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textfiles_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = RecentList.recentList.get(position);
        holder.mIdView.setText(RecentList.recentList.get(position).readTime);
        holder.mContentView.setText(RecentList.recentList.get(position).textFileName);
        Log.d(TAG,"onBind");
        Log.d(TAG,RecentList.recentList.get(RecentList.recentList.size()-1).textFileName);

    }

    @Override
    public int getItemCount() {
        return RecentList.recentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public TextFileInfo mItem;

        public ViewHolder(View view) {
            super(view);
            Log.d(TAG,RecentList.recentList.get(RecentList.recentList.size()-1).textFileName);
            Log.d(TAG,"Viewholder");

            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}