package com.example.stopwatchar;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimeDisplay;
    private Button btnStart, btnPause, btnReset, btnLap;
    private LinearLayout lapContainer;

    private Stopwatch stopwatch = new Stopwatch();
    private Handler handler = new Handler();
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimeDisplay();
            handler.postDelayed(this, 50); // Update every 50ms
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimeDisplay = findViewById(R.id.tvTimeDisplay);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);
        btnLap = findViewById(R.id.btnLap);
        lapContainer = findViewById(R.id.lapContainer);

        setupListeners();
    }

    private void setupListeners() {
        btnStart.setOnClickListener(v -> {
            stopwatch.start();
            startUpdating();
        });

        btnPause.setOnClickListener(v -> {
            stopwatch.pause();
            stopUpdating();
        });

        btnReset.setOnClickListener(v -> {
            stopwatch.reset();
            stopUpdating();
            tvTimeDisplay.setText("00:00:00");
            lapContainer.removeAllViews();
        });

        btnLap.setOnClickListener(v -> {
            if (stopwatch.isRunning()) {
                stopwatch.lap(stopwatch.getCurrentTime());
                addLapView(stopwatch.getLaps().get(stopwatch.getLaps().size() - 1));
            }
        });
    }

    private void startUpdating() {
        if (!handler.hasCallbacks(updateRunnable)) {
            handler.post(updateRunnable);
        }
    }

    private void stopUpdating() {
        handler.removeCallbacks(updateRunnable);
    }

    private void updateTimeDisplay() {
        long time = stopwatch.getCurrentTime();
        tvTimeDisplay.setText(Stopwatch.formatTime(time));


    }

    private void addLapView(String lapText) {
        Button lapBtn = new Button(this);
        lapBtn.setText(lapText);
        lapBtn.setEnabled(false);
        lapBtn.setAllCaps(false);
        lapBtn.setBackgroundColor(getColor(android.R.color.transparent));
        lapBtn.setTextColor(getColor(android.R.color.holo_blue_dark));
        lapContainer.addView(lapBtn, 0); // Add at top
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopUpdating(); // Clean up handler
    }
}