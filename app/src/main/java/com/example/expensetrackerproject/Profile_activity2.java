package com.example.expensetrackerproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Profile_activity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private boolean fabExpanded = false;
    private FloatingActionButton fab, fab1, fab2;

    DatabaseReference reff;
    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login_activity.class));
        }
        FirebaseUser user = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity2);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpWindow popUpClass = new PopUpWindow();
                popUpClass.showPopupWindow(v);
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_history, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawer.addDrawerListener(drawerToggle);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uName = snapshot.child("Name").getValue().toString();
                String uEmail = snapshot.child("Email").getValue().toString();
                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = (TextView) headerView.findViewById(R.id.name2);
                TextView navEmail = (TextView) headerView.findViewById(R.id.email2);
                // Passing each menu ID as a set of Ids because each
                navUsername.setText(uName);
                navEmail.setText(uEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout() {
        mAuth.signOut();
        //closing activity
        finish();
        //starting login activity
        startActivity(new Intent(Profile_activity2.this, Login_activity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}