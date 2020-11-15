package com.amnapp.defsdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GsonObjectSaveManager {//텍스트파일 실행 이력의 리스트를 직력화하여 sharedPreference에 저장한다
    private static final String TAG = "GsonM";
    private Gson gson;
    public GsonObjectSaveManager() {
        gson = new GsonBuilder().create();
    }

    public void saveObject(ArrayList<TextFileInfo> recent, String key){
        JSONArray jArray = new JSONArray();//배열
        try {
            for (int i = 0; i < recent.size(); i++) {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("textFileName", recent.get(i).textFileName);
                sObject.put("readTime", recent.get(i).readTime);
                sObject.put("uriStr", recent.get(i).uriStr);
                jArray.put(sObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        SharedPreferences sp = MainActivity.mContext.getSharedPreferences("GsonObjectSaveManager", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, jArray.toString()); // JSON으로 변환한 객체를 저장한다.
        editor.putString("test","check");
        editor.apply(); //완료한다.
    }

    public ArrayList<TextFileInfo> loadObject(String key){
        SharedPreferences sp = MainActivity.mContext.getSharedPreferences("GsonObjectSaveManager", Context.MODE_PRIVATE);
        String strObj = sp.getString(key, "");
        Log.d(TAG, strObj+"load");
        Log.d(TAG, sp.getString("test", "")+"testCommand");

// 변환

        ArrayList<TextFileInfo> recent = new ArrayList<>();
        try {
            JSONArray jarray = new JSONArray(strObj);
            for(int i=0; i < jarray.length(); i++){
                JSONObject jObject = jarray.getJSONObject(i);
                String textFileName = jObject.getString("textFileName");
                String readTime = jObject.getString("readTime");
                String uriStr = jObject.getString("uriStr");

                TextFileInfo textFileInfo = new TextFileInfo(Uri.parse(uriStr), readTime);
                recent.add(textFileInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recent;
    }

}
