package com.example.a305_31c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
public class QuizActivity extends AppCompatActivity {
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private RadioButton option1, option2, option3, option4;
    private Button submitButton;
    private ProgressBar progressBar;
    private TextView progressText;
    private TextView welcomeText;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String userName;
    private int selectedOptionIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        welcomeText = findViewById(R.id.welcomeText);

        // Get user name
        userName = getIntent().getStringExtra("USER_NAME");
        welcomeText.setText("Welcome " + userName + "!");

        // Load questions
        questions = QuizData.getQuestions();

        // Set up first question
        loadQuestion();

        // Radio group listener
        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.option1) {
                    selectedOptionIndex = 0;
                } else if (checkedId == R.id.option2) {
                    selectedOptionIndex = 1;
                } else if (checkedId == R.id.option3) {
                    selectedOptionIndex = 2;
                } else if (checkedId == R.id.option4) {
                    selectedOptionIndex = 3;
                }
            }
        });

        // Submit button listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionIndex == -1) {
                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkAnswer();
            }
        });
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);

            // Reset UI state for new question
            resetQuestionUI();

            questionTextView.setText(currentQuestion.getQuestionText());
            List<String> options = currentQuestion.getOptions();
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            updateProgress();

            // Set submit button text
            submitButton.setText(currentQuestionIndex == questions.size() - 1 ? "Finish" : "Submit");
        } else {
            showResults();
        }
    }

    private void resetQuestionUI() {
        // Enable all radio buttons
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);

        // Reset text colors
        option1.setTextColor(Color.BLACK);
        option2.setTextColor(Color.BLACK);
        option3.setTextColor(Color.BLACK);
        option4.setTextColor(Color.BLACK);

        // Clear selection
        optionsRadioGroup.clearCheck();
        selectedOptionIndex = -1;

        // Reset button listener
        submitButton.setOnClickListener(v -> {
            if (selectedOptionIndex == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }
            checkAnswer();
        });
    }

    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();

        // Disable all options
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);

        // Highlight correct answer
        RadioButton correctOption = getRadioButton(correctAnswerIndex);
        correctOption.setTextColor(Color.GREEN);

        // Highlight wrong answer if selected
        if (selectedOptionIndex != correctAnswerIndex && selectedOptionIndex != -1) {
            RadioButton selectedOption = getRadioButton(selectedOptionIndex);
            selectedOption.setTextColor(Color.RED);
        }

        // Update score if correct
        if (selectedOptionIndex == correctAnswerIndex) {
            score++;
        }

        // Change button to "Next" and update listener
        submitButton.setText("Next");
        submitButton.setOnClickListener(v -> {
            currentQuestionIndex++;
            loadQuestion();
        });
    }

    private RadioButton getRadioButton(int index) {
        switch (index) {
            case 0: return option1;
            case 1: return option2;
            case 2: return option3;
            case 3: return option4;
            default: return option1;
        }
    }

    private void updateProgress() {
        int progress = (int) (((float) currentQuestionIndex / questions.size()) * 100);
        progressBar.setProgress(progress);
        progressText.setText((currentQuestionIndex + 1) + "/" + questions.size());
    }

    private void showResults() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questions.size());
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
        finish();
    }
}