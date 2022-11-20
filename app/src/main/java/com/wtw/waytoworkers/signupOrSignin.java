package com.wtw.waytoworkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class signupOrSignin extends AppCompatActivity {
    Button register,login;
    ImageView nature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_or_signin);
        final SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        final int flag=pre.getInt("FLAG",0);

        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        nature=findViewById(R.id.scenery);

        if (flag==0){
            nature.setImageResource(R.drawable.tree);
        }
        else if (flag==1)
        {
            nature.setImageResource(R.drawable.nature);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupOrSignin.this,signin.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0){
                startActivity(new Intent(signupOrSignin.this,usersingup.class));
                }else if(flag==1){
                    startActivity(new Intent(signupOrSignin.this,workersignup.class));
                }
            }
        });
    }
}
