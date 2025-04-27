package com.example.auticare;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView hiUserText, dateText, bandStatus;
    TextView stressStatus;
    ProgressBar stressProgress;
    TextView heartRateValue, spo2Value, tempValue, respValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find all views
        hiUserText = findViewById(R.id.hiUserText);
        dateText = findViewById(R.id.dateText);
        bandStatus = findViewById(R.id.bandStatus);
        stressStatus = findViewById(R.id.stressStatus);
        stressProgress = findViewById(R.id.stressProgress);
        heartRateValue = findViewById(R.id.heartRateValue);
        spo2Value = findViewById(R.id.spo2Value);
        tempValue = findViewById(R.id.tempValue);
        respValue = findViewById(R.id.respValue);

        // --- Dummy Data Loading (TEMPORARY) ---
        hiUserText.setText("Hi Ashwin");
        dateText.setText("Monday 18, 2023");
        bandStatus.setText("Band Connected •");

        stressStatus.setText("Calm ...");
        stressProgress.setProgress(40);

        heartRateValue.setText("72 bpm");
        spo2Value.setText("98 %");
        tempValue.setText("36.7 °C");
        respValue.setText("28.2 /min");
    }
}
