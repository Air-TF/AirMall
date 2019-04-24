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
import android.widget.Button;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.activity.LoginActivity;
import com.example.airmall.network.Impl.UserServiceImpl;
import com.example.airmall.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class MineFragment extends Fragment {
    private TextView tv_id;
    private TextView tv_name;
    private TextView tv_email;
    private Button btn_logout;

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
        btn_logout = view.findViewById(R.id.btn_logout);

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

        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            SPUtils.clear(getActivity());
            startActivity(intent);
            getActivity().finish();
        });
    }

}
