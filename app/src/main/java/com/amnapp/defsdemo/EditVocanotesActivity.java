package com.amnapp.defsdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditVocanotesActivity extends AppCompatActivity {
    TextInputEditText mHeadWord;
    EditText mRelatedWord;
    EditText mMeaning;
    EditText mExampleSentence;
    EditText mOtherMemo;

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
    }

    private void initButtons() {
        Button confirmBt = findViewById(R.id.editorConfirmButton);
        Button backBt = findViewById(R.id.editorBackBitton);

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headWord, relatedWord, meaning, exampleSentence, otherMemo;
                headWord=mHeadWord.getText().toString();
                relatedWord=mRelatedWord.getText().toString();
                meaning=mMeaning.getText().toString();
                exampleSentence=mExampleSentence.getText().toString();
                otherMemo=mOtherMemo.getText().toString();
                VocanotesEntity vocanotesEntity = new VocanotesEntity(headWord, relatedWord, meaning, exampleSentence, otherMemo);//UI에 적힌 값으로 새로운 객체생성
                VocanotesRecyclerViewAdapter.vocaList.add(0,vocanotesEntity);// 새 객체를 어댑터 속 리스트에 추가
                MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.InsertVocanotes, vocanotesEntity);// DB에도 추가
                new Thread(dbt).start();//DB에 추가하는 스레드 실행
                finish();
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