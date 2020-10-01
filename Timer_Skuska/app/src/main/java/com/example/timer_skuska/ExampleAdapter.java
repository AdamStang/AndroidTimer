package com.example.timer_skuska;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> myExampleList;
    private OnNoteListener mOnNoteListener;
    private OnAllListener mOnAllListener;
    private OnDeleteListener mOnDeleteListener;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView myName;
        public TextView myWork;
        public TextView myRest;
        public TextView myRounds;
        public ImageView myViewEdit;
        public ImageView myViewDelete;
        final OnNoteListener onNoteListener;
        final OnAllListener onAllListener;
        final OnDeleteListener onDeleteListener;

        public ExampleViewHolder(View itemView, final OnNoteListener onNoteListener, final OnAllListener onAllListener, final OnDeleteListener onDeleteListener) {
            super(itemView);
            myName = itemView.findViewById(R.id.myName);
            myWork = itemView.findViewById(R.id.myWork);
            myRest = itemView.findViewById(R.id.myRest);
            myRounds = itemView.findViewById(R.id.myRounds);
            myViewEdit = itemView.findViewById(R.id.myEdit);
            myViewDelete = itemView.findViewById(R.id.myDelete);
            this.onNoteListener = onNoteListener;
            myViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNoteListener.onNoteClick(getAdapterPosition());
                }
            });
            this.onAllListener = onAllListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAllListener.onAllClick(getAdapterPosition());
                }
            });
            this.onDeleteListener = onDeleteListener;
            myViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteListener.onDeleteClick(getAdapterPosition());
                }
            });
        }

    }

    public ExampleAdapter(ArrayList<ExampleItem> arrayList, OnNoteListener onNoteListener, OnAllListener onAllListener, OnDeleteListener onDeleteListener){
        this.myExampleList = arrayList;
        this.mOnNoteListener = onNoteListener;
        this.mOnAllListener = onAllListener;
        this.mOnDeleteListener = onDeleteListener;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mOnNoteListener, mOnAllListener, mOnDeleteListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = myExampleList.get(position);

        holder.myName.setText(currentItem.getName());
        holder.myWork.setText(currentItem.getWork());
        holder.myRest.setText(currentItem.getRest());
        holder.myRounds.setText(currentItem.getRounds());
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public interface OnAllListener{
        void onAllClick(int position);
    }

    public interface OnDeleteListener{
        void onDeleteClick(int position);
    }

    @Override
    public int getItemCount() {
        return myExampleList.size();
    }
}
