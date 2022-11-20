package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class editProfile extends AppCompatActivity {
    TextView name,phone,city,rate,age,address,department,gender,link;
    Button editBtn;
    LinearLayout Llayout,deptLayout;
    User user;
    Worker worker;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        name=findViewById(R.id.editName);
        phone=findViewById(R.id.editPhone);
        city=findViewById(R.id.editCity);
        rate=findViewById(R.id.editRate);
        age=findViewById(R.id.editAge);
        address=findViewById(R.id.editAddress);
        editBtn=findViewById(R.id.editEditBtn);
        Llayout=findViewById(R.id.editLayoutVisibility);
        gender=findViewById(R.id.editGender);
        department=findViewById(R.id.editDept);
        link=findViewById(R.id.editLink);
        deptLayout=findViewById(R.id.editDeptLayout);
        //retreving data from database
        user=new User();
        worker=new Worker();
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        final int flag=pre.getInt("FLAG",1);

        Bundle extras=getIntent().getExtras();
        name.setText(extras.getString("NAME"));
        phone.setText(extras.getString("PHONE"));
        city.setText(extras.getString("CITY"));
        address.setText(extras.getString("ADDRESS"));
        gender.setText(extras.getString("GENDER"));
        link.setText(extras.getString("URL"));
        if (flag==1) {
            rate.setText(extras.getString("RATE"));
            age.setText(extras.getString("AGE"));
            department.setText(extras.getString("DEPARTMENT"));
        }
        else if(flag==0){
            deptLayout.setVisibility(View.GONE );
            Llayout.setVisibility(View.GONE);
        }

        //EDIT BUTTON ONCLICK
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IF WORKER
                if (flag==1)
                {
                    databaseReference=FirebaseDatabase.getInstance().getReference("Worker");
                    Query query = FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workeremail")
                            .equalTo(mAuth.getCurrentUser().getEmail());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                worker = childSnapshot.getValue(Worker.class);
                                if (worker != null) {

                                    //UPDATING AVAILABILITY
                                    databaseReference.child(worker.getWorkerid()).child("workeraddress").setValue(address.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workerage").setValue(age.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workercity").setValue(city.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workerdept").setValue(department.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workergender").setValue(gender.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workerimageurl").setValue(link.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workername").setValue(name.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workerphone").setValue(phone.getText().toString());
                                    databaseReference.child(worker.getWorkerid()).child("workerrate").setValue(rate.getText().toString());
                                    //UPDATING CPD WHEN CITY CHANGES
                                    String cpd1,cty,dpmt;
                                    cty=city.getText().toString().toUpperCase().trim();
                                    dpmt=department.getText().toString().toUpperCase().trim();
                                    cpd1=cty+dpmt;
                                    databaseReference.child(worker.getWorkerid()).child("workercpd").setValue(cpd1);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(editProfile.this,"UPDATE FAILED",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                //IF USER
                else if(flag==0){
                    databaseReference=FirebaseDatabase.getInstance().getReference("User");
                    Query query = FirebaseDatabase.getInstance().getReference("User")
                            .orderByChild("useremail")
                            .equalTo(mAuth.getCurrentUser().getEmail());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                user = childSnapshot.getValue(User.class);
                                if (user != null) {
                                    //UPDATING AVAILABILITY
                                    databaseReference.child(user.getUserid()).child("useraddress").setValue(address.getText().toString());
                                    databaseReference.child(user.getUserid()).child("usercity").setValue(city.getText().toString());
                                    databaseReference.child(user.getUserid()).child("usergender").setValue(gender.getText().toString());
                                    databaseReference.child(user.getUserid()).child("userimgurl").setValue(link.getText().toString());
                                    databaseReference.child(user.getUserid()).child("username").setValue(name.getText().toString());
                                    databaseReference.child(user.getUserid()).child("userphone").setValue(phone.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                Toast.makeText(editProfile.this,"UPDATED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(editProfile.this,profile.class));
                finish();
            }

        });
    }
}
