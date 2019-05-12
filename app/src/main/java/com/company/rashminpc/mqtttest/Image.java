package com.company.rashminpc.mqtttest;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.L;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Image extends AppCompatActivity  {
    RelativeLayout rel;
    ImageView ımage_view;
    WebView streamView;
    Bitmap bm;
    private TextToSpeech textToSpeech;
    //SurfaceView surfaceView;
    private Bitmap bitmap;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    TextView textView;
    //GraphicOverlay graphicOverlay;
    String text;
    Button btnCapture;
    final int RequestCameraPermissionID = 1001;
    String videoUrL="http://172.20.10.5:8000/index.html";
    //String videoUrL="https://stackoverflow.com/questions/20942623/which-can-replace-capturepicture-function/20963109#20963109";
    // String videoUrL="https://r3---sn-nv47lnlz.googlevideo.com/videoplayback?txp=5431432&key=cms1&clen=15961797&requiressl=yes&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&gir=yes&expire=1557076642&lmt=1539980028600594&c=WEB&ei=QsbOXKLeCLO3z7sP3raJwAI&fvip=3&dur=662.395&pl=22&id=o-ANtvh8gLYm7cLrOUP6B0YmzBAgmORMEGooWCS87YLf7n&source=youtube&signature=7721694E27CA5A98D85AA41F53C1C9CEEE08FEF0.287165F3C9FD83F75F3EC678BED7E8D1DB6BA099&mime=video%2Fmp4&ip=139.255.17.2&itag=18&ratebypass=yes&ipbits=0&video_id=mCTVkmV5bRY&title=elaki+sesleri+hece+%C3%A7al%C4%B1%C5%9Fmas%C4%B1+hece+okuma&rm=sn-4pgnuhxqp5-jb3r7s,sn-npolr7s&req_id=ad6e3d30447aa3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=78.176.179.157&mm=30&mn=sn-nv47lnlz&ms=nxu&mt=1557054488&mv=u";
    //String videoUrL="https://r3---sn-nv47lnlz.googlevideo.com/videoplayback?requiressl=yes&pl=23&c=WEB&ratebypass=yes&ei=8r3NXNThE6HOz7sPxMy4wAY&id=o-APZ_v-iFgHd3TeWmoN3eQgseAXEU6Sm-RqionlSb3bB1&ipbits=0&key=cms1&ip=182.52.74.89&txp=5531432&clen=18126034&lmt=1540455413591952&dur=260.852&fvip=3&gir=yes&expire=1557008978&itag=18&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&beids=9466586&source=youtube&mime=video%2Fmp4&signature=82AB1C5AA8B85C99B62EC94589E4F28AB768C78D.4A148CDCB0473E7C859319A1FB608ADFA10C6F79&video_id=1ZeiF1muZig&title=Bora+Duran+-+Sana+Do%C4%9Fru&rm=sn-uvu-c3367z,sn-30al67s&fexp=9466586&req_id=4fef63c888f3a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=78.176.179.157&mm=30&mn=sn-nv47lnlz&ms=nxu&mt=1556990546&mv=m";
    // String videoUrL="https://r5---sn-nv47lnsr.googlevideo.com/videoplayback?id=o-AIS2XI4EJNBcZZdY9rK-DWSIdlocBo_eiuxCdcQojasa&itag=18&source=youtube&requiressl=yes&pl=20&ei=G23MXN2-HIWkz7sP5tC1yAg&mime=video%2Fmp4&gir=yes&clen=18299213&ratebypass=yes&dur=368.779&lmt=1556894563488278&fvip=5&c=WEB&txp=4531432&ip=27.116.51.119&ipbits=0&expire=1556922747&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&signature=10BF7EF08A4FD211C579F37269E7CD6B20FDA469.69FBF37ADA4821D5C65529C9414E029CC3344E22&key=cms1&video_id=BT-fsmWjcuo&title=Avengers+Endgame-+Alternatif+Son+ve+Silinmi%C5%9F+Sahneler&rm=sn-bu2a-5hql7l,sn-cvhsz76&req_id=552ac4642aeca3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=212.175.210.190&mm=29&mn=sn-nv47lnsr&ms=rdu&mt=1556910481&mv=m";
    //String videoUrL="http://192.168.43.176:8000/index.html";
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        // graphicOverlay=(GraphicOverlay)findViewById(R.id.graphic_overlay);
        btnCapture=(Button)findViewById(R.id.btn_capture);
        rel=(RelativeLayout)findViewById(R.id.rel);
        ımage_view=(ImageView)findViewById(R.id.image_view);
        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if(result==TextToSpeech.LANG_MISSING_DATA
                            || result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not supported!");
                    }else{
                        System.out.print("Listen Enabled");
                    }
                }else{
                    Log.e("TTS","Initiliaze failed!");
                }
            }
        });
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //surfaceView=(SurfaceView)findViewById(R.id.surface);
        // surfaceHolder=surfaceView.getHolder();
        //surfaceHolder.addCallback(Image.this);
      /*  mediaPlayer=new MediaPlayer();
        mediaPlayer.setDisplay(surfaceHolder);
        try {
            mediaPlayer.setDataSource(videoUrL);
            mediaPlayer.prepare();
            // mediaPlayer.setOnPreparedListener(Image.this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();
        }catch (IOException e){
            e.printStackTrace();
        }*/
        streamView = (WebView)findViewById(R.id.streamview);
        Uri video = Uri.parse(videoUrL);
        streamView.loadUrl(videoUrL);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create bitmap screen capture
