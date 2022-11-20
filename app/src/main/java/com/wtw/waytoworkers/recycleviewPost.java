package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class recycleviewPost extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private postAdapter postAdapter;
    private List<Post> postList;
    private LinearLayoutManager mLinearLayoutManager;
    String cUid;
    LinearLayout nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_post);

        recyclerView=findViewById(R.id.postRecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new postAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);
        progressBar=findViewById(R.id.loadPostProgress);
        nothing=findViewById(R.id.nothing);
        nothing.setVisibility(View.GONE);

        //
        SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int isFromHome=pre.getInt("POST_TO_HOME",1);
        SharedPreferences savedpref = getSharedPreferences("MY_SHARED_LOC", MODE_PRIVATE);
        cUid=savedpref.getString("USER_ID","");

        //IF FROM HOME DISPLAY ALL POST
        if(isFromHome==1)
        {
            Query query= FirebaseDatabase.getInstance().getReference("Post");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    if (dataSnapshot.exists()) {
                        progressBar.setVisibility(View.GONE);
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = snapshot.getValue(Post.class);
                            postList.add(post);
                        }
                        postAdapter.notifyDataSetChanged();
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        nothing.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(recycleviewPost.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();

                }
            });
            postAdapter.setOnItemClickListner(new postAdapter.OnItemClickListner() {
                @Override
                public void onDeleteClick(int position) {
                    Toast.makeText(recycleviewPost.this,"Go to settings(my post) to delete",Toast.LENGTH_SHORT).show();
                }
            });
        }
        //IF FROM SETTINGS ONLY CURRENT USER POST
        else if(isFromHome==0)
        {
            Query query= FirebaseDatabase.getInstance().getReference("Post")
                    .orderByChild("cuid")
                    .equalTo(cUid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    if (dataSnapshot.exists()) {
                        progressBar.setVisibility(View.GONE);
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = snapshot.getValue(Post.class);
                            postList.add(post);
                        }
                        postAdapter.notifyDataSetChanged();
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        nothing.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(recycleviewPost.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();

                }
            });

            postAdapter.setOnItemClickListner(new postAdapter.OnItemClickListner() {
                @Override
                public void onDeleteClick(final int position) {
                    Query query= FirebaseDatabase.getInstance().getReference("Post")
                            .orderByChild("cuid")
                            .equalTo(cUid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                }
                                postAdapter.notifyItemRemoved(position);
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                nothing.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(recycleviewPost.this,"Your post deleted",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
