package com.amnapp.defsdemo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amnapp.defsdemo.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class TextfilesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String TAG = "TextfilesFragment";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView mRecyclerView;
    TextfilesRecyclerViewAdapter mTextfilesRecyclerViewAdapter;

    ViewGroup mContainer;

    public TextfilesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TextfilesFragment newInstance(int columnCount) {
        TextfilesFragment fragment = new TextfilesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContainer = container;
        View view = inflater.inflate(R.layout.fragment_textfiles, container, false);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.textfilesListView);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        mTextfilesRecyclerViewAdapter = new TextfilesRecyclerViewAdapter(RecentList.recentList);
        mRecyclerView.setAdapter(mTextfilesRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"resume");
        mTextfilesRecyclerViewAdapter.notifyDataSetChanged();;
    }
}