package com.example.a305_31c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private TextView congratsTextView;
    private Button newQuizButton;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreTextView = findViewById(R.id.scoreTextView);
        congratsTextView = findViewById(R.id.congratsTextView);
        newQuizButton = findViewById(R.id.newQuizButton);
        finishButton = findViewById(R.id.finishButton);

        // Get data from intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);
        String userName = intent.getStringExtra("USER_NAME");

        // Set UI elements
        congratsTextView.setText("Congratulations " + userName + "!");
        scoreTextView.setText(score + "/" + totalQuestions);

        // Button listeners
        newQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.putExtra("USER_NAME", userName);
                startActivity(intent);
                finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Close the app
            }
        });
    }
}