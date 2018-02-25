package com.example.kash.cstimemanagement;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IntroSlides extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button NextButton;
    private Button BackButton;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slides);

        mSlideViewPager = (ViewPager) findViewById(R.id.introVp);
        mDotLayout = (LinearLayout) findViewById(R.id.introDots);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        NextButton = (Button) findViewById(R.id.buttonNext);
        BackButton = (Button) findViewById(R.id.buttonBack);

        dots(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        NextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(currentPage == mDots.length -1 ){
                    //Toast.makeText(IntroSlides.this, "last", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(IntroSlides.this,Main2Activity.class));

                }else{
                    mSlideViewPager.setCurrentItem(currentPage + 1);
                    //Toast.makeText(IntroSlides.this, "next", Toast.LENGTH_SHORT).show();
                }

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(currentPage - 1);
            }
        });

    }

    public void dots(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.dotInactive));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextSize(50);
            mDots[position].setTextColor(getResources().getColor(R.color.dotActive));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            dots(position);

            currentPage = position;
            //changes the text and visibility settings of the navigation buttons depending on the current screen

            //on the first screen the back button is disabled
            if(position == 0) {
                NextButton.setEnabled(true);
                BackButton.setEnabled(false);
                BackButton.setVisibility(View.INVISIBLE);

                NextButton.setText("Next");
                BackButton.setText("");
                //on the last screen the next button text changes to "done!" and both are visible
            }else if(position == mDots.length -1){
                NextButton.setEnabled(true);
                BackButton.setEnabled(true);
                BackButton.setVisibility(View.VISIBLE);

                NextButton.setText("Done!");
                BackButton.setText("Back");

                //on every other screen both buttons are visible/useable and have the default next/back text
            } else{
                NextButton.setEnabled(true);
                BackButton.setEnabled(true);
                BackButton.setVisibility(View.VISIBLE);

                NextButton.setText("Next");
                BackButton.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
