package com.amnapp.defsdemo;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class TextfilesRecyclerViewAdapter extends RecyclerView.Adapter<TextfilesRecyclerViewAdapter.ViewHolder> {


    public static final String TAG = "TextfilesRVAdapter";

    public TextfilesRecyclerViewAdapter(RecentList recentList) {
        Log.d(TAG,"AD constructor");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreate");

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textfiles_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = RecentList.recentList.get(position);
        holder.mIdView.setText(RecentList.recentList.get(position).readTime);
        holder.mContentView.setText(RecentList.recentList.get(position).textFileName);
        Log.d(TAG,"onBind");
        Log.d(TAG,RecentList.recentList.get(RecentList.recentList.size()-1).textFileName);

    }

    @Override
    public int getItemCount() {
        return RecentList.recentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public TextFileInfo mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG,RecentList.recentList.get(RecentList.recentList.size()-1).textFileName);
            Log.d(TAG,"Viewholder");

            mView = itemView;
            mIdView = (TextView) itemView.findViewById(R.id.item_number);
            mContentView = (TextView) itemView.findViewById(R.id.content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"item onClicked");
                    int pos = getAdapterPosition() ;//어댑터에서의 포지션을 가져오는 함수
                    if (pos != RecyclerView.NO_POSITION) {
                        // 데이터 리스트로부터 아이템 데이터 참조.
                        TextFileInfo item = RecentList.recentList.get(pos) ;
                        Uri uri = Uri.parse(item.uriStr);
                        Intent intent = new Intent(MainActivity.mContext, TextReaderActivity.class);
                        intent.setData(uri);
                        long now = System.currentTimeMillis();//현재시간을~
                        Date mDate = new Date(now);//~구한다
                        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//시간구한다
                        String getTime = simpleDate.format(mDate);
                        RecentList.recentList.add(new TextFileInfo(uri,getTime));//최근파일리스트에 파일을 등록
                        Log.d(TAG,"Recent file add");
                        MainActivity.mContext.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}