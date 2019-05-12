package com.company.rashminpc.mqtttest;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;

public class CarControl extends AppCompatActivity {
    ImageButton UP,DOWN,RIGHT,LEFT;
    WebView streamView;
    String videoUrLUp="http://172.20.10.14:80/forward";
    String videoUrLDown="http://172.20.10.14:80/backwards";
    String videoUrLRight="http://172.20.10.14:80/right";
    String videoUrLLeft="http://172.20.10.14:80/left";
    public MediaPlayer mediaPlayer;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_control);
        streamView = (WebView)findViewById(R.id.streamview1);
        UP=(ImageButton)findViewById(R.id.up);
        DOWN=(ImageButton)findViewById(R.id.down);
        RIGHT=(ImageButton)findViewById(R.id.right);
        LEFT=(ImageButton)findViewById(R.id.left);
        UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //yukarı kod komutları buraya
                mediaController = new MediaController(CarControl.this);
                mediaController.setAnchorView(streamView);
                streamView.loadUrl(videoUrLUp);
            }
        });
        DOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aşağı kod komutları buraya
                mediaController = new MediaController(CarControl.this);
                mediaController.setAnchorView(streamView);
                streamView.loadUrl(videoUrLDown);
            }
        });
        RIGHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sağ kod komutları buraya
                mediaController = new MediaController(CarControl.this);
                mediaController.setAnchorView(streamView);
                streamView.loadUrl(videoUrLRight);
            }
        });
        LEFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sol kod komutları buraya
                mediaController = new MediaController(CarControl.this);
                mediaController.setAnchorView(streamView);
                streamView.loadUrl(videoUrLLeft);
            }
        });
    }
}
