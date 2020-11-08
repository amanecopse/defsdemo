package com.amnapp.defsdemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RecentList<E> implements Iterable<E> {//최근 입력된 순으로 데이터를 중복없이 정렬하는 콘테이너
    private static final String TAG = "RecentList";
    private final ArrayList<E> recent = new ArrayList<>();
    private final int maxLength = 10;
    public static RecentList<TextFileInfo> recentList = new RecentList<>();

    public RecentList() {

    };

    public void add(E element) {
        if(element instanceof TextFileInfo){//중복 원소를 찾아 삭제하고 새로들어온 원소를 0번에 놓는다
            TextFileInfo textFileInfo = (TextFileInfo) element;
            Log.d(TAG, "중복판별진입");
            for(int i=0;i<recentList.size();i++){
                if(textFileInfo.uri.getPath().equals(recentList.get(i).uri.getPath())){
                    recentList.remove(i);
                    Log.d(TAG, "삭제됨");

                }
            }
        }
        recent.add(0, element);
        reduce();
    }

    public E get(int position) {
        return recent.get(position);
    }

    public int size() {
        return recent.size();
    }

    public void remove(int position) {
        recent.remove(position);
    }

    private void reduce() {
        while (recent.size() > maxLength) {
            recent.remove(recent.size() - 1);
        }
    }

    public void clear() {
        recent.clear();
    }

    public Iterator<E> iterator() {
        return Collections.unmodifiableCollection(recent).iterator();
    }
}
