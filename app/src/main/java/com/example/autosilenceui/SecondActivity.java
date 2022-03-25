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
    private CustomAdapter customAdapter;
    private ImageView backButton;
    private ArrayList<Location> locations = new ArrayList<Location>();
    private String[] names = {"Regal Ronkonkoma Stadium 9", "Movieland of Coram", "Sayville Theatre", "AMC Loews Stony Brook 17", "P.J. Cinemas", "Island Cinemas - Mastic", "Regal Deer Park Stadium 16 & IMAX", "Elwood Cinema", "South Bay Cinemas"};
    private String[] addresses = {"565 Portion Road, Ronkonkoma, NY", "1850 Rt 112,Coram, NY", "103 Railroad Ave.,Sayville, NY", "2196 Nesconset Highway, Stony Brook, NY", "1068 Route 112, Port Jefferson Station, NY", "1708 Montauk Highway, Mastic, NY", "1050 Commack Road, Deer Park, NY", "1950 East Jericho Turnpike, East Northport, NY", "495 W. Montauk Hwy, West Babylon, NY"};
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("test", "success2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("0/address");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("test", "Value is: " + value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("test", "Failed to read value.", error.toException());
            }
        });
//        Log.d("print", "" + myRef.child("3085").child("address").);
        listView = (ListView) findViewById(R.id.listview);
        customAdapter = new CustomAdapter(getApplicationContext(), locations);
        listView.setAdapter(customAdapter);
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
};
