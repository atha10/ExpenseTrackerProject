package com.example.expensetrackerproject.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetrackerproject.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    TextView cardname, cardemail;
    EditText profilename, profileemail, profilephone;
    DatabaseReference reff;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        cardname = root.findViewById(R.id.fullname);
        cardemail = root.findViewById(R.id.profilemail);
        profilename = root.findViewById(R.id.profilename);
        profileemail = root.findViewById(R.id.profileemail);
        profilephone = root.findViewById(R.id.profilephoneno);

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uName = snapshot.child("Name").getValue().toString();
                String uEmail = snapshot.child("Email").getValue().toString();
                String uPhoneno = snapshot.child("PhoneNo").getValue().toString();
                String uPass = snapshot.child("Password").getValue().toString();
                profileemail.setText(uEmail);
                profilephone.setText(uPhoneno);

                profilename.setText(uName);
                cardemail.setText(uEmail);
                cardname.setText(uName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;

    }
}