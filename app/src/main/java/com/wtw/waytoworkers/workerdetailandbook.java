package com.wtw.waytoworkers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class workerdetailandbook extends Activity {

    private static final int REQUEST_PHONE_CALL =102 ;
    private static final int MY_SMS_CODE = 103;
    ImageView displaypic, deptpic;
    ImageButton callbtn;
    TextView name, department, status, address, phone, age, rate, gender, city, email,numberofratings;
    DatePicker datePicker;
    RatingBar ratingBar;
    Button bookButton,rateBtn;
    Dialog popupDialog;
    int day,month,year;
    String date,wId;
    int cDay,cMonth,cYear;
    String cDate,cName,cUid,cAddress,bookedWorkID,present;
    DatabaseReference databaseReference,databaseRatingRef;
    public  Booked booked;
    Rating rating;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workerdetailsandbook);

        displaypic = findViewById(R.id.detailspic);
        deptpic = findViewById(R.id.detailsDeptPic);
        callbtn = findViewById(R.id.detailsCall);
        name = findViewById(R.id.detailsName);
        department = findViewById(R.id.detailsDept);
        status = findViewById(R.id.detailsStatus);
        address = findViewById(R.id.detailsAddress);
        phone = findViewById(R.id.detailsPhone);
        datePicker = findViewById(R.id.datepicker);
        ratingBar = findViewById(R.id.detailsRating);
        bookButton = findViewById(R.id.detailsBookbtn);
        age = findViewById(R.id.detailsAge);
        rate = findViewById(R.id.detailsRate);
        gender = findViewById(R.id.detailsGender);
        city = findViewById(R.id.detailsCity);
        rateBtn=findViewById(R.id.ratingButton);
        numberofratings=findViewById(R.id.numberofratings);

        rating=new Rating();
        //
        SharedPreferences savedpref = getSharedPreferences("MY_SHARED_LOC", MODE_PRIVATE);
        cName=savedpref.getString("CU_NAME","");
        cUid=savedpref.getString("USER_ID","");
        cAddress=savedpref.getString("ADDRESS","");
        bookedWorkID=savedpref.getString("BOOKED_WORKER_ID","");
        present=savedpref.getString("PRESENT","");

        //database reference
        databaseReference= FirebaseDatabase.getInstance().getReference("Booked");
        databaseRatingRef= FirebaseDatabase.getInstance().getReference("Rating");

        booked=new Booked();
        //POPUP WINDOW
        popupDialog=new Dialog(this);

        //SETTING VALUES TO HOLDERS
        final Intent intent = getIntent();
        final Worker worker = intent.getParcelableExtra("details");

        name.setText(worker.getWorkername().toUpperCase());
        department.setText(worker.getWorkerdept());
        address.setText("Address: "+worker.getWorkeraddress());
        phone.setText("Phone: "+worker.getWorkerphone());
        age.setText("Age: "+worker.getWorkerage());
        rate.setText("Rupees/hour: "+worker.getWorkerrate());
        gender.setText("Gender: "+worker.getWorkergender());
        city.setText("City: "+worker.getWorkercity());
        status.setText(worker.getWorkeravailable());

        wId=worker.getWorkerid();
        //CURRENT DEFAULT DATE
        cDay=day=datePicker.getDayOfMonth();
        cMonth=month=datePicker.getMonth()+1;
        cYear=year=datePicker.getYear();
        cDate=cDay+"|"+cMonth+"|"+cYear;
        date = cDate;

        //date from datepicker
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
            //DISABLING PAST DATES
        datePicker.setMinDate(System.currentTimeMillis());
            //SETTING CHANGE LISTENER
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                day=d;
                month=m+1;
                year=y;
                date=day+"|"+month+"|"+year;
            }
        });

        if (status.getText().equals("AVAILABLE"))
        {
            status.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else
        {
            status.setTextColor(Color.RED);
        }
        //LINK
        String driveLink=worker.workerimageurl;
        if (driveLink.length()!=0 && driveLink.length()>=65) {
            String substr = " ";
            substr = driveLink.substring(32, 65);
            String loadUrl = "https://docs.google.com/uc?id=" + substr;
            Glide.with(workerdetailandbook.this).load(loadUrl).circleCrop().into(displaypic);
        }
        else
        {
            displaypic.setImageResource(R.drawable.nodp);
        }

        if (worker.getWorkerdept().equals("Farmer")) {
            deptpic.setImageResource(R.drawable.agriculture);
        } else if (worker.getWorkerdept().equals("Contractor")) {
            deptpic.setImageResource(R.drawable.contractor);
        } else if (worker.getWorkerdept().equals("Carpenter")) {
            deptpic.setImageResource(R.drawable.carpenter);
        } else if (worker.getWorkerdept().equals("Laundry")) {
            deptpic.setImageResource(R.drawable.laundry);
        } else if (worker.getWorkerdept().equals("Service worker")) {
            deptpic.setImageResource(R.drawable.services);
        } else if (worker.getWorkerdept().equals("Mechanic")) {
            deptpic.setImageResource(R.drawable.mechanic);
        } else if (worker.getWorkerdept().equals("Electrician")) {
            deptpic.setImageResource(R.drawable.electrician);
        } else if (worker.getWorkerdept().equals("Chef")) {
            deptpic.setImageResource(R.drawable.chef);
        } else if (worker.getWorkerdept().equals("Hairdresser")) {
            deptpic.setImageResource(R.drawable.salon);
        }

        if(bookedWorkID.equals(worker.getWorkerid()))
        {
            rateBtn.setVisibility(View.VISIBLE);
        }

        Query query = FirebaseDatabase.getInstance().getReference("Rating")
                .orderByChild("workerratingid")
                .equalTo(wId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    rating = childSnapshot.getValue(Rating.class);
                    if (childSnapshot.exists()) {
                        float t = rating.getAveragerating();
                        ratingBar.setRating(t);
                        numberofratings.setText("("+ ((int) rating.getNumberofrating())+")");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //CALLING WORKER
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(workerdetailandbook.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(workerdetailandbook.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    String number;
                    number=worker.getWorkerphone();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    startActivity(intent);
                }
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        //IF WORKER AVAILABLE BOOK / BLOCK BOOKING
                        if (worker.workeravailable.equals("AVAILABLE"))
                        {
                            if (worker.workerid.equals(cUid))
                            {
                                Toast.makeText(workerdetailandbook.this,"Cannot book yourself",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(worker.workerdept.equals("Farmer") || worker.workerdept.equals("Contractor"))
                                {
                                    if(cDate.equals(date)){
                                        Toast.makeText(workerdetailandbook.this,"Farmer/Contractor should be booked one day before",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        showpopup();
                                    }
                                }
                                else {
                                    showpopup();
                                }
                            }
                        }
                        else {
                            Toast.makeText(workerdetailandbook.this,"Worker Not Available",Toast.LENGTH_SHORT).show();
                        }
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(workerdetailandbook.this,rateWorker.class);
                intent.putExtra("workerRatingId",worker.getWorkerid());
                startActivity(intent);
            }
        });

    }

    //POPUP WINDOW
    private void showpopup() {

        Button  conformBook;
        ImageButton close;
        TextView displayDetails;
        popupDialog.setContentView(R.layout.booking_popup);
        close=popupDialog.findViewById(R.id.popupclose);
        displayDetails=popupDialog.findViewById(R.id.popupText);
        conformBook=popupDialog.findViewById(R.id.popupButton);
        final String phoneNumber,message;
        phoneNumber=phone.getText().toString();

        //GETTING PARCELLED INFO
        Intent intent = getIntent();
        final Worker worker = intent.getParcelableExtra("details");

        displayDetails.setText("You are booking "+worker.getWorkerdept().toUpperCase()
                +" named "+worker.getWorkername().toUpperCase()
                +" on "+day+"|"+month+"|"+year+" at rupees "+worker.getWorkerrate().toUpperCase() +"/per hour.");

        /*message="I "+cName +" booked you ("+worker.getWorkerdept().toUpperCase()+") on "+day+"|"+month+"|"+year
                +" at rupees "+worker.getWorkerrate().toUpperCase() +"/per hour.Work location- "+cAddress+". From WAY TO WORKERS.";*/

        //ONCLICK LISTENERS
        conformBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        /*SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null);*/
                        popupDialog.dismiss();
                        addBookingToFirebase();
                        rateBtn.setVisibility(View.VISIBLE);
                        SavePreferences("BOOKED_WORKER_ID",worker.getWorkerid());
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });
        popupDialog.show();
    }


    private void addBookingToFirebase() {
        //GETTING PARCELLED INFO
        Intent intent = getIntent();
        final Worker worker = intent.getParcelableExtra("details");

        String currentUserId=cUid;
        String bookedWorkerId=worker.workerid;
        String dateBooking=day+"|"+month+"|"+year;

        String id=databaseReference.push().getKey();
        Booked booked=new Booked(currentUserId,bookedWorkerId,dateBooking);
        databaseReference.child(id).setValue(booked);
        Toast.makeText(workerdetailandbook.this,"BOOKING CONFORMED",Toast.LENGTH_SHORT).show();
    }


    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_LOC", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
