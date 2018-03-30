package com.example.xshowroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by CUBASTION on 30-03-2018.
 */

public class TabsPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
    private ArrayList<String> titles=new ArrayList<String>();

    public TabsPageAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);

    }
    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
       return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    public void AddFragment(Fragment fragment,String title)
    {
        fragments.add(fragment);
        titles.add(title);
    }
}
