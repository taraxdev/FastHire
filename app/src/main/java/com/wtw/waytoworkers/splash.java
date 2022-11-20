package com.wtw.waytoworkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.splash);

        Thread splash = new Thread(){
            public void run(){
                try {
                    Thread.sleep(4500);
                    startActivity(new Intent(com.wtw.waytoworkers.splash.this,chooseor.class));
                    finish();
                }catch(Exception e)
                {
                    Toast.makeText(com.wtw.waytoworkers.splash.this, "Error in Loading..!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        splash.start();
    }
}
