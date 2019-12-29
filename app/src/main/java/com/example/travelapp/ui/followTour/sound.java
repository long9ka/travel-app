package com.example.travelapp.ui.followTour;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.travelapp.R;

import java.io.IOException;

public class sound extends AppCompatActivity {

    Button play, stop, start;
    String path_save="";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    final int REQUEST_PERMISSION_CODE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);


    }

    private boolean Check(){
        int write= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recode=ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write== PackageManager.PERMISSION_GRANTED && recode==PackageManager.PERMISSION_GRANTED;
    }
}
