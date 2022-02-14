package com.example.autosilenceui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView silenceButton;
    private AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        silenceButton = findViewById(R.id.silence);

        updateText();
        int state = audioManager.getRingerMode();
        Log.d("state","" + state);
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

                if(silenceButton.getText() == "SILENCE")
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                else
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                updateText();
            }
        });
    }
    void updateText() {
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
            silenceButton.setText("SILENCE");
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
            silenceButton.setText("UNSILENCE");
    }
}