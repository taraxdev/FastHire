package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class settings extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    LinearLayout profilebtn,postbtn,filterbtn;
    Button button;
    TextView madeby;
    boolean success = false;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Button logout=findViewById(R.id.logoutbtn);
        profilebtn=findViewById(R.id.settingsProfile);
        postbtn=findViewById(R.id.settingsPost);
        filterbtn=findViewById(R.id.settingsFilter);
        mAuth=FirebaseAuth.getInstance();
        button=findViewById(R.id.flaticon);
        madeby=findViewById(R.id.madeby);
        progressBar=findViewById(R.id.logoutProgressBar);

        progressBar.setVisibility(View.GONE);
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(settings.this, chooseor.class));
                    finish();
                }
            }
        };

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this,profile.class));
            }
        });
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("POST_TO_HOME",0);
                startActivity(new Intent(settings.this,recycleviewPost.class));
            }
        });
        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this,filters.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this,flaticonAttribution.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    mAuth.signOut();
                    success=true;
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(settings.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(success)
                {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(settings.this, chooseor.class));
                    finish();
                }

            }
        });
        madeby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/taraxdev"); // missing 'http://' will cause crash
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    private void SavePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
