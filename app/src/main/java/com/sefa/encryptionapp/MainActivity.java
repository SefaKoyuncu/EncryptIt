package com.sefa.encryptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FullScreen();

        Looper l=Looper.getMainLooper();
        Handler h=new Handler(l);
        h.postDelayed(new Runnable()
                      {
                          @Override public void run(){
                              Intent intent=new Intent(MainActivity.this,BaseActivity.class);
                              startActivity(intent);
                              finish();
                          }
                      }
                ,3600);
    }

    public void FullScreen()
    {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}