package com.example.timer_skuska;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SetActivity extends AppCompatActivity {

    private ConstraintLayout backgroundLayout;
    private ConstraintLayout cl;

    private TextView tvTime;
    private TextView tvState;
    private TextView tvTotalRounds;
    private TextView tvCurrentRound;

    private FloatingActionButton buttonStartPause;
    private FloatingActionButton buttonStop;

    private Animation workAnimation;
    private Animation roundsAnimation;
    private Animation readyAnimation;
    private Animation lastAnimation;

    private MediaPlayer mpFirstLastSec;
    private MediaPlayer mpLastSec;
    private MediaPlayer mpLastRound;

    private CountDownTimer countDownTimer;
    
    private long TimeToEnd;
    private long TimeToEndTemp;
    private long TimeToEndRest;
    private long TimeToStart = 5900;
    
    private boolean state = true;
    private boolean work = true;

    private String time;
    private String time2;
    private String rounds;
    
    private int minutes;
    private int seconds;
    private int minutesRest;
    private int secondsRest;

    private int counterRoundsAll;
    private int counterAll;
    private int actualRounds = 0;

    private ProgressBar pb;
    private Handler handler = new Handler();
    private int progress = 1000;
    private ObjectAnimator animator;
    private long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_parameters);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        backgroundLayout = findViewById(R.id.paramBack);
        cl = (ConstraintLayout) findViewById(R.id.paramBack);

        tvTime = (TextView) findViewById(R.id.textView);
        tvState = (TextView) findViewById(R.id.textView2);
        tvTotalRounds = (TextView) findViewById(R.id.textView3);
        tvCurrentRound = (TextView) findViewById(R.id.textView4);

        buttonStartPause = (FloatingActionButton) findViewById(R.id.buttonStart);
        buttonStop = (FloatingActionButton) findViewById(R.id.buttonStop);

        workAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        roundsAnimation = AnimationUtils.loadAnimation(this, R.anim.bounceone);
        readyAnimation = AnimationUtils.loadAnimation(this, R.anim.ready);
        lastAnimation = AnimationUtils.loadAnimation(this, R.anim.lastsec);

        mpFirstLastSec = MediaPlayer.create(this, R.raw.zaciatok);
        mpLastSec = MediaPlayer.create(this, R.raw.koniec2);
        mpLastRound = MediaPlayer.create(this, R.raw.boxbell);

        pb = findViewById(R.id.progressBar);
        Drawable drawable = getDrawable(R.drawable.circle);
        pb.setProgress(1000);
        pb.setProgressDrawable(drawable);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            tvTime.setText(extras.getString("time"));
            time = extras.getString("time");
            time2 = extras.getString("time2");
            rounds = extras.getString("rounds");
        }

        counterRoundsAll = (Integer.valueOf(rounds) * 2) - 1;
        counterAll = counterRoundsAll;

        if(time.length() == 5){
            minutes = Integer.valueOf(time.substring(0, 2));
            if(time.charAt(3) == '0'){
                seconds = Integer.valueOf(time.substring(4));
            }else {
                seconds = Integer.valueOf(time.substring(3));
            }
        }else{
            minutes = Integer.valueOf(time.substring(0, 1));
            if(time.charAt(2) == '0'){
                seconds = Integer.valueOf(time.substring(3));
            }else {
                seconds = Integer.valueOf(time.substring(2));
            }
        }

        TimeToEnd = TimeToStart;
        TimeToEndTemp = (minutes * 60000) + (seconds * 1000);
        tvTime.setText(String.valueOf(time));

        if(time2.length() == 5){
            minutesRest = Integer.valueOf(time2.substring(0, 2));
            if(time2.charAt(3) == '0'){
                secondsRest = Integer.valueOf(time2.substring(4));
            }else {
                secondsRest = Integer.valueOf(time2.substring(3));
            }
        }else{
            minutesRest = Integer.valueOf(time2.substring(0, 1));
            if(time2.charAt(2) == '0'){
                secondsRest = Integer.valueOf(time2.substring(3));
            }else {
                secondsRest = Integer.valueOf(time2.substring(2));
            }
        }

        TimeToEndRest = (minutesRest * 60000) + (secondsRest * 1000);

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == false) {
                    state = true;
                    buttonStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    startTimer();
                }else if(state == true){
                    state = false;
                    buttonStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    stopTimer();
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        updateTimer();
        startTimer();
    }

    public void startTimer(){
        if(counterRoundsAll < counterAll) {
            pb.setVisibility(View.VISIBLE);
            animator.start();
            animator.setCurrentPlayTime(currentTime);

        }else{
            pb.setVisibility(View.INVISIBLE);
        }
        countDownTimer = new CountDownTimer(TimeToEnd, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                TimeToEnd = millisUntilFinished;

                if(counterRoundsAll < counterAll) {
                    if (pb.getProgress() > 0) {
                        progress -= 1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                pb.setProgress(progress);
                            }
                        });
                    }
                }
                if(TimeToEnd < 4000 && TimeToEnd > 1000){
                    mpFirstLastSec.start();
                    if(counterRoundsAll % 2 == 0){
                        backgroundLayout.startAnimation(lastAnimation);
                    }
                }else if(TimeToEnd < 1000){
                    TimeToEnd = 0;
                    mpLastSec.start();
                }
                tvTotalRounds.setText("Total rounds:   " + rounds);
                tvCurrentRound.setText("Current round:  " + actualRounds);
                updateTimer();

            }

            @Override
            public void onFinish() {
                animator = ObjectAnimator.ofInt(pb, "progress", 1000, 0);
                progress = 100;
                currentTime = 100;

                counterRoundsAll--;
                if(counterRoundsAll >= 0) {
                    if(counterRoundsAll % 2 == 1) {
                        TimeToEnd = TimeToEndRest + 900;
                    }else{
                        TimeToEnd = TimeToEndTemp + 900;
                        work = true;
                        actualRounds++;
                    }
                    animator.setDuration(TimeToEnd);
                    animator.setInterpolator(new LinearInterpolator());
                    //animator.start();


                    startTimer();

//                    buttonStartPause.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            startTimer();
//
//                        }
//                    }, 1000);

                }else{
                    AlertDialog.Builder b = new AlertDialog.Builder(SetActivity.this);
                    b.setMessage("Great job! Workout is done");
                    b.setCancelable(false);
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SetActivity.this.finish();
                        }
                    });
                    AlertDialog ad = b.create();
                    ad.show();
                }
            }
        }.start();
        state = true;
    }

