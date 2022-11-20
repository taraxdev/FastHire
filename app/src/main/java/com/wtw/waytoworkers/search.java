package com.wtw.waytoworkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class search extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);

        list = new ArrayList<>();
        list.add("Department");
        list.add("My posts");
        list.add("My bookings");
        list.add("Post something");
        list.add("Profile");
        list.add("Rate worker");
        list.add("Settings");
        list.add("View post");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                    if(query.equals("Department")){
                        startActivity(new Intent(search.this,department.class));
                    }
                    else if(query.equals("My posts")){
                        SavePreferences("POST_TO_HOME",0);
                        startActivity(new Intent(search.this,recycleviewPost.class));
                    }
                    else if(query.equals("My bookings")){
                        startActivity(new Intent(search.this,Booked.class));
                    }
                    else if(query.equals("Post something")){
                        startActivity(new Intent(search.this,postDetails.class));
                    }
                    else if(query.equals("Profile")){
                        startActivity(new Intent(search.this,profile.class));
                    }
                    else if(query.equals("Rate worker")){
                        startActivity(new Intent(search.this,department.class));
                    }
                    else if(query.equals("Settings")){
                        startActivity(new Intent(search.this,settings.class));
                    }
                    else if(query.equals("View post")){
                        SavePreferences("POST_TO_HOME",1);
                        startActivity(new Intent(search.this,recycleviewPost.class));
                    }
                }else{
                    Toast.makeText(search.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                   adapter.getFilter().filter(newText);
                return false;
            }
        });
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).equals("Department")){
                    startActivity(new Intent(search.this,department.class));
                }
                else if(list.get(position).equals("My Posts")){
                    startActivity(new Intent(search.this,recycleviewPost.class));
                }
            }
        }); */

    }
    private void SavePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
