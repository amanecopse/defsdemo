package com.amnapp.defsdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class OuterMeaningActivity extends AppCompatActivity {
    TextView mHeadWord;
    ImageButton mSearchButton;
    TextView mOriginMeaning;
    EditText mNewMeaning;
    TextView mBackButton;
    TextView mConfirmButton;

    VocanotesEntity mTmpVocanotesEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer_meaning);
        initUI();
    }

    private void initUI() {
        mHeadWord = findViewById(R.id.omHeadWord);
        mSearchButton = findViewById(R.id.omSearchButton);
        mOriginMeaning = findViewById(R.id.omOriginMeaning);
        mNewMeaning = findViewById(R.id.omNewMeaning);
        mBackButton = findViewById(R.id.omBack);
        mConfirmButton = findViewById(R.id.omConfirm);

        Intent intent = getIntent();
        String newMeaning = (String) intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        mNewMeaning.setText(newMeaning);//커스텀메뉴를 누를 때 선택했던 텍스트를 UI에 셋팅한다

        mSearchButton.setOnClickListener(new View.OnClickListener() {//검색키
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OuterMeaningActivity.this, VocanotesSearchActivity.class);
                intent.putExtra(VocanotesSearchActivity.INTENT_KEYWORD_REQUEST_CODE, VocanotesSearchActivity.OUTER_SEARCH_REQUEST);
                startActivityForResult(intent, VocanotesSearchActivity.OUTER_SEARCH_REQUEST);
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {//취소키
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {//확인키
            @Override
            public void onClick(View v) {
                String newMeaning = mNewMeaning.getText().toString();
                mTmpVocanotesEntity.setMeaning(newMeaning);//임시객체에 새 뜻 설정
                MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.UpdateVocanotes, mTmpVocanotesEntity);//DB에 업뎃
                new Thread(dbt).start();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==VocanotesSearchActivity.OUTER_SEARCH_REQUEST&&resultCode==VocanotesSearchActivity.OUTER_SEARCH_RESULT){
            //임시 객체로 저장된 vocanotesEntity로 UI입력값 입력
            mTmpVocanotesEntity = VocanotesRecyclerViewAdapter.tmpVocanotesEntity;
            initUIFromData();
        }
    }

    private void initUIFromData() {
        mHeadWord.setText(mTmpVocanotesEntity.getHeadWord());
        mOriginMeaning.setText(mTmpVocanotesEntity.getMeaning());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.DBThread dbt2 = new MainActivity.DBThread(MainActivity.DBThread.LoadVocanotes);//리스트 리로드
        new Thread(dbt2).start();
    }
}