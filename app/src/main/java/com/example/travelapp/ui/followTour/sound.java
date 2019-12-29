package com.example.travelapp.ui.followTour;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import hcmus.tvp.testretrofit.Retrofit.ListTours;
//import hcmus.tvp.testretrofit.Retrofit.ListToursService;
//import hcmus.tvp.testretrofit.Retrofit.Login;
//import hcmus.tvp.testretrofit.Retrofit.LoginService;
//import hcmus.tvp.testretrofit.Retrofit.Register;
//import hcmus.tvp.testretrofit.Retrofit.RegisterError;
//import hcmus.tvp.testretrofit.Retrofit.RegisterService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.Target;
import com.example.travelapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class sound extends AppCompatActivity {
    private static final int REQUEST_CODE = 005;
    String token;
    String pathSave = "";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    TextView txtRes;
    int code = 1004;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        final Button btnStart = findViewById(R.id.start);
        final Button btnStop = findViewById(R.id.stop);
        final Button btnPlay = findViewById(R.id.play);
        final Button btnStopPlay = findViewById(R.id.stoprecord);
        if(checkPermissionOnCreate()){

        }
        else{
            requestPermissionOnCreate();
        }
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio.3gp";
                setUpMediaRecorder();
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                btnPlay.setEnabled(false);
                btnStopPlay.setEnabled(false);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                btnStop.setEnabled(true);
                btnPlay.setEnabled(true);
                btnStopPlay.setEnabled(false);
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStop.setEnabled(false);
                btnStart.setEnabled(false);
                btnStopPlay.setEnabled(true);
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(pathSave);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                Toast.makeText(getApplicationContext(), "Playing......", Toast.LENGTH_SHORT).show();
            }
        });
        btnStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart.setEnabled(true);
                btnPlay.setEnabled(true);
                btnStop.setEnabled(false);
                btnStopPlay.setEnabled(false);
                if(mediaPlayer!=null)
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    setUpMediaRecorder();
                }
            }
        });
    }

    private void setUpMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }

    private void requestPermissionOnCreate() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
            {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Granted Permission", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "No hope", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private boolean checkPermissionOnCreate() {
        int write_internal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_internal == PackageManager.PERMISSION_GRANTED && record == PackageManager.PERMISSION_GRANTED;
    }


}
