package com.example.expensetrackerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {
    private EditText inputEmail;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        inputEmail = (EditText) findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
    }
    public void relogin2 (View v){
        startActivity(new Intent(this,Login_activity.class));
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void reset (View v){
        String email = inputEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotpassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgotpassword.this,Login_activity.class));
                            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                        } else {
                            Toast.makeText(forgotpassword.this, "No Email Address Found !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}