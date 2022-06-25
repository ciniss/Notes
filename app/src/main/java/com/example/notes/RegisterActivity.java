package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email_v;
    private EditText password_v;
    private EditText password2_v;
    private Button register_v;
    private CheckBox tosCheckBox;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        email_v = findViewById(R.id.reg_email);
        password_v = findViewById(R.id.reg_password);
        password2_v = findViewById(R.id.reg_password2);
        register_v = findViewById(R.id.register_btn);
        tosCheckBox = findViewById(R.id.checkTOS);

        register_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_v.getText().toString();
                String password = password_v.getText().toString();
                String password2 = password2_v.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Credentials cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6){
                    Toast.makeText(RegisterActivity.this,"Password too short!\n Make sure it's longer than 6", Toast.LENGTH_SHORT).show();

                }
                else if (!password.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                } else if (!tosCheckBox.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "You must agree to TOS!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password);
                }
            }
        });
    }
    private void registerUser(String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registration successful!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}