package com.example.timer_skuska;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyDialog2.MyDialogListener, MyDialog.MyDialogListener{

    Button b;
    Button b2;
    Button b3;
    Button b4;
    String time1;
    String time2;
    String rounds1;
    Intent intent;
    Intent intent2;
    Intent intent3;
    int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        final TextView t = findViewById(R.id.myText);
        intent = new Intent(this, SetActivity.class);


        b = findViewById(R.id.greetings);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(time1 != null && time2 != null && rounds1 != null) {
                    intent.putExtra("time", time1);
                    intent.putExtra("time2", time2);
                    intent.putExtra("rounds", rounds1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
                }
            }
        });

        b2 = findViewById(R.id.buttonDialog);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter = 1;
                openDialog();
            }
        });

        b3 = findViewById(R.id.buttonDialog2);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 2;
                openDialog();
            }
        });

        b4 = findViewById(R.id.buttonDialog3);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 3;
                openDialog2();
            }
        });

        time1 = "1:00";
        time2 = "1:00";
        rounds1 = "5";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            time1 = extras.getString("work");
            time2 = extras.getString("rest");
            rounds1 = extras.getString("rounds");
            //MainActivity.this.finish();
        }
        if(time1 == null){
            time1 = "1:00";
        }
        if(time2 == null){
            time2 = "1:00";
        }
        if(rounds1 == null){
            rounds1 = "5";
        }

        b2.setText(time1);
        b3.setText(time2);
        b4.setText(rounds1);

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
            b2.setText(time);
            time1 = time;
        }else if(counter == 2) {
            b3.setText(time);
            time2 = time;
        }
        if(counter == 3){
            b4.setText(time);
            rounds1 = time;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_workout) {
            intent3 = new Intent(MainActivity.this, editActivity.class);
            intent3.putExtra("work", time1);
            intent3.putExtra("rest", time2);
            intent3.putExtra("rounds", rounds1);
            startActivity(intent3);
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            MainActivity.this.finish();
            return true;
        }

        if(id == R.id.show_workouts){
            intent2 = new Intent(MainActivity.this, MyWorkouts.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            MainActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //MainActivity.this.finish();
    }


}
