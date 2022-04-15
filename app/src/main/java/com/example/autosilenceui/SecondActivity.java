package com.example.autosilenceui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {
    private ListView listView;
    private LocationsAdapter adapter;
    private ImageView backButton;
    private ArrayList<Location> locations = new ArrayList<Location>();
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("test", "success2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        for(DatabaseReference myRef : database.) {
        DatabaseReference myRef = database.getReference();
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class) + ", " + snapshot.child("city").getValue(String.class) + ", " + snapshot.child("state").getValue(String.class) + " " + snapshot.child("zip").getValue(Long.class);
                        int lon = snapshot.child("lon").getValue(Integer.class);
                        int lat = snapshot.child("lat").getValue(Integer.class);
                        Location loc = new Location(name, address, lon, lat);
                        locations.add(loc);
                        Log.d("test", "Value is: " + address);
                    }
                    listView = (ListView) findViewById(R.id.listview);
                    adapter = new LocationsAdapter(getApplicationContext(), locations);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("test", "Failed to read value.", error.toException());
                }
            });
//        }
//        Log.d("print", "" + myRef.child("3085").child("address").);
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment, new MyFragment());
//                ft.commit();
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
