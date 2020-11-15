package com.amnapp.defsdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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

        mVocanotesRecyclerViewAdapter.setOnEditClickListener(new VocanotesRecyclerViewAdapter.OnEditClickListener() {//edit버튼의 터치 이벤트 구현
            @Override
            public void onEditClick(View v, int position) {
                Intent intent = new Intent(MainActivity.mContext, EditVocanotesActivity.class);
                intent.putExtra("infoIndex",position);//단어장이 있는 ArrayList의 인덱스를 넘김
                getActivity().startActivityForResult(intent.addFlags(FLAG_ACTIVITY_NEW_TASK),MainActivity.EDIT_VOCANOTES);
            }
        });
        mVocanotesRecyclerViewAdapter.setOnDeleteClickListener(new VocanotesRecyclerViewAdapter.OnDeleteClickListener() {//content의 터치 이벤트 구현
            @Override
            public void onDeleteClick(View v, int position) {
                VocanotesEntity vocanotesEntity = VocanotesRecyclerViewAdapter.vocaList.get(position);
                MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.DeleteVocanotes, vocanotesEntity);//DB에서 삭제
                new Thread(dbt).start();
                VocanotesRecyclerViewAdapter.vocaList.remove(position);//리스트에서 삭제
                mVocanotesRecyclerViewAdapter.notifyDataSetChanged();//새로고침
            }
        });
        mVocanotesRecyclerViewAdapter.setOnContentClickListener(new VocanotesRecyclerViewAdapter.OnContentClickListener() {//content의 터치 이벤트 구현
            @Override
            public void onContentClick(View v, int position) {
                Intent intent = new Intent(MainActivity.mContext, VocanotesContentDialogActivity.class);
                intent.putExtra("infoIndex",position);//단어장이 있는 ArrayList의 인덱스를 넘김
                getActivity().startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });

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