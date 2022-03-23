package com.example.expensetrackerproject.ui.history;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetrackerproject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    LinearLayout linearLayout;
    String id ;
    ArrayList<String> item;
    DatabaseReference reff;
    private FirebaseAuth mAuth;
    ArrayAdapter<String> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_history, container, false);
        linearLayout = root.findViewById(R.id.linear1);
        item = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        reff= FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = reff.child("Transaction").child(mAuth.getCurrentUser().getUid());
        users.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){
                    //yes
                    long num = snapshot.getChildrenCount();
                    for(long i = num ; i > 0 ; i--)
                    {
                        id = "TID" + (i-1);
                        String amount = snapshot.child(id).child("Amount").getValue().toString();
                        String type = snapshot.child(id).child("Type").getValue().toString();
                        String category = snapshot.child(id).child("Category").getValue().toString();
                        String time = snapshot.child(id).child("Time").getValue().toString();
                        String main = "Amount : "+ amount + "\nDate and Time : " + time + "\nCategory :" + category;


                        TextInputEditText text2 = new TextInputEditText(root.getContext());
                        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT);
                        text2.setLayoutParams(layoutParams2);
                        text2.setText(main);


                        TextInputLayout text1 = new TextInputLayout(root.getContext());
                        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT);
                        layoutParams.bottomMargin = 10;
                        text1.setLayoutParams(layoutParams);
                        SpannableString content = new SpannableString(type);
                        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
                        text1.setHint(content);
                        text1.addView(text2);
                        linearLayout.addView(text1);

                    }
                }
                else{

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}