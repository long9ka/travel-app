package com.example.travelapp.ui.followTour;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;

import java.io.IOException;

public class sound extends AppCompatActivity {
    private Button start, stop, play;
    private MediaRecorder myAudioRecorder;
    private String outputFile=Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        anh xa
        setContentView(R.layout.activity_sound);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        play = findViewById(R.id.play);
        stop.setEnabled(false);
        play.setEnabled(false);
//        cau hinh media
        final MediaRecorder myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
//        start
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                start.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Recording started0", Toast.LENGTH_LONG).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
//                myAudioRecorder =null;
                start.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully",Toast.LENGTH_LONG).show();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer=new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(),"Playing",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
