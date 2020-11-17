package com.amnapp.defsdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OuterSearchActivity extends AppCompatActivity {
    View mVocanotesView;
    WebView mWebView;
    Boolean isWebViewVisible = false;
    RecyclerView mRecyclerView;
    VocanotesRecyclerViewAdapter mVocanotesRecyclerViewAdapter;
    int mTmpPosition;
    View mSearchButton;
    TextInputEditText mTextInputEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_outer_search);
//        setRecyclerView();
//        setWebView();
//        initButton();
    }

//    private void setRecyclerView(){
//        mRecyclerView = (RecyclerView) findViewById(R.id.VocanotesListView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mVocanotesRecyclerViewAdapter = new VocanotesRecyclerViewAdapter();//db에 존재하면 그 값을 생성자 인자로 전해줄 것.
//
//        mVocanotesRecyclerViewAdapter.setOnEditClickListener(new VocanotesRecyclerViewAdapter.OnEditClickListener() {//edit버튼의 터치 이벤트 구현
//            @Override
//            public void onEditClick(View v, int position) {//에딧버튼
//                Intent intent = new Intent(MainActivity.mContext, EditVocanotesActivity.class);
//                intent.putExtra("infoIndex",position);//단어장이 있는 ArrayList의 인덱스를 넘김
//                startActivityForResult(intent.addFlags(FLAG_ACTIVITY_NEW_TASK),MainActivity.EDIT_VOCANOTES);
//            }
//        });
//        mVocanotesRecyclerViewAdapter.setOnDeleteClickListener(new VocanotesRecyclerViewAdapter.OnDeleteClickListener() {//content의 터치 이벤트 구현
//            @Override
//            public void onDeleteClick(View v, int position) {//삭제버튼
//                mTmpPosition = position;
//                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getApplicationContext())//다이얼로그 팝업으로 진짜 지울 건지 물어봄
//                        .setTitle("단어 삭제")
//                        .setMessage("이 단어을 삭제 하시겠습니까?")
//                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//                            @Override public void onClick(DialogInterface dialogInterface, int i) {
//                                VocanotesEntity vocanotesEntity = VocanotesRecyclerViewAdapter.vocaList.get(mTmpPosition);
//                                MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.DeleteVocanotes, vocanotesEntity);//DB에서 삭제
//                                new Thread(dbt).start();
//                                VocanotesRecyclerViewAdapter.vocaList.remove(mTmpPosition);//리스트에서 삭제
//                                mVocanotesRecyclerViewAdapter.notifyDataSetChanged();//새로고침
//                            }
//                        })
//                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                            }
//                        });
//                AlertDialog msgDlg = msgBuilder.create();
//                msgDlg.show();
//            }
//        });
//        mVocanotesRecyclerViewAdapter.setOnContentClickListener(new VocanotesRecyclerViewAdapter.OnContentClickListener() {//content의 터치 이벤트 구현
//            @Override
//            public void onContentClick(View v, int position) {//콘텐트버튼
//                Intent intent = new Intent(MainActivity.mContext, VocanotesContentDialogActivity.class);
//                intent.putExtra("infoIndex",position);//단어장이 있는 ArrayList의 인덱스를 넘김
//                startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
//            }
//        });
//
//        mSearchButton = findViewById(R.id.vSearchButton);
//        mTextInputEditText = findViewById(R.id.vSearchWord);
//
//        mSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String searchWord = mTextInputEditText.getText().toString();
//                if(!searchWord.equals("")){//검색어가 있을 경우 검색
//                    MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.SearchVocanotes, searchWord);
//                    new Thread(dbt).start();
//                    mVocanotesRecyclerViewAdapter.notifyDataSetChanged();
//                }else{// 없다면 리스트를 리로드
//                    MainActivity.DBThread dbt = new MainActivity.DBThread(MainActivity.DBThread.LoadVocanotes);
//                    new Thread(dbt).start();
//                    mVocanotesRecyclerViewAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//
//        mRecyclerView.setAdapter(mVocanotesRecyclerViewAdapter);
//    }
//
//    private void initButton() {
//        TextView backButton = findViewById(R.id.osBackButton);
//        Button changeButton = findViewById(R.id.osChangeButton);
//        mVocanotesView = findViewById(R.id.vocanotesView);
//
//        changeButton.setOnClickListener(new View.OnClickListener() {//보이는 뷰를 번갈아 바꾼다
//            @Override
//            public void onClick(View v) {
//                if(!isWebViewVisible){
//                    mWebView.setVisibility(View.VISIBLE);
//                    mVocanotesView.setVisibility(View.GONE);
//                }else{
//                    mWebView.setVisibility(View.GONE);
//                    mVocanotesView.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
//
//    private void setWebView() {
//        mWebView = (WebView) findViewById(R.id.vcWebView);
//        mWebView.setVisibility(View.GONE);//일단 숨겨둠
//        Intent intent =  getIntent();
//        String searchWord = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();
//
//        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//캐시 만료 오류해결
//        WebSettings webSettings = mWebView.getSettings(); //세부 세팅 등록
//        webSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
//        webSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
//        webSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
//        webSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
//        webSettings.setSupportZoom(false); // 화면 줌 허용 여부
//        webSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
//        webSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
//        String webAddress = SettingsPreferences.webAddressArray[SettingsPreferences.getInstance().getWebAddressPosition()];
//        webAddress+=searchWord;
//        mWebView.loadUrl(webAddress);// 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
//    }
}