package com.amnapp.defsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_reader);

        TextView textView = findViewById(R.id.textBody);
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
        textView.setText(tv);
    }
}