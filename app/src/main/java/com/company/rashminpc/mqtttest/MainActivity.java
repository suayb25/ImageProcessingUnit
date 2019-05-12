package com.company.rashminpc.mqtttest;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ListView gasList;
    int previous=0;
    ArrayList<String> a;
    ArrayAdapter<String> arrayAdapter;
    LineGraphSeries<DataPoint> series;//graph data series
     EditText usernameInput;
     EditText passwordInput;
     EditText passwordInput2;
    SimpleDateFormat sdf1,
            sdf2,
            sdf3;
    public  static  final String Channel_1_ID="channel1";
    public  static  final String Channel_2_ID="channel2";
    private NotificationManagerCompat notificationManagerCompat;

    Date tarih;//telefon tarihi
    GraphView graph;
    double h,k;
    int l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gasList=(ListView) findViewById(R.id.gasList);
        View header = (View)getLayoutInflater().inflate(R.layout.header,null);
        gasList.addHeaderView(header);
         a=new ArrayList<>();
          arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, a );
        gasList.setAdapter(arrayAdapter);
        sdf2 = new SimpleDateFormat("yyyy-MM-dd");

         double x,y;
         x=-5.0;
         graph=(GraphView)findViewById(R.id.graph);
         series=new LineGraphSeries<DataPoint>();
        notificationManagerCompat=NotificationManagerCompat.from(this);
        createNotification();
         /*for(int i=0;i<500;i++){
             x=x+0.1;
             y=Math.sin(x);
             series.appendData(new DataPoint(x,y),true,700);

         }*/
         //graph.addSeries(series);
        connect();
    }

    public void connect(){
      //tcp://m23.cloudmqtt.com:10876
        //xytpeihn
        //-7vWRxt0ZSaX

           String clientId = MqttClient.generateClientId();
           final MqttAndroidClient client =
                   new MqttAndroidClient(this.getApplicationContext(), "tcp://m23.cloudmqtt.com:10876",
                           clientId);

           MqttConnectOptions options = new MqttConnectOptions();
           options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
           options.setCleanSession(false);
           options.setUserName("xytpeihn");
           options.setPassword("-7vWRxt0ZSaX".toCharArray());


           try {
               IMqttToken token = client.connect(options);
               //IMqttToken token = client.connect();
               token.setActionCallback(new IMqttActionListener() {
                   @Override
                   public void onSuccess(IMqttToken asyncActionToken) {
                       // We are connected
                       Log.d("file", "onSuccess");
                       //publish(client,"payloadd");
                       subscribe(client,"Gas");
                       client.setCallback(new MqttCallback() {

                           TextView tt = (TextView) findViewById(R.id.tt);
                           @Override
                           public void connectionLost(Throwable cause) {

                           }
                           @Override
                           public void messageArrived(String topic, MqttMessage message) throws Exception {
                               Log.d("file", message.toString());

                               if (topic.equals("Gas")){
                                   tt.setText(message.toString());
                                   tarih = new Date();
                                   sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");

                                   if(previous!=0){//bir önceki verinin yüklü olup olmadığına bakıyourz
                                       l= Integer.parseInt(message.toString());//yeni gelen değer

                                       a.add("Date: "+sdf1.format(tarih) + " Gas: "+message.toString()+" Val Diff: " + (l-previous));//arraylist e verileri ekliyoruz.
                                   }else{
                                       a.add("Date: "+sdf1.format(tarih) + " Gas: "+message.toString());//arraylist e verileri ekliyoruz.
                                   }
                                   gasList.setAdapter(arrayAdapter);
                                   System.out.println("değer gwlsiii");
                                   previous=Integer.parseInt(message.toString());//bir önceki
                                   h=l;
                                   //h=h+100;
                                   k=k+1;
                                   series.appendData(new DataPoint(k,h),true,500);
                                   graph.addSeries(series);
                                   if(l>600){
                                       sendOnChannel1();
                                   }
                               }

                           }
                           @Override
                           public void deliveryComplete(IMqttDeliveryToken token) {

                           }
                       });
                   }

                   @Override
                   public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                       // Something went wrong e.g. connection timeout or firewall problems
                       Log.d("file", "onFailure");

                   }
               });
           } catch (MqttException e) {
               e.printStackTrace();
           }


    }

    public void publish(MqttAndroidClient client, String payload){
        String topic = "foo/bar";
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(MqttAndroidClient client , String topic){
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
        private void createNotification(){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel channel1=new NotificationChannel(
                        Channel_1_ID,
                        "Oy Bildirim",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel1.setDescription("Oy Bildirim");
                NotificationChannel channel2=new NotificationChannel(
                        Channel_2_ID,
                        "Oy Bildirim 2",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel2.setDescription("Oy Bildirim 2");

                NotificationManager manager=getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel1);
                manager.createNotificationChannel(channel2);
            }
        }

    public void sendOnChannel1(){
        Notification notification=new NotificationCompat.Builder(MainActivity.this,Channel_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Dangerous")
                .setContentText("Gas Value exceeds the critical value")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.plucky))
                .build();
        notificationManagerCompat.notify(new Random().nextInt(),notification);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Profile:
                    final AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                    builder1.setTitle("Enter Your MQTT Database İnfo");
                    builder1.setCancelable(false);
                    LayoutInflater inflater=MainActivity.this.getLayoutInflater();
                    //this is what I did to added the layout to the alert dialog
                    View layout=inflater.inflate(R.layout.dialog,null);
                    builder1.setView(layout);
                     usernameInput=(EditText)layout.findViewById(R.id.dialogusername);
                     passwordInput=(EditText)layout.findViewById(R.id.dialogpassword);
                     passwordInput2=(EditText)layout.findViewById(R.id.pas);
                   /* final EditText input = new EditText(MainActivity.this);
                    final EditText input2 = new EditText(MainActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input2.setLayoutParams(lp);*/
                    //builder1.setView(input);
                    builder1.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder1.show();
                    break;
            }

        return super.onOptionsItemSelected(item);
    }


}


