package com.htetznaing.callernumbertalker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AdView banner;
    AdRequest adRequest;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();

        adRequest = new AdRequest.Builder().build();
        banner = (AdView) findViewById(R.id.adView);
        banner.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1325188641119577/9224527870");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAD();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                loadAD();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                loadAD();
            }
        });


        sharedPreferences = getSharedPreferences("myFile",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        textView = (TextView) findViewById(R.id.tvRunning);
        button = (Button) findViewById(R.id.btn);

        boolean b = sharedPreferences.getBoolean("what",true);

        if (b==false){
            button.setText("Start");
            textView.setText("Caller Number Talker : Stopped\nClick Start to Run it!");
        }else{
            button.setText("Stop");
            textView.setText("Caller Number Talker : Running");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAD();
                boolean b = sharedPreferences.getBoolean("what",true);
                if (b==true){
                    b = false;
                    editor.putBoolean("what",b);
                    editor.commit();
                    editor.apply();
                    button.setText("Start");
                    textView.setText("Caller Number Talker : Stopped\nClick Start to Run it!");
                }else{
                    b = true;
                    editor.putBoolean("what",b);
                    editor.commit();
                    editor.apply();
                    button.setText("Stop");
                    textView.setText("Caller Number Talker : Running");
                }
            }
        });
    }

    private boolean checkPermissions() {
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), 5217);
            Log.d("TAG","Permission"+"\n"+String.valueOf(false));
            return false;
        }
        Log.d("Permission","Permission"+"\n"+String.valueOf(true));
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 5217: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    checkPermissions();
                    Toast.makeText(this, "You need to Allow READ Phone State Permission!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void loadAD(){
        if (!interstitialAd.isLoaded()){
            interstitialAd.loadAd(adRequest);
        }
    }

    public void showAD(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }else{
            interstitialAd.loadAd(adRequest);
        }
    }

    public void dev(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("fb://profile/100011339710114"));
            startActivity(intent);
        }catch (Exception e){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://m.facebook.com/KHtetzNaing"));
            startActivity(intent);
        }
    }

    public void share(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Myanmar Caller Number Talker Download Free at Google Play Store : https://play.google.com/store/apps/details?id=com.htetznaing.callernumbertalker");
        startActivity(new Intent(Intent.createChooser(intent,"Myanmar Font Styles App")));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention!");
        builder.setMessage("Do you want to Exit ?");
        builder.setIcon(R.drawable.icon);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showAD();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showAD();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
