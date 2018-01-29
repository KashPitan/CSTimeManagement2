package com.example.kash.cstimemanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Kash on 31/12/2017.
 */

public class SwipeActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_intro);

        ViewPager viewPager = (ViewPager)findViewById(R.id.swipe_intro);

        SwipeIntro swipeIntro = new SwipeIntro(getSupportFragmentManager());
        viewPager.setAdapter(swipeIntro);
        viewPager.setOffscreenPageLimit(1);

        viewPager.setCurrentItem(0);

       /* fabImageButton.setOnClickListener(new View.onClickListener(){

        }*/

    }
}
