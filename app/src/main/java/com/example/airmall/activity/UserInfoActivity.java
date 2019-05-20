package com.example.airmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.airmall.R;
import com.example.airmall.utils.SPUtils;
import com.example.airmall.weight.section.SectionTextItemView;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserInfoActivity extends AppCompatActivity {

    private Button btn_logout;
    private SectionTextItemView tv_userId;
    private SimpleDraweeView img_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
    }

    private void initView() {
        btn_logout = findViewById(R.id.btn_logout);
        tv_userId = findViewById(R.id.tv_userId);
        img_account= findViewById(R.id.img_account);

        tv_userId.setContextTxt((String) SPUtils.get(UserInfoActivity.this, "userId", ""));

        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
            SPUtils.clear(UserInfoActivity.this);
            //清空Activity栈
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
