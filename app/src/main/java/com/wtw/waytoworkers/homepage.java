package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class homepage extends AppCompatActivity {
    private LinearLayout departmentbtn,settingsbtn,seepost,availLayout,homeToProfile,searchbtn,shoeError;
    private ImageView userdp;
    private TextView username,useraddress,userphone,useremail,welcome,setAvailablility;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button postButton;
    private Switch aSwitch;
    User user;
    Worker worker;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.bgcolor));
        }
        setContentView(R.layout.homepage);
        SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int flag=pre.getInt("FLAG",1);

        departmentbtn=(LinearLayout)findViewById(R.id.departmentbtn);
        settingsbtn=(LinearLayout)findViewById(R.id.settingsbtn);
        userdp=(ImageView)findViewById(R.id.userhomedp);
        username= findViewById(R.id.userhomename);
        useraddress= findViewById(R.id.userhomeaddress);
        userphone= findViewById(R.id.userhomephone);
        useremail=findViewById(R.id.userhomeemail);
        welcome=findViewById(R.id.welcome);
        progressBar=findViewById(R.id.loadFromFireProgress);
        progressBar.setVisibility(View.VISIBLE);
        postButton=findViewById(R.id.homePostBtn);
        seepost=findViewById(R.id.seepost);
        aSwitch=findViewById(R.id.switchAvailable);
        setAvailablility=findViewById(R.id.textAvailable);
        availLayout=findViewById(R.id.availLayout);
        homeToProfile=findViewById(R.id.homeToProfile);
        searchbtn=findViewById(R.id.homeSearch);
        shoeError=findViewById(R.id.showError);

        //retreving data from database
        user=new User();
        worker=new Worker();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        //TESTING BELOW 2 LINES


        //USER DATA FROM FIREBASE
        if(flag==0) {
            availLayout.setVisibility(View.GONE);
            Query query = FirebaseDatabase.getInstance().getReference("User")
                    .orderByChild("useremail")
                    .equalTo(mAuth.getCurrentUser().getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //data
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        user=childSnapshot.getValue(User.class);
                        if(user!=null) {
                            progressBar.setVisibility(View.GONE);
                            shoeError.setVisibility(View.GONE);
                            username.setText(user.getUsername());
                            useraddress.setText(user.getUseraddress());
                            userphone.setText(user.getUserphone());
                            useremail.setText(mAuth.getCurrentUser().getEmail());
                            //LINK
                            String driveLink=user.userimgurl;
                            if (driveLink.length()!=0 && driveLink.length()>=65) {
                                String substr = " ";
                                substr = driveLink.substring(32, 65);
                                String loadUrl = "https://docs.google.com/uc?id=" + substr;
                                Glide.with(homepage.this).load(loadUrl).circleCrop().into(userdp);
                            }
                            else
                            {
                                userdp.setImageResource(R.drawable.nodp);
                            }
                            //SAVE CURRENT USER LOCATION TO PREF
                            SavePreferences("LOCATION",user.getUsercity().toUpperCase().trim());
                            SavePreferences("CU_NAME",user.username.trim());
                            SavePreferences("USER_ID",user.getUserid().trim());
                            SavePreferences("PHOTO_URL",user.userimgurl.trim());
                            SavePreferences("ADDRESS",user.useraddress.trim());

                        }
                        else {
                            shoeError.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }}
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(homepage.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        //WORKER DATA FROM FIREBASE
        else if(flag==1) {
            availLayout.setVisibility(View.VISIBLE);
            Query query = FirebaseDatabase.getInstance().getReference("Worker")
                    .orderByChild("workeremail")
                    .equalTo(mAuth.getCurrentUser().getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //data
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        worker = childSnapshot.getValue(Worker.class);
                        if (worker != null) {
                            progressBar.setVisibility(View.GONE);
                            shoeError.setVisibility(View.GONE);
                            username.setText(worker.getWorkername());
                            useraddress.setText(worker.getWorkeraddress());
                            userphone.setText(worker.getWorkerphone());
                            useremail.setText(mAuth.getCurrentUser().getEmail());
                            welcome.setText("Welcome " + worker.getWorkerdept());
                            //LINK
                            String driveLink=worker.workerimageurl;
                            if (driveLink.length()!=0 && driveLink.length()>=65) {
                                String substr = " ";
                                substr = driveLink.substring(32, 65);
                                String loadUrl = "https://docs.google.com/uc?id=" + substr;
                                Glide.with(homepage.this).load(loadUrl).circleCrop().into(userdp);
                            }
                            else
                            {
                                userdp.setImageResource(R.drawable.nodp);
                            }
                            //SAVE CURRENT USER LOCATION TO PREF
                            SavePreferences("LOCATION",worker.getWorkercity().toUpperCase().trim());
                            SavePreferences("CU_NAME",worker.workername.trim());
                            SavePreferences("USER_ID",worker.getWorkerid().trim());
                            SavePreferences("PHOTO_URL",worker.workerimageurl.trim());
                            SavePreferences("ADDRESS",worker.workeraddress.trim());

                            //SWITCH
                            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        SavePreferences("AVAILABILITY",1);
                                        setAvailablility.setText("AVAILABLE");
                                        setAvailablility.setTextColor(getResources().getColor(R.color.colorPrimary));
                                        //UPDATING AVAILABILITY
                                        FirebaseDatabase.getInstance().getReference("Worker").child(worker.getWorkerid())
                                                .child("workeravailable").setValue("AVAILABLE");
                                    }
                                    else
                                    {
                                        SavePreferences("AVAILABILITY",0);
                                        setAvailablility.setText("UNAVAILABLE");
                                        setAvailablility.setTextColor(Color.RED);
                                        //UPDATING AVAILABILITY
                                        FirebaseDatabase.getInstance().getReference("Worker").child(worker.getWorkerid())
                                                .child("workeravailable").setValue("UNAVAILABLE");
                                    }
                                }
                            });
                            SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
                            int mySwitch=pre.getInt("AVAILABILITY",1);
                            if (mySwitch==1)
                            {
                                aSwitch.setChecked(true);
                            }
                            else
                            {
                                aSwitch.setChecked(false);
                            }

                        } else {
                            shoeError.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(homepage.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

       searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this,search.class));
            }
        });

        departmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this,department.class));
            }
        });
        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this,settings.class));
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this,postDetails.class));
            }
        });

        seepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences("POST_TO_HOME",1);
                startActivity(new Intent(homepage.this, recycleviewPost.class));
            }
        });

        homeToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this, profile.class));
            }
        });

    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_LOC", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    private void SavePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

}
