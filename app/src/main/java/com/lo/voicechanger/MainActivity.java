package com.lo.voicechanger;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT) * 2;

    private AudioRecord recorder;
    private AudioTrack player;
    private Thread processingThread;
    private boolean isRunning = false;
    private float pitchFactor = 1.0f;

    private Button btnStart;
    private SeekBar seekPitch;
    private TextView tvPitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        seekPitch = findViewById(R.id.seekPitch);
        tvPitch = findViewById(R.id.tvPitch);

        ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.RECORD_AUDIO}, 1);

        seekPitch.setMax(20);
        seekPitch.setProgress(10);
        seekPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar s, int p, boolean u) {
                pitchFactor = 0.5f + (p / 10.0f);
                tvPitch.setText("Ses Tonu: " + String.format("%.1f", pitchFactor));
            }
            public void onStartTrackingTouch(SeekBar s) {}
            public void onStopTrackingTouch(SeekBar s) {}
        });

        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startProcessing();
                btnStart.setText("Durdur");
            } else {
                st