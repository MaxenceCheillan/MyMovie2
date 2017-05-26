package com.project.movie.mymovie.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.movie.mymovie.TabFragmentSeries.TabFragmentSeries1;
import com.project.movie.mymovie.TabFragmentSeries.TabFragmentSeries2;
import com.project.movie.mymovie.TabFragmentSeries.TabFragmentSeries3;
import com.project.movie.mymovie.TabFragmentSeries.TabFragmentSeries4;

/**
 * Created by Maxence Cheillan on 06/06/2016.
 */
public class PageAdapterSeries extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapterSeries (FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentSeries1 tab1 = new TabFragmentSeries1();
                return  tab1;
            case 1:
                TabFragmentSeries2 tab2 = new TabFragmentSeries2();
                return tab2;
            case 2:
                TabFragmentSeries3 tab3 = new TabFragmentSeries3();
                return tab3;
            case 3:
                TabFragmentSeries4 tab4 = new TabFragmentSeries4();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
