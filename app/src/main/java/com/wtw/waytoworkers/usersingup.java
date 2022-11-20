package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

public class usersingup extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int MY_STORAGE_REQUEST_CODE = 100;
    EditText username,usercity,useraddress,useremail,userpassword,conformpass,userLink;
    Spinner userspinner;
    Button signupbtn;
    private FirebaseAuth mAuth;
    private static final String TAG = "";
    String userphone;
    Uri uriProfileImage;
    ProgressBar picprogress;
    //public String profileImageUrl;
    String imageReference;
    DatabaseReference databaseUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersingup);

        //database reference
        databaseUserRef= FirebaseDatabase.getInstance().getReference("User");
        //getExtra
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userphone= null;
            } else {
                userphone= extras.getString("verifiedPhoneNumber");
            }
        } else {
            userphone= (String) savedInstanceState.getSerializable("verifiedPhoneNumber");
        }
        //database
        username=findViewById(R.id.username);
        usercity=findViewById(R.id.usercity);
        useraddress=findViewById(R.id.useraddress);
        useremail=findViewById(R.id.user_email);
        userpassword=findViewById(R.id.user_password);
        //userLink=findViewById(R.id.userLink);
        userspinner=findViewById(R.id.userspinner);
        signupbtn=findViewById(R.id.userbtn_signup) ;
        conformpass=findViewById(R.id.user_conformpassword);
        imageReference="TEST";


        final ProgressBar progressBar;
        mAuth = FirebaseAuth.getInstance();
        progressBar =findViewById(R.id.progressBar);
        signupbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = useremail.getText().toString();
                String password = userpassword.getText().toString();
                String name=username.getText().toString();
                String city=usercity.getText().toString();
                String address=useraddress.getText().toString();
                String conformPass=conformpass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    useremail.setError("Enter Email Id");
                    useremail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    username.setError("Enter Name");
                    username.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    usercity.setError("Enter City");
                    usercity.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    useraddress.setError("Enter Address");
                    useraddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    userpassword.setError("Enter Password");
                    userpassword.requestFocus();
                    return;
                }
                if (!password.equals(conformPass)){
                    conformpass.setError("Conform password not matching ");
                    conformpass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                if(!name.matches("")  && !city.matches("") && !address.matches("")
                         && conformPass.equals(password)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(usersingup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        //upload to fire base
                                        //uploadImageToFirebase();
                                        adduser();
                                        Intent intent = new Intent(usersingup.this, homepage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(usersingup.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        //
        //
        //To signin page
        TextView signinbtn=(TextView)findViewById(R.id.userlogin_page);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usersingup.this, signin.class));
            }
        });

        //To upload photo
         /*Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        //ask permission if not allowed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_STORAGE_REQUEST_CODE);
            }
        } */
    }
    //outside oncreate
    private void adduser(){
        String uemail=useremail.getText().toString().trim();
        String uname=username.getText().toString().trim();
        String uphone=userphone;
        String ugender=userspinner.getSelectedItem().toString();
        String ucity=usercity.getText().toString().trim();
        String uaddress=useraddress.getText().toString();
        String uimageurl=userLink.getText().toString();

        String id=databaseUserRef.push().getKey();
        User user=new User(uemail,id,uname,uphone,ugender,ucity,uaddress,uimageurl);
        databaseUserRef.child(id).setValue(user);
    }
     /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            uriProfileImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(Objects.requireNonNull(uriProfileImage),
                    filePathColumn, null, null, null);
            Objects.requireNonNull(cursor).moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }


    }

   private void uploadImageToFirebase() {
        FirebaseUser firebaseUser;
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final User user=new User();
        picprogress.setVisibility(View.VISIBLE);
        if(uriProfileImage!=null)
        {
                final StorageReference profilereference=FirebaseStorage.getInstance().getReference().child("profilepics/" + firebaseUser.getUid() + "/" + uriProfileImage.getLastPathSegment());
                //final StorageReference profilereference= FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
                profilereference.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            picprogress.setVisibility(View.GONE);
                            //profileImageUrl= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            Task<Uri> downloadUrl = profilereference.getDownloadUrl();
                            downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageReference = uri.toString();
                                    FirebaseDatabase.getInstance().getReference().child("User").child(user.getUserid()).child("userimgurl").setValue(imageReference);
                                    //user.setUserimgurl(imageReference);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(usersingup.this,"DOWNLOAD FAILURE",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                }).addOnFailureListener(new OnFailureListener()
                {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            picprogress.setVisibility(View.GONE);
                            Toast.makeText(usersingup.this,"PUTFILE FAILURE",Toast.LENGTH_SHORT).show();
                            Toast.makeText(usersingup.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                });
        }
    }*/

}
