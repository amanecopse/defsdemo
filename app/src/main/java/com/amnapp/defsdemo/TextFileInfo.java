package com.amnapp.defsdemo;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.OpenableColumns;

import java.util.jar.Pack200;

public class TextFileInfo{//생성자로 uri과 시간을 받아 저장한다. uri은 파일이름을 추출하여 이름을 따로 저장.
    public String textFileName;
    public String readTime;
    public String uriStr;

    public TextFileInfo(Uri textFileUri, String readTime) {
        this.uriStr = textFileUri.toString();
        this.textFileName = getFileName(textFileUri);
        this.readTime = readTime;
    }

    public String getFileName(Uri uri) {
        String result = null;

        if (uri.getScheme().equals("content")) {
            Cursor cursor = MainActivity.mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

}
