package com.example.airmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.airmall.R;
import com.example.airmall.utils.SPUtils;
import com.example.airmall.weight.section.SectionTextItemView;

public class SecurityActivity extends AppCompatActivity {

    private SectionTextItemView tv_userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        initView();
    }

    private void initView() {
        tv_userId = findViewById(R.id.tv_userId);

        tv_userId.setContextTxt((String) SPUtils.get(SecurityActivity.this,"userId",""));
    }
}
