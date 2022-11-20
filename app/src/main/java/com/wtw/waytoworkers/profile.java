package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    ImageView dp,beauty;
    TextView name,phone,city,rate,age,address,email,department,gender,registeredAs;
    Button editBtn;
    public String link;
    LinearLayout layout,deptLayout;
    User user;
    Worker worker;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        dp=findViewById(R.id.profileDp);
        //beauty=findViewById(R.id.beautyImage);
        name=findViewById(R.id.profileName);
        phone=findViewById(R.id.profilePhone);
        city=findViewById(R.id.profileCity);
        rate=findViewById(R.id.profileRate);
        age=findViewById(R.id.profileAge);
        address=findViewById(R.id.profileAddress);
        email=findViewById(R.id.profileEmail);
        editBtn=findViewById(R.id.profileEditBtn);
        layout=findViewById(R.id.profileLayoutVisibility);
        registeredAs=findViewById(R.id.profileAs);
        gender=findViewById(R.id.profileGender);
        department=findViewById(R.id.profileDept);
        deptLayout=findViewById(R.id.profileDeptLayout);

        //SAVED PREF
        SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int flag=pre.getInt("FLAG",1);
        //retreving data from database
        user=new User();
        worker=new Worker();
        mAuth = FirebaseAuth.getInstance();

        //USER DATA FROM FIREBASE
        if(flag==0) {
            layout.setVisibility(View.GONE);
            //beauty.setImageResource(R.drawable.tree);
            registeredAs.setText("REGISTERED AS USER");
            deptLayout.setVisibility(View.GONE);
            Query query = FirebaseDatabase.getInstance().getReference("User")
                    .orderByChild("useremail")
                    .equalTo(mAuth.getCurrentUser().getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        user=childSnapshot.getValue(User.class);
                        if(user!=null) {
                            name.setText(user.getUsername());
                            city.setText(user.getUsercity());
                            address.setText(user.getUseraddress());
                            phone.setText(user.getUserphone());
                            gender.setText(user.getUsergender());
                            email.setText(mAuth.getCurrentUser().getEmail());
                            link=user.getUserimgurl();
                            //LINK
                            String driveLink=user.userimgurl;
                            if (driveLink.length()!=0 && driveLink.length()>=65) {
                                String substr = " ";
                                substr = driveLink.substring(32, 65);
                                String loadUrl = "https://docs.google.com/uc?id=" + substr;
                                Glide.with(profile.this).load(loadUrl).circleCrop().into(dp);
                            }
                            else
                            {
                                dp.setImageResource(R.drawable.nodp);
                            }
                        }
                        else {
                            Toast.makeText(profile.this,"User does not exist",Toast.LENGTH_SHORT).show();
                        }}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        //WORKER DATA
        else if (flag==1){
            layout.setVisibility(View.VISIBLE);
            //beauty.setImageResource(R.drawable.nature);
            registeredAs.setText("REGISTERED AS WORKER");
            Query query = FirebaseDatabase.getInstance().getReference("Worker")
                    .orderByChild("workeremail")
                    .equalTo(mAuth.getCurrentUser().getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        worker = childSnapshot.getValue(Worker.class);
                        if (worker != null) {
                            name.setText(worker.getWorkername());
                            address.setText(worker.getWorkeraddress());
                            phone.setText(worker.getWorkerphone());
                            email.setText(mAuth.getCurrentUser().getEmail());
                            city.setText(worker.getWorkercity());
                            rate.setText(worker.getWorkerrate());
                            age.setText(worker.getWorkerage());
                            department.setText(worker.getWorkerdept());
                            gender.setText(worker.getWorkergender());
                            link=worker.getWorkerimageurl();

                            //LINK
                            String driveLink=worker.workerimageurl;
                            if (driveLink.length()!=0 && driveLink.length()>=65) {
                                String substr = " ";
                                substr = driveLink.substring(32, 65);
                                String loadUrl = "https://docs.google.com/uc?id=" + substr;
                                Glide.with(getApplicationContext()).load(loadUrl).circleCrop().into(dp);
                            }
                            else
                            {
                                dp.setImageResource(R.drawable.nodp);
                            }
                        } else {
                            Toast.makeText(profile.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profile.this,editProfile.class);
                intent.putExtra("NAME",name.getText().toString());
                intent.putExtra("DEPARTMENT",department.getText().toString());
                intent.putExtra("GENDER",gender.getText().toString());
                intent.putExtra("PHONE",phone.getText().toString());
                intent.putExtra("CITY",city.getText().toString());
                intent.putExtra("ADDRESS",address.getText().toString());
                intent.putExtra("RATE",rate.getText().toString());
                intent.putExtra("AGE",age.getText().toString());
                intent.putExtra("URL",link);
                startActivity(intent);
            }
        });

    }
}
