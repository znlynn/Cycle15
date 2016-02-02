package com.daelabs.brainteaser;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfAnswers;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    int score = 0;
    int numberOfQuestions = 0;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;
    TextView sumTextView;
    RelativeLayout gameRelativeLayout;
    boolean gameOver;

    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);

        playAgain(findViewById(R.id.playAgainButton));
    }

    public void generateQuestion() {
        if (gameOver != true) {
            Random rand = new Random();
            int a = rand.nextInt(20) + 1;
            int b = rand.nextInt(20) + 1;

            sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

            locationOfAnswers = rand.nextInt(4);
            answers.clear();
            int incorrectAnswer;
            for (int i = 0; i < 4; i++) {
                if (i == locationOfAnswers) {
                    answers.add(a + b);
                } else {
                    incorrectAnswer = rand.nextInt(39) + 2;
                    while (incorrectAnswer == a + b) {
                        incorrectAnswer = rand.nextInt(39) + 2;
                    }
                    answers.add(incorrectAnswer);
                }
            }
            button0.setText(Integer.toString(answers.get(0)));
            button1.setText(Integer.toString(answers.get(1)));
            button2.setText(Integer.toString(answers.get(2)));
            button3.setText(Integer.toString(answers.get(3)));
        }
    }

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        gameOver = false;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateQuestion();

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000 + "s"));
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                gameOver = true;
                resultTextView.setText("Your score: " + Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
            }
        }.start();


    }

    public void chooseAnswer(View view) {
        if(gameOver != true) {
            if (view.getTag().toString().equals(Integer.toString(locationOfAnswers))) {
                score++;
                resultTextView.setText("Correct!");
            } else {
                resultTextView.setText("Incorrect!");
            }

            numberOfQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            generateQuestion();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);

        playAgain(findViewById(R.id.playAgainButton));

    }
}
