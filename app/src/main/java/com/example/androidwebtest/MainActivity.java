package com.example.androidwebtest;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import io.micronaut.context.ApplicationContext;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ApplicationContext mServerContext;
    private ServerController mServerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mServerController = new ServerController(this);

        Button btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(v -> mServerController.startWebServer());

        Button btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(v -> mServerController.stopWebServer());
    }

}