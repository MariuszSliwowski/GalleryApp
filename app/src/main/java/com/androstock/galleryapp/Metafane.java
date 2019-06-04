package com.androstock.galleryapp;

import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

public class Metafane extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.metafane, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ExifInterface exif=null;
        try {
             exif=new ExifInterface(getArguments().getString("path"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextView dane;

        dane=getView().findViewById(R.id.textDataView);
        dane.setText(exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL));
        dane=getView().findViewById(R.id.textWidthView);
        dane.setText(exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
        dane=getView().findViewById(R.id.textLengthView);
        dane.setText(exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
        dane=getView().findViewById(R.id.oriantView);
        dane.setText(exif.getAttribute(ExifInterface.TAG_ORIENTATION));
        dane=getView().findViewById(R.id.textMakeView);
        dane.setText(exif.getAttribute(ExifInterface.TAG_MAKE));
        dane=getView().findViewById(R.id.byMakeViev);
        dane.setText(exif.getAttribute(ExifInterface.TAG_MODEL));

        dane=getView().findViewById(R.id.GPS1W);
        dane.setText(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
        dane=getView().findViewById(R.id.GPS2W);
        dane.setText(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
        dane=getView().findViewById(R.id.GPS3W);
        dane.setText(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
        dane=getView().findViewById(R.id.GPS4W);
        dane.setText(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));

    }


}