/*
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    v1.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);*/
                //  bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(streamView);


                bitmap=getBitmapFromWebView(streamView);
                ımage_view.setImageBitmap(bitmap);
                //ımage_view.setVisibility(View.VISIBLE);
                textRecognize(bitmap);
            }
        });

        //  playStream();

    }
    public Bitmap getBitmapFromWebView(View v) {
        streamView.setDrawingCacheEnabled(true);
        streamView.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        try {
            FileOutputStream fos = openFileOutput("test.png", Context.MODE_PRIVATE);
            //fos = new FileOutputStream( "/sdcard/"  + "page.jpg" );
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
            }
        } catch (Exception e) {
            System.out.println("-----error--" + e);
        }
        return bitmap;
    }

    public void textRecognize(Bitmap photo){
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(photo);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        Task<FirebaseVisionText> result =
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                for(FirebaseVisionText.TextBlock block:firebaseVisionText.getTextBlocks()){
                                    Rect rect=block.getBoundingBox();
                                    Point[] corner=block.getCornerPoints();
                                    text=block.getText();
                                    Toast.makeText(Image.this,"t: "+text,Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder1=new AlertDialog.Builder(Image.this);
                                    builder1.setTitle("Your Text");
                                    builder1.setMessage("Recognized Text: "+ text);
                                    builder1.setCancelable(false);
                                    builder1.setPositiveButton("Repeat", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    builder1.setNegativeButton("Listen", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            speak();
                                        }
                                    });
                                    builder1.show();
                                   /* for(FirebaseVisionText.Line line :block.getLines()){
                                        for (FirebaseVisionText.Element element:line.getElements()){
                                            //Toast.makeText(Image.this,"Element: "+element.getText(),Toast.LENGTH_SHORT).show();
                                        }
                                    }*/
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Image.this,"Failed to Recognize",Toast.LENGTH_SHORT).show();
                                    }
                                });
    }

    private void speak(){

        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
       /* if(text.contains("Google Open")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.tr/"));
            startActivity(intent);
        }
        if(text.contains("Işık University")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.isikun.edu.tr/"));
            startActivity(intent);
        }
        if(text.contains("Call")){
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:02167102873"));
            startActivity(callIntent);
        }*/
    }
    @Override
    protected void onDestroy() {
        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }
    /*
    public void recognizeText(Bitmap bitmap){
        FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionCloudTextRecognizerOptions options=
                new FirebaseVisionCloudTextRecognizerOptions.Builder()
                        .setLanguageHints(Arrays.asList("en"))
                        .build();
        FirebaseVisionTextRecognizer textRecognizer=FirebaseVision.getInstance()
                .getCloudTextRecognizer(options);
        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        drawTextResult(firebaseVisionText);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void drawTextResult(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blocks= firebaseVisionText.getTextBlocks();
        if(blocks.size()==0){
            Toast.makeText(this,"No Text Found",Toast.LENGTH_SHORT).show();
           return;
        }
           graphicOverlay.clear();
        for (int i=0;i<blocks.size();i++){
             List<FirebaseVisionText.Line> lines=blocks.get(i).getLines();
             for (int j=0;j<lines.size();j++){
                 List<FirebaseVisionText.Element> elements=lines.get(j).getElements();
                 for (int k=0;k<elements.size();k++){
                     TextGraphic textGraphic=new TextGraphic(graphicOverlay,elements.get(k));
                     graphicOverlay.add(textGraphic);
                 }
             }
        }
    }*/

    private void playStream(){
        mediaController = new MediaController(this);
        mediaController.setAnchorView(streamView);
        streamView.loadUrl(videoUrL);
       /* streamView.setMediaController(mediaController);
        streamView.setVideoURI(Uri.parse(videoUrL));
        streamView.requestFocus();
        streamView.start();*/
    }

  /*  @Override
    public void onPrepared(MediaPlayer mp) {
mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
mediaPlayer=new MediaPlayer();
mediaPlayer.setDisplay(surfaceHolder);
try {
mediaPlayer.setDataSource(videoUrL);
mediaPlayer.prepare();
mediaPlayer.setOnPreparedListener(Image.this);
mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

}catch (IOException e){
e.printStackTrace();
}
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        streamView.stopPlayback();
    }*/
}
