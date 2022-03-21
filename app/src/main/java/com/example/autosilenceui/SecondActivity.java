package com.example.autosilenceui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter customAdapter;
    private ImageView backButton;
    private String[] names = {"Regal Ronkonkoma Stadium 9", "Movieland of Coram", "Sayville Theatre", "AMC Loews Stony Brook 17", "P.J. Cinemas", "Island Cinemas - Mastic", "Regal Deer Park Stadium 16 & IMAX", "Elwood Cinema", "South Bay Cinemas"};
    private String[] addresses = {"565 Portion Road, Ronkonkoma, NY", "1850 Rt 112,Coram, NY", "103 Railroad Ave.,Sayville, NY", "2196 Nesconset Highway, Stony Brook, NY", "1068 Route 112, Port Jefferson Station, NY", "1708 Montauk Highway, Mastic, NY", "1050 Commack Road, Deer Park, NY", "1950 East Jericho Turnpike, East Northport, NY", "495 W. Montauk Hwy, West Babylon, NY"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        listView = (ListView) findViewById(R.id.listview);
        customAdapter = new CustomAdapter(getApplicationContext(), names, addresses);
        listView.setAdapter(customAdapter);
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment, new MyFragment());
//                ft.commit();
        backButton = findViewById(R.id.imageView5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
            }

        });
    }
};
