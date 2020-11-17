package com.amnapp.defsdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    Button mSetWebViewButton;
    WebView mWebView;
    int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSettingsPreference();
        setContentView(R.layout.activity_edit_vocanotes);
        initEditTexts();
        initButtons();
    }

    private void loadSettingsPreference() {
        SharedPreferences pref = getSharedPreferences(SettingsPreferences.PREFERENCE_KEY, Context.MODE_PRIVATE);//sharedpref에 저장된 설정 데이터를 가져온다
        int readerTextPosition = pref.getInt(SettingsPreferences.READER_TEXT_SIZE_KEY, 2);
        boolean isDarkTheme = pref.getBoolean(SettingsPreferences.DARK_THEME_KEY, false);
        int webAddressPosition = pref.getInt(SettingsPreferences.WEB_ADDRESS_KEY,0);
        SettingsPreferences settingPreferences = new SettingsPreferences(readerTextPosition,isDarkTheme,webAddressPosition);
        SettingsPreferences.setInstance(settingPreferences);
        if(settingPreferences.isDarkTheme()){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.LightTheme);
        }
    }

    private void initEditTexts() {
        mHeadWord=findViewById(R.id.headword_edit);
        mRelatedWord=findViewById(R.id.related_edit);
        mMeaning=findViewById(R.id.meaning_edit);
        mExampleSentence=findViewById(R.id.example_edit);
        mOtherMemo=findViewById(R.id.memo_edit);

        Intent intent = getIntent();
        if(intent!=null){//인텐트가 빈 값이 아님
            mHeadWord.setText(intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT));//외부에서 커스텀메뉴를 통해 유입된 경우 셀렉트했던 텍스트를 받아 표제어로 넣는다.
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
        mSetWebViewButton = findViewById(R.id.evSetWebButton);

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

        mSetWebViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView = (WebView) findViewById(R.id.evWebView);

                mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//캐시 만료 오류해결
                WebSettings webSettings = mWebView.getSettings(); //세부 세팅 등록
                //webSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
                webSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
                webSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
                webSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
                webSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
                webSettings.setSupportZoom(false); // 화면 줌 허용 여부
                webSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
                webSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
                String webAddress = SettingsPreferences.webAddressArray[SettingsPreferences.getInstance().getWebAddressPosition()];
                String searchWord = mHeadWord.getText().toString();
                webAddress+=searchWord;
                mWebView.loadUrl(webAddress);// 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

            }
        });
    }
}