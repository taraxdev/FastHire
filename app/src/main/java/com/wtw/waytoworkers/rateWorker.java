package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class rateWorker extends AppCompatActivity {

    RatingBar ratingBar;
    TextView textView;
    DatabaseReference databaseReference,databaserefworker;
    public  float stars;
    String wId;
    Rating rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_worker);

        ratingBar=findViewById(R.id.Rating);
        textView=findViewById(R.id.textRating);
        //database reference
        databaseReference= FirebaseDatabase.getInstance().getReference("Rating");
        databaserefworker=FirebaseDatabase.getInstance().getReference("Worker");

        Bundle bundle=getIntent().getExtras();
        wId=bundle.getString("workerRatingId");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                stars= ratingBar.getRating();
                calculate();
                textView.setText("You rated "+stars+ " stars.");
                ratingBar.setIsIndicator(true);
                SavePreferences("BOOKED_WORKER_ID","");
            }
        });
    }

    private void calculate() {
        Query query = FirebaseDatabase.getInstance().getReference("Rating")
                .orderByChild("workerratingid")
                .equalTo(wId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    rating = childSnapshot.getValue(Rating.class);
                    if(rating!=null){
                        
                        float num,sum,avg,num1,sum1;
                        num=rating.getNumberofrating();
                        sum=rating.getSumofrating();
                        
                        num1=num+1;
                        sum1=sum+stars;
                        avg=sum1/num1;
                        
                        databaseReference.child(rating.getItemid()).child("sumofrating").setValue(sum1);
                        databaseReference.child(rating.getItemid()).child("numberofrating").setValue(num1);
                        databaseReference.child(rating.getItemid()).child("averagerating").setValue(avg);

                        databaserefworker.child(rating.getWorkerratingid()).child("workerrating").setValue(avg);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_LOC", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
