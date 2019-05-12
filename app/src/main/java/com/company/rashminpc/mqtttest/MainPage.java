package com.company.rashminpc.mqtttest;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {
    Toolbar toolbar1;
    GridLayout gridLayout1;
    ImageButton car,gas,live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        car=(ImageButton)findViewById(R.id.CarControl);
        gas=(ImageButton)findViewById(R.id.GasValue);
        live=(ImageButton)findViewById(R.id.LiveStream);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image=new Intent(MainPage.this,CarControl.class);
                startActivity(image);
            }
        });
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image=new Intent(MainPage.this,Image.class);
                startActivity(image);
            }
        });
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image=new Intent(MainPage.this,MainActivity.class);
                startActivity(image);
            }
        });

    }

}