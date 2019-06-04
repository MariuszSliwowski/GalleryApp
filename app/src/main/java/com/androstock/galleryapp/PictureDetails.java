package com.androstock.galleryapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PictureDetails extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_testowe, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        TextView dane;

        dane=getView().findViewById(R.id.textView6);
        dane.setText(getArguments().getString("path"));
        dane=getView().findViewById(R.id.textView5);
        dane.setText(getArguments().getString("name"));
        dane=getView().findViewById(R.id.textView11);
        dane.setText(getArguments().getString("size"));

    }



}
