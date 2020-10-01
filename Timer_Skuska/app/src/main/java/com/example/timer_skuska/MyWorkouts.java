package com.example.timer_skuska;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyWorkouts extends AppCompatActivity implements WorkoutAdapter.OnDeleteListener{

    private SQLiteDatabase mDatabase;
    private WorkoutAdapter mAdapter;
//    private EditText mEditTextName;
//    private EditText mEditTextWork;
//    private EditText mEditTextRest;
//    private EditText mEditTextRounds;
//    private Button addButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list_view);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.myRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WorkoutAdapter(this, getAllItems(), this);
        recyclerView.setAdapter(mAdapter);

//        mEditTextName = findViewById(R.id.myNameT);
//        mEditTextWork = findViewById(R.id.myWorkT);
//        mEditTextRest = findViewById(R.id.myRestT);
//        mEditTextRounds = findViewById(R.id.myRoundsT);
//
//        addButton= findViewById(R.id.myButton);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addItem(mEditTextName.getText().toString(), mEditTextWork.getText().toString(), mEditTextRest.getText().toString(), mEditTextRounds.getText().toString());
//            }
//        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            long id = extras.getLong("id");
            String name = extras.getString("name");
            String work = extras.getString("work");
            String rest = extras.getString("rest");
            String rounds = extras.getString("rounds");
            if(id ==  0) {
                addItem(name, work, rest, rounds);
            }else{
                ContentValues cv = new ContentValues();
                cv.put(Workouts.WorkoutsEntry.COLUMN_NAME, name);
                cv.put(Workouts.WorkoutsEntry.COLUMN_WORK, work);
                cv.put(Workouts.WorkoutsEntry.COLUMN_REST, rest);
                cv.put(Workouts.WorkoutsEntry.COLUMN_ROUNDS, rounds);
                mDatabase.update(Workouts.WorkoutsEntry.TABLE_NAME, cv, Workouts.WorkoutsEntry._ID + "=?", new String[]{String.valueOf(id)});
                mAdapter.swapCursor(getAllItems());
            }
            //MyWorkouts.this.finish();
        }
    }

    private void addItem(String name, String work, String rest, String rounds){
//        String name = mEditTextName.getText().toString();
//        String work = mEditTextWork.getText().toString();
//        String rest = mEditTextRest.getText().toString();
//        String rounds = mEditTextRounds.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(Workouts.WorkoutsEntry.COLUMN_NAME, name);
        cv.put(Workouts.WorkoutsEntry.COLUMN_WORK, work);
        cv.put(Workouts.WorkoutsEntry.COLUMN_REST, rest);
        cv.put(Workouts.WorkoutsEntry.COLUMN_ROUNDS, rounds);
        mDatabase.insert(Workouts.WorkoutsEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
    }

    public Cursor getAllItems(){
        return mDatabase.query(
                Workouts.WorkoutsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Workouts.WorkoutsEntry.COLUMN_NAME + " DESC"
        );
    }

    @Override
    public void onDeleteClick(final long id) {
        final AlertDialog.Builder b = new AlertDialog.Builder(MyWorkouts.this);
        b.setMessage("Really want to remove this workout?");
        b.setCancelable(false);
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.delete(Workouts.WorkoutsEntry.TABLE_NAME, Workouts.WorkoutsEntry._ID + "=" + id, null);
                mAdapter.swapCursor(getAllItems());
                Toast.makeText(getApplicationContext(), "Workout has been deleted", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog ad = b.create();
        ad.show();

    }

    @Override
    public void onGetItemClick(long id) {
        Intent intent2 = new Intent(this, MainActivity.class);
        Cursor name = mDatabase.rawQuery("SELECT * FROM " + Workouts.WorkoutsEntry.TABLE_NAME + " WHERE " + Workouts.WorkoutsEntry._ID + "=" + id, null);
        String namem = "";
        String rest = "";
        String rounds = "";
        while(name.moveToNext()){
            namem = name.getString(name.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_WORK));
            rest = name.getString(name.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_REST));
            rounds = name.getString(name.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_ROUNDS));
        }
        intent2.putExtra("work", namem);
        intent2.putExtra("rest", rest);
        intent2.putExtra("rounds", rounds);
        startActivity(intent2);
        overridePendingTransition(R.anim.slide_to_left2, R.anim.slide_to_right2);
        MyWorkouts.this.finish();
    }

    @Override
    public void onEditListener(long id) {
        Intent intent3 = new Intent(this, editActivity.class);
        Cursor name = mDatabase.rawQuery("SELECT * FROM " + Workouts.WorkoutsEntry.TABLE_NAME + " WHERE " + Workouts.WorkoutsEntry._ID + "=" + id, null);
        long mid = id;
        String meno = "";
        String namem = "";
        String rest = "";
        String rounds = "";
        while(name.moveToNext()){
            meno = name.getString(name.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_NAME));
            namem = name.getString(name.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_WORK));
            rest = name.getString(name.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_REST));
            rounds = name.getString(name.getColumnIndex(Workouts.WorkoutsEntry.COLUMN_ROUNDS));
        }
        intent3.putExtra("id", mid);
        intent3.putExtra("name", meno);
        intent3.putExtra("work", namem);
        intent3.putExtra("rest", rest);
        intent3.putExtra("rounds", rounds);
        startActivity(intent3);
        overridePendingTransition(R.anim.slide_to_left2, R.anim.slide_to_right2);
        MyWorkouts.this.finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent4 = new Intent(MyWorkouts.this, MainActivity.class);
        startActivity(intent4);
        overridePendingTransition(R.anim.slide_to_left2, R.anim.slide_to_right2);
        MyWorkouts.this.finish();
    }
}
