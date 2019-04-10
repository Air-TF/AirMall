package com.example.airmall.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.activity.LoginActivity;
import com.example.airmall.utils.SPUtils;


public class MineFragment extends Fragment {
    private TextView tv_id;
    private TextView tv_email;
    private Button btn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_id = view.findViewById(R.id.tv_id);
        tv_email = view.findViewById(R.id.tv_email);
        btn_logout = view.findViewById(R.id.btn_logout);

        tv_id.setText(SPUtils.get(getActivity(), "userId", "").toString());
        tv_email.setText(SPUtils.get(getActivity(), "account", "").toString());

        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            SPUtils.clear(getActivity());
            startActivity(intent);
            getActivity().finish();
        });
    }

}
