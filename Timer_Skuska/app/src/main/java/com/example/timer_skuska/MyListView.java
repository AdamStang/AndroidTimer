package com.example.timer_skuska;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timer_skuska.MainActivity.*;

import java.util.ArrayList;

public class MyListView  extends AppCompatActivity implements ExampleAdapter.OnNoteListener, ExampleAdapter.OnAllListener, ExampleAdapter.OnDeleteListener {
    private RecyclerView myRecView;
    private RecyclerView.Adapter myRecViewAdapt;
    private RecyclerView.LayoutManager myLayManage;
    private ArrayList<ExampleItem> myAL;
    private String name;
    private String work;
    private String rest;
    private String rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list_view);

        createList();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name = extras.getString("name");
            work = extras.getString("work");
            rest = extras.getString("rest");
            rounds = extras.getString("rounds");
            myAL.add(new ExampleItem(name, "Work: " + work, "Rest: " + rest, "Rounds: " + rounds));
        }

        buildRecyclerView();
    }

    public void insertItem(int position){}

    public void removeItem(int position){}

    public void createList(){
        myAL = new ArrayList<>();
        myAL.add(new ExampleItem("My workout 1", "Work: 2:00", "Rest: 1:30", "Rounds: 5"));
        myAL.add(new ExampleItem("My workout 2", "Work: 2:30", "Rest: 1:00", "Rounds: 4"));
    }

    public void buildRecyclerView(){
        myRecView = findViewById(R.id.myRecycleView);
        myRecView.setHasFixedSize(true);
        myLayManage = new LinearLayoutManager(this);
        myRecViewAdapt = new ExampleAdapter(myAL, this, this, this);

        myRecView.setLayoutManager(myLayManage);
        myRecView.setAdapter(myRecViewAdapt);
    }

    @Override
    public void onNoteClick(int position) {
        myAL.get(position);
        Intent intent = new Intent(this, editActivity.class);
        intent.putExtra("work", myAL.get(position).getWork());
        intent.putExtra("rest", myAL.get(position).getRest());
        intent.putExtra("rounds", myAL.get(position).getRounds());
        startActivity(intent);
    }

    @Override
    public void onAllClick(int position) {
        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.putExtra("work", myAL.get(position).getWork().substring(6));
        intent2.putExtra("rest", myAL.get(position).getRest().substring(6));
        intent2.putExtra("rounds", myAL.get(position).getRounds().substring(8));
        startActivity(intent2);
    }

    @Override
    public void onDeleteClick(int position) {
        myAL.remove(position);
        myRecViewAdapt.notifyItemRemoved(position);
    }
}
