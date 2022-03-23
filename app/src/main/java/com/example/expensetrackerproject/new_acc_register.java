package com.example.expensetrackerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static java.lang.Float.isNaN;

public class    new_acc_register extends AppCompatActivity implements View.OnClickListener {
    private CircularProgressButton register;
    private TextView relogin;
    private FirebaseAuth mAuth;
    DatabaseReference reff;
    Users users;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        mAuth = FirebaseAuth.getInstance();
        relogin =  this.findViewById(R.id.relogin);
        register = this.findViewById(R.id.reg);
        relogin.setOnClickListener(this);
        register.setOnClickListener(this);

    }
    public void relogin (View v){
        startActivity(new Intent(this,Login_activity.class));
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void createAcc(View v){

       final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_tick);
        EditText emailAdd =this.findViewById(R.id.email);
        final String email = emailAdd.getText().toString();
        EditText uName =this.findViewById(R.id.name);
        final String name = uName.getText().toString();
        EditText phoneNumber =this.findViewById(R.id.phoneno);
        final String pn = phoneNumber.getText().toString();

        final EditText pass1 =this.findViewById(R.id.pass);
        final String password = pass1.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pn)){
            Toast.makeText(this,"Please enter Phone Number",Toast.LENGTH_SHORT).show();
            return;
        }
        final double phoneNo =  Double.parseDouble(phoneNumber.getText().toString().trim());
        users = new Users();
        register.startAnimation();
      mAuth.createUserWithEmailAndPassword(email,password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information
                          Log.d(TAG, "createUserWithEmail:success");
                          Toast.makeText(new_acc_register.this, "Pass",
                                  Toast.LENGTH_SHORT).show();
                          reff= FirebaseDatabase.getInstance().getReference();
                          DatabaseReference users = reff.child("Users").child(mAuth.getCurrentUser().getUid());
                          users.child("Name").setValue(name);
                          users.child("Email").setValue(email);
                          users.child("Password").setValue(password);
                          users.child("PhoneNo").setValue(phoneNo);

                          FirebaseUser user = mAuth.getCurrentUser();
                         register.revertAnimation();
                          updateUI(user);
                      } else {
                          // If sign in fails, display a message to the user.
                          Log.w(TAG, "createUserWithEmail:failure", task.getException());
                          Toast.makeText(new_acc_register.this, "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();
                          register.revertAnimation();
                          updateUI(null);
                      }

                      // ...
                  }
              });
  }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            startActivity(new Intent(this, Profile_activity2.class));
        }
    }
    @Override
    public void onClick(View view) {

        if(view == relogin){
            relogin(view);
        }

        if(view == register){
            createAcc(view);
            //open login activity when user taps on the already registered textView

        }

    }
}
