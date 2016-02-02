package com.daelabs.workout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

        public static final String EXTRA_WORKOUT_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WorkoutDetailFragment workoutDetailFragment = (WorkoutDetailFragment);
        getFragmentManager().findFragmentById(R.id.detail_frag);
        int workoutId = (int) getIntent().getExtras().get(EXTRA_WORKOUT_ID);
        workoutDetailFragment.setWorkoutId(workoutId);
    }

}
