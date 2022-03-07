package com.example.autosilenceui;

import static android.app.Service.START_STICKY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView silenceButton;
    private TextView textView;
    private AudioManager audioManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private int PERMISSION_ID = 44;
    private ArrayList<Location> locationArrayList;
    private Scanner scanner;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("status", "working");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        silenceButton = findViewById(R.id.silence);
        textView = findViewById(R.id.your_device);
        try {
            scanner = new Scanner(new File(context.getFilesDir().getAbsolutePath() + "/theaters.csv"));
            Log.d("test", "" + scanner.next());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                        if(location.getLatitude() > 37 && location.getLongitude() < 38 && location.getLongitude() < -122 && location.getLongitude() > -123){
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                updateText();
                        }
                    }
                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.getMainLooper());
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