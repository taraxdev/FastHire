package com.wtw.waytoworkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class filters extends AppCompatActivity {

    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);
        aSwitch=findViewById(R.id.filterSwitch);

        SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int mySwitch=pre.getInt("FBL",0);

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

        if (mySwitch==1)
        {
            aSwitch.setChecked(true);
        }
        else
        {
            aSwitch.setChecked(false);
        }

    }
    private void SavePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
