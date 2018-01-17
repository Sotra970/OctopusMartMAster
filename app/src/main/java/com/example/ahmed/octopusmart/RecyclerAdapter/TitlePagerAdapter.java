package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Mohab on 4/8/2017.
 */

public class TitlePagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();

    public TitlePagerAdapter(FragmentManager fm)
    {
        super(fm);
    }


    public void addFragment(Fragment fragment  , String title) {
        titleList.add(title) ;
        fragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titleList.get(position);
    }
}
