package com.amnapp.defsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int PICK_TEXT_FILE = 1;
    static final int EDIT_VOCANOTES = 2;
    private static final String TAG = "mainAT";
    DrawerLayout mDrawerLayout;
    enum FragmentNames {TEXTFILES,VOCANOTES};//Fab에게 어느 프래그먼트인지 알려줌
    FragmentNames currentFragment;//현재 프래그먼트가 어느것인지 fab에게 알려줌
    Fragment mFragment = new TextfilesFragment();//현재 프래그먼트
    FloatingActionButton mFab;
    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragmentView();
        initDrawer();
        initToolBar();
        initFab();
        mContext = this.getApplicationContext();
        loadFileListGson();
        loadVocanotes();


    }

    public static class DBThread implements Runnable {//DB관련 작업처리 스레드, 생성자가 받은 커맨드에 따라 다른 행동을 한다.
        String command;
        public static final String LoadVocanotes = "LoadVocanotes";
        public static final String InsertVocanotes = "InsertVocanotes";
        public static final String UpdateVocanotes = "UpdateVocanotes";
        public static final String DeleteVocanotes = "DeleteVocanotes";
        VocanotesEntity mVocanotesEntity;

        public DBThread(String command) {
            this.command = command;
        }

        public DBThread(String command, VocanotesEntity vocanotesEntity) {
            this.command = command;
            this.mVocanotesEntity = vocanotesEntity;
        }

        @Override
        public void run() {
            if(command.equals("LoadVocanotes")){//앱이 기동될 때 DB로부터 불러온 VocanotesEntity객체의 리스트를 보카 어댑터에 전달한다
                AppDatabase db = AppDatabase.getInstance(MainActivity.mContext);
                List vocaList = db.vocanotesDao().loadAllVocanotes();
                if(vocaList!=null){
                    VocanotesRecyclerViewAdapter.vocaList = (ArrayList<VocanotesEntity>) vocaList;
                }
            }
            if(command.equals("InsertVocanotes")){//받은 VocanotesEntity객체를 DB에 추가하는 작업
                AppDatabase db = AppDatabase.getInstance(MainActivity.mContext);
                if(VocanotesRecyclerViewAdapter.vocaList!=null){
                    db.vocanotesDao().insert(mVocanotesEntity);
                }
            }
            if(command.equals("UpdateVocanotes")){//받은 VocanotesEntity객체를 DB에 업데이트하는 작업
                AppDatabase db = AppDatabase.getInstance(MainActivity.mContext);
                if(VocanotesRecyclerViewAdapter.vocaList!=null){
                    db.vocanotesDao().update(mVocanotesEntity);
                }
            }
            if(command.equals("DeleteVocanotes")){//받은 VocanotesEntity객체를 DB에서 삭제하는 작업
                AppDatabase db = AppDatabase.getInstance(MainActivity.mContext);
                if(VocanotesRecyclerViewAdapter.vocaList!=null){
                    db.vocanotesDao().delete(mVocanotesEntity);
                }
            }

        }
    }

    private void loadVocanotes() {
        DBThread dbt = new DBThread("LoadVocanotes");
        new Thread(dbt).start();
    }

    private void loadFileListGson() {
        Log.d(TAG, "loadGson");
        GsonObjectSaveManager recentGson = new GsonObjectSaveManager();
        ArrayList obj = recentGson.loadObject("recentList");
        if(obj != null){
            RecentList.setArrayList(obj);
        }
    }

    private void initFab() {

        currentFragment=FragmentNames.TEXTFILES;
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(currentFragment){//initDrawer에서 item을 뭘로 눌렀냐에 따라 결정됨
                    case TEXTFILES:
                        onTextfilesFabClicked();
                        break;
                    case VOCANOTES:
                        Intent intent = new Intent(MainActivity.this, EditVocanotesActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    private void onTextfilesFabClicked() {
        openFilePicker();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.

        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, PICK_TEXT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_TEXT_FILE
                && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Intent intent = new Intent(MainActivity.this, TextReaderActivity.class);
                intent.setData(uri);
                long now = System.currentTimeMillis();//현재시간을~
                Date mDate = new Date(now);//~구한다
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//시간구한다
                String getTime = simpleDate.format(mDate);
                RecentList.recentList.add(new TextFileInfo(uri,getTime));//최근파일리스트에 파일을 등록
                Log.d("main",RecentList.recentList.get(RecentList.recentList.size()-1).textFileName);
                startActivity(intent);
            }
        }

    }
    private void setFragmentView(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();//이하의 commit까지의 코드는 프래그먼트 전환을 위한 코드임
        fragmentTransaction.replace(R.id.fragment_container, mFragment);
        fragmentTransaction.commit();
    }
    private void initDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView mNavigationView = findViewById(R.id.navi_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.textfiles){
                    currentFragment = FragmentNames.TEXTFILES;
                    Toast.makeText(getApplicationContext(), title + ": 읽을 텍스트를 추가하거나 읽은 목록에서 선택", Toast.LENGTH_SHORT).show();
                    mFragment = new TextfilesFragment();
                    setFragmentView();
                }
                else if(id == R.id.vocanotes){
                    currentFragment = FragmentNames.VOCANOTES;
                    Toast.makeText(getApplicationContext(), title + ": 사전을 추가 또는 편집하거나 열람", Toast.LENGTH_SHORT).show();
                    mFragment = new VocanotesFragment();//단어장 목록 db에서 불러올때 인텐트를 인자로 보낼것
                    setFragmentView();
                }
                else if(id == R.id.settings){
                    Toast.makeText(getApplicationContext(), title + ": 환경설정 변경", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 햄버거 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        actionBar.setElevation(100);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//툴바의 햄버거 버튼이 눌릴 때
        switch (item.getItemId()){
            case android.R.id.home:{ // 햄버거 눌림
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        GsonObjectSaveManager recentGson = new GsonObjectSaveManager();
        recentGson.saveObject(RecentList.getArrayList(), "recentList");//텍스트파일리스트를 영구저장
        Log.d(TAG, "saveGson");
    }
}



