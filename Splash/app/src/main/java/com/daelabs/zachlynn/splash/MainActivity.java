package com.daelabs.zachlynn.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner teams;
    String team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teams = (Spinner) findViewById(R.id.spinner);
        teams.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                team = getResources().getStringArray(R.array.mlb_teams)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                team = teams.getSelectedItem().toString().split(",")[0];
            }
        });

        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ColorGrid.class);
                i.putExtra("team", team);
                startActivity(i);
            }
        });
    }
}
