package com.amnapp.defsdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VocanotesFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String TAG = "VocanotesFragment";
    RecyclerView mRecyclerView;
    VocanotesRecyclerViewAdapter mVocanotesRecyclerViewAdapter;
    CardView mSearchButton;
    TextInputEditText mTextInputEditText;
    int mTmpPosition;

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
        setAdapter(view);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        mVocanotesRecyclerViewAdapter.setOnEditClickListener(new VocanotesRecyclerViewAdapter.OnEditClickListener() {//edit버튼의 터치 이벤트 구현
            @Override
            public void onEditClick(View v, int position) {//에딧버튼
                Intent intent = new Intent(MainActivity.mContext, EditVocanotesActivity.class);
                intent.putExtra("infoIndex",position);//단어장이 있는 ArrayList의 인덱스를 넘김
                getActivity().startActivityForResult(intent.addFlags(FLAG_ACTIVITY_NEW_TASK),MainActivity.EDIT_VOCANOTES);
            }
        });
        mVocanotesRecyclerViewAdapter.setOnDeleteClickListener(new VocanotesRecyclerViewAdapter.OnDeleteClickListener() {//content의 터치 이벤트 구현
            @Override
            public void onDeleteClick(View v, int position) {//삭제버튼
                mTmpPosition = position;
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())//다이얼로그 팝업으로 진짜 지울 건지 물어봄
                        .setTitle("단어 삭제")
                        .setMessage("이 단어을 삭제 하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                VocanotesEntity vocanotesEntity = VocanotesRecyclerViewAdapter.vocaList.get(mTmpPosition);
                                MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.DeleteVocanotes, vocanotesEntity);//DB에서 삭제
                                new Thread(dbt).start();
                                VocanotesRecyclerViewAdapter.vocaList.remove(mTmpPosition);//리스트에서 삭제
                                mVocanotesRecyclerViewAdapter.notifyDataSetChanged();//새로고침
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                        }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();
            }
        });
        mVocanotesRecyclerViewAdapter.setOnContentClickListener(new VocanotesRecyclerViewAdapter.OnContentClickListener() {//content의 터치 이벤트 구현
            @Override
            public void onContentClick(View v, int position) {//콘텐트버튼
                Intent intent = new Intent(MainActivity.mContext, VocanotesContentDialogActivity.class);
                intent.putExtra("infoIndex",position);//단어장이 있는 ArrayList의 인덱스를 넘김
                getActivity().startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });

        mSearchButton = view.findViewById(R.id.vSearchButton);
        mTextInputEditText = view.findViewById(R.id.vSearchWord);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = mTextInputEditText.getText().toString();
                if(!searchWord.equals("")){//검색어가 있을 경우 검색
                    MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.SearchVocanotes, searchWord);
                    new Thread(dbt).start();
                    mVocanotesRecyclerViewAdapter.notifyDataSetChanged();
                }else{// 없다면 리스트를 리로드
                    MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.LoadVocanotes);
                    new Thread(dbt).start();
                    mVocanotesRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        mRecyclerView.setAdapter(mVocanotesRecyclerViewAdapter);
    }

    private void setAdapter(View view) {
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.VocanotesListView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mVocanotesRecyclerViewAdapter = new VocanotesRecyclerViewAdapter();//db에 존재하면 그 값을 생성자 인자로 전해줄 것.
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"resume");
        mVocanotesRecyclerViewAdapter.notifyDataSetChanged();
    }
}