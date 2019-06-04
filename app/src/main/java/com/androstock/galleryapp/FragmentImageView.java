package com.androstock.galleryapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class  FragmentImageView extends Fragment  {

	private String itemData;
	 ImageView ivImage;
//	private ScaleGestureDetector mScaleGestureDetector;
	private float mScaleFactor = 1.0f;

	public static FragmentImageView newInstance() {
		FragmentImageView f = new FragmentImageView();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.imageview, container, false);
		ivImage = (ImageView) root.findViewById(R.id.ivImageView);
	//	View view2=root.findViewById(R.id.relative_container);
	//	view2.setOnTouchListener(this);
	//	mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());


		setImageInViewPager();
		return root;
	}

	public void setImageList(String integer) {
		this.itemData = integer;
	}

	public void setImageInViewPager() {



		Glide.with(this)
				.load(itemData)
				.into(ivImage);



	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

	}

	/*@Override
	public boolean onTouch(View v, MotionEvent event) {
		mScaleGestureDetector.onTouchEvent(event);
		return true;
	}
*/
	/*private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector scaleGestureDetector){
			System.out.println("jrwa");
			mScaleFactor *= scaleGestureDetector.getScaleFactor();
			mScaleFactor = Math.max(1.0f,
					Math.min(mScaleFactor, 2.0f));
			ivImage.setScaleX(mScaleFactor);
			ivImage.setScaleY(mScaleFactor);
			System.out.println(mScaleFactor);

			return true;
		}
	}
	*/
}
