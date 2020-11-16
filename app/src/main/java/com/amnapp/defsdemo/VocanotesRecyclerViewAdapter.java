package com.amnapp.defsdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VocanotesRecyclerViewAdapter extends RecyclerView.Adapter<VocanotesRecyclerViewAdapter.ViewHolder> {


    public static final String TAG = "VocanotesRVAdapter";
    public static ArrayList<VocanotesEntity> vocaList = new ArrayList<>();
    public static VocanotesEntity tmpVocanotesEntity;
    public static Boolean editBackButtonVisible = true;

    private OnEditClickListener mEditListener = null ;
    private OnDeleteClickListener mDeleteListener = null ;
    private OnContentClickListener mContentListener = null ;
    public interface OnEditClickListener {
        void onEditClick(View v, int position) ;
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(View v, int position) ;
    }
    public interface OnContentClickListener {
        void onContentClick(View v, int position) ;
    }
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.mEditListener = listener ;
    }
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.mDeleteListener = listener ;
    }
    public void setOnContentClickListener(OnContentClickListener listener) {
        this.mContentListener = listener ;
    }


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
        if(!editBackButtonVisible){//에딧,취소 버튼이 보이게 될지 판단
            view.findViewById(R.id.viEdit).setVisibility(View.GONE);
            view.findViewById(R.id.viDelete).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.viEdit).setVisibility(View.VISIBLE);
            view.findViewById(R.id.viDelete).setVisibility(View.VISIBLE);
        }
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

            View vcEdit = itemView.findViewById(R.id.viEdit);
            vcEdit.setOnClickListener(new View.OnClickListener() {//Edit 버튼에 터치 이벤트 설정
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"Edit onClicked");
                    int pos = getAdapterPosition() ;//어댑터에서의 포지션을 가져오는 함수
                    if (pos != RecyclerView.NO_POSITION&&mEditListener!=null) {
                        mEditListener.onEditClick(v, pos);
                    }
                }
            });

            View vcDelete = itemView.findViewById(R.id.viDelete);
            vcDelete.setOnClickListener(new View.OnClickListener() {//Content에 터치 이벤트 설정
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"Delete onClicked");
                    int pos = getAdapterPosition() ;//어댑터에서의 포지션을 가져오는 함수
                    if (pos != RecyclerView.NO_POSITION&&mDeleteListener!=null) {
                        mDeleteListener.onDeleteClick(v, pos);
                    }
                }
            });

            View vcContent = itemView.findViewById(R.id.viContentCardView);
            vcContent.setOnClickListener(new View.OnClickListener() {//Content에 터치 이벤트 설정
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"Content onClicked");
                    int pos = getAdapterPosition() ;//어댑터에서의 포지션을 가져오는 함수
                    if (pos != RecyclerView.NO_POSITION&&mContentListener!=null) {
                        mContentListener.onContentClick(v, pos);
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