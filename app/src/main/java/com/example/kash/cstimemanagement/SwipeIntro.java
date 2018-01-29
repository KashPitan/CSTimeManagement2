package com.example.kash.cstimemanagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Kash on 31/12/2017.
 */

public class SwipeIntro extends FragmentStatePagerAdapter {

    public SwipeIntro(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment pageFragment = new SwipePage();
        Bundle bundle = new Bundle();
        bundle.putInt("pagenumber",position + 1);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public int getCount() {//number of pages
        return 3;
    }

}
