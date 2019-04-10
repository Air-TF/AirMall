package com.example.airmall.adapter;

import com.example.airmall.fragment.StarFragment;
import com.example.airmall.fragment.CategoryFragment;
import com.example.airmall.fragment.HomeFragment;
import com.example.airmall.activity.MainActivity;
import com.example.airmall.fragment.MineFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NavigationPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private StarFragment starFragment;
    private MineFragment mineFragment;

    public NavigationPagerAdapter(FragmentManager fm) {
        super(fm);
        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        starFragment = new StarFragment();
        mineFragment = new MineFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_HOME:
                fragment = homeFragment;
                break;
            case MainActivity.PAGE_CATEGORY:
                fragment = categoryFragment;
                break;
            case MainActivity.PAGE_STAR:
                fragment = starFragment;
                break;
            case MainActivity.PAGE_MINE:
                fragment = mineFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }


}
