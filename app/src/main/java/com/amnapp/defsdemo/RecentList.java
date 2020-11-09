package com.amnapp.defsdemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RecentList implements Iterable {//최근 입력된 순으로 데이터를 중복없이 정렬하는 콘테이너
    private static final String TAG = "RecentList";
    private static ArrayList<TextFileInfo> recent;
    public static RecentList recentList = new RecentList();

    public RecentList() {
        recent = new ArrayList<TextFileInfo>();
    };

    public static void setArrayList(ArrayList<TextFileInfo> list){
        recent = list;
    }

    public static ArrayList<TextFileInfo> getArrayList(){
        return recent;
    }

    public void add(TextFileInfo element) {
        //중복 원소를 찾아 삭제하고 새로들어온 원소를 0번에 놓는다
        TextFileInfo textFileInfo = (TextFileInfo) element;
        Log.d(TAG, "중복판별진입");
        for(int i=0;i<recentList.size();i++){
            if(textFileInfo.uriStr.equals(recentList.get(i).uriStr)){
                recentList.remove(i);
                Log.d(TAG, "삭제됨");

            }
        }

        recent.add(0, element);
        reduce();
    }

    public TextFileInfo get(int position) {
        return recent.get(position);
    }

    public int size() {
        return recent.size();
    }

    public void remove(int position) {
        recent.remove(position);
    }

    private void reduce() {
        int maxLength = 10;
        while (recent.size() > maxLength) {
            recent.remove(recent.size() - 1);
        }
    }

    public void clear() {
        recent.clear();
    }

    public Iterator<TextFileInfo> iterator() {
        return Collections.unmodifiableCollection(recent).iterator();
    }
}