//    public void startTimerRest(){
//        countDownTimer2 = new CountDownTimer(TimeToEndRest, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                TimeToEndRest = millisUntilFinished;
//                updateTimerRest();
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();
//        state = true;
//    }

    public void stopTimer(){
        if(countDownTimer != null) {
            countDownTimer.cancel();
            if(counterRoundsAll < counterAll) {
                currentTime = animator.getCurrentPlayTime();
                animator.pause();
            }
        }
//        if(countDownTimer2 != null){
//            countDownTimer2.cancel();
//        }
        state = false;
    }

    public void updateTimer(){
        int minutesLeft = (int) TimeToEnd / 60000;
        int secondsLeft = (int) TimeToEnd % 60000 / 1000;
        time = "" + minutesLeft;
        time += ":";
        if(secondsLeft < 10){
            time += "0";
        }
        time += secondsLeft;
        if(counterRoundsAll == (Integer.valueOf(rounds) * 2) - 1){
            cl.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
            tvState.setText("GET READY");
            tvState.setTextColor(ContextCompat.getColor(this, R.color.yellower));
            tvState.startAnimation(readyAnimation);
        }else if(counterRoundsAll %2 == 1){
            cl.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            tvState.setText("REST");
            tvState.setTextColor(ContextCompat.getColor(this, R.color.reder));
        }else{
            cl.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            tvState.setText("WORK");
            if(work == true){
                tvState.startAnimation(workAnimation);
                tvCurrentRound.startAnimation(roundsAnimation);
                if(counterRoundsAll == 0){
                    mpLastRound.start();
                }
                work = false;
            }
            tvState.setTextColor(ContextCompat.getColor(this, R.color.greener));
        }
        tvTime.setText(time);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        stopTimer();
        final AlertDialog.Builder b = new AlertDialog.Builder(SetActivity.this);
        b.setMessage("Really want to end?");
        b.setCancelable(false);
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startTimer();
            }
        });
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SetActivity.this.finish();
            }
        });
        AlertDialog ad = b.create();
        ad.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_to_left2, R.anim.slide_to_right2);
    }
}


