package com.example.expensetrackerproject;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class transaction {
    private FirebaseAuth mAuth;
    String id = "TID0";
    EditText inputValue;
    DatabaseReference reff;
    public void addTransaction(final View view, final String value,final String type,final String ob){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_window, null);
        mAuth = FirebaseAuth.getInstance();
        reff= FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = reff.child("Transaction").child(mAuth.getCurrentUser().getUid());
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                   //yes
                    long num = snapshot.getChildrenCount();
                    id = "TID" + num;
                 //   Toast.makeText(view.getContext(),"Please Enter Value",Toast.LENGTH_SHORT).show();
                    add(id,value,type,ob);
                }
                else{
                   add(id,value,type,ob);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return;

    }
    public void add(String id2,String va,String type,String ob){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm a ", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        DatabaseReference users2 = reff.child("Transaction").child(mAuth.getCurrentUser().getUid()).child(id2);
        users2.child("Type").setValue(type);
        users2.child("Amount").setValue(va);
        users2.child("Category").setValue(ob);
        users2.child("Time").setValue(currentDateandTime);

    }
}
