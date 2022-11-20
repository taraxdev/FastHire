package com.wtw.waytoworkers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showlist extends AppCompatActivity implements workerAdapter.OnNoteListner{
    private RecyclerView recyclerView;
    private workerAdapter adapter;
    private List<Worker> workerList;
    ProgressBar progressBar;
    String city;
    LinearLayout nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlist);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workerList = new ArrayList<>();
        adapter = new workerAdapter(this, workerList,this);
        recyclerView.setAdapter(adapter);

        ImageView deptimage=findViewById(R.id.listdeptpic);
        TextView deptname=findViewById(R.id.listdeptname);
        progressBar=findViewById(R.id.loadListProgress);
        progressBar.setVisibility(View.VISIBLE);
        nothing=findViewById(R.id.showNothing);
        nothing.setVisibility(View.GONE);
        //workerimage=findViewById(R.id.listpic);

        SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int deptPref=pre.getInt("DEPARTMENT",0);
        int filterByLoc=pre.getInt("FBL",0);
        SharedPreferences LSP= getSharedPreferences("MY_SHARED_LOC",MODE_PRIVATE);
        city=LSP.getString("LOCATION","");


        //WHEN UNCHECKED
        if(filterByLoc==0)
        {
            switch(deptPref){
                case 1:Query query= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Farmer");
                    query.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.agriculture);
                    deptname.setText("List of Farmers");
                    break;
                case 2:Query query2= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Contractor");
                    query2.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.contractor);
                    deptname.setText("List of Contractors");
                    break;
                case 3:Query query3= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Carpenter");

                    query3.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.carpenter);
                    deptname.setText("List of Carpenter");
                    break;
                case 4:Query query4= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Laundry");
                    query4.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.laundry);
                    deptname.setText("List of Laundry");
                    break;
                case 5:Query query5= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Service worker");
                    query5.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.services);
                    deptname.setText("List of Service workers");
                    break;
                case 6:Query query6= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Mechanic");
                    query6.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.mechanic);
                    deptname.setText("List of Mechanic");
                    break;
                case 7:Query query7= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Electrician");
                    query7.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.electrician);
                    deptname.setText("List of Electrician");
                    break;
                case 8:Query query8= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Chef");
                    query8.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.chef);
                    deptname.setText("List of Chef");
                    break;
                case 9:Query query9= FirebaseDatabase.getInstance().getReference("Worker")
                        .orderByChild("workerdept")
                        .equalTo("Hairdresser");

                    query9.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.salon);
                    deptname.setText("List of Hairdresser");
                    break;
            }
        }
        //CHECKED
        else if (filterByLoc==1)
        {
            String cpd;
            switch(deptPref){

                case 1:cpd=city+"FARMER";
                    Query query= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.agriculture);
                    deptname.setText("List of Farmers");
                    break;

                case 2:cpd=city+"CONTRACTOR";
                    Query query2= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query2.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.contractor);
                    deptname.setText("List of Contractors");
                    break;

                case 3:cpd=city+"CARPENTER";
                    Query query3= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query3.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.carpenter);
                    deptname.setText("List of Carpenter");
                    break;

                case 4:cpd=city+"LAUNDRY";
                    Query query4= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query4.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.laundry);
                    deptname.setText("List of Laundry");
                    break;

                case 5:cpd=city+"SERVICE WORKER";
                    Query query5= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query5.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.services);
                    deptname.setText("List of Service workers");
                    break;

                case 6:cpd=city+"MECHANIC";
                    Query query6= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query6.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.mechanic);
                    deptname.setText("List of Mechanic");
                    break;

                case 7:cpd=city+"ELECTRICIAN";
                    Query query7= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query7.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.electrician);
                    deptname.setText("List of Electrician");
                    break;

                case 8:cpd=city+"CHEF";
                    Query query8= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);
                    query8.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.chef);
                    deptname.setText("List of Chef");
                    break;

                case 9:cpd=city+"HAIRDRESSER";
                    Query query9= FirebaseDatabase.getInstance().getReference("Worker")
                            .orderByChild("workercpd")
                            .equalTo(cpd);

                    query9.addListenerForSingleValueEvent(valueEventListener);
                    //setting IMAGE AND DEPARTMENT NAME
                    deptimage.setImageResource(R.drawable.salon);
                    deptname.setText("List of Hairdresser");
                    break;
            }
        }
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            workerList.clear();
            if (dataSnapshot.exists()) {
                progressBar.setVisibility(View.GONE);
                nothing.setVisibility(View.GONE);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Worker worker = snapshot.getValue(Worker.class);

                    workerList.add(worker);
                }
                adapter.notifyDataSetChanged();
            }
            else {
                progressBar.setVisibility(View.GONE);
                nothing.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(showlist.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void OnNoteClick(int position) {

        Intent intent= new  Intent(showlist.this,workerdetailandbook.class);
        intent.putExtra("details",workerList.get(position));
        startActivity(intent);
    }
}
