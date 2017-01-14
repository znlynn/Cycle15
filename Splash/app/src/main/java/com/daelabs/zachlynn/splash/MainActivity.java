package com.daelabs.zachlynn.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //private Button play = (Button) findViewById(R.id.btnPlay);
    //private Button exit = (Button) findViewById(R.id.btnExit);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ColorGrid.class);
                startActivity(i);
            }
        });

    }
}
