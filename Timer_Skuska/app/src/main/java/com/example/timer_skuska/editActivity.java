package com.example.timer_skuska;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class editActivity extends AppCompatActivity implements MyDialog2.MyDialogListener, MyDialog.MyDialogListener {
    private String work;
    private String rest;
    private String rounds;
    private String name;
    private Button bWork;
    private Button bRest;
    private Button bRounds;
    private Button bSave;
    private Button bCancel;
    private long id;
    private EditText tName;
    private int counter;
    private Intent intent;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        bWork = (Button) findViewById(R.id.editButtonDialog);
        bRest = (Button) findViewById(R.id.editButtonDialog2);
        bRounds = (Button) findViewById(R.id.editButtonDialog3);
        tName = findViewById(R.id.editMyText);
        bSave = (Button) findViewById(R.id.mySaveButton);
        bCancel = (Button) findViewById(R.id.myCancelButton);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            work = extras.getString("work");
            rest = extras.getString("rest");
            rounds = extras.getString("rounds");
            id = extras.getLong("id");
            name = extras.getString("name");
            if(name == null){
                a = 1;
            }else{
                a = 2;
            }
            tName.setText(name);
        }

        if(work.contains("Work: ")){
            work = work.substring(6);
        }
        if(rest.contains("Rest: ")){
            rest = rest.substring(6);
        }
        if(rounds.contains("Rounds: ")){
            rounds = rounds.substring(8);
        }

        bWork.setText(work);
        bRest.setText(rest);
        bRounds.setText(rounds);

        bWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 1;
                openDialog();
            }
        });

        bRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 2;
                openDialog();
            }
        });

        bRounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 3;
                openDialog2();
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(editActivity.this, MyWorkouts.class);
                name = tName.getText().toString();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("work", work);
                intent.putExtra("rest", rest);
                intent.putExtra("rounds", rounds);
                if(TextUtils.isEmpty(tName.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Type the Name", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(intent);
                    editActivity.this.finish();

                    Toast.makeText(getApplicationContext(), "Workout is saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a == 1){
                    startActivity(new Intent(editActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_to_left2, R.anim.slide_to_right2);
                }
                if(a == 2){
                    startActivity(new Intent(editActivity.this, MyWorkouts.class));
                    overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
                }
                editActivity.this.finish();
            }
        });
    }

    private void openDialog(){
        MyDialog md = new MyDialog();
        md.show(getSupportFragmentManager(), "my dialog");

    }

    private void openDialog2(){
        MyDialog2 md2 = new MyDialog2();
        md2.show(getSupportFragmentManager(), "my dialog 2");
    }

    @Override
    public void applyTexts(String time) {
        if(counter == 1) {
            bWork.setText(time);
            work = time;
        }else if(counter == 2) {
            bRest.setText(time);
            rest = time;
        }
        if(counter == 3){
            bRounds.setText(time);
            rounds = time;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

            if(a == 1){
                startActivity(new Intent(editActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_to_left2, R.anim.slide_to_right2);
            }
        if(a == 2){
            startActivity(new Intent(editActivity.this, MyWorkouts.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
        }
            editActivity.this.finish();

    }
}
