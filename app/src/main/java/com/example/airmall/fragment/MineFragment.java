package com.example.airmall.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.activity.AboutActivity;
import com.example.airmall.activity.PrivacyActivity;
import com.example.airmall.activity.SecurityActivity;
import com.example.airmall.activity.SuggestionActivity;
import com.example.airmall.activity.UserInfoActivity;
import com.example.airmall.network.Impl.UserServiceImpl;
import com.example.airmall.utils.SPUtils;
import com.example.airmall.weight.section.SectionTextItemView;

import org.json.JSONObject;


public class MineFragment extends Fragment {
    private TextView tv_id;
    private TextView tv_name;
    private TextView tv_email;
    private RelativeLayout rl_user_info; //用户信息
    private SectionTextItemView stv_security;//帐号与安全
    private SectionTextItemView stv_privacy;//隐私
    private SectionTextItemView stv_about;//关于我们
    private SectionTextItemView stv_suggestion;//意见反馈

    private String userId;
    private String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_id = view.findViewById(R.id.tv_id);
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        rl_user_info = view.findViewById(R.id.rl_user_info);
        stv_security = view.findViewById(R.id.stv_security);
        stv_privacy = view.findViewById(R.id.stv_privacy);
        stv_about = view.findViewById(R.id.stv_about);
        stv_suggestion = view.findViewById(R.id.stv_suggestion);

        userId = SPUtils.get(getActivity(), "userId", "").toString();
        tv_id.setText(userId);
        tv_email.setText(SPUtils.get(getActivity(), "account", "").toString());

        UserServiceImpl.getUserService().getUser(userId, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    userName = data.getString("name");
                    tv_name.setText(userName);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        rl_user_info.setOnClickListener((view1) -> {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        });

        stv_security.setOnClickListener((view1) -> {
            Intent intent = new Intent(getActivity(), SecurityActivity.class);
            startActivity(intent);
        });
        stv_privacy.setOnClickListener((view1) -> {
            Intent intent = new Intent(getActivity(), PrivacyActivity.class);
            startActivity(intent);
        });
        stv_about.setOnClickListener((view1) -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });
        stv_suggestion.setOnClickListener((view1) -> {
            Intent intent = new Intent(getActivity(), SuggestionActivity.class);
            startActivity(intent);
        });
    }

}
