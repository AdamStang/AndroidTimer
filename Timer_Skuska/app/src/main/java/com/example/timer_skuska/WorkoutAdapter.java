package com.example.timer_skuska;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private OnDeleteListener mOnDeleteListener;
    private Context mContext;
    private Cursor mCursor;

    public WorkoutAdapter(Context context, Cursor cursor, OnDeleteListener onDeleteListener){
        mContext = context;
        mCursor = cursor;
        this.mOnDeleteListener = onDeleteListener;
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder{

        public TextView nameText;
        public TextView workText;
        public TextView restText;
        public TextView roundsText;
        public ImageView myViewDelete;
        public ImageView myViewEdit;
        final OnDeleteListener onDeleteListener;

        public WorkoutViewHolder(@NonNull final View itemView, final OnDeleteListener onDeleteListener) {
            super(itemView);

            nameText = itemView.findViewById(R.id.myName);
            workText = itemView.findViewById(R.id.myWork);
            restText = itemView.findViewById(R.id.myRest);
            roundsText = itemView.findViewById(R.id.myRounds);
            myViewDelete = itemView.findViewById(R.id.myDelete);
            myViewEdit = itemView.findViewById(R.id.myEdit);
            myViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteListener.onEditListener((long) itemView.getTag());
                }
            });
            this.onDeleteListener = onDeleteListener;
            myViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteListener.onDeleteClick((long) itemView.getTag());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteListener.onGetItemClick((long) itemView.getTag());
                }
            });
        }
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.my_item, parent, false);
        return new WorkoutViewHolder(view, mOnDeleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_NAME));
        String work = mCursor.getString(mCursor.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_WORK));
        String rest = mCursor.getString(mCursor.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_REST));
        String rounds = mCursor.getString(mCursor.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_ROUNDS));
        long id = mCursor.getLong(mCursor.getColumnIndex(Workouts.WorkoutsEntry._ID));

        holder.nameText.setText(name);
        holder.workText.setText("Work: " + work);
        holder.restText.setText("Rest: " + rest);
        holder.roundsText.setText("Rounds: " + rounds);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }

    public interface OnDeleteListener{
        void onDeleteClick(long id);
        void onGetItemClick(long id);
        void onEditListener(long id);
    }
}
