package com.example.kash.cstimemanagement;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                    //display splash screen for 3000 milliseconds
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    //Intent intent = new Intent(SplashScreen.this,IntroSlides.class);
                    //temporarily disable intro slides til complete
                    Intent intent = new Intent(SplashScreen.this,IntroSlides.class);
                    startActivity(intent);
                    //start main activity once sleep time is up
                }

            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
