package com.amnapp.defsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextReaderActivity extends AppCompatActivity {
    TextView mContentTextView;
    EditText mEditText;
    ScrollView mScrollView;
    StringBuilder mFullText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSettingsPreference();
        setContentView(R.layout.activity_text_reader);
        //ScrollView scrollView = findViewById(R.id.text_reader_scroll_view);
        initContentText();
        setReaderTextSize();
        initSearchButton();
    }

    private void initSearchButton() {
        Button searchButton = (Button) findViewById(R.id.trSearchButton);
        mEditText = findViewById(R.id.trEditText);
        mScrollView = findViewById(R.id.text_reader_scroll_view);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String criteria = mEditText.getText().toString();
                String fullText = mContentTextView.getText().toString();
                if (fullText.contains(criteria)) {
                    int indexOfCriteria = fullText.indexOf(criteria);
                    int lineNumber = mContentTextView.getLayout().getLineForOffset(indexOfCriteria);
//                    String highlighted = "<font color='red'>"+criteria+"</font>";
//                    fullText = fullText.replace(criteria, highlighted);
//                    mContentTextView.setText(Html.fromHtml(fullText));

                    mScrollView.scrollTo(0, mContentTextView.getLayout().getLineTop(lineNumber));
                }
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
//                    String fullText = mContentTextView.getText().toString();
//                    fullText = fullText.replace("<font color='red'>", "");
//                    fullText = fullText.replace("</font>", "");
//                    mContentTextView.setText(fullText);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setReaderTextSize() {
        int pos = SettingsPreferences.getInstance().getReaderTextSizePosition();//텍스트 사이즈의 배열의 인덱스 구함
        int size = SettingsPreferences.readerTextSizeArray[pos];
        mContentTextView.setTextSize(size);
    }

    private void initContentText() {
        mContentTextView = findViewById(R.id.textBody);
        String line = null;
        StringBuilder tv= new StringBuilder();
        String txt=null;

        Uri uri = getIntent().getData();
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));

            while((line=buf.readLine())!=null){

                tv.append(line);
                tv.append("\n");
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mContentTextView.setText(tv);
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
}