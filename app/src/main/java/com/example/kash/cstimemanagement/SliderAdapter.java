package com.example.kash.cstimemanagement;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public int[] images = {R.drawable.intromanage,R.drawable.grouptoproject,R.drawable.swipetocomplete};

    public String[] headings = {"Maximise Productivity","Create Projects","Swipe to complete"};

    public String[] descriptions = {"Create tasks and have them automatically prioritised","Keep groups of tasks organised by grouping them into projects","Keep track of completed tasks by swiping them"};

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }


    public Object instantiateItem(ViewGroup container, int position){

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_layout,container,false);

        TextView heading = (TextView) view.findViewById(R.id.introTitle);
        TextView description = (TextView) view.findViewById(R.id.introDescription);
        ImageView image = (ImageView)view.findViewById(R.id.intro_image);

        heading.setText(headings[position]);
        description.setText(descriptions[position]);
        image.setImageResource(images[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout)object);

    }



}
