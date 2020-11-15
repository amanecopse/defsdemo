package com.amnapp.defsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditVocanotesActivity extends AppCompatActivity {
    TextInputEditText mHeadWord;
    EditText mRelatedWord;
    EditText mMeaning;
    EditText mExampleSentence;
    EditText mOtherMemo;
    int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vocanotes);
        initEditTexts();
        initButtons();
    }

    private void initEditTexts() {
        mHeadWord=findViewById(R.id.headword_edit);
        mRelatedWord=findViewById(R.id.related_edit);
        mMeaning=findViewById(R.id.meaning_edit);
        mExampleSentence=findViewById(R.id.example_edit);
        mOtherMemo=findViewById(R.id.memo_edit);

        Intent intent = getIntent();
        if(intent!=null){//인텐트가 빈 값이 아님
            mIndex = intent.getIntExtra("infoIndex",-1);//받은 인덱스가 있는지 확인
            if(mIndex != -1){//인덱스를 구하는 것에 성공
                VocanotesEntity vocanotesEntity = VocanotesRecyclerViewAdapter.vocaList.get(mIndex);
                mHeadWord.setText(vocanotesEntity.getHeadWord());
                mRelatedWord.setText(vocanotesEntity.getRelatedWord());
                mMeaning.setText(vocanotesEntity.getMeaning());
                mExampleSentence.setText(vocanotesEntity.getExampleSentence());
                mOtherMemo.setText(vocanotesEntity.getOtherMemo());
            }
        }
    }

    private void initButtons() {
        Button confirmBt = findViewById(R.id.editorConfirmButton);
        Button backBt = findViewById(R.id.editorBackBitton);

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headWord, relatedWord, meaning, exampleSentence, otherMemo;
                if(mHeadWord.getText().toString().equals("")){//표제어가 공란인지 검사
                    Toast.makeText(EditVocanotesActivity.this, "표제어를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    headWord=mHeadWord.getText().toString();
                    relatedWord=mRelatedWord.getText().toString();
                    meaning=mMeaning.getText().toString();
                    exampleSentence=mExampleSentence.getText().toString();
                    otherMemo=mOtherMemo.getText().toString();
                    if(mIndex==-1){//받은 인덱스가 없어 최초 생성이라면
                        VocanotesEntity vocanotesEntity = new VocanotesEntity(headWord, relatedWord, meaning, exampleSentence, otherMemo);//UI에 적힌 값으로 새로운 객체생성
                        VocanotesRecyclerViewAdapter.vocaList.add(0,vocanotesEntity);// 새 객체를 어댑터 속 리스트에 추가
                        MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.InsertVocanotes, vocanotesEntity);// DB에도 추가
                        new Thread(dbt).start();//DB에 추가하는 스레드 실행
                        finish();
                    }else{//받은 인덱스가 있어 특정 자료를 업뎃하는 경우
                        VocanotesEntity vocanotesEntity = VocanotesRecyclerViewAdapter.vocaList.get(mIndex);
                        vocanotesEntity.setHeadWord(headWord);
                        vocanotesEntity.setRelatedWord(relatedWord);
                        vocanotesEntity.setMeaning(meaning);
                        vocanotesEntity.setExampleSentence(exampleSentence);
                        vocanotesEntity.setOtherMemo(otherMemo);
                        VocanotesRecyclerViewAdapter.vocaList.remove(mIndex);//기존에 리스트에 존재한 객체를 삭제
                        VocanotesRecyclerViewAdapter.vocaList.add(mIndex, vocanotesEntity);//수정한 것으로 대체
                        MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.UpdateVocanotes, vocanotesEntity);// DB에도 업데이트
                        new Thread(dbt).start();//DB에 추가하는 스레드 실행
                        finish();

                    }
                }
            }
        });
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}