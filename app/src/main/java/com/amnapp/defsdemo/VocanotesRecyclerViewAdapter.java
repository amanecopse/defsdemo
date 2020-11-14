package com.amnapp.defsdemo;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VocanotesRecyclerViewAdapter extends RecyclerView.Adapter<VocanotesRecyclerViewAdapter.ViewHolder> {


    public static final String TAG = "VocanotesRVAdapter";
    //public static ArrayList<VocanotesInfo> vocaList = new ArrayList<>();
    public static ArrayList<VocanotesEntity> vocaList = new ArrayList<>();


    public VocanotesRecyclerViewAdapter() {
        Log.d(TAG,"AD constructor");
    }

    public VocanotesRecyclerViewAdapter(ArrayList<VocanotesEntity> vocaList) {
        Log.d(TAG,"AD constructor");
        this.vocaList = vocaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreate");

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocanotes_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = vocaList.get(position);
        holder.mHeadView.setText(vocaList.get(position).getHeadWord());
        holder.mMeaningView.setText(vocaList.get(position).getMeaning());
        Log.d(TAG,"onBind");

    }

    @Override
    public int getItemCount() {
        return vocaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHeadView;
        public final TextView mMeaningView;
        public VocanotesEntity mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG,"Viewholder");

            mView = itemView;
            mHeadView = (TextView) itemView.findViewById(R.id.item_head);
            mMeaningView = (TextView) itemView.findViewById(R.id.item_meaning);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"item onClicked");
                    int pos = getAdapterPosition() ;//어댑터에서의 포지션을 가져오는 함수
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(MainActivity.mContext, EditVocanotesActivity.class);
                        intent.putExtra("infoIndex",pos);//단어장이 있는 ArrayList의 인덱스를 넘김
                        MainActivity.mContext.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMeaningView.getText() + "'";
        }
    }

}