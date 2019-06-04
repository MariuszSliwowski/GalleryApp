package com.androstock.galleryapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.GestureDetector;



import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.CustomViewHolder> {

   private final List<String> mFileList;
   public final FragmentActivity mActivity;
    private FragmentPagerAdapter adapter;
    private ViewPager viewPage;
    private GestureDetector gestureDetector;
    private boolean listCHeck=false;
    private double scala=1;

    public GalleryAdapter(FragmentActivity activity, List<String> fileList) {
       mActivity = activity;
       mFileList = fileList;
   }

   @Override
   public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_row
               , parent, false);
       GalleryAdapter.CustomViewHolder holder=  new GalleryAdapter.CustomViewHolder(view);



       return holder;
   }

   @SuppressLint("ClickableViewAccessibility")
   @Override
   public void onBindViewHolder(CustomViewHolder holder, final int position) {

       if(listCHeck) {
           DisplayMetrics displayMetrics = new DisplayMetrics();
           mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
           int height = displayMetrics.heightPixels;
           int width = displayMetrics.widthPixels;


           LinearLayout.LayoutParams f = new LinearLayout.LayoutParams((int)(width*scala), (int)(height * 2 / 3*scala));


           holder.imageResource.setLayoutParams(f);
       }


       Glide
               .with(mActivity)
               .load(mFileList.get(position))
               .into(holder.imageResource);


       final int itemPosition = holder.getAdapterPosition();

      holder.imageResource.setOnClickListener(new View.OnClickListener() {


           @Override
           public void onClick(View v) {



               Intent intent = new Intent(mActivity, ImagePagerView.class);
               intent.putStringArrayListExtra("paths", (ArrayList<String>) mFileList);
               intent.putExtra("path",mFileList.get(position));
               intent.putExtra("startPosition",position);
               mActivity.startActivity(intent);
               Toast.makeText(mActivity, mFileList.get(itemPosition), Toast.LENGTH_SHORT).show();



           }

       });


   }
    public void setListView(){
        listCHeck=true;
    }
    public void setGridView(){
        listCHeck=false;
    }
    public void setScala(double d){
        scala=d;
    }
   @Override
   public int getItemCount() {
       return mFileList.size();
   }



   static class CustomViewHolder extends RecyclerView.ViewHolder {
       final ImageView imageResource;
       CustomViewHolder(View itemView) {
           super(itemView);
           this.imageResource = (ImageView) itemView.findViewById(R.id.image_resource);


         //  LinearLayout.LayoutParams f=new LinearLayout.LayoutParams(200,200);
          // imageResource.setLayoutParams(f);
       }
   }



}
