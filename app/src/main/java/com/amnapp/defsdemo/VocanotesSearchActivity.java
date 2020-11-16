package com.amnapp.defsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VocanotesSearchActivity extends AppCompatActivity {
    public static final int OUTER_SEARCH_REQUEST = 3;
    public static final int OUTER_SEARCH_RESULT = 103;
    public static final String INTENT_KEYWORD_REQUEST_CODE = "requestCode";
    public static final String INTENT_KEYWORD_RESULT_CODE = "resultCode";


    RecyclerView mRecyclerView;
    VocanotesRecyclerViewAdapter mVocanotesRecyclerViewAdapter;
    ImageButton mSearchButton;
    EditText mEdit;
    TextView mBackButton;
    int mReceivedRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocanotes_search);
        getRequestCode();
        initRecyclerView();
        initUI();
    }

    private void getRequestCode() {//다른 액티비티에서 요청한 코드를 읽는다. 이 값은 이후 액티비티의 행동을 결정
        Intent intent = getIntent();
        if(intent!=null){
            mReceivedRequestCode = intent.getIntExtra(INTENT_KEYWORD_REQUEST_CODE, -1);
        }
    }

    private void initUI() {
        mSearchButton = findViewById(R.id.vsSearchButton);
        mEdit = findViewById(R.id.vsEdit);
        mBackButton = findViewById(R.id.vsBackButton);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = mEdit.getText().toString();
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

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.vsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVocanotesRecyclerViewAdapter = new VocanotesRecyclerViewAdapter();

        if(mReceivedRequestCode==OUTER_SEARCH_REQUEST){
            VocanotesRecyclerViewAdapter.editBackButtonVisible=false;//일시적으로 불필요한 뷰 제거
            mVocanotesRecyclerViewAdapter.setOnContentClickListener(new VocanotesRecyclerViewAdapter.OnContentClickListener() {//content의 터치 이벤트 구현
                @Override
                public void onContentClick(View v, int position) {
                    //콘텐트 터치이벤트
                    VocanotesRecyclerViewAdapter.tmpVocanotesEntity = VocanotesRecyclerViewAdapter.vocaList.get(position);//전달할 객체를 임시정적변수에 저장
                    setResult(OUTER_SEARCH_RESULT);
                    finish();
                }
            });
        }else{
            mVocanotesRecyclerViewAdapter.setOnEditClickListener(new VocanotesRecyclerViewAdapter.OnEditClickListener() {//edit버튼의 터치 이벤트 구현
                @Override
                public void onEditClick(View v, int position) {
                    Intent intent = new Intent(MainActivity.mContext, EditVocanotesActivity.class);
                    intent.putExtra("infoIndex",position);//단어장이 있는 ArrayList의 인덱스를 넘김
                    startActivityForResult(intent.addFlags(FLAG_ACTIVITY_NEW_TASK),MainActivity.EDIT_VOCANOTES);
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
                    startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
                }
            });

            mRecyclerView.setAdapter(mVocanotesRecyclerViewAdapter);
        }

        mRecyclerView.setAdapter(mVocanotesRecyclerViewAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //리스트를 필터링 없는 상태로 되돌려 놓는 코드가 올 것
        MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.LoadVocanotes);
        new Thread(dbt).start();
        //일시적으로 숨긴 뷰 복원
        VocanotesRecyclerViewAdapter.editBackButtonVisible=true;
    }
}