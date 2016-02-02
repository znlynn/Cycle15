package com.dexterousdev.stopwatch;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Calendar;

import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class SettingsActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    private TimePicker timePicker;
    private Calendar calendar;
    private String format = "";
    private int hour;
    private int min;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        int default_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int default_min = calendar.get(Calendar.MINUTE);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        hour = settings.getInt("hour", default_hour);
        min = settings.getInt("min", default_min);
        showTime(hour, min);
    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("hour", hour);
        editor.putInt("min", min);


        // Commit the edits!
        editor.commit();
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }
    public void setTime(View view) {
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
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
