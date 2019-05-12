package com.company.rashminpc.mqtttest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Main3Activity.this, MainPage.class));
                finish();
            }
        }, secondsDelayed * 2000);
       /* Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();*/
    }

}
