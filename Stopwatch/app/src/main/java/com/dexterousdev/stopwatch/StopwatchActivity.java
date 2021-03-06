package com.dexterousdev.stopwatch;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

import android.os.Handler;

public class StopwatchActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech engine;

    private int seconds = 900;
    private int phase = 300;
    private int step = 0;
    private boolean running;
    private boolean firstPlay;
    private boolean canSpeak;
    private boolean wasRunning;
    private int bike = R.drawable.stationary_bike;
    private int fastBike = R.drawable.moving_bike;
    private int play = R.drawable.play;
    private int pause = R.drawable.pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            phase = savedInstanceState.getInt("phase");
            running = savedInstanceState.getBoolean("running");
            step = savedInstanceState.getInt("step");
            canSpeak = savedInstanceState.getBoolean("canSpeak");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        engine = new TextToSpeech(this, this);
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putInt("phase", phase);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putInt("step", step);
        savedInstanceState.putBoolean("canSpeak", canSpeak);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning)
            running = true;
    }

    public void onClickStartStop(View view) {
        ImageButton start_stop = (ImageButton)findViewById(R.id.start_stop);

        if(running) {
            start_stop.setImageResource(play);
            running = false;
        }
        else {
            start_stop.setImageResource(pause);
            running = true;
        }

    }

    public void showInfo (View view) {
        Intent intent = new Intent(this,InfoActivity.class);
        startActivity(intent);
    }

    public void showSettings (View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    private void runTimer() {
        final TextView totalTimeView = (TextView)findViewById(R.id.total_time_view);
        final TextView phaseTimeView = (TextView)findViewById(R.id.phase_time_view);
        final ImageView bikeView = (ImageView)findViewById(R.id.bike);
        final TextView instructions = (TextView)findViewById(R.id.instructions);
        final String highSpeedHalf = getString(R.string.high_speed_half);
        final String highSpeedFull = getString(R.string.high_speed_full);
        final String normalSpeed = getString(R.string.regular_speed);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canSpeak = true;
        }
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                if (running) {
                    seconds--;
                    phase--;
                    int totalMinutes = (seconds % 3600) / 60;
                    int totalSecs = seconds % 60;
                    int phaseMinutes = (phase % 3600) / 60;
                    int phaseSecs = phase % 60;
                    String totalTime = String.format("%02d:%02d", totalMinutes, totalSecs);
                    String phaseTime = String.format("%02d:%02d", phaseMinutes, phaseSecs);
                    totalTimeView.setText(totalTime);
                    phaseTimeView.setText(phaseTime);

                    if (seconds == 0) {
                        bikeView.setImageResource(bike);
                        running = false;
                        seconds = 900;
                        phase = 3;

                    } else {
                        if (phase == 0) {
                            step++;
                            if (step == 1) {
                                if(canSpeak) {
                                    engine.speak(highSpeedHalf, TextToSpeech.QUEUE_FLUSH, null, null);
                                }
                                bikeView.setImageResource(fastBike);
                                instructions.setText(highSpeedHalf);
                                phase = 10;


                            } else if (step == 2) {
                                if (canSpeak) {
                                    engine.speak(highSpeedHalf, TextToSpeech.QUEUE_FLUSH, null, null);
                                }
                                bikeView.setImageResource(fastBike);
                                instructions.setText(highSpeedFull);
                                phase = 20;


                            } else {
                                if (canSpeak) {
                                    engine.speak(highSpeedHalf, TextToSpeech.QUEUE_FLUSH, null, null);
                                }
                                instructions.setText(normalSpeed);
                                bikeView.setImageResource(bike);
                                phase = 90;
                                step = 0;
                            }

                        }
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onInit(int i) {


        if (i == TextToSpeech.SUCCESS) {
            //Setting speech Language
            engine.setLanguage(Locale.getDefault());
            engine.setPitch(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
