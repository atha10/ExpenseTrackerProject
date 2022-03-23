package com.example.expensetrackerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Login_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private CircularProgressButton login;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(this,Profile_activity2.class));
        }

    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            startActivity(new Intent(this, Profile_activity2.class));
        }
        else {
            EditText pass1 =this.findViewById(R.id.editTextPassword);

            pass1.setText("");
        }
    }

    public void submit (View v){
        login = this.findViewById(R.id.cirLoginButton);


        EditText emailAdd =this.findViewById(R.id.editTextEmail);
        String email = emailAdd.getText().toString();
        EditText pass1 =this.findViewById(R.id.editTextPassword);
        String password = pass1.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        login.startAnimation();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            login.revertAnimation();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            login.revertAnimation();
                            updateUI(null);
                        }
                    }
                });
    }
    public void viewForgotPassword(View v){
        startActivity(new Intent(this,forgotpassword.class));
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void newacc (View v){
        startActivity(new Intent(this,new_acc_register.class));
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }


}
