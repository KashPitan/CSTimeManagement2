package com.example.kash.cstimemanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Kash on 31/12/2017.
 */

public class SwipePage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view;
        Bundle bundle=getArguments();
        int pageNumber = bundle.getInt("pageNumber");

        view = inflater.inflate(R.layout.swipe_intro,container,false);
        TextView textView = (TextView)view.findViewById(R.id.vp_tv_2);
        textView.setText(Integer.toString(pageNumber));

        return view;




        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
