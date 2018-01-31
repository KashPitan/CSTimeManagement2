package com.example.kash.cstimemanagement;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Kash on 29/01/2018.
 */

public class SliderAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public String[] headings = {"HEAD1","HEAD2","HEAD3"};

    public String[] descriptions = {"DESC1","DESC2","DESC3"};

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }


    public Object instantiateItem(ViewGroup container, int position){

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_layout,container,false);

        TextView heading = (TextView) view.findViewById(R.id.introTitle);
        TextView description = (TextView) view.findViewById(R.id.introDescription);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }



}
