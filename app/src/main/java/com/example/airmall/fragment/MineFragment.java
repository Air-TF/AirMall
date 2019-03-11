package com.example.airmall.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.utils.SPUtils;


public class MineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        TextView textView = view.findViewById(R.id.tv_mine);
        textView.setText((String)SPUtils.get(getActivity(),"userId",""));
        return view;
    }

}
