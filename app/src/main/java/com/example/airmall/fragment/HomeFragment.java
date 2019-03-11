package com.example.airmall.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.activity.SearchActivity;


public class HomeFragment extends Fragment {

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView= view.findViewById(R.id.tv_home);
        textView.setText("home");

        initView(view);
        return view;
    }

    private void initView(View view) {
        editText=view.findViewById(R.id.et_toolbar);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
