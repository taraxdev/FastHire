package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class workersignup extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int MY_STORAGE_REQUEST_CODE = 100;
    EditText workername,workercity,workeraddress,workeremail,workerpassword,conformPassword,
            workerage,workerrate,workerimageurl;
    Spinner workerspinnerDept,workerspinnerGender;
    Button signupbtn;
    String workerphone;
    private FirebaseAuth mAuth;
    private static final String TAG = "";

    DatabaseReference databaseWorker,databaseRatingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workersignup);
        //database reference
        databaseWorker= FirebaseDatabase.getInstance().getReference("Worker");
        databaseRatingRef= FirebaseDatabase.getInstance().getReference("Rating");
        //getExtra
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                workerphone= null;
            } else {
                workerphone= extras.getString("verifiedPhoneNumber");
            }
        } else {
            workerphone= (String) savedInstanceState.getSerializable("verifiedPhoneNumber");
        }
        //database
        workername=findViewById(R.id.workername);
        workercity=findViewById(R.id.workercity);
        workeraddress=(EditText)findViewById(R.id.workerAddress);
        workeremail=(EditText)findViewById(R.id.worker_email);
        workerpassword=(EditText)findViewById(R.id.worker_password);
        conformPassword=findViewById(R.id.worker_conformpassword);
        workerspinnerDept=(Spinner)findViewById(R.id.spinnerdept);
        workerspinnerGender=(Spinner)findViewById(R.id.workerGenderspinner);
        workerage=findViewById(R.id.workerAge);
        workerrate=findViewById(R.id.workerRate);
        workerimageurl=findViewById(R.id.workerLink);

        signupbtn=(Button)findViewById(R.id.workerbtn_signup) ;
        //

        final ProgressBar progressBar;
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = workeremail.getText().toString();
                String password = workerpassword.getText().toString();
                String name=workername.getText().toString();
                String city=workercity.getText().toString().trim();
                String address=workeraddress.getText().toString();
                String conformpass=conformPassword.getText().toString();
                String age=workerage.getText().toString();
                String rate=workerrate.getText().toString();


                if (TextUtils.isEmpty(email)) {
                   workeremail.setError("Enter Email id");
                   workeremail.requestFocus();
                   return;
                }
                if (TextUtils.isEmpty(name)) {
                    workername.setError("Enter Name");
                    workername.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    workercity.setError("Enter City");
                    workercity.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    workeraddress.setError("Enter Address");
                    workeraddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(age)) {
                    workerage.setError("Enter Age");
                    workerage.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(rate)) {
                    workerrate.setError("Enter Rate");
                    workerrate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    workerpassword.setError("Enter Password");
                    workerpassword.requestFocus();
                    return;
                }
                if (!password.equals(conformpass)){
                    conformPassword.setError("Conform password not matching");
                    conformPassword.requestFocus();
                    return;
                }
                if(!name.matches("") && !city.matches("") && !address.matches("")
                &&  conformpass.equals(password) && !age.matches("") && !rate.matches("")){

                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(workersignup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        adduser();
                                        Intent intent = new Intent(workersignup.this, homepage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(workersignup.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        //To signin page
        TextView signinbtn=(TextView)findViewById(R.id.workerlogin_page);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(workersignup.this, signin.class));
            }
        });

        /*Button buttonLoadImage = (Button) findViewById(R.id.workerLoadbtn);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_STORAGE_REQUEST_CODE);
            }
        }*/
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(Objects.requireNonNull(selectedImage),
                    filePathColumn, null, null, null);
            Objects.requireNonNull(cursor).moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.workerimgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }*/

    private void adduser(){

        String wemail=workeremail.getText().toString();
        String wdept=workerspinnerDept.getSelectedItem().toString();
        String wname=workername.getText().toString().trim();
        String wphone=workerphone;
        String wgender=workerspinnerGender.getSelectedItem().toString();
        String wcity=workercity.getText().toString().trim();
        String waddress=workeraddress.getText().toString();
        String wage=workerage.getText().toString();
        String wrate=workerrate.getText().toString();
        String cpd=(workercity.getText().toString().toUpperCase().trim()+workerspinnerDept.getSelectedItem().toString().toUpperCase().trim());
        String s=workerimageurl.getText().toString();
        float stars;
        stars=0;
        String wavailable="AVAILABLE";
        String wimageurl;
        if (s!=null)
        {
           wimageurl=(workerimageurl.getText().toString());
        }
        else
        {
            wimageurl="";
        }
        String id=databaseWorker.push().getKey();
        Worker worker=new Worker(wemail,id,wdept,wname,wphone,wgender,wcity,waddress,wage,wrate,cpd,wimageurl,wavailable,stars);
        databaseWorker.child(id).setValue(worker);

        float avg=0,num=0,sum=0;
        String id2=databaseRatingRef.push().getKey();
        Rating rating1= new Rating(id2,id,num,sum,avg);
        databaseRatingRef.child(id2).setValue(rating1);
    }
}
