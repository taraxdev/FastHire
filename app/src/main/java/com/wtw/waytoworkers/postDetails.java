package com.wtw.waytoworkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class postDetails extends AppCompatActivity {

    private ImageButton previewPost;
    private LinearLayout layoutPostPreview;
    private EditText link,caption;
    private TextView showCaption;
    private ImageView showPic;
    private Button postButton;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);

        previewPost=findViewById(R.id.postPreviewButton);
        layoutPostPreview=findViewById(R.id.postPreviewLayout);
        link=findViewById(R.id.postImageLink);
        caption=findViewById(R.id.postCaption);
        showPic=findViewById(R.id.postShowPic);
        showCaption=findViewById(R.id.postShowCaption);
        postButton=findViewById(R.id.postButton);

        //database reference
        databaseReference= FirebaseDatabase.getInstance().getReference("Post");

        //PREVIEW POST
        previewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl=link.getText().toString().trim();
                String imageCaption=caption.getText().toString().trim();

                //SETTING DETAILS TO PREVIEW
                if (imageCaption.length()!=0 ) {
                    showCaption.setText(imageCaption);
                }
                else
                {
                    showCaption.setText("NO CAPTION");
                }
                //LINK
                if (imageUrl.length()!=0 && imageUrl.length()>=65) {
                    String substr = " ";
                    substr = imageUrl.substring(32,65);
                    String loadUrl = "https://docs.google.com/uc?id=" + substr;
                    Glide.with(postDetails.this).load(loadUrl).centerCrop().into(showPic);
                }
                else {
                    Toast.makeText(postDetails.this,"INVALID LINK",Toast.LENGTH_LONG).show();
                }

                layoutPostPreview.setVisibility(View.VISIBLE);
            }
        });

        //TO FIREBASE
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(link.getText().toString().length()!=0 && link.getText().toString().length()>=65) {
                    addPostToFirebase();
                    startActivity(new Intent(postDetails.this, homepage.class));
                }
                else {
                    Toast.makeText(postDetails.this,"INVALID LINK, CANNOT POST",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addPostToFirebase() {

        //SHARED PREF
        SharedPreferences LSP= getSharedPreferences("MY_SHARED_LOC",MODE_PRIVATE);
        String dpurl=LSP.getString("PHOTO_URL","");
        String cuname= LSP.getString("CU_NAME","");
        String cuid=LSP.getString("USER_ID","") ;

        String postUrl=link.getText().toString().trim();
        String postCaption=caption.getText().toString().trim();

        String id=databaseReference.push().getKey();
        Post post=new Post(dpurl,cuname,cuid,postUrl,postCaption);
        databaseReference.child(id).setValue(post);
        Toast.makeText(postDetails.this,"POSTED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
    }
}
