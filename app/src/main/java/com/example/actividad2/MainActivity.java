package com.example.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView questionText, timerText, scoreText;
    private EditText answerUser;
    private Button answerButton, tryAgainButton;
    private Question question;
    private int timer, score, timePressed;
    private boolean isPressing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        scoreText = findViewById(R.id.scoreText);
        answerUser = findViewById(R.id.answerUser);
        answerButton = findViewById(R.id.answerButton);
        tryAgainButton = findViewById(R.id.tryAgainButton);

        //Variables
        score = 0;
        timer = 30;
        isPressing = false;

        //First question
        createNewQuestion();

        //Set text for view
        questionText.setText(question.getQuestion());
        timerText.setText("" + timer);
        scoreText.setText("Puntaje: " + score);
        tryAgainButton.setVisibility(View.GONE);

        //Timer
        timerSettings();

        //Buttons
        answerButton.setOnClickListener(
                (view) -> {
                    verifyAnswer();
                }
        );

        tryAgainButton.setOnClickListener(
                (view) -> {
                    tryAgainButton.setVisibility(View.GONE);
                    timer = 30;
                    timerText.setText(""+timer);
                    timerSettings();
                }
        );

        questionText.setOnTouchListener(
                (view, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isPressing = true;
                            new Thread(() -> {
                                timePressed++;
                                while (timePressed < 1500) {
                                    try {
                                        Thread.sleep(150);
                                        timePressed+=150;
                                        if (!isPressing) {
                                            return;
                                        }
                                    } catch (InterruptedException e) {
                                    }
                                }

                                runOnUiThread(() -> {
                                    createNewQuestion();
                                    timer = 30;
                                    timerText.setText("" + timer);
                                    timerSettings();
                                });

                            }).start();
                            break;
                        case MotionEvent.ACTION_UP:
                            isPressing = false;
                            break;
                    }
                    return true;
                }
        );
    }

    public void verifyAnswer() {
        String answerText = answerUser.getText().toString();
        int answerInt = (int) Integer.parseInt(answerText);

        if (answerInt == question.getAnswer()) {
            Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
            score += 5;
            scoreText.setText("Puntaje: " + score);
            createNewQuestion();
            timer = 30;
            timerSettings();
        } else {
            Toast.makeText(this, "Malo", Toast.LENGTH_SHORT).show();
        }

        createNewQuestion();
    }

    public void createNewQuestion(){
        question = new Question();
        questionText.setText(question.getQuestion());
        answerUser.getText().clear();
    }

    public void timerSettings(){
        new Thread(
                () -> {
                    while (timer > 0) {
                        try {
                            timer--;
                            runOnUiThread(
                                    () -> {
                                        timerText.setText("" + timer);
                                        if (timer == 0){
                                            tryAgainButton.setVisibility(View.VISIBLE);
                                        }
                                    }
                            );
                            Thread.sleep(1000);
                        } catch (Exception e) {

                        }
                    }
                }
        ).start();
    }
}