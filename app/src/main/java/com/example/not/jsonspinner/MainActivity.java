package com.example.not.jsonspinner;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.not.jsonspinner.JSON.JsonDownloader;

public class MainActivity extends AppCompatActivity {

    private String mJsonUrl = "http://careers.picpay.com/tests/mobdev/users";
    private Spinner mSpinner;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonDownloader jsonDownloader = new JsonDownloader(MainActivity.this, mJsonUrl, mSpinner);
                jsonDownloader.execute();
            }
        });

    }
}
