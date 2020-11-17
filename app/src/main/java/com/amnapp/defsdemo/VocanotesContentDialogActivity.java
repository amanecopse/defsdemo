package com.amnapp.defsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class VocanotesContentDialogActivity extends AppCompatActivity {
    TextView mBtn1;
    TextView mHeadWord;
    TextView mRelatedWord;
    TextView mMeaning;
    TextView mExampleSentence;
    TextView mOtherMemo;
    Button mSetWebViewButton;
    WebView mWebView;
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

        mSetWebViewButton = findViewById(R.id.vcSetWebButton);
        mSetWebViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView = (WebView) findViewById(R.id.vcWebView);

                mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//캐시 만료 오류해결
                WebSettings webSettings = mWebView.getSettings(); //세부 세팅 등록
                webSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
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