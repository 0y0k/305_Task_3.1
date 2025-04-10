package com.example.a305_31c;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        startButton = findViewById(R.id.startButton);

        // Check if we're returning from a quiz to preserve the name
        String savedName = getIntent().getStringExtra("USER_NAME");
        if (savedName != null) {
            nameEditText.setText(savedName);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameEditText.getText().toString().trim();
                if (!userName.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    startActivity(intent);
                } else {
                    nameEditText.setError("Please enter your name");
                }
            }
        });
    }
}