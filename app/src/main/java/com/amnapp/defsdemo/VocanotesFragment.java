package com.amnapp.defsdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VocanotesFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String TAG = "VocanotesFragment";
    RecyclerView mRecyclerView;
    VocanotesRecyclerViewAdapter mVocanotesRecyclerViewAdapter;

    ViewGroup mContainer;

    public VocanotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        mContainer = container;
        View view = inflater.inflate(R.layout.fragment_vocanotes, container, false);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.VocanotesListView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mVocanotesRecyclerViewAdapter = new VocanotesRecyclerViewAdapter();//db에 존재하면 그 값을 생성자 인자로 전해줄 것.
        mRecyclerView.setAdapter(mVocanotesRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"resume");
        mVocanotesRecyclerViewAdapter.notifyDataSetChanged();
    }
}