package com.example.airmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.adapter.NavigationPagerAdapter;
import com.example.airmall.fragment.MineFragment;
import com.example.airmall.utils.SPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private TextView txt_toolbar;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_home;
    private RadioButton rb_category;
    private RadioButton rb_star;
    private RadioButton rb_mine;
    private ViewPager viewPager;
    private NavigationPagerAdapter pagerAdapter;

    //几个代表页面的常量
    public static final int PAGE_HOME = 0;
    public static final int PAGE_CATEGORY = 1;
    public static final int PAGE_STAR = 2;
    public static final int PAGE_MINE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        if (SPUtils.get(this, "userId", "").equals("")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        initView();
        int mainId = getIntent().getIntExtra("mainId", 0);
        switch (mainId) {
            case 0:
                rb_home.setChecked(true);
                break;
            case 1:
                rb_category.setChecked(true);
                break;
            case 2:
                rb_star.setChecked(true);
                break;
            case 3:
                rb_mine.setChecked(true);
                break;
        }
    }

    private void initView() {
        txt_toolbar = findViewById(R.id.txt_toolbar);
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rb_home = findViewById(R.id.rb_home);
        rb_category = findViewById(R.id.rb_category);
        rb_star = findViewById(R.id.rb_star);
        rb_mine = findViewById(R.id.rb_mine);
        viewPager = findViewById(R.id.viewpager);

        pagerAdapter = new NavigationPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        rg_tab_bar.setOnCheckedChangeListener(this);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                viewPager.setCurrentItem(PAGE_HOME);
                break;
            case R.id.rb_category:
                viewPager.setCurrentItem(PAGE_CATEGORY);
                break;
            case R.id.rb_star:
                viewPager.setCurrentItem(PAGE_STAR);
                break;
            case R.id.rb_mine:
                viewPager.setCurrentItem(PAGE_MINE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_HOME:
                    rb_home.setChecked(true);
                    break;
                case PAGE_CATEGORY:
                    rb_category.setChecked(true);
                    break;
                case PAGE_STAR:
                    rb_star.setChecked(true);
                    break;
                case PAGE_MINE:
                    rb_mine.setChecked(true);
                    break;
            }
        }
    }
}
