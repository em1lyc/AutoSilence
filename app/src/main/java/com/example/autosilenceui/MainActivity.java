package com.example.autosilenceui;

import static android.app.Service.START_STICKY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView silenceButton;
    private TextView locationButton;
    private TextView textView;
    private AudioManager audioManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private int PERMISSION_ID = 44;
    private ArrayList<Location> locationArrayList;
    private Scanner scanner;
    private double lat;
    private double lon;
    private double radius;
    private DatabaseReference mDatabase;
    private String[] names = {"Regal Ronkonkoma Stadium 9", "Movieland of Coram", "Sayville Theatre", "AMC Loews Stony Brook 17", "P.J. Cinemas", "Island Cinemas - Mastic", "Regal Deer Park Stadium 16 & IMAX", "Elwood Cinema", "South Bay Cinemas"};
    private String[] addresses = {"565 Portion Road, Ronkonkoma, NY", "1850 Rt 112,Coram, NY", "103 Railroad Ave.,Sayville, NY", "2196 Nesconset Highway, Stony Brook, NY", "1068 Route 112, Port Jefferson Station, NY", "1708 Montauk Highway, Mastic, NY", "1050 Commack Road, Deer Park, NY", "1950 East Jericho Turnpike, East Northport, NY", "495 W. Montauk Hwy, West Babylon, NY"};
    private ListView listView;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("status", "working");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        silenceButton = findViewById(R.id.silence);
        locationButton = findViewById(R.id.locations);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_locations);
                listView = (ListView) findViewById(R.id.listview);
                customAdapter = new CustomAdapter(getApplicationContext(), names, addresses);
                listView.setAdapter(customAdapter);
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment, new MyFragment());
//                ft.commit();
            }
        });
        textView = findViewById(R.id.your_device);

        try {
            Scanner scanner = new Scanner(new File("theaters.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("error", "didn't work");
        }

        updateText();
        int state = audioManager.getRingerMode();
        Log.d("state", "" + state);
        silenceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NotificationManager notificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                    startActivity(intent);
                }

                if (silenceButton.getText() == "SILENCE")
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                else
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                updateText();
            }

        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }

        mLocationRequest = mLocationRequest.create();
        mLocationRequest.setInterval(100);
        mLocationRequest.setFastestInterval(50);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    if (locationResult == null) {
                        return;
                    }
                    //Showing the latitude, longitude and accuracy on the home screen.
                    for (Location location : locationResult.getLocations()) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        Log.d("latitude", "" + lat);
                        Log.d("longitude", "" + lon);
                        if(Math.abs(lat - 37.8199) <= 0.0001 && Math.abs(lon - (-122.4783)) <= 0.0001){
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                updateText();
                        }
                    }
                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.getMainLooper());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
    void updateText() {
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            silenceButton.setText("SILENCE");
            textView.setText("Your device is currently not silenced");
        }
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
            silenceButton.setText("UNSILENCE");
            textView.setText("Your device is currently silenced");
        }
    }

}