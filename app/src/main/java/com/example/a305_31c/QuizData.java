package com.example.a305_31c;

import java.util.ArrayList;
import java.util.List;

public class QuizData {
    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        // Add your questions here
        questions.add(new Question(
                "In android, mini activities are also known as",
                new ArrayList<String>() {{
                    add("Fragments");
                    add("Service");
                    add("Activity");
                    add("Adapter");
                }},
                0
        ));

        questions.add(new Question(
                "What is the base class for all Android activities?",
                new ArrayList<String>() {{
                    add("Context");
                    add("Activity");
                    add("AppCompatActivity");
                    add("FragmentActivity");
                }},
                1
        ));

        // Add more questions as needed
        return questions;
    }
}