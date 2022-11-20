package com.wtw.waytoworkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

public class department extends AppCompatActivity {


    ImageButton farmer,contractor,carpenter,laundry,services,mechanic,electician,chef,hairdresser;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
        farmer=findViewById(R.id.farmer);
        contractor=findViewById(R.id.contractor);
        carpenter=findViewById(R.id.carpenter);
        laundry=findViewById(R.id.laundry);
        services=findViewById(R.id.services);
        mechanic=findViewById(R.id.mechanic);
        electician=findViewById(R.id.electrician);
        chef=findViewById(R.id.chef);
        hairdresser=findViewById(R.id.hairdresser);
        aSwitch=findViewById(R.id.switchLocationOnly);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SavePreferences("FBL",1);
                }
                else
                {
                    SavePreferences("FBL",0);
                }
            }
        });
        SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int mySwitch=pre.getInt("FBL",0);
        if (mySwitch==1)
        {
            aSwitch.setChecked(true);
        }
        else
        {
            aSwitch.setChecked(false);
        }
        //SETTING ONCLICK TO ALL DEPARTMENTS
        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",1);
                startActivity(new Intent(department.this,showlist.class));
            }
        });
        contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",2);
                startActivity(new Intent(department.this,showlist.class));
            }
        });
        carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",3);
                startActivity(new Intent(department.this,showlist.class));
            }
        });

        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",4);
                startActivity(new Intent(department.this,showlist.class));
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",5);
                startActivity(new Intent(department.this,showlist.class));
            }
        });
        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",6);
                startActivity(new Intent(department.this,showlist.class));
            }
        });
        electician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",7);
                startActivity(new Intent(department.this,showlist.class));
            }
        });
        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",8);
                startActivity(new Intent(department.this,showlist.class));
            }
        });
        hairdresser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("DEPARTMENT",9);
                startActivity(new Intent(department.this,showlist.class));
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
