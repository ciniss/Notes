package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NotedActivity extends AppCompatActivity {
    private TextView text;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noted);
        auth = FirebaseAuth.getInstance();
//        text = findViewById(R.id.aaa);
        FirebaseUser user = auth.getCurrentUser();
//        text.setText(user.getEmail());
    }
}