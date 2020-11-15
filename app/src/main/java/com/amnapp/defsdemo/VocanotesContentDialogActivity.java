package com.amnapp.defsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class VocanotesContentDialogActivity extends AppCompatActivity {
    TextView mBtn1;
    TextView mHeadWord;
    TextView mRelatedWord;
    TextView mMeaning;
    TextView mExampleSentence;
    TextView mOtherMemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vocanotes_content_dialog);
        initbutton();
        initTextView();
    }

    private void initTextView() {
        mHeadWord=findViewById(R.id.vcDialogHeadWord);
        mRelatedWord=findViewById(R.id.vcDialogRelatedWord);
        mMeaning=findViewById(R.id.vcDialogMeaning);
        mExampleSentence=findViewById(R.id.vcDialogExampleSentence);
        mOtherMemo=findViewById(R.id.vcDialogOtherMemo);
        Intent intent = getIntent();
        if(intent!=null){//인텐트가 빈 값이 아님
            int index = intent.getIntExtra("infoIndex", -1);
            if(index!=-1){//인덱스를 구하는 것에 성공
                VocanotesEntity vocanotesEntity = VocanotesRecyclerViewAdapter.vocaList.get(index);

                mHeadWord.setText(vocanotesEntity.getHeadWord());
                mRelatedWord.setText(vocanotesEntity.getRelatedWord());
                mMeaning.setText(vocanotesEntity.getMeaning());
                mExampleSentence.setText(vocanotesEntity.getExampleSentence());
                mOtherMemo.setText(vocanotesEntity.getOtherMemo());
            }
        }

    }

    private void initbutton() {
        mBtn1 = (TextView) findViewById(R.id.vcDialogButton);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}